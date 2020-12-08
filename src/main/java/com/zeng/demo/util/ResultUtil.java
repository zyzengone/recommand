package com.zeng.demo.util;

import com.zeng.demo.pojo.Result;

/**
 * @ClassName: ResultUtil
 * @Description: TODO
 * @author: yourname
 * @date: 2020年12月08日 下午7:13
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setResult(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}