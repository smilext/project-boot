package com.dlmu.sell;

import com.dlmu.sell.dao.ProductCategoryRepository;
import com.dlmu.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellApplicationTests {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
     ProductCategory pc = repository.findOne(1);
     System.out.println(pc.toString());

    }

}
