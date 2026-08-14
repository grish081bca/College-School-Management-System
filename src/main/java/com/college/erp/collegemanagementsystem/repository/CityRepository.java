package com.college.erp.collegemanagementsystem.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.college.erp.collegemanagementsystem.entity.City;

/**
 * @author grish
 *
 */


public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    City findCityById(Long id);

    List<City> findAllByOrderByIdDesc();

    List<City> findAllByState_IdOrderByIdDesc(Long stateId);

    Optional<City> findByNameIgnoreCaseAndState_Id(String name, Long stateId);

    boolean existsByNameIgnoreCaseAndState_Id(String name, Long stateId);

    boolean existsByNameIgnoreCaseAndState_IdAndIdNot(String name, Long stateId, Long id);

    boolean existsByState_Id(Long stateId);
}
