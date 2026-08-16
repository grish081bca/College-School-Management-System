package com.college.erp.collegemanagementsystem.startup;

import com.college.erp.collegemanagementsystem.entity.City;
import com.college.erp.collegemanagementsystem.entity.Country;
import com.college.erp.collegemanagementsystem.entity.State;
import com.college.erp.collegemanagementsystem.entity.Tenant;
import com.college.erp.collegemanagementsystem.entity.User;
import com.college.erp.collegemanagementsystem.entity.UserTemplate;
import com.college.erp.collegemanagementsystem.enums.TenantStatus;
import com.college.erp.collegemanagementsystem.enums.TenantType;
import com.college.erp.collegemanagementsystem.enums.UserStatus;
import com.college.erp.collegemanagementsystem.enums.UserType;
import com.college.erp.collegemanagementsystem.repository.CityRepository;
import com.college.erp.collegemanagementsystem.repository.CountryRepository;
import com.college.erp.collegemanagementsystem.repository.StateRepository;
import com.college.erp.collegemanagementsystem.repository.TenantRepository;
import com.college.erp.collegemanagementsystem.repository.UserRepository;
import com.college.erp.collegemanagementsystem.repository.UserTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author grish
 *
 */
@Component
public class ApplicationStartupInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupInitializer.class);

    private static final String DEFAULT_COUNTRY_NAME = "Nepal";
    private static final String DEFAULT_COUNTRY_ISO_CODE = "NPL";
    private static final String DEFAULT_STATE_NAME = "Bagmati";
    private static final String DEFAULT_CITY_NAME = "Kathmandu";
    private static final String DEFAULT_TENANT_CODE = "COLLEGEA";
    private static final String DEFAULT_TENANT_NAME = "Demo College";
    private static final String DEFAULT_PASSWORD = "Password@123";

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserTemplateRepository userTemplateRepository;
    private final PasswordEncoder passwordEncoder;
    private final MenuCreation menuCreation;

    public ApplicationStartupInitializer(CountryRepository countryRepository,
                                         StateRepository stateRepository,
                                         CityRepository cityRepository,
                                         TenantRepository tenantRepository,
                                         UserRepository userRepository,
                                         UserTemplateRepository userTemplateRepository,
                                         PasswordEncoder passwordEncoder,
                                         MenuCreation menuCreation) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.userTemplateRepository = userTemplateRepository;
        this.passwordEncoder = passwordEncoder;
        this.menuCreation = menuCreation;
    }
    @Override
    @Transactional
    public void run(String... args) {
        try {
            initializeDefaults();
        } catch (Exception e) {
            LOGGER.error("Application startup initialization failed.", e);
            throw e;
        }
    }

    private void initializeDefaults() {
        Country country = initializeCountry();
        State state = initializeState(country);
        City city = initializeCity(state);
        Tenant tenant = initializeTenant(country, state, city);

        menuCreation.startupCreator();

        initializeUser(
                "sysadmin",
                "sysadmin@gmail.com",
                "System",
                "Admin",
                null,
                UserType.SUPER_ADMIN
        );
        initializeUser(
                "collegeadmin",
                "collegeadmin@collegeerp.local",
                "College",
                "Admin",
                tenant,
                UserType.COLLEGE_ADMIN
        );
    }

    private Country initializeCountry() {
        return countryRepository.findByIsoCodeIgnoreCase(DEFAULT_COUNTRY_ISO_CODE).or(() -> countryRepository.findByNameIgnoreCase(DEFAULT_COUNTRY_NAME)).orElseGet(() -> {
                    Country country = new Country();
                    country.setName(DEFAULT_COUNTRY_NAME);
                    country.setIsoCode(DEFAULT_COUNTRY_ISO_CODE);
                    return countryRepository.save(country);
                });
    }

    private State initializeState(Country country) {
        return stateRepository.findByNameIgnoreCaseAndCountry_Id(DEFAULT_STATE_NAME, country.getId()).orElseGet(() -> {
                    State state = new State();
                    state.setName(DEFAULT_STATE_NAME);
                    state.setCountry(country);
                    return stateRepository.save(state);
                });
    }

    private City initializeCity(State state) {
        return cityRepository.findByNameIgnoreCaseAndState_Id(DEFAULT_CITY_NAME, state.getId()).orElseGet(() -> {
                    City city = new City();
                    city.setName(DEFAULT_CITY_NAME);
                    city.setState(state);
                    return cityRepository.save(city);
                });
    }

    private Tenant initializeTenant(Country country, State state, City city) {
        return tenantRepository.findByTenantCodeIgnoreCase(DEFAULT_TENANT_CODE).or(() -> tenantRepository.findByTenantNameIgnoreCase(DEFAULT_TENANT_NAME)).orElseGet(() -> {
                    Tenant tenant = new Tenant();
                    tenant.setTenantCode(DEFAULT_TENANT_CODE);
                    tenant.setTenantName(DEFAULT_TENANT_NAME);
                    tenant.setContactEmail("grishshrestha2@gmail.com");
                    tenant.setContactPhone("9865453089");
                    tenant.setAddressLine1("Kathmandu");
                    tenant.setPostalCode("44600");
                    tenant.setCountry(country);
                    tenant.setState(state);
                    tenant.setCity(city);
                    tenant.setStatus(TenantStatus.ACTIVE);
                    tenant.setTenantType(TenantType.HEAD);
                    return tenantRepository.save(tenant);
                });
    }

    private void initializeUser(String username,
                                String email,
                                String firstName,
                                String lastName,
                                Tenant tenant,
                                UserType userType) {
        userRepository.findByUsernameIgnoreCase(username).or(() -> userRepository.findByEmailIgnoreCase(email)).ifPresentOrElse(
                        user -> assignMissingUserDefaults(user, tenant, userType), () -> createUser(username, email, firstName, lastName, tenant, userType));
    }

    private void createUser(String username,
                            String email,
                            String firstName,
                            String lastName,
                            Tenant tenant,
                            UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(null);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setPasswordResetRequired(false);
        user.setUserType(userType);
        user.setTenant(tenant);
        user.setUserTemplate(findUserTemplate(userType));
        userRepository.save(user);
    }

    private void assignMissingUserDefaults(User user, Tenant tenant, UserType userType) {
        boolean changed = false;
        UserType effectiveUserType = user.getUserType() != null ? user.getUserType() : userType;
        if (!passwordEncoder.matches(DEFAULT_PASSWORD, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            changed = true;
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            user.setStatus(UserStatus.ACTIVE);
            changed = true;
        }
        if (!user.isEnabled()) {
            user.setEnabled(true);
            changed = true;
        }
        if (!user.isAccountNonLocked()) {
            user.setAccountNonLocked(true);
            changed = true;
        }
        if (user.isPasswordResetRequired()) {
            user.setPasswordResetRequired(false);
            changed = true;
        }
        if (user.getUserTemplate() == null) {
            user.setUserTemplate(findUserTemplate(effectiveUserType));
            changed = true;
        }
        if (user.getTenant() == null && tenant != null && requiresTenant(effectiveUserType)) {
            user.setTenant(tenant);
            changed = true;
        }
        if (changed) {
            userRepository.save(user);
        }
    }

    private boolean requiresTenant(UserType userType) {
        return userType != UserType.SUPER_ADMIN && userType != UserType.SYSTEM_ADMIN;
    }

    private UserTemplate findUserTemplate(UserType userType) {
        return userTemplateRepository.findByUserType(userType).orElse(null);
    }
}
