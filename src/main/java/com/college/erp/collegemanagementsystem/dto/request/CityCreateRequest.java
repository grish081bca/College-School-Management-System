package com.college.erp.collegemanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Getter
@Setter
public class CityCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Long stateId;
}
