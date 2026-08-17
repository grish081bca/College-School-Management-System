package com.college.erp.collegemanagementsystem.service;

import com.college.erp.collegemanagementsystem.entity.EntityChangeLog;
import com.college.erp.collegemanagementsystem.enums.EntityChangeAction;

import java.util.List;

/**
 * @author grish
 *
 */
public interface EntityChangeLogService {

    boolean hasChanged(Object oldValue, Object newValue);

    void logChange(String entityName, Long entityId, EntityChangeAction action, String fieldName, Object oldValue, Object newValue, String remarks);

    List<EntityChangeLog> getRecentChanges(String entityName, Long entityId);

    default void logChange(String entityName, Long entityId, EntityChangeAction action, String fieldName, Object oldValue, Object newValue) {
        logChange(entityName, entityId, action, fieldName, oldValue, newValue, null);
    }
}
