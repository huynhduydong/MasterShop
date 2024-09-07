package com.dong.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateCartRequest {
    private Long productItemId;
    private Long productId;
    private long delta;
}