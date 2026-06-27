package com.college.erp.collegemanagementsystem.dto;

import lombok.*;
import com.college.erp.collegemanagementsystem.enums.ResponseStatus;

/**
 * @author grish
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RestResponseDTO {
    private String status;
    private String code;
    private String message;
    private Object details;
    private Object detail;

    public RestResponseDTO(String message) {
        this.message = message;
    }

    public static RestResponseDTO badRequest(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.BAD_REQUEST.getKey())
                .status(ResponseStatus.BAD_REQUEST.getValue())
                .message(message)
                .build();
    }
    public static RestResponseDTO failure(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.FAILURE.getKey())
                .status(ResponseStatus.FAILURE.getValue())
                .message(message)
                .build();
    }
    public static RestResponseDTO unauthorized(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.UNAUTHORIZED_USER.getKey())
                .status(ResponseStatus.UNAUTHORIZED_USER.getValue())
                .message(message)
                .build();
    }
    public static RestResponseDTO success(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.SUCCESS.getKey())
                .status(ResponseStatus.SUCCESS.getValue())
                .message(message)
                .build();
    }
    public static RestResponseDTO success(String message, Object detail) {
        return RestResponseDTO.builder().code(ResponseStatus.SUCCESS.getKey())
                .status(ResponseStatus.SUCCESS.getValue())
                .message(message)
                .detail(detail)
                .build();
    }

    public static RestResponseDTO internalServerError(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.INTERNAL_SERVER_ERROR.getKey())
                .status(ResponseStatus.INTERNAL_SERVER_ERROR.getValue())
                .message(message)
                .build();
    }

    public static RestResponseDTO error(String message) {
        return RestResponseDTO.builder().code(ResponseStatus.FAILURE.getKey())
                .status(ResponseStatus.FAILURE.getValue())
                .message(message)
                .build();
    }

    public static RestResponseDTO error(String message, Object detail) {
        return RestResponseDTO.builder().code(ResponseStatus.FAILURE.getKey())
                .status(ResponseStatus.FAILURE.getValue())
                .message(message)
                .detail(detail)
                .build();
    }

    public RestResponseDTO(Object details, Object detail) {
        this.details = details;
        this.detail = detail;
    }

}
