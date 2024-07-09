-- Create sequences
CREATE SEQUENCE IF NOT EXISTS users_seq
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS role_seq
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS token_seq
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY NOT NULL,
                       nom VARCHAR(255),
                       prenom VARCHAR(255),
                       date_naissance DATE,
                       email VARCHAR(255) UNIQUE,
                       sexe VARCHAR(50),
                       adresse TEXT,
                       numero VARCHAR(50),
                       password VARCHAR(255),
                       account_locked BOOLEAN,
                       enabled BOOLEAN,
                       created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       last_modified_date TIMESTAMP
);

-- Create role table
CREATE TABLE role (
                      id SERIAL PRIMARY KEY NOT NULL,
                      nom VARCHAR(255) UNIQUE,
                      created_date TIMESTAMP NOT NULL,
                      last_modified_date TIMESTAMP
);

-- Create user_roles junction table
CREATE TABLE user_roles (
                            user_id INTEGER NOT NULL ,
                            role_id INTEGER NOT NULL ,
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id),
                            CONSTRAINT fk_role
                                FOREIGN KEY (role_id)
                                    REFERENCES role(id)
);

-- Create token table
CREATE TABLE token (
                       id SERIAL PRIMARY KEY NOT NULL,
                       token VARCHAR(255) UNIQUE NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       expires_at TIMESTAMP NOT NULL,
                       validated_at TIMESTAMP,
                       user_id INTEGER NOT NULL,
                       CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);