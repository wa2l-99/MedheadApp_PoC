package com.poc.medhead.dao;

import com.poc.medhead.model.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    @Query("""
        SELECT h
        FROM Hospital h
        JOIN h.specialitesMedicales s
        WHERE s.id = :specialityId
    """)
    Page<Hospital> findHospitalsBySpeciality(Pageable pageable, Integer specialityId);



    @Query("""
        SELECT h
        FROM Hospital h
        JOIN h.specialitesMedicales s
        WHERE s.nom = :specialty
        AND h.litsDisponible > 0
    """)
    List<Hospital> findNearestAvailableHospitalsBySpecialty(String specialty);

    boolean existsByNomOrganisation(String nomOrganisation);
}