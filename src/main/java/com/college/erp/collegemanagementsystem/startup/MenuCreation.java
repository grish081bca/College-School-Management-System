package com.college.erp.collegemanagementsystem.startup;

import com.college.erp.collegemanagementsystem.entity.Menu;
import com.college.erp.collegemanagementsystem.entity.MenuTemplate;
import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.MenuStatus;
import com.college.erp.collegemanagementsystem.enums.MenuType;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.repository.MenuRepository;
import com.college.erp.collegemanagementsystem.repository.MenuTemplateRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuCreation implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuCreation.class);

    private final MenuRepository menuRepository;
    private final MenuTemplateRepository menuTemplateRepository;
    private final UserTemplateRepository userTemplateRepository;

    public MenuCreation(MenuRepository menuRepository,
                        MenuTemplateRepository menuTemplateRepository,
                        UserTemplateRepository userTemplateRepository) {
        this.menuRepository = menuRepository;
        this.menuTemplateRepository = menuTemplateRepository;
        this.userTemplateRepository = userTemplateRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        startupCreator();
    }

    public void startupCreator() {
        try {
            Map<String, Menu> menus = createMenus();
            createMenuTemplates(menus);
        } catch (Exception e) {
            LOGGER.error("Menu creation failed.", e);
        }
    }

    private Map<String, Menu> createMenus() {
        Map<String, Menu> createdMenus = new LinkedHashMap<>();

        List<MenuSeed> menuSeeds = List.of(
                superMenu("DASHBOARD", "Dashboard", "/web/dashboard", "layout-dashboard", 10),

                superMenu("TENANT_MANAGEMENT", "Tenant Management", null, "building-2", 20),
                subMenu("TENANTS_LIST", "List Tenants", "/web/tenants", "TENANT_MANAGEMENT", 21),
                subMenu("TENANTS_ADD", "Add Tenant", "/web/tenants/add", "TENANT_MANAGEMENT", 22),

                superMenu("USER_MANAGEMENT", "User Management", null, "users", 30),
                subMenu("USERS_LIST", "List Users", "/web/users", "USER_MANAGEMENT", 31),
                subMenu("USERS_ADD", "Add User", "/web/users/add", "USER_MANAGEMENT", 32),
                subMenu("USER_TEMPLATES_LIST", "List User Templates", "/web/user-templates", "USER_MANAGEMENT", 33),
                subMenu("USER_TEMPLATES_ADD", "Add User Template", "/web/user-templates/add", "USER_MANAGEMENT", 34),

                superMenu("ACCESS_ADMINISTRATION", "Access Administration", null, "shield-check", 40),
                subMenu("MENUS_LIST", "List Menus", "/web/menus", "ACCESS_ADMINISTRATION", 41),
                subMenu("MENUS_ADD", "Add Menu", "/web/menus/add", "ACCESS_ADMINISTRATION", 42),
                subMenu("MENU_TEMPLATES_LIST", "List Menu Templates", "/web/menu-templates", "ACCESS_ADMINISTRATION", 43),
                subMenu("MENU_TEMPLATES_ADD", "Add Menu Template", "/web/menu-templates/add", "ACCESS_ADMINISTRATION", 44),

                superMenu("LOCATION_SETUP", "Location Setup", null, "map-pin", 50),
                subMenu("COUNTRIES_LIST", "List Countries", "/web/countries", "LOCATION_SETUP", 51),
                subMenu("COUNTRIES_ADD", "Add Country", "/web/countries/add", "LOCATION_SETUP", 52),
                subMenu("STATES_LIST", "List States", "/web/states", "LOCATION_SETUP", 53),
                subMenu("STATES_ADD", "Add State", "/web/states/add", "LOCATION_SETUP", 54),
                subMenu("CITIES_LIST", "List Cities", "/web/cities", "LOCATION_SETUP", 55),
                subMenu("CITIES_ADD", "Add City", "/web/cities/add", "LOCATION_SETUP", 56)
        );

        for (MenuSeed seed : menuSeeds) {
            Menu parentMenu = seed.parentCode() == null ? null : createdMenus.get(seed.parentCode());
            Menu menu = menuRepository.findByMenuCodeIgnoreCase(seed.code()).orElseGet(Menu::new);
            menu.setMenuCode(seed.code());
            menu.setName(seed.name());
            menu.setMenuUrl(seed.url());
            menu.setIcon(seed.icon());
            menu.setParentMenu(parentMenu);
            menu.setDisplayOrder(seed.displayOrder());
            menu.setStatus(MenuStatus.ACTIVE);
            menu.setMenuType(seed.parentCode() == null ? MenuType.SUPER_MENU : MenuType.SUB_MENU);
            createdMenus.put(seed.code(), menuRepository.save(menu));
        }

        return createdMenus;
    }

    private void createMenuTemplates(Map<String, Menu> menus) {
        List<String> allMenus = new ArrayList<>(menus.keySet());
        List<String> collegeAdminMenus = List.of(
                "DASHBOARD",
                "USER_MANAGEMENT", "USERS_LIST", "USERS_ADD", "USER_TEMPLATES_LIST", "USER_TEMPLATES_ADD",
                "LOCATION_SETUP", "COUNTRIES_LIST", "COUNTRIES_ADD", "STATES_LIST", "STATES_ADD", "CITIES_LIST", "CITIES_ADD"
        );
        List<String> tenantAdminMenus = List.of(
                "DASHBOARD",
                "USER_MANAGEMENT", "USERS_LIST", "USERS_ADD", "USER_TEMPLATES_LIST", "USER_TEMPLATES_ADD",
                "LOCATION_SETUP", "COUNTRIES_LIST", "STATES_LIST", "CITIES_LIST"
        );
        List<String> staffMenus = List.of("DASHBOARD", "USER_MANAGEMENT", "USERS_LIST");
        List<String> basicMenus = List.of("DASHBOARD");

        createTemplate(UserType.SUPER_ADMIN, "Super Admin Menu Template", allMenus, menus);
        createTemplate(UserType.SYSTEM_ADMIN, "System Admin Menu Template", allMenus, menus);
        createTemplate(UserType.TENANT_ADMIN, "Tenant Admin Menu Template", tenantAdminMenus, menus);
        createTemplate(UserType.COLLEGE_ADMIN, "College Admin Menu Template", collegeAdminMenus, menus);
        createTemplate(UserType.COLLEGE_BRANCH, "College Branch Menu Template", staffMenus, menus);
        createTemplate(UserType.PRINCIPAL, "Principal Menu Template", staffMenus, menus);
        createTemplate(UserType.TEACHER, "Teacher Menu Template", basicMenus, menus);
        createTemplate(UserType.ACCOUNTANT, "Accountant Menu Template", basicMenus, menus);
        createTemplate(UserType.LIBRARIAN, "Librarian Menu Template", basicMenus, menus);
        createTemplate(UserType.STUDENT, "Student Menu Template", basicMenus, menus);
        createTemplate(UserType.GUARDIAN, "Guardian Menu Template", basicMenus, menus);
        createTemplate(UserType.STAFF, "Staff Menu Template", staffMenus, menus);
    }

    private void createTemplate(UserType userType,
                                String templateName,
                                List<String> menuCodes,
                                Map<String, Menu> menus) {
        MenuTemplate menuTemplate = menuTemplateRepository.findAllByUserTypeOrderByIdAsc(userType).stream()
                .findFirst()
                .orElseGet(MenuTemplate::new);
        menuTemplate.setName(templateName);
        menuTemplate.setUserType(userType);
        menuTemplate.setStatus(MenuStatus.ACTIVE);
        menuTemplate.getMenus().clear();
        for (String menuCode : menuCodes) {
            Menu menu = menus.get(menuCode);
            if (menu != null && !menuTemplate.getMenus().contains(menu)) {
                menuTemplate.getMenus().add(menu);
            }
        }
        menuTemplate = menuTemplateRepository.save(menuTemplate);

        UserTemplate userTemplate = userTemplateRepository.findAllByUserTypeOrderByIdAsc(userType).stream()
                .findFirst()
                .orElseGet(UserTemplate::new);
        userTemplate.setUserType(userType);
        userTemplate.setMenuTemplate(menuTemplate);
        userTemplate.setStatus(UserStatus.ACTIVE);
        userTemplateRepository.save(userTemplate);
    }

    private static MenuSeed superMenu(String code, String name, String url, String icon, int displayOrder) {
        return new MenuSeed(code, name, url, icon, null, displayOrder);
    }

    private static MenuSeed subMenu(String code, String name, String url, String parentCode, int displayOrder) {
        return new MenuSeed(code, name, url, null, parentCode, displayOrder);
    }

    private record MenuSeed(String code,
                            String name,
                            String url,
                            String icon,
                            String parentCode,
                            int displayOrder) {
    }
}
