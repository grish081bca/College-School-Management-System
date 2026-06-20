CREATE TABLE countries (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    iso_code VARCHAR(3) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_countries_name UNIQUE (name),
    CONSTRAINT uk_countries_iso_code UNIQUE (iso_code)
);

CREATE TABLE states (
    id BIGINT NOT NULL AUTO_INCREMENT,
    country_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_states_country_name UNIQUE (country_id, name),
    CONSTRAINT fk_states_country FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE cities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    state_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_cities_state_name UNIQUE (state_id, name),
    CONSTRAINT fk_cities_state FOREIGN KEY (state_id) REFERENCES states (id)
);

ALTER TABLE tenants
    ADD country_id BIGINT NULL,
    ADD state_id BIGINT NULL,
    ADD city_id BIGINT NULL;

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_country FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_state FOREIGN KEY (state_id) REFERENCES states (id);

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_city FOREIGN KEY (city_id) REFERENCES cities (id);

ALTER TABLE tenants
    DROP COLUMN country,
    DROP COLUMN state,
    DROP COLUMN city;
