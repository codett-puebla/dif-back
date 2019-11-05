package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.SalesDetail;
import com.devnous.erp.Entity.SalesHeader;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Repository.SaleRepository;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.SaleService;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("saleService")
public class SaleServiceImpl implements SaleService {
    @Autowired
    @Qualifier("saleRepository")
    SaleRepository saleRepository;

    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("helperFolioService")
    HelperFolioService helperFolioService;
    private String clase = SalesHeader.class.getSimpleName();

    @Override
    @SuppressWarnings("Duplicates")
    public void createSalesHeader(SalesHeader salesHeader) throws UniqueAttributeException, TransactionInterruptedException {
        if (salesHeader.getFolio() == null || salesHeader.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = saleRepository.findTopBySeries(salesHeader.getSeries()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            salesHeader.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        SalesHeader exist = saleRepository.findByFolioAndSeries(salesHeader.getFolio(), salesHeader.getSeries());
        if (exist == null) {
            salesHeader.setDate(new Date(System.currentTimeMillis()));
            salesHeader.setStatus(1);
            //logica para descontar de inventario y realizar transacciones
            boolean successful = true;
            List<Inventory> inventoriesUpdatables = new ArrayList<Inventory>();
            int contDetails = 0;
            double totalSale = 0;
            for (SalesDetail salesDetail : salesHeader.getSalesDetails()) {
                List<Inventory> inventories = inventoryRepository.findByItemIdAndWarehouseId(salesDetail.getItem().getId(), salesHeader.getWarehouse().getId());
                int total = 0;
                for (Inventory inventory : inventories) {
                    if (inventory.getQuantity() >= salesDetail.getQuantity()) {
                        inventory.setQuantity(inventory.getQuantity() - salesDetail.getQuantity());
                        inventoriesUpdatables.add(inventory);
                        contDetails++;
                        break;
                    }
                }
                //calcular descuentos y totales del detalle de la venta
                salesDetail.setTotalPrice(salesDetail.getUnitPrice() * salesDetail.getQuantity());
                //agregar iva ,solictud a la api de contabilidad
                double iva = 0;
                salesDetail.setCurrentAmount(salesDetail.getQuantity());
                salesDetail.setUnitImport(salesDetail.getUnitPrice() + iva);
                salesDetail.setTotalImport((salesDetail.getUnitImport() - salesDetail.getDiscount()) * salesDetail.getQuantity());
                totalSale += salesDetail.getTotalImport();
            }
            if (contDetails != salesHeader.getSalesDetails().size()) {
                successful = false;
            }
            if (successful) {
                //se guarda primero para obtener el id
                salesHeader.setSubtotal(totalSale);
                salesHeader.setTotal(totalSale - salesHeader.getDiscount());
                saleRepository.save(salesHeader);
                //guardamos los inventarios que se modificaron
                for (Inventory inventory : inventoriesUpdatables) {
                    inventoryRepository.save(inventory);
                }

                Transaction transaction = null; //creamos las transacciones de la venta
                for (SalesDetail salesDetail : salesHeader.getSalesDetails()) {
                    transaction = makeTransaction(salesHeader, salesDetail);
                    transaction.setQuantity(salesDetail.getQuantity());
                    transaction.setTypeTransaction(Transaction.VENTA);
                    transaction.setReason(Transaction.REASON_SALIDA_VENTA);

                    transactionService.createTransaction(transaction);
                }
            } else {
                throw new TransactionInterruptedException();
            }
        } else {
            throw new UniqueAttributeException("[series,folios,idCompany]");
        }
    }

    @Override
    public SalesHeader readSalesHeader(int id) {
        SalesHeader salesHeader = saleRepository.findById(id);
        if (salesHeader == null) {
            throw new ResourceNotFoundException(clase);
        }
        return salesHeader;
    }

    @Override
    public List<SalesHeader> readAllActiveSalesHeader() {
        return saleRepository.findByStatus(1);
    }

    @Override
    public List<SalesHeader> readAllRemovedSalesHeader() {
        return saleRepository.findByStatus(0);
    }

    @Override
    public void updateSalesHeader(SalesHeader salesHeader) {
        if (saleRepository.findById(salesHeader.getId()) != null) {
            saleRepository.save(salesHeader);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteSalesHeader(int id) {
        saleRepository.deleteById(id);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void softDeleteSales(int id) {
        SalesHeader salesHeader = saleRepository.findByIdAndStatus(id, 1); //chek if is active yet
        if (salesHeader != null) {
            salesHeader.setStatus(SalesHeader.CANCELADO);
            Transaction transaction = null;
            Inventory inventory = null;
            for (SalesDetail salesDetail : salesHeader.getSalesDetails()) {
                //logica para la devolucion del producto
                inventory = inventoryRepository.findTopByItemIdAndWarehouseId(salesDetail.getItem().getId(), salesHeader.getWarehouse().getId());
                inventory.setQuantity(inventory.getQuantity() + salesDetail.getQuantity());
                inventoryRepository.save(inventory);

                transaction = makeTransaction(salesHeader, salesDetail);
                transaction.setQuantity(salesDetail.getQuantity() * -1);
                transaction.setTypeTransaction(Transaction.VENTA);
                transaction.setReason(Transaction.REASON_ENTRADA_INVENTARIO);

                transactionService.createTransaction(transaction);
            }
            saleRepository.save(salesHeader);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override //validate is the folio doesnt exist
    public boolean isAValidFolioAndSeries(String folio, String series) {
        boolean flag;
        flag = saleRepository.findByFolioAndSeries(folio, series) != null;
        return flag;
    }

    @SuppressWarnings("Duplicates")
    private static Transaction makeTransaction(SalesHeader salesHeader, SalesDetail salesDetail) {
        Transaction transaction;
        transaction = new Transaction();
        transaction.setStatus(1);
        transaction.setIdTransaction(salesHeader.getId());
        transaction.setItem(salesDetail.getItem());
        transaction.setWarehouse(salesHeader.getWarehouse());
        transaction.setMovementStatus(Transaction.ACTIVO);
        return transaction;
    }
}
