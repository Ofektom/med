package com.ofektom.med.dto.response;


import java.util.List;

public record PaginatedResponse<T>(
        int currentPage,
        int pageSize,
        int totalPages,
        long totalRecords,
        List<T> data
) {}
