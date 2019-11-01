package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.DepartureDetail;
import com.devnous.erp.Entity.Departures;
import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.DeparturesRepository;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Service.DeparturesService;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("departuresService")
public class DeparturesServiceImpl implements DeparturesService {
    @Autowired
    @Qualifier("departureRepository")
    DeparturesRepository departuresRepository;
    private String clase = Departures.class.getSimpleName();

    @Autowired
    @Qualifier("helperFolioService")
    private HelperFolioService helperFolioService;

    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Override
    @SuppressWarnings("Duplicates")
    public void createDeparture(Departures departures) {
        if (departures.getFolio() == null || departures.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = departuresRepository.findTopBySeriesAndIdCompany(departures.getSeries(), departures.getIdCompany()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            departures.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        Departures exist = departuresRepository.findByFolioAndSeriesAndIdCompany(departures.getFolio(), departures.getSeries(), departures.getIdCompany());
        if (exist == null) {
            departures.setDate(new Date(System.currentTimeMillis()));
            departures.setStatus(1);
            List<Inventory> inventoriesUpdatables = new ArrayList<>();
            boolean sucessful = true;
            int contDetails = 0;
            for (DepartureDetail departuresDetail : departures.getDepartureDetails()) {
                List<Inventory> inventories = inventoryRepository.findByItemIdAndWarehouseId(departuresDetail.getItem().getId(), departures.getWarehouse().getId());
                for (Inventory inventory : inventories) {
                    if (inventory.getQuantity() >= departuresDetail.getQuantity()) {
                        inventory.setQuantity(inventory.getQuantity() - departuresDetail.getQuantity());
                        inventoriesUpdatables.add(inventory);
                        contDetails++;
                        break;
                    }
                }
            }
            if (contDetails != departures.getDepartureDetails().size()) {
                sucessful = false;
            }
            if (sucessful) {
                departuresRepository.save(departures);

                for (Inventory inventory : inventoriesUpdatables) {
                    inventoryRepository.save(inventory);
                }
                Transaction transaction = null; //creamos las transacciones de la venta
                for (DepartureDetail departuresDetail : departures.getDepartureDetails()) {
                    transaction = makeTransaction(departures, departuresDetail);
                    transaction.setQuantity(departuresDetail.getQuantity());
                    transaction.setTypeTransaction(Transaction.SALIDA);
                    transaction.setReason(Transaction.REASON_SALIDA_INVENTARIO);

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
    public Departures readDepartures(int id) {
        Departures departures = departuresRepository.findById(id);
        if (departures == null) {
            throw new ResourceNotFoundException(clase);
        }
        return departures;
    }

    @Override
    public List<Departures> readAllActiveDepartures(int idCompany) {
        return departuresRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Departures> readAllRemovedDepartures(int idCompany) {
        return departuresRepository.findByStatusAndIdCompany(0, idCompany);
    }

    @Override
    public void updateDepartures(Departures departures) {
        if (departuresRepository.findById(departures.getId()) != null) {
            departuresRepository.save(departures);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteDepartures(int id) {
        departuresRepository.deleteById(id);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void softDeleteDeparture(int id) {
        Departures departure = departuresRepository.findByIdAndStatus(id, 1); //check if is active yet
        if (departure != null) {
            departure.setStatus(Departures.CANCELADO);
            Transaction transaction = null;
            Inventory inventory = null;
            for (DepartureDetail departureDetail : departure.getDepartureDetails()) {
                //logica para la devolucion del producto
                inventory = inventoryRepository.findTopByItemIdAndWarehouseId(departureDetail.getItem().getId(), departure.getWarehouse().getId());
                inventory.setQuantity(inventory.getQuantity() + departureDetail.getQuantity());
                inventoryRepository.save(inventory);

                transaction = makeTransaction(departure, departureDetail);
                transaction.setQuantity(departureDetail.getQuantity() * -1);
                transaction.setTypeTransaction(Transaction.ENTRADA);
                transaction.setReason(Transaction.REASON_ENTRADA_INVENTARIO);

                transactionService.createTransaction(transaction);
            }
            departuresRepository.save(departure);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @SuppressWarnings("Duplicates")
    private Transaction makeTransaction(Departures departure, DepartureDetail departureDetail) {
        Transaction transaction;
        transaction = new Transaction();
        transaction.setStatus(1);
        transaction.setIdCompany(departure.getIdCompany());
        transaction.setIdTransaction(departure.getId());
        transaction.setItem(departureDetail.getItem());
        transaction.setWarehouse(departure.getWarehouse());
        transaction.setMovementStatus(Transaction.ACTIVO);
        return transaction;
    }
}
