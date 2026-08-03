UPDATE menus
SET menu_type = 'SUPER_MENU'
WHERE parent_menu_id IS NULL;

UPDATE menus
SET menu_type = 'SUB_MENU'
WHERE parent_menu_id IS NOT NULL;

UPDATE menus
SET menu_code = 'LIST_TENANTS', menu_name = 'List Tenants'
WHERE menu_code = 'TENANTS';

UPDATE menus
SET menu_code = 'LIST_MENUS', menu_name = 'List Menus'
WHERE menu_code = 'MENUS';

UPDATE menus
SET menu_code = 'LIST_MENU_TEMPLATES', menu_name = 'List Menu Templates'
WHERE menu_code = 'MENU_TEMPLATES';

UPDATE menus
SET menu_code = 'LIST_USER_TEMPLATES', menu_name = 'List User Templates'
WHERE menu_code = 'USER_TEMPLATES';

UPDATE menus
SET menu_code = 'LIST_COUNTRIES', menu_name = 'List Countries'
WHERE menu_code = 'COUNTRIES';

UPDATE menus
SET menu_code = 'LIST_STATES', menu_name = 'List States'
WHERE menu_code = 'STATES';

UPDATE menus
SET menu_code = 'LIST_CITIES', menu_name = 'List Cities'
WHERE menu_code = 'CITIES';

INSERT INTO menus (menu_code, menu_name, menu_url, icon, display_order, status, menu_type, created_at, updated_at)
SELECT 'TENANT_MANAGEMENT', 'Tenant Management', NULL, 'building', 20, 'ACTIVE', 'SUPER_MENU', NOW(6), NOW(6)
WHERE NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'TENANT_MANAGEMENT');

INSERT INTO menus (menu_code, menu_name, menu_url, icon, display_order, status, menu_type, created_at, updated_at)
SELECT 'MENU_MANAGEMENT', 'Menu Management', NULL, 'menu', 30, 'ACTIVE', 'SUPER_MENU', NOW(6), NOW(6)
WHERE NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'MENU_MANAGEMENT');

INSERT INTO menus (menu_code, menu_name, menu_url, icon, display_order, status, menu_type, created_at, updated_at)
SELECT 'LOCATION_SETUP', 'Location Setup', NULL, 'map', 40, 'ACTIVE', 'SUPER_MENU', NOW(6), NOW(6)
WHERE NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'LOCATION_SETUP');

UPDATE menus SET parent_menu_id = (SELECT id FROM (SELECT id FROM menus WHERE menu_code = 'TENANT_MANAGEMENT') parent_menu), menu_type = 'SUB_MENU'
WHERE menu_code = 'LIST_TENANTS';

UPDATE menus SET parent_menu_id = (SELECT id FROM (SELECT id FROM menus WHERE menu_code = 'MENU_MANAGEMENT') parent_menu), menu_type = 'SUB_MENU'
WHERE menu_code IN ('LIST_MENUS', 'LIST_MENU_TEMPLATES', 'LIST_USER_TEMPLATES');

UPDATE menus SET parent_menu_id = (SELECT id FROM (SELECT id FROM menus WHERE menu_code = 'LOCATION_SETUP') parent_menu), menu_type = 'SUB_MENU'
WHERE menu_code IN ('LIST_COUNTRIES', 'LIST_STATES', 'LIST_CITIES');

INSERT INTO menus (menu_code, menu_name, menu_url, icon, parent_menu_id, display_order, status, menu_type, created_at, updated_at)
SELECT 'ADD_TENANT', 'Add Tenant', '/web/tenants/add', 'plus', id, 20, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'TENANT_MANAGEMENT' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_TENANT')
UNION ALL
SELECT 'ADD_MENU', 'Add Menu', '/web/menus/add', 'plus', id, 20, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'MENU_MANAGEMENT' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_MENU')
UNION ALL
SELECT 'ADD_MENU_TEMPLATE', 'Add Menu Template', '/web/menu-templates/add', 'plus', id, 40, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'MENU_MANAGEMENT' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_MENU_TEMPLATE')
UNION ALL
SELECT 'ADD_USER_TEMPLATE', 'Add User Template', '/web/user-templates/add', 'plus', id, 60, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'MENU_MANAGEMENT' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_USER_TEMPLATE')
UNION ALL
SELECT 'ADD_COUNTRY', 'Add Country', '/web/countries/add', 'plus', id, 20, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'LOCATION_SETUP' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_COUNTRY')
UNION ALL
SELECT 'ADD_STATE', 'Add State', '/web/states/add', 'plus', id, 40, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'LOCATION_SETUP' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_STATE')
UNION ALL
SELECT 'ADD_CITY', 'Add City', '/web/cities/add', 'plus', id, 60, 'ACTIVE', 'SUB_MENU', NOW(6), NOW(6) FROM menus
WHERE menu_code = 'LOCATION_SETUP' AND NOT EXISTS (SELECT 1 FROM menus WHERE menu_code = 'ADD_CITY');

INSERT INTO menu_templates (tenant_id, user_type, menu_id, status, created_at, updated_at)
SELECT mt.tenant_id, mt.user_type, m.id, 'ACTIVE', NOW(6), NOW(6)
FROM menu_templates mt
JOIN menus existing_menu ON existing_menu.id = mt.menu_id
JOIN menus m ON (
    (existing_menu.menu_code = 'LIST_TENANTS' AND m.menu_code IN ('TENANT_MANAGEMENT', 'ADD_TENANT'))
    OR (existing_menu.menu_code = 'LIST_MENUS' AND m.menu_code IN ('MENU_MANAGEMENT', 'ADD_MENU'))
    OR (existing_menu.menu_code = 'LIST_MENU_TEMPLATES' AND m.menu_code = 'ADD_MENU_TEMPLATE')
    OR (existing_menu.menu_code = 'LIST_USER_TEMPLATES' AND m.menu_code = 'ADD_USER_TEMPLATE')
    OR (existing_menu.menu_code = 'LIST_COUNTRIES' AND m.menu_code IN ('LOCATION_SETUP', 'ADD_COUNTRY'))
    OR (existing_menu.menu_code = 'LIST_STATES' AND m.menu_code = 'ADD_STATE')
    OR (existing_menu.menu_code = 'LIST_CITIES' AND m.menu_code = 'ADD_CITY')
)
WHERE NOT EXISTS (
    SELECT 1 FROM menu_templates duplicate_template
    WHERE (duplicate_template.tenant_id = mt.tenant_id OR (duplicate_template.tenant_id IS NULL AND mt.tenant_id IS NULL))
      AND duplicate_template.user_type = mt.user_type
      AND duplicate_template.menu_id = m.id
);
