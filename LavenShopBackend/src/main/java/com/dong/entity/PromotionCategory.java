package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_category")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PromotionCategory {

    @EmbeddedId
    private PromotionCategoryKey id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
    public PromotionCategory(Category category, Promotion promotion) {
        this.category = category;
        this.promotion = promotion;
        this.id = new PromotionCategoryKey(category.getId(), promotion.getId());
    }
}
