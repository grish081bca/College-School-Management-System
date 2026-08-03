package com.college.erp.collegemanagementsystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.college.erp.collegemanagementsystem.entity.State;

/**
 * @author grish
 *
 */


public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {
    State findStateById(Long id);

    List<State> findAllByOrderByIdDesc();

    List<State> findAllByCountry_IdOrderByIdDesc(Long countryId);

    boolean existsByNameIgnoreCaseAndCountry_Id(String name, Long countryId);

    boolean existsByNameIgnoreCaseAndCountry_IdAndIdNot(String name, Long countryId, Long id);

    boolean existsByCountry_Id(Long countryId);
}
