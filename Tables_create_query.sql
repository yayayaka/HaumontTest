CREATE TABLE Patient
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY,
    pat_name VARCHAR(20) NOT NULL,
    pat_secname VARCHAR(20) DEFAULT 'NONE',
    pat_otch VARCHAR(20) DEFAULT 'NONE',
    tel_no NUMERIC(11) DEFAULT 0
);

CREATE TABLE Doctor
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY,
    doc_name VARCHAR(20) NOT NULL,
    doc_secname VARCHAR(20) NOT NULL,
    doc_otch VARCHAR(20) DEFAULT 'NONE',
    doc_spec VARCHAR(20) NOT NULL
);

CREATE TABLE Prescr_priority
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY,
    prior_name VARCHAR(6) NOT NULL UNIQUE
);

INSERT INTO Prescr_priority
(prior_name)
VALUES
('Normal');

INSERT INTO Prescr_priority
(prior_name)
VALUES
('Cito'); -- срочный

INSERT INTO Prescr_priority
(prior_name)
VALUES
('Statim'); -- немедленный

CREATE TABLE Prescription -- рецепт
(
    id BIGINT NOT NULL PRIMARY KEY IDENTITY,
    description VARCHAR(20) DEFAULT 'NONE', -- описание
    patient_id BIGINT NOT NULL FOREIGN KEY REFERENCES Patient(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    doc_id BIGINT NOT NULL FOREIGN KEY REFERENCES Doctor(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    creation_date DATE NOT NULL,
    expire_date DATE NOT NULL,
    priority BIGINT NOT NULL FOREIGN KEY REFERENCES Prescr_priority(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);