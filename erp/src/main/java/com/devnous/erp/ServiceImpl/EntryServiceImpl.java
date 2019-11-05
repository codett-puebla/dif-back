package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Entry;
import com.devnous.erp.Entity.EntryDetail;
import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.EntryRepository;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Service.EntryService;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("entryService")
public class EntryServiceImpl implements EntryService {
    @Autowired
    @Qualifier("entryRepository")
    EntryRepository entryRepository;
    private String clase = Entry.class.getSimpleName();

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
    public void createEntry(Entry entry) throws UniqueAttributeException {
        if (entry.getFolio() == null || entry.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = entryRepository.findTopBySeries(entry.getSeries()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            entry.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        Entry exist = entryRepository.findByFolioAndSeries(entry.getFolio(), entry.getSeries());
        if (exist == null) {
            entry.setDate(new Date(System.currentTimeMillis()));
            entry.setStatus(1);
            List<Inventory> inventoriesUpdatables = new ArrayList<>();
            boolean sucessful = true;
            int contDetails = 0;
            for (EntryDetail entryDetail : entry.getEntryDetails()) {
                List<Inventory> inventories = inventoryRepository.findByItemIdAndWarehouseId(entryDetail.getItem().getId(), entry.getWarehouse().getId());
                for (Inventory inventory : inventories) {
                    if (inventory.getQuantity() >= entryDetail.getQuantity()) {
                        inventory.setQuantity(inventory.getQuantity() - (entryDetail.getQuantity() * -1));
                        inventoriesUpdatables.add(inventory);
                        contDetails++;
                        break;
                    }
                }
            }
            if (contDetails != entry.getEntryDetails().size()) {
                sucessful = false;
            }
            if (sucessful) {
                entryRepository.save(entry);

                for (Inventory inventory : inventoriesUpdatables) {
                    inventoryRepository.save(inventory);
                }
                Transaction transaction = null; //creamos las transacciones de la venta
                for (EntryDetail entryDetail : entry.getEntryDetails()) {
                    transaction = makeTransaction(entry, entryDetail);
                    transaction.setQuantity(entryDetail.getQuantity() * -1);
                    transaction.setTypeTransaction(Transaction.ENTRADA);
                    transaction.setReason(Transaction.REASON_ENTRADA_INVENTARIO);

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
    public Entry readEntrys(int id) throws ResourceNotFoundException {
        Entry entry = entryRepository.findById(id);
        if (entry == null) {
            throw new ResourceNotFoundException(clase);
        }
        return entry;
    }

    @Override
    public List<Entry> readAllActiveEntrys() {
        return entryRepository.findByStatus(1);
    }

    @Override
    public List<Entry> readAllRemovedEntrys() {
        return entryRepository.findByStatus(0);
    }

    @Override
    public void updateEntrys(Entry entry) throws ResourceNotFoundException {
        if (entryRepository.findById(entry.getId()) != null) {
            entryRepository.save(entry);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteEntrys(int id) {
        entryRepository.deleteById(id);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void softDeleteEntry(int id) throws ResourceNotFoundException {
        Entry entry = entryRepository.findByIdAndStatus(id, 1); //check if is active yet
        if (entry != null) {
            entry.setStatus(Entry.CANCELADO);
            Transaction transaction = null;
            Inventory inventory = null;
            for (EntryDetail entrysDetail : entry.getEntryDetails()) {
                //logica para la devolucion del producto
                inventory = inventoryRepository.findTopByItemIdAndWarehouseId(entrysDetail.getItem().getId(), entry.getWarehouse().getId());
                inventory.setQuantity(inventory.getQuantity() - entrysDetail.getQuantity());
                inventoryRepository.save(inventory);

                transaction = makeTransaction(entry, entrysDetail);
                transaction.setQuantity(entrysDetail.getQuantity());
                transaction.setTypeTransaction(Transaction.SALIDA);
                transaction.setReason(Transaction.REASON_SALIDA_INVENTARIO);

                transactionService.createTransaction(transaction);
            }
            entryRepository.save(entry);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @SuppressWarnings("Duplicates")
    private Transaction makeTransaction(Entry entry, EntryDetail entryDetail) {
        Transaction transaction;
        transaction = new Transaction();
        transaction.setStatus(1);
        transaction.setIdTransaction(entry.getId());
        transaction.setItem(entryDetail.getItem());
        transaction.setWarehouse(entry.getWarehouse());
        transaction.setMovementStatus(Transaction.ACTIVO);
        return transaction;
    }
}
