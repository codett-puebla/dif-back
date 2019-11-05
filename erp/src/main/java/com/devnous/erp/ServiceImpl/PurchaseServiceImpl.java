package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.PurchaseDetail;
import com.devnous.erp.Entity.PurchaseHeader;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Repository.PurchaseRepository;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.PurchaseService;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    @Qualifier("purchaseRepository")
    PurchaseRepository purchaseRepository;
    private String clase = PurchaseHeader.class.getSimpleName();

    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("helperFolioService")
    HelperFolioService helperFolioService;


    @Override
    @SuppressWarnings("Duplicates")
    public void createPurchaseHeader(PurchaseHeader purchaseHeader) {
        if (purchaseHeader.getFolio() == null || purchaseHeader.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = purchaseRepository.findTopBySeries(purchaseHeader.getSeries()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            purchaseHeader.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        PurchaseHeader exist = purchaseRepository.findByFolioAndSeries(purchaseHeader.getFolio(), purchaseHeader.getSeries());
        if (exist == null) {
            purchaseHeader.setDate(new Date(System.currentTimeMillis()));
            purchaseHeader.setStatus(1);
            //logica para descontar de inventario y realizar transacciones
            boolean successful = true;
            List<Inventory> inventoriesUpdatables = new ArrayList<Inventory>();
            int contDetails = 0;
            double total = 0;
            for (PurchaseDetail purchaseDetail : purchaseHeader.getPurchaseDetails()) {
                List<Inventory> inventories = inventoryRepository.findByItemIdAndWarehouseId(purchaseDetail.getItem().getId(), purchaseHeader.getWarehouse().getId());
                for (Inventory inventory : inventories) {
                    if (inventory.getQuantity() >= purchaseDetail.getQuantity()) {
                        inventory.setQuantity(inventory.getQuantity() + purchaseDetail.getQuantity());
                        inventoriesUpdatables.add(inventory);
                        contDetails++;
                        break;
                    }
                }
                //calcular descuentos y totales del detalle de la venta
                purchaseDetail.setTotalPrice(purchaseDetail.getUnitPrice() * purchaseDetail.getQuantity());
                purchaseDetail.setCurrentAmount(purchaseDetail.getQuantity());
                total = purchaseDetail.getTotalPrice();
            }
            if (contDetails != purchaseHeader.getPurchaseDetails().size()) {
                successful = false;
            }
            if (successful) {
                //se guarda primero para obtener el id
                purchaseHeader.setTotal(total);
                purchaseRepository.save(purchaseHeader);
                //guardamos los inventarios que se modificaron
                for (Inventory inventory : inventoriesUpdatables) {
                    inventoryRepository.save(inventory);
                }

                Transaction transaction = null; //creamos las transacciones de la venta
                for (PurchaseDetail purchaseDetail : purchaseHeader.getPurchaseDetails()) {
                    transaction = makeTransaction(purchaseHeader, purchaseDetail);
                    transaction.setQuantity(purchaseDetail.getQuantity() * -1);
                    transaction.setTypeTransaction(Transaction.COMPRA);
                    transaction.setReason(Transaction.REASON_ENTRADA_COMPRA);

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
    public PurchaseHeader readPurchaseHeader(int id) {
        PurchaseHeader purchaseHeader = purchaseRepository.findById(id);
        if (purchaseHeader == null) {
            throw new ResourceNotFoundException(clase);
        }
        return purchaseHeader;
    }

    @Override
    public List<PurchaseHeader> readAllActivePurchaseHeader() {
        return purchaseRepository.findByStatus(1);
    }

    @Override
    public List<PurchaseHeader> readAllRemovedPurchaseHeader() {
        return purchaseRepository.findByStatus(0);
    }

    @Override
    public void updatePurchaseHeader(PurchaseHeader purchaseHeader) {
        if (purchaseRepository.findById(purchaseHeader.getId()) != null) {
            purchaseRepository.save(purchaseHeader);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deletePurchaseHeader(int id) {
        purchaseRepository.deleteById(id);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void softDeletePurchaseHeader(int id) {
        PurchaseHeader purchaseHeader = purchaseRepository.findByIdAndStatus(id, 1); //chek if is active yet
        if (purchaseHeader != null) {
            purchaseHeader.setStatus(PurchaseHeader.CANCELADO);
            Transaction transaction = null;
            Inventory inventory = null;
            for (PurchaseDetail purchaseDetail : purchaseHeader.getPurchaseDetails()) {
                //logica para la devolucion del producto
                inventory = inventoryRepository.findTopByItemIdAndWarehouseId(purchaseDetail.getItem().getId(), purchaseHeader.getWarehouse().getId());
                inventory.setQuantity(inventory.getQuantity() + purchaseDetail.getQuantity() * -1);
                inventoryRepository.save(inventory);

                transaction = makeTransaction(purchaseHeader, purchaseDetail);
                transaction.setQuantity(purchaseDetail.getQuantity());
                transaction.setTypeTransaction(Transaction.VENTA);
                transaction.setReason(Transaction.REASON_ENTRADA_INVENTARIO);

                transactionService.createTransaction(transaction);
            }
            purchaseRepository.save(purchaseHeader);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @SuppressWarnings("Duplicates")
    private static Transaction makeTransaction(PurchaseHeader purchaseHeader, PurchaseDetail purchaseDetail) {
        Transaction transaction;
        transaction = new Transaction();
        transaction.setStatus(1);
        transaction.setIdTransaction(purchaseHeader.getId());
        transaction.setItem(purchaseDetail.getItem());
        transaction.setWarehouse(purchaseHeader.getWarehouse());
        transaction.setMovementStatus(Transaction.ACTIVO);
        return transaction;
    }
}
