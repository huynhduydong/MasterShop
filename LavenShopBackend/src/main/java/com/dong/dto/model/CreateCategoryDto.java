package com.dong.dto.model;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateCategoryDto {
    @NotEmpty(message = "Tên không được bỏ trống")
    @Size(min = 2, message = "Tên phải có độ dài tối thiểu là 2 ký tự")
    private String name;
    @NotEmpty
    private String thumbnailUrl;
    private Long parentId;
    private boolean isPrimary;
}
