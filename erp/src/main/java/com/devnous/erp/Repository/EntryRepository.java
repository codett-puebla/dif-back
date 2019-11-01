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

    List<Entry> findByStatusAndIdCompany(int status, int idCompany);

    @Query(value = "Select * from Entry as e where e.folio LIKE  '%-%-%' AND e.series = ?1 AND e.idCompany = ?2 ORDER BY e.id DESC LIMIT 1", nativeQuery = true)
    Entry findTopBySeriesAndIdCompany(String series, int idCompany);

    Entry findByFolioAndSeriesAndIdCompany(String folio, String series, int idCompany);
}
