package com.example.medhead.dao;

import com.example.medhead.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByUsername(String userName);

}
