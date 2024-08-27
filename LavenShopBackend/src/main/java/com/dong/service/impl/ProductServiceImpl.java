package com.dong.service.impl;

import com.dong.dto.mapper.ProductMapper;
import com.dong.dto.model.ProductDto;
import com.dong.dto.response.ProductResponse;
import com.dong.entity.Product;
import com.dong.exception.ResourceNotFoundException;
import com.dong.repositories.ProductRepository;
import com.dong.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    //Repositories
    private ProductRepository productRepository;
    //Mappers
    private ProductMapper productMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Tao sort
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Tao 1 pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Tao 1 mang cac trang product su dung find all voi tham so la pageable
        Page<Product> pages = this.productRepository.findAll(pageable);

        // Lay ra gia tri (content) cua page
        List<Product> products = pages.getContent();

        // Ep kieu sang dto
        List<ProductDto> content = products.stream().map(product -> productMapper.mapToDto(product)).collect(Collectors.toList());

        // Gan gia tri (content) cua page vao ProductResponse de tra ve
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(content);
        productResponse.setTotalElements(pages.getTotalElements());
        productResponse.setPageNo(pages.getNumber());
        productResponse.setPageSize(pages.getSize());
        productResponse.setLast(pages.isLast());

        return productResponse;

   }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        ProductDto productDto = productMapper.mapToDto(product);

        return productDto;    }
}
