-- Table de  Medical Specialities
CREATE TABLE medical_speciality (
    id INTEGER NOT NULL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL UNIQUE
);

-- Table for Hospitals
CREATE TABLE hospital (
    id INTEGER NOT NULL PRIMARY KEY,
    nom_organisation VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    code_postal VARCHAR(5) NOT NULL UNIQUE,
    lits_disponible INT NOT NULL,
    longitude FLOAT NOT NULL,
    latitude FLOAT NOT NULL
);

-- Junction Table for Hospital and Medical Specialities
CREATE TABLE hospital_speciality (
     hospital_id INTEGER NOT NULL,
     speciality_id INTEGER NOT NULL,
     CONSTRAINT fk_hospital
         FOREIGN KEY(hospital_id)
             REFERENCES hospital(id),
     CONSTRAINT fk_speciality
         FOREIGN KEY(speciality_id)
             REFERENCES medical_speciality(id),
     PRIMARY KEY (hospital_id, speciality_id)
);

CREATE SEQUENCE IF NOT EXISTS medical_speciality_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS hospital_seq INCREMENT BY 50