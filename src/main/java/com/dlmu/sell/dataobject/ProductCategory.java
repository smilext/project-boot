package com.dlmu.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// @DynamicUpdate 自动更新时间
//  引入插件lombok，然后加上@Data可以自动生成更get set tostring等方法
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /** 类目id. */
   @Id
   @GeneratedValue
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
