-- Rename menu.menu_name -> name, menu_templates.template_name -> name, add user_templates.user_template_name, add menus.bank_id (nullable)
ALTER TABLE menus CHANGE COLUMN menu_name `name` VARCHAR(150) NOT NULL;
ALTER TABLE menu_templates CHANGE COLUMN template_name `name` VARCHAR(150) NOT NULL;
ALTER TABLE user_templates ADD COLUMN user_template_name VARCHAR(150) NULL;
ALTER TABLE menus ADD COLUMN bank_id BIGINT NULL;
-- Note: parent_menu_id remains for hierarchical menus (superId semantics)
