package com.college.erp.collegemanagementsystem.controller;

import org.springframework.ui.Model;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

final class WebPagination {
    private WebPagination() {
    }

    static void add(Model model, String baseUrl, Integer size, Map<String, ?> filters) {
        model.addAttribute("paginationBaseUrl", baseUrl);
        model.addAttribute("pageSize", size == null || size < 1 ? 10 : size);
        model.addAttribute("paginationQuery", buildQuery(filters));
    }

    static Map<String, Object> filters() {
        return new LinkedHashMap<>();
    }

    private static String buildQuery(Map<String, ?> filters) {
        StringBuilder builder = new StringBuilder();
        if (filters == null) {
            return "";
        }
        filters.forEach((key, value) -> {
            if (value == null || value.toString().isBlank()) {
                return;
            }
            builder.append('&')
                    .append(UriUtils.encodeQueryParam(key, StandardCharsets.UTF_8))
                    .append('=')
                    .append(UriUtils.encodeQueryParam(value.toString(), StandardCharsets.UTF_8));
        });
        return builder.toString();
    }
}
