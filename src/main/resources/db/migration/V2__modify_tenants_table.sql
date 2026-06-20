ALTER TABLE tenants
    ADD contact_email_secondary VARCHAR(200) NULL;

ALTER TABLE tenants
    ADD contact_phone_secondary VARCHAR(15) NULL;

ALTER TABLE tenants
    MODIFY city VARCHAR (255);

ALTER TABLE tenants
    MODIFY contact_email VARCHAR (200);

ALTER TABLE tenants
    MODIFY contact_email VARCHAR (200) NOT NULL;

ALTER TABLE tenants
    MODIFY contact_phone VARCHAR (15);

ALTER TABLE tenants
    MODIFY state VARCHAR (255);

ALTER TABLE tenants
    MODIFY tenant_name VARCHAR (200);