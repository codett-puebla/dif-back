package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Departures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("departureRepository")
public interface DeparturesRepository extends JpaRepository<Departures, Serializable> {
    Departures findById(int id);

    List<Departures> findByStatus(int status);

    Departures findByIdAndStatus(int id, int status);

    @Query(value = "Select * from Departures as d where d.folio LIKE  '%-%-%' AND d.series = ?1 ORDER BY d.id DESC LIMIT 1", nativeQuery = true)
    Departures findTopBySeries(String series);

    Departures findByFolioAndSeries(String folio, String series);
}
