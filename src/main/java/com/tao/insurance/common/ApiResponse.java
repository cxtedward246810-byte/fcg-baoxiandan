package com.tao.insurance.common;

import lombok.Data;

/**
 * 通用 API 返回结构
 */
@Data
public class ApiResponse<T> {

    private int code;       // 0: 成功，非0: 失败
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static <T> ApiResponse<T> fail(String msg) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(201);
        r.setMsg(msg);
        return r;
    }
}
