package com.college.erp.collegemanagementsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.college.erp.collegemanagementsystem.entity.State;

/**
 * @author grish
 *
 */


public interface StateRepository extends JpaRepository<State, Long> {
}
