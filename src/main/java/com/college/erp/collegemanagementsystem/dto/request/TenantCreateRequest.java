package com.college.erp.collegemanagementsystem.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author grish
 */

@Setter
@Getter
public class TenantCreateRequest {
    @NotBlank
    @Size(min = 2, max = 200)
    private String tenantName;

    @Email
    @Size(max = 200)
    @NotNull
    private String contactEmail;

    @Email
    @Size(max = 200)
    @Nullable
    private String contactEmailSecondary;

    @Size(max = 15)
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact Phone must be a valid 10-digit number")
    @NotNull
    private String contactPhone;

    @Size(max = 15)
    @Nullable
    private String contactPhoneSecondary;

    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    @Nullable
    private String addressLine2;

    @NotNull
    private Long countryId;

    @NotNull
    private Long stateId;

    @NotNull
    private Long cityId;

    @Size(max = 20)
    @NotNull
    private String postalCode;
}
