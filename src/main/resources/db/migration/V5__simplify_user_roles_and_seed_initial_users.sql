DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS role_permissions;
DROP TABLE IF EXISTS permissions;
DROP TABLE IF EXISTS roles;

ALTER TABLE users
    ADD COLUMN user_role VARCHAR(50) NOT NULL DEFAULT 'COLLEGE_ADMIN',
    ADD COLUMN user_type VARCHAR(50) NOT NULL DEFAULT 'COLLEGE_ADMIN';

ALTER TABLE users
    DROP INDEX uk_users_tenant_username,
    DROP INDEX uk_users_tenant_email,
    ADD CONSTRAINT uk_users_username UNIQUE (username),
    ADD CONSTRAINT uk_users_email UNIQUE (email);

INSERT IGNORE INTO tenants (
    tenant_code,
    tenant_name,
    contact_email,
    contact_phone,
    status,
    tenant_type,
    created_at,
    updated_at
) VALUES
('SYSTEM', 'System Tenant', 'system@college-erp.local', '9800000000', 'ACTIVE', 'HEAD', NOW(6), NOW(6)),
('DEMO-COLLEGE', 'Demo College', 'admin@demo-college.local', '9800000001', 'ACTIVE', 'HEAD', NOW(6), NOW(6));

INSERT IGNORE INTO tenants (
    tenant_code,
    tenant_name,
    contact_email,
    contact_phone,
    status,
    tenant_type,
    parent_tenant_id,
    created_at,
    updated_at
)
SELECT
    'DEMO-BRANCH',
    'Demo College Branch',
    'branch@demo-college.local',
    '9800000002',
    'ACTIVE',
    'BRANCH',
    id,
    NOW(6),
    NOW(6)
FROM tenants
WHERE tenant_code = 'DEMO-COLLEGE';

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
SELECT t.id, 'system.admin', 'system.admin@college-erp.local', 'System', 'Admin',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'SYSTEM_ADMIN', 'SYSTEM_ADMIN', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'SYSTEM'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'system.admin');

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
SELECT t.id, 'college.admin', 'college.admin@college-erp.local', 'College', 'Admin',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'COLLEGE_ADMIN', 'COLLEGE_ADMIN', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'DEMO-COLLEGE'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'college.admin');

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
SELECT t.id, 'branch.admin', 'branch.admin@college-erp.local', 'Branch', 'Admin',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'COLLEGE_BRANCH_ADMIN', 'COLLEGE_BRANCH', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'DEMO-BRANCH'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'branch.admin');

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
SELECT t.id, 'teacher', 'teacher@college-erp.local', 'Demo', 'Teacher',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'TEACHER', 'TEACHER', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'DEMO-COLLEGE'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'teacher');

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
SELECT t.id, 'student', 'student@college-erp.local', 'Demo', 'Student',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'STUDENT', 'STUDENT', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'DEMO-COLLEGE'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'student');

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
SELECT t.id, 'staff', 'staff@college-erp.local', 'Demo', 'Staff',
       '$2a$12$9wS1NNrJNtTuS6NqkHEQmeNRaEM5cp/2XF.7MZVVkJjdU6yE.dOrC',
       'ACTIVE', TRUE, TRUE, FALSE, 'STAFF', 'STAFF', NOW(6), NOW(6)
FROM tenants t
WHERE t.tenant_code = 'DEMO-COLLEGE'
  AND NOT EXISTS (SELECT 1 FROM users u WHERE u.username = 'staff');
