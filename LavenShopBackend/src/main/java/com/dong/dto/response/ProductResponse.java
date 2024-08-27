package com.dong.dto.response;

import com.dong.dto.model.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLast;
}
