package com.college.erp.collegemanagementsystem.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Getter
@Setter
public class CityUpdateRequest {

    @Size(max = 100)
    private String name;

    private Long stateId;
}
