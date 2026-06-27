package com.college.erp.collegemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author grish
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StateDTO {
    private Long id;
    private String createdDate;
    private String updatedDate;
    private String name;
    private Long countryId;
    private String countryName;
}
