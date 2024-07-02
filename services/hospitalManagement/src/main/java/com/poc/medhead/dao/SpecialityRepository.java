package com.poc.medhead.dao;

import com.poc.medhead.model.MedicalSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<MedicalSpeciality, Integer> {

}
