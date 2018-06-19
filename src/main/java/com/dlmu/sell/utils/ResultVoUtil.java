package com.dlmu.sell.utils;

import com.dlmu.sell.Vo.ResultVo;

public class ResultVoUtil {
    public  static ResultVo sucess(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(0);
        resultVo.setMeg("成功");
        return  resultVo;
    }
    public static ResultVo sucess(){
        return sucess(null);
    }
    public  static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMeg(msg);
        return  resultVo;
    }

}
