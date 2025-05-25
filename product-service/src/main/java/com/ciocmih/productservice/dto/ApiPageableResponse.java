package com.ciocmih.productservice.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ApiPageableResponse<T> extends ApiResponse<List<T>> {

    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;

    public ApiPageableResponse(Page<T> data, int statusCode, boolean success) {
        super(data.getContent(), success, statusCode);
        this.totalPages = data.getTotalPages();
        this.totalElements = data.getTotalElements();
        this.currentPage = data.getNumber();
        this.pageSize = data.getSize();
    }
}