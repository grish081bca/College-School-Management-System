ALTER TABLE tenants
    ADD COLUMN tenant_type VARCHAR(20) NOT NULL DEFAULT 'HEAD',
    ADD COLUMN parent_tenant_id BIGINT NULL AFTER tenant_type;

ALTER TABLE tenants
    ADD CONSTRAINT fk_tenants_parent FOREIGN KEY (parent_tenant_id) REFERENCES tenants (id);

CREATE INDEX idx_tenants_parent_tenant_id ON tenants (parent_tenant_id);

CREATE TABLE permissions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    permission_code VARCHAR(120) NOT NULL,
    permission_name VARCHAR(150) NOT NULL,
    module_name VARCHAR(150) NOT NULL,
    description VARCHAR(500) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_permissions_code UNIQUE (permission_code)
);

CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_code VARCHAR(120) NOT NULL,
    role_name VARCHAR(150) NOT NULL,
    description VARCHAR(500) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_roles_code UNIQUE (role_code)
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tenant_id BIGINT NOT NULL,
    username VARCHAR(120) NOT NULL,
    email VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NULL,
    last_name VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    password_changed_at DATETIME(6) NULL,
    last_login_at DATETIME(6) NULL,
    password_reset_required BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_tenant_username UNIQUE (tenant_id, username),
    CONSTRAINT uk_users_tenant_email UNIQUE (tenant_id, email),
    CONSTRAINT fk_users_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_tenant ON users (tenant_id);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE refresh_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT,
    token_hash VARCHAR(64) NOT NULL,
    user_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    expires_at DATETIME(6) NOT NULL,
    revoked_at DATETIME(6) NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    last_used_at DATETIME(6) NULL,
    created_by_ip VARCHAR(45) NULL,
    user_agent VARCHAR(500) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_refresh_tokens_hash UNIQUE (token_hash),
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_refresh_tokens_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);
CREATE INDEX idx_refresh_tokens_tenant_id ON refresh_tokens (tenant_id);

CREATE TABLE password_reset_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT,
    token_hash VARCHAR(64) NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at DATETIME(6) NOT NULL,
    used_at DATETIME(6) NULL,
    revoked_at DATETIME(6) NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_password_reset_tokens_hash UNIQUE (token_hash),
    CONSTRAINT fk_password_reset_tokens_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_password_reset_tokens_user_id ON password_reset_tokens (user_id);

INSERT INTO permissions (permission_code, permission_name, module_name, description, created_at, updated_at) VALUES
('TENANT_MANAGE', 'Tenant Manage', 'TENANT', 'Create, update and manage tenants', NOW(6), NOW(6)),
('USER_MANAGE', 'User Manage', 'USER', 'Create, update and manage users', NOW(6), NOW(6)),
('ROLE_MANAGE', 'Role Manage', 'SECURITY', 'Create, update and manage roles', NOW(6), NOW(6)),
('PERMISSION_MANAGE', 'Permission Manage', 'SECURITY', 'Create, update and manage permissions', NOW(6), NOW(6)),
('DASHBOARD_VIEW', 'Dashboard View', 'DASHBOARD', 'View dashboard metrics', NOW(6), NOW(6)),
('COURSE_MANAGE', 'Course Manage', 'ACADEMIC', 'Manage courses', NOW(6), NOW(6)),
('COURSE_VIEW', 'Course View', 'ACADEMIC', 'View courses', NOW(6), NOW(6)),
('DEPARTMENT_MANAGE', 'Department Manage', 'ACADEMIC', 'Manage departments', NOW(6), NOW(6)),
('TEACHER_MANAGE', 'Teacher Manage', 'ACADEMIC', 'Manage teachers', NOW(6), NOW(6)),
('STUDENT_MANAGE', 'Student Manage', 'ACADEMIC', 'Manage students', NOW(6), NOW(6)),
('CLASS_MANAGE', 'Class Manage', 'ACADEMIC', 'Manage classes', NOW(6), NOW(6)),
('CLASS_VIEW', 'Class View', 'ACADEMIC', 'View classes', NOW(6), NOW(6)),
('ATTENDANCE_MANAGE', 'Attendance Manage', 'ACADEMIC', 'Manage attendance', NOW(6), NOW(6)),
('ATTENDANCE_VIEW', 'Attendance View', 'ACADEMIC', 'View attendance', NOW(6), NOW(6)),
('EXAM_MANAGE', 'Exam Manage', 'ACADEMIC', 'Manage exams', NOW(6), NOW(6)),
('EXAM_VIEW', 'Exam View', 'ACADEMIC', 'View exams', NOW(6), NOW(6)),
('FEE_MANAGE', 'Fee Manage', 'FINANCE', 'Manage fees', NOW(6), NOW(6)),
('FEE_VIEW', 'Fee View', 'FINANCE', 'View fees', NOW(6), NOW(6)),
('LIBRARY_MANAGE', 'Library Manage', 'LIBRARY', 'Manage library resources', NOW(6), NOW(6)),
('LIBRARY_VIEW', 'Library View', 'LIBRARY', 'View library resources', NOW(6), NOW(6)),
('EVENT_MANAGE', 'Event Manage', 'EVENT', 'Manage events', NOW(6), NOW(6)),
('EVENT_VIEW', 'Event View', 'EVENT', 'View events', NOW(6), NOW(6)),
('REPORT_VIEW', 'Report View', 'REPORTING', 'View reports', NOW(6), NOW(6)),
('SETTINGS_MANAGE', 'Settings Manage', 'SYSTEM', 'Manage system settings', NOW(6), NOW(6));

