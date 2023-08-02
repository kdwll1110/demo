package com.hyf.demo.result;

import com.hyf.demo.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    //响应状态
    private Boolean success;

    //响应码
    private Integer code;

    //响应信息
    private String msg;

    //响应信息
    private Object data;


    public static Result success(){
        return new Result(true,200,CommonConstant.OPERATE_SUCCESS,null);
    }

    public static Result success(String msg){
        return new Result(true,200,msg,null);
    }

    public static Result success(Object data){
        return new Result(true,200,CommonConstant.OPERATE_SUCCESS,data);
    }

    public static Result success(String msg,Object data){
        return new Result(true,200,msg,data);
    }


    public static Result fail(String msg){
        return new Result(false,null,msg,null);
    }

    public static Result fail(Integer code,String msg){
        return new Result(false,code,msg,null);
    }

    public static Result fail(Integer code,String msg,Object data){
        return new Result(false,code,msg,data);
    }



}
