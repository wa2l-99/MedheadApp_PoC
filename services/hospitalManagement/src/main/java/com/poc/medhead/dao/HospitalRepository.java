package com.poc.medhead.dao;

import com.poc.medhead.model.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    @Query("""
        SELECT DISTINCT h
        FROM Hospital h
        JOIN h.specialites_medicales s
        WHERE s.id = :specialityId
    """)
    Page<Hospital> findHospitalsBySpeciality(Pageable pageable, Integer specialityId);


    @Query("""
        SELECT h
        FROM Hospital h
        JOIN h.specialites_medicales s
        WHERE s.nom = :specialty
        AND h.lits_disponible > 0
    """
    )
    Page<Hospital> findHospitalsWithSpecialtyAndAvailableBeds(Pageable pageable, String specialty);
}