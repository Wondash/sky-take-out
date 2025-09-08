package com.sky.service.impl;

import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @Override
    public void newCategory(Category category) {
        Category category1 = Category.builder()
                .type(category.getType())
                .name(category.getName())
                .sort(category.getSort())
                .status(category.getStatus())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(category.getCreateUser())
                .updateUser(category.getUpdateUser())
                .build();
        categoryMapper.insert(category1);
    }
}
