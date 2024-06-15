CREATE TABLE Department (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL UNIQUE,
    room_number VARCHAR(50),
    number_employee INT,
    chief_id BIGINT,
    deputy_1_id BIGINT,
    deputy_2_id BIGINT
);

CREATE TABLE Employee (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    date_of_birth DATE,
    base_salary DOUBLE,
    net_salary DOUBLE,
    insurance_base DOUBLE,
    department_id INT,
    position VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    is_off BOOLEAN,
    CONSTRAINT fk_department
        FOREIGN KEY (department_id) 
        REFERENCES Department(id)
);

