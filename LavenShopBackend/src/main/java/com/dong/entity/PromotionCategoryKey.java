package com.dong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PromotionCategoryKey implements Serializable {

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "promotion_id")
    private int promotionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionCategoryKey that = (PromotionCategoryKey) o;
        return categoryId == that.categoryId && promotionId == that.promotionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, promotionId);
    }
}
