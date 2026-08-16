package com.college.erp.collegemanagementsystem.service.impl;

import com.college.erp.collegemanagementsystem.entity.EntityChangeLog;
import com.college.erp.collegemanagementsystem.enums.EntityChangeAction;
import com.college.erp.collegemanagementsystem.repository.EntityChangeLogRepository;
import com.college.erp.collegemanagementsystem.service.EntityChangeLogService;
import com.college.erp.collegemanagementsystem.util.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author grish
 *
 */
@Service
public class EntityChangeLogServiceImpl implements EntityChangeLogService {

    private final EntityChangeLogRepository repository;

    public EntityChangeLogServiceImpl(EntityChangeLogRepository repository) {
        this.repository = repository;
    }
    @Override
    public boolean hasChanged(Object oldValue, Object newValue) {
        return !Objects.equals(stringify(oldValue), stringify(newValue));
    }
    @Override
    public void logChange(String entityName, Long entityId, EntityChangeAction action, String fieldName, Object oldValue, Object newValue, String remarks) {
        String previousValue = stringify(oldValue);
        String currentValue = stringify(newValue);
        if (!hasChanged(previousValue, currentValue)) {
            return;
        }
        EntityChangeLog log = new EntityChangeLog();
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setFieldName(fieldName);
        log.setOldValue(previousValue);
        log.setNewValue(currentValue);
        log.setRemarks(ConvertUtils.normalizeText(remarks));
        repository.save(log);
    }

    private String stringify(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