INSERT INTO roles (role_code, role_name, description, created_at, updated_at) VALUES
('SUPER_ADMIN', 'Super Admin', 'Platform administrator with full access', NOW(6), NOW(6)),
('COLLEGE_ADMIN', 'College Admin', 'Head college administrator', NOW(6), NOW(6)),
('BRANCH_ADMIN', 'Branch Admin', 'Branch college administrator', NOW(6), NOW(6)),
('TEACHER', 'Teacher', 'Teaching staff', NOW(6), NOW(6)),
('STUDENT', 'Student', 'Student user', NOW(6), NOW(6)),
('STAFF', 'Staff', 'Non-teaching staff user', NOW(6), NOW(6));

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'SUPER_ADMIN';

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'COLLEGE_ADMIN'
  AND p.permission_code IN ('TENANT_MANAGE', 'USER_MANAGE', 'ROLE_MANAGE', 'PERMISSION_MANAGE', 'DASHBOARD_VIEW', 'COURSE_MANAGE', 'COURSE_VIEW', 'DEPARTMENT_MANAGE', 'TEACHER_MANAGE', 'STUDENT_MANAGE', 'CLASS_MANAGE', 'CLASS_VIEW', 'ATTENDANCE_MANAGE', 'ATTENDANCE_VIEW', 'EXAM_MANAGE', 'EXAM_VIEW', 'FEE_MANAGE', 'FEE_VIEW', 'LIBRARY_MANAGE', 'LIBRARY_VIEW', 'EVENT_MANAGE', 'EVENT_VIEW', 'REPORT_VIEW', 'SETTINGS_MANAGE');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'BRANCH_ADMIN'
  AND p.permission_code IN ('USER_MANAGE', 'DASHBOARD_VIEW', 'COURSE_VIEW', 'TEACHER_MANAGE', 'STUDENT_MANAGE', 'CLASS_MANAGE', 'CLASS_VIEW', 'ATTENDANCE_MANAGE', 'ATTENDANCE_VIEW', 'EXAM_MANAGE', 'EXAM_VIEW', 'FEE_MANAGE', 'FEE_VIEW', 'LIBRARY_MANAGE', 'LIBRARY_VIEW', 'EVENT_MANAGE', 'EVENT_VIEW', 'REPORT_VIEW');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'TEACHER'
  AND p.permission_code IN ('DASHBOARD_VIEW', 'COURSE_VIEW', 'CLASS_VIEW', 'ATTENDANCE_MANAGE', 'ATTENDANCE_VIEW', 'EXAM_MANAGE', 'EXAM_VIEW', 'REPORT_VIEW');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'STUDENT'
  AND p.permission_code IN ('DASHBOARD_VIEW', 'COURSE_VIEW', 'CLASS_VIEW', 'ATTENDANCE_VIEW', 'EXAM_VIEW', 'FEE_VIEW', 'EVENT_VIEW', 'LIBRARY_VIEW', 'REPORT_VIEW');

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p
WHERE r.role_code = 'STAFF'
  AND p.permission_code IN ('DASHBOARD_VIEW', 'FEE_MANAGE', 'FEE_VIEW', 'LIBRARY_MANAGE', 'LIBRARY_VIEW', 'EVENT_MANAGE', 'EVENT_VIEW', 'REPORT_VIEW');
