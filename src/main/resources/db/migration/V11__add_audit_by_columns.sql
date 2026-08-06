-- Add created_by and updated_by columns to tables that inherit AuditableEntity
-- Use INFORMATION_SCHEMA checks to stay compatible with MySQL versions that don't support IF NOT EXISTS on ADD COLUMN

-- countries
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'countries' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE countries ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'countries' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE countries ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- states
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'states' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE states ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'states' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE states ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- cities
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'cities' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE cities ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'cities' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE cities ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- tenants
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'tenants' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE tenants ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'tenants' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE tenants ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- menus
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'menus' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE menus ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'menus' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE menus ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- menu_templates
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'menu_templates' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE menu_templates ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'menu_templates' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE menu_templates ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- user_templates
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'user_templates' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE user_templates ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'user_templates' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE user_templates ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- users
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'users' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE users ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'users' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE users ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- password_reset_tokens
SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'password_reset_tokens' AND column_name = 'created_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE password_reset_tokens ADD COLUMN created_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @cnt = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = DATABASE() AND table_name = 'password_reset_tokens' AND column_name = 'updated_by');
SET @s = IF(@cnt = 0, 'ALTER TABLE password_reset_tokens ADD COLUMN updated_by VARCHAR(150) NULL', 'SELECT 1');
PREPARE stmt FROM @s; EXECUTE stmt; DEALLOCATE PREPARE stmt;
