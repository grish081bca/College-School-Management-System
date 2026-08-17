package com.college.erp.collegemanagementsystem.repository;

import com.college.erp.collegemanagementsystem.entity.EntityChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author grish
 *
 */
public interface EntityChangeLogRepository extends JpaRepository<EntityChangeLog, Long> {
    List<EntityChangeLog> findTop25ByEntityNameAndEntityIdOrderByCreatedAtDesc(String entityName, Long entityId);
}
