package com.sky.service;

import com.sky.entity.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    void newCategory(Category categoryDTO);
}
