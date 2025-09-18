package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;


public interface CategoryService {
    /**
     * 新增分类
     * @param category
     * @return
     */
    Category newCategory(CategoryDTO category);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void editCategory(CategoryDTO categoryDTO);

    void startOrStop(Integer status, Long id);

    void deleteCategory(Long id);

    List<Category> list(Integer type);

}
