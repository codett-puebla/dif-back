package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Entry;
import com.devnous.erp.Entity.SalesHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("entryRepository")
public interface EntryRepository extends JpaRepository<Entry, Serializable> {
    Entry findById(int id);

    Entry findByIdAndStatus(int id, int status);

    List<Entry> findByStatus(int status);

    @Query(value = "Select * from entry as e where e.folio LIKE  '%-%-%' AND e.series = ?1 ORDER BY e.id DESC LIMIT 1", nativeQuery = true)
    Entry findTopBySeries(String series);

    Entry findByFolioAndSeries(String folio, String series);
}
