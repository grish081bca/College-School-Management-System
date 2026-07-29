CREATE TABLE menus (
    id BIGINT NOT NULL AUTO_INCREMENT,
    menu_code VARCHAR(100) NOT NULL,
    menu_name VARCHAR(150) NOT NULL,
    menu_url VARCHAR(255) NULL,
    icon VARCHAR(100) NULL,
    parent_menu_id BIGINT NULL,
    display_order INT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    menu_type VARCHAR(30) NOT NULL DEFAULT 'TENANT',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_menus_menu_code UNIQUE (menu_code),
    CONSTRAINT fk_menus_parent FOREIGN KEY (parent_menu_id) REFERENCES menus (id)
);

CREATE INDEX idx_menus_parent_id ON menus (parent_menu_id);
CREATE INDEX idx_menus_status ON menus (status);

CREATE TABLE menu_templates (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tenant_id BIGINT NULL,
    user_type VARCHAR(50) NOT NULL,
    menu_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_menu_templates_scope UNIQUE (tenant_id, user_type, menu_id),
    CONSTRAINT fk_menu_templates_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id),
    CONSTRAINT fk_menu_templates_menu FOREIGN KEY (menu_id) REFERENCES menus (id)
);

CREATE INDEX idx_menu_templates_tenant_user_type ON menu_templates (tenant_id, user_type);
CREATE INDEX idx_menu_templates_status ON menu_templates (status);

CREATE TABLE user_templates (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tenant_id BIGINT NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_user_templates_tenant_user_type UNIQUE (tenant_id, user_type),
    CONSTRAINT fk_user_templates_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

CREATE INDEX idx_user_templates_tenant_status ON user_templates (tenant_id, status);

INSERT INTO menus (menu_code, menu_name, menu_url, icon, display_order, status, menu_type, created_at, updated_at) VALUES
('DASHBOARD', 'Dashboard', '/web/dashboard', 'dashboard', 10, 'ACTIVE', 'GLOBAL', NOW(6), NOW(6)),
('TENANTS', 'Tenants', '/web/tenants', 'building', 20, 'ACTIVE', 'GLOBAL', NOW(6), NOW(6)),
('MENUS', 'Menus', '/web/menus', 'menu', 30, 'ACTIVE', 'SETTINGS', NOW(6), NOW(6)),
('MENU_TEMPLATES', 'Menu Templates', '/web/menu-templates', 'template', 40, 'ACTIVE', 'SETTINGS', NOW(6), NOW(6)),
('USER_TEMPLATES', 'User Templates', '/web/user-templates', 'users', 50, 'ACTIVE', 'SETTINGS', NOW(6), NOW(6)),
('COUNTRIES', 'Countries', '/web/countries', 'map', 60, 'ACTIVE', 'GLOBAL', NOW(6), NOW(6)),
('STATES', 'States', '/web/states', 'map-pin', 70, 'ACTIVE', 'GLOBAL', NOW(6), NOW(6)),
('CITIES', 'Cities', '/web/cities', 'map-pin', 80, 'ACTIVE', 'GLOBAL', NOW(6), NOW(6));

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT NULL, 'SUPER_ADMIN', m.id, 'ACTIVE', NOW(6), NOW(6)
FROM menus m;

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT NULL, 'SYSTEM_ADMIN', m.id, 'ACTIVE', NOW(6), NOW(6)
FROM menus m;

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT t.id, 'TENANT_ADMIN', m.id, 'ACTIVE', NOW(6), NOW(6)
FROM tenants t
JOIN menus m ON m.menu_code IN ('DASHBOARD', 'MENUS', 'MENU_TEMPLATES', 'USER_TEMPLATES')
WHERE t.tenant_code <> 'SYSTEM';

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT t.id, 'COLLEGE_ADMIN', m.id, 'ACTIVE', NOW(6), NOW(6)
FROM tenants t
JOIN menus m ON m.menu_code IN ('DASHBOARD', 'USER_TEMPLATES')
WHERE t.tenant_code <> 'SYSTEM';

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT t.id, u.user_type, m.id, 'ACTIVE', NOW(6), NOW(6)
FROM tenants t
JOIN (
    SELECT 'PRINCIPAL' AS user_type UNION ALL
    SELECT 'TEACHER' UNION ALL
    SELECT 'ACCOUNTANT' UNION ALL
    SELECT 'LIBRARIAN' UNION ALL
    SELECT 'STUDENT' UNION ALL
    SELECT 'GUARDIAN'
) u
JOIN menus m ON m.menu_code = 'DASHBOARD'
WHERE t.tenant_code <> 'SYSTEM';

INSERT INTO user_templates (tenant_id, user_type, status, created_at, updated_at)
SELECT t.id, u.user_type, 'ACTIVE', NOW(6), NOW(6)
FROM tenants t
JOIN (
    SELECT 'TENANT_ADMIN' AS user_type UNION ALL
    SELECT 'COLLEGE_ADMIN' UNION ALL
    SELECT 'PRINCIPAL' UNION ALL
    SELECT 'TEACHER' UNION ALL
    SELECT 'ACCOUNTANT' UNION ALL
    SELECT 'LIBRARIAN' UNION ALL
    SELECT 'STUDENT' UNION ALL
    SELECT 'GUARDIAN'
) u
WHERE t.tenant_code <> 'SYSTEM';

INSERT INTO users (
    tenant_id,
    username,
    email,
    first_name,
    last_name,
    password_hash,
    status,
    enabled,
    account_non_locked,
    password_reset_required,
    user_role,
    user_type,
    created_at,
    updated_at
)
SELECT t.id, 'super.admin', 'super.admin@college-erp.local', 'Super', 'Admin',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'SUPER_ADMIN', 'SUPER_ADMIN', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'SYSTEM'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'super.admin');
