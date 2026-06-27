package com.college.erp.collegemanagementsystem.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Getter
@Setter
public class CountryUpdateRequest {

    @Size(max = 100)
    private String name;

    @Size(min = 2, max = 3)
    private String isoCode;
}
