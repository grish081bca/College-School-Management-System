CREATE TABLE tenants (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tenant_code VARCHAR(50) NOT NULL,
    tenant_name VARCHAR(150) NOT NULL,
    contact_email VARCHAR(150),
    contact_phone VARCHAR(30),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_tenants_tenant_code UNIQUE (tenant_code),
    CONSTRAINT uk_tenants_tenant_name UNIQUE (tenant_name)
);

CREATE INDEX idx_tenants_status ON tenants (status);
