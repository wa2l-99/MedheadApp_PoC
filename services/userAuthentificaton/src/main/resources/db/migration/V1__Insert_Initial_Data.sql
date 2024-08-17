-- Insert roles
INSERT INTO role (nom, created_date)
VALUES ('Admin', CURRENT_TIMESTAMP)
ON CONFLICT (nom) DO NOTHING;

INSERT INTO role (nom, created_date)
VALUES ('Patient', CURRENT_TIMESTAMP)
ON CONFLICT (nom) DO NOTHING;

-- Insert users
INSERT INTO users (nom, prenom, date_naissance, email, sexe, adresse, numero, password, account_locked, enabled, created_date)
VALUES ('Admin', 'User', '1990-01-01', 'admin@example.com', 'Male', 'Admin Address', '1234567890','$2y$10$KeLphdCMw74r1vcOBCP9o.BKakhYrMx/gFdwYO80u1ozjurLruUn6', FALSE, TRUE, CURRENT_TIMESTAMP);

INSERT INTO users (nom, prenom, date_naissance, email, sexe, adresse, numero, password, account_locked, enabled, created_date)
VALUES ('Patient', 'User', '1995-01-01', 'patient@example.com', 'Female', 'Patient Address', '0987654321', '$2y$10$G0l4vshvH0fLtlJ9wn2F1OmQGZq0WyC9rWKSiPHGhRfmSf8Q1nNjS', FALSE, TRUE, CURRENT_TIMESTAMP);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@example.com'), (SELECT id FROM role WHERE nom = 'Admin'));

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'patient@example.com'), (SELECT id FROM role WHERE nom = 'Patient'));

-- Générer et insérer des tokens pour les utilisateurs
-- Admin token
INSERT INTO token (token, created_at, expires_at, user_id)
VALUES (
           '123456',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP + INTERVAL '15 minutes',
           (SELECT id FROM users WHERE email = 'admin@example.com')
       );

-- Patient token
INSERT INTO token (token, created_at, expires_at, user_id)
VALUES (
           '654321',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP + INTERVAL '15 minutes',
           (SELECT id FROM users WHERE email = 'patient@example.com')
       );

