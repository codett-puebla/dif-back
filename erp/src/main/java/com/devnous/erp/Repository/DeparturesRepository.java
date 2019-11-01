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

    List<Departures> findByStatusAndIdCompany(int status, int idCompany);

    Departures findByIdAndStatus(int id, int status);

    @Query(value = "Select * from Departures as d where d.folio LIKE  '%-%-%' AND d.series = ?1 AND d.idCompany = ?2 ORDER BY d.id DESC LIMIT 1", nativeQuery = true)
    Departures findTopBySeriesAndIdCompany(String series, int idCompany);

    Departures findByFolioAndSeriesAndIdCompany(String folio, String series, int idCompany);
}
