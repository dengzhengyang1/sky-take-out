package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
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
    @Autowired
    private SetMealMapper setmealMapper;

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

    @Override
    public void deleteBatch(List<Long> ids) {
        // 可以一次删除一个菜品，也可以批量删除菜品
        ids.forEach(id -> {
            // 起售中的菜品不能删除
            if (StatusConstant.ENABLE.equals(dishMapper.getByDishId(id).getStatus())) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });

        // 被套餐关联的菜品不能删除
        List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

//        ids.forEach(id -> {
//            // 删除菜品
//            dishMapper.deleteByDishId(id);
//
//            // 除菜品后，关联的口味数据也需要删除掉
//            dishFlavorMapper.deleteByDishId(id);
//        });

        dishMapper.deleteByDishIds(ids);

        dishFlavorMapper.deleteByDishIds(ids);
    }

}
