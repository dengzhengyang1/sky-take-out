package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

import java.util.List;

public interface DishService {


    void saveWithFlavors(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);
}
