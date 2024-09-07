package com.dong.repositories;

import com.dong.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByUrlKey(String urlKey);
}
