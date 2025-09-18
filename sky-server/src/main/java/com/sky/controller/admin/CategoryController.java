package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result newCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.newCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }
    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result edit(@RequestBody CategoryDTO categoryDTO){
        categoryService.editCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用分类")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用分类：{},{}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation(value = "根据id删除分类")
    public Result deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return Result.success();

    }

    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询分类")
    public Result<List<Category>> list( Integer type){
        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }
}
