package com.dlmu.sell.controller;

import com.dlmu.sell.Vo.ProductInfoVo;
import com.dlmu.sell.Vo.ProductVo;
import com.dlmu.sell.Vo.ResultVo;
import com.dlmu.sell.dataobject.ProductCategory;
import com.dlmu.sell.dataobject.ProductInfo;
import com.dlmu.sell.server.CategoryService;
import com.dlmu.sell.server.ProductService;
import com.dlmu.sell.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResultVo list(){
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2.查询类目
        List<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList =  categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼接
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
             ProductVo productVo = new ProductVo();
             productVo.setCategoryType(productCategory.getCategoryType());
             productVo.setCategoryName(productCategory.getCategoryName());


             List<ProductInfoVo> productInfoVoList = new ArrayList<>();
             for (ProductInfo productInfo:productInfoList){
                 if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                     ProductInfoVo productInfoVo = new ProductInfoVo();
                     BeanUtils.copyProperties(productInfo,productInfoVo);
                     productInfoVoList.add(productInfoVo);

                 }
             }
             productVo.setProductInfoVoList(productInfoVoList);
             productVoList.add(productVo);
        }


        return ResultVoUtil.sucess(productVoList);
    }
}
