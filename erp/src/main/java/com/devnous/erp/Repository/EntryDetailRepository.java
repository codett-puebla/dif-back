package com.devnous.erp.Repository;

import com.devnous.erp.Entity.EntryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("entryDetailRepository")
public interface EntryDetailRepository extends JpaRepository<EntryDetail, Serializable> {
    EntryDetail findById(int id);
}
