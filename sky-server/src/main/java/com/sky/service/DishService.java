package com.sky.service;

import com.sky.controller.admin.CommonController;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

public interface DishService {
    public void addDishWithFlavor(DishDTO dishDTO);
}
