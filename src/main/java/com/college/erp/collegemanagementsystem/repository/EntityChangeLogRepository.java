package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.EntityChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author grish
 *
 */
public interface EntityChangeLogRepository extends JpaRepository<EntityChangeLog, Long> {
}
