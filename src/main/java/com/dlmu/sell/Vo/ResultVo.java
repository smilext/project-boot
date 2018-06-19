package com.dlmu.sell.Vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    //错误码
    private  Integer code;
    //提示信息
    private String meg;
   //具体内容
    private T data;

}
