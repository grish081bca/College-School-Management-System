package com.college.erp.collegemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.college.erp.collegemanagementsystem.entity.Country;

import java.util.Optional;

/**
 * @author grish
 *
 */

public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
    Country findCountryById(Long id);

    Optional<Country> findByNameIgnoreCase(String name);

    Optional<Country> findByIsoCodeIgnoreCase(String isoCode);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByIsoCodeIgnoreCase(String isoCode);

    boolean existsByIsoCodeIgnoreCaseAndIdNot(String isoCode, Long id);

    java.util.List<Country> findAllByOrderByIdDesc();
}
