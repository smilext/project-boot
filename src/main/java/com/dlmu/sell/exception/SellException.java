package com.dlmu.sell.exception;

import com.dlmu.sell.enums.ResultEnum;

public class SellException extends RuntimeException {
 private  Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}

