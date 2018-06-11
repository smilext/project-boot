package com.dlmu.sell.server.impl;

import com.dlmu.sell.dataobject.ProductCategory;
import com.dlmu.sell.server.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory result = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),result.getCategoryId());
    }

    @Test
    public void findAll() {
       List<ProductCategory> list =  categoryService.findAll();
       Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list =  categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("女王专享",5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}