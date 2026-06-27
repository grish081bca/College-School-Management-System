package com.college.erp.collegemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.college.erp.collegemanagementsystem.entity.Country;

/**
 * @author grish
 *
 */

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findCountryById(Long id);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByIsoCodeIgnoreCase(String isoCode);

    boolean existsByIsoCodeIgnoreCaseAndIdNot(String isoCode, Long id);

    java.util.List<Country> findAllByOrderByIdDesc();
}
