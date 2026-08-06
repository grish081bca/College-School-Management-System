ALTER TABLE menu_templates
    ADD COLUMN template_name VARCHAR(150) NULL AFTER id;

CREATE TABLE menu_template_menus (
    menu_template_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (menu_template_id, menu_id),
    CONSTRAINT fk_menu_template_menus_template FOREIGN KEY (menu_template_id) REFERENCES menu_templates (id) ON DELETE CASCADE,
    CONSTRAINT fk_menu_template_menus_menu FOREIGN KEY (menu_id) REFERENCES menus (id)
);

INSERT INTO menu_template_menus (menu_template_id, menu_id)
SELECT keeper.id, mt.menu_id
FROM menu_templates mt
JOIN (
    SELECT MIN(id) AS id, tenant_id, user_type
    FROM menu_templates
    GROUP BY tenant_id, user_type
) keeper ON keeper.user_type = mt.user_type
    AND (keeper.tenant_id = mt.tenant_id OR (keeper.tenant_id IS NULL AND mt.tenant_id IS NULL))
WHERE mt.menu_id IS NOT NULL
GROUP BY keeper.id, mt.menu_id;

DELETE mt
FROM menu_templates mt
JOIN (
    SELECT MIN(id) AS id, tenant_id, user_type
    FROM menu_templates
    GROUP BY tenant_id, user_type
) keeper ON keeper.user_type = mt.user_type
    AND (keeper.tenant_id = mt.tenant_id OR (keeper.tenant_id IS NULL AND mt.tenant_id IS NULL))
WHERE mt.id <> keeper.id;

UPDATE menu_templates mt
LEFT JOIN tenants t ON t.id = mt.tenant_id
SET mt.template_name = CONCAT(
    CASE WHEN t.tenant_name IS NULL THEN 'Global' ELSE t.tenant_name END,
    ' ',
    REPLACE(mt.user_type, '_', ' '),
    ' Menu Template'
);

ALTER TABLE menu_templates
    MODIFY template_name VARCHAR(150) NOT NULL;

ALTER TABLE menu_templates
    DROP FOREIGN KEY fk_menu_templates_menu;

ALTER TABLE menu_templates
    DROP INDEX uk_menu_templates_scope;

ALTER TABLE menu_templates
    DROP COLUMN menu_id;

ALTER TABLE menu_templates
    ADD CONSTRAINT uk_menu_templates_tenant_user_type UNIQUE (tenant_id, user_type);

ALTER TABLE user_templates
    ADD COLUMN menu_template_id BIGINT NULL AFTER user_type,
    ADD CONSTRAINT fk_user_templates_menu_template FOREIGN KEY (menu_template_id) REFERENCES menu_templates (id);

UPDATE user_templates ut
JOIN menu_templates mt ON mt.user_type = ut.user_type
    AND (mt.tenant_id = ut.tenant_id OR mt.tenant_id IS NULL)
SET ut.menu_template_id = mt.id
WHERE ut.menu_template_id IS NULL;

ALTER TABLE users
    ADD COLUMN user_template_id BIGINT NULL AFTER user_type,
    ADD CONSTRAINT fk_users_user_template FOREIGN KEY (user_template_id) REFERENCES user_templates (id);

UPDATE users u
JOIN user_templates ut ON ut.tenant_id = u.tenant_id
    AND ut.user_type = u.user_type
SET u.user_template_id = ut.id
WHERE u.user_template_id IS NULL;
