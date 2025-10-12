package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     * @param dishDTO
     */
    @Transactional
    @Override
    public void saveWithFlavors(DishDTO dishDTO) {
        // 菜品表新增菜品
        Dish dish = new Dish();
        // 属性拷贝
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        // 获取insert语句获取的主键值
        Long dishId = dish.getId();

        // 口味表新增菜品ID
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (!CollectionUtils.isEmpty(flavors)) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
