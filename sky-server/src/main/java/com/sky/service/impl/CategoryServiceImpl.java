package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
//    @Getter
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增分类
     *
     * @param categoryDTO
     * @return
     */
    @Override
    public Category newCategory(CategoryDTO categoryDTO) {
//        Category category1 = Category.builder()
//                .type(category.getType())
//                .name(category.getName())
//                .sort(category.getSort())
//                .status(category.getStatus())
//                .createTime(LocalDateTime.now())
//                .updateTime(LocalDateTime.now())
//                .createUser(category.getCreateUser())
//                .updateUser(category.getUpdateUser())
//                .build();
        Category category1 = new Category();
        BeanUtils.copyProperties(categoryDTO,category1);
        category1.setStatus(StatusConstant.DISABLE);
        category1.setCreateTime(LocalDateTime.now());
        category1.setUpdateTime(LocalDateTime.now());
        category1.setCreateUser(BaseContext.getCurrentId());
        category1.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category1);

        return null;
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void editCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count = setmealMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.delete(id);
    }

    @Override
    public List<Category> list(Integer type){
        return categoryMapper.list(type);
    }

}
