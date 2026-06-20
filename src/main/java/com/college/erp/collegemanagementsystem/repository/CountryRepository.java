package com.college.erp.collegemanagementsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.college.erp.collegemanagementsystem.entity.Country;

/**
 * @author grish
 *
 */

public interface CountryRepository extends JpaRepository<Country, Long> {
}
