package com.dlmu.sell.dao;

import com.dlmu.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
//@Transactional 所有的测试都回滚  不会在数据库中包含测试数据

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("男生最爱",4);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }
    @Test
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("最受女生欢迎");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }


    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        List<ProductCategory> result =  repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }


}