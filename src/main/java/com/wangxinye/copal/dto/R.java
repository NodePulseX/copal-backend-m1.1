package com.wangxinye.copal.dto;

import lombok.Data;

@Data
public class R<T> {
    private int code;
    private String message;
    private T data;

    // 必须手动添加带参数的构造函数（@Data只生成无参构造）
    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功响应（带数据）
    public static <T> R<T> success(T data) {
        return new R<>(200, "操作成功", data);
    }

    // 新增：成功响应（带自定义消息，无数据）
    public static <T> R<T> success(String message) {
        return new R<>(200, message, null);
    }

    // 成功响应（无数据，默认消息）
    public static <T> R<T> success() {
        return new R<>(200, "操作成功", null);
    }

    // 失败响应（默认500）
    public static <T> R<T> fail(String message) {
        return new R<>(500, message, null);
    }

    // 失败响应（自定义响应码）
    public static <T> R<T> fail(int code, String message) {
        return new R<>(code, message, null);
    }
}