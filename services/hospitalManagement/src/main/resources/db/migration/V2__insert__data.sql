-- Insertion des spécialités médicales
INSERT INTO medical_speciality (id, nom) VALUES
                                             (nextval('medical_speciality_seq'), 'Cardiologie'),
                                             (nextval('medical_speciality_seq'), 'Neurologie'),
                                             (nextval('medical_speciality_seq'), 'Pédiatrie'),
                                             (nextval('medical_speciality_seq'), 'Oncologie'),
                                             (nextval('medical_speciality_seq'), 'Chirurgie générale'),
                                             (nextval('medical_speciality_seq'), 'Orthopédie'),
                                             (nextval('medical_speciality_seq'), 'Gynécologie'),
                                             (nextval('medical_speciality_seq'), 'Dermatologie'),
                                             (nextval('medical_speciality_seq'), 'Ophtalmologie'),
                                             (nextval('medical_speciality_seq'), 'Psychiatrie');

-- Insertion des hôpitaux avec des codes postaux uniques
INSERT INTO hospital (id, nom_organisation, adresse, code_postal, lits_disponible, longitude, latitude, created_date) VALUES
                                                                                                                          (nextval('hospital_seq'), 'Hôpital Saint-Louis', '1 Avenue Claude Vellefaux', '75010', 650, 2.3665, 48.8747, current_date),
                                                                                                                          (nextval('hospital_seq'), 'Hôpital Necker', '149 Rue de Sèvres', '75015', 500, 2.3165, 48.8472, current_date),
                                                                                                                          (nextval('hospital_seq'), 'Hôpital Pitié-Salpêtrière', '47-83 Boulevard de l''Hôpital', '75013', 1000, 2.3644, 48.8399, current_date),
    (nextval('hospital_seq'), 'Hôpital Cochin', '27 Rue du Faubourg Saint-Jacques', '75014', 450, 2.3391, 48.8361, current_date),
    (nextval('hospital_seq'), 'Hôpital Bichat', '46 Rue Henri Huchard', '75018', 480, 2.3308, 48.8992, current_date),
    (nextval('hospital_seq'), 'Hôpital Robert Debré', '48 Boulevard Sérurier', '75019', 400, 2.4016, 48.8793, current_date),
    (nextval('hospital_seq'), 'Hôpital Tenon', '4 Rue de la Chine', '75020', 360, 2.4009, 48.8651, current_date),
    (nextval('hospital_seq'), 'Hôpital Européen Georges-Pompidou', '20 Rue Leblanc', '75908', 550, 2.2738, 48.8391, current_date);

-- Insertion des relations hôpital-spécialité
WITH specialities AS (
    SELECT id, nom FROM medical_speciality
),
hospitals AS (
    SELECT id, nom_organisation FROM hospital
)
INSERT INTO hospital_speciality (hospital_id, speciality_id)
SELECT h.id, s.id
FROM hospitals h
CROSS JOIN specialities s
WHERE (h.nom_organisation, s.nom) IN (
    ('Hôpital Saint-Louis', 'Oncologie'),
    ('Hôpital Saint-Louis', 'Chirurgie générale'),
    ('Hôpital Saint-Louis', 'Dermatologie'),
    ('Hôpital Necker', 'Pédiatrie'),
    ('Hôpital Necker', 'Cardiologie'),
    ('Hôpital Necker', 'Ophtalmologie'),
    ('Hôpital Pitié-Salpêtrière', 'Neurologie'),
    ('Hôpital Pitié-Salpêtrière', 'Cardiologie'),
    ('Hôpital Pitié-Salpêtrière', 'Psychiatrie'),
    ('Hôpital Cochin', 'Chirurgie générale'),
    ('Hôpital Cochin', 'Oncologie'),
    ('Hôpital Cochin', 'Gynécologie'),
    ('Hôpital Bichat', 'Cardiologie'),
    ('Hôpital Bichat', 'Chirurgie générale'),
    ('Hôpital Robert Debré', 'Pédiatrie'),
    ('Hôpital Robert Debré', 'Orthopédie'),
    ('Hôpital Tenon', 'Gynécologie'),
    ('Hôpital Tenon', 'Oncologie'),
    ('Hôpital Européen Georges-Pompidou', 'Cardiologie'),
    ('Hôpital Européen Georges-Pompidou', 'Oncologie')
);

-- Vérification des insertions
SELECT 'Spécialités médicales' AS table_name, COUNT(*) AS row_count FROM medical_speciality
UNION ALL
SELECT 'Hôpitaux' AS table_name, COUNT(*) AS row_count FROM hospital
UNION ALL
SELECT 'Relations hôpital-spécialité' AS table_name, COUNT(*) AS row_count FROM hospital_speciality;
