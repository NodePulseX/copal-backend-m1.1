package com.wangxinye.copal.exception;

import com.wangxinye.copal.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * 全局异常处理器
 * 统一捕获并处理项目中各类异常，返回标准化响应
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理用户名/密码错误异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    public R<Void> handleBadCredentialsException(BadCredentialsException e) {
        log.error("认证失败：{}", e.getMessage());
        return R.fail("用户名或密码错误");
    }

    /**
     * 处理用户不存在异常
     */
    @ExceptionHandler(UserNotFoundException.class)
    public R<Void> handleUserNotFoundException(UserNotFoundException e) {
        log.error("认证失败：{}", e.getMessage());
        return R.fail(e.getMessage());
    }

    /**
     * 处理账户禁用异常
     */
    @ExceptionHandler(DisabledException.class)
    public R<Void> handleDisabledException(DisabledException e) {
        log.error("认证失败：{}", e.getMessage());
        return R.fail("账户已禁用，请联系管理员");
    }

    /**
     * 处理Token无效/过期异常
     */
    @ExceptionHandler(InvalidTokenException.class)
    public R<Void> handleInvalidTokenException(InvalidTokenException e) {
        log.error("Token异常：{}", e.getMessage());
        return R.fail(401, e.getMessage());
    }

    /**
     * 处理请求方式不支持异常（核心新增）
     * 解决GET请求POST接口的报错
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String supportedMethods = Arrays.toString(e.getSupportedMethods());
        String errorMsg = String.format("请求方式错误！该接口仅支持 %s 请求", supportedMethods);
        log.error("请求方式异常：{}", errorMsg);
        return R.fail(405, errorMsg); // 405 = Method Not Allowed
    }

    /**
     * 处理参数校验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder("参数校验失败：");

        // 遍历所有参数错误，拼接提示
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsg.append(fieldError.getField())
                    .append("：")
                    .append(fieldError.getDefaultMessage())
                    .append("，");
        }

        // 移除最后一个逗号
        String finalMsg = errorMsg.length() > 0 ? errorMsg.substring(0, errorMsg.length() - 1) : "参数校验失败";
        log.error("参数校验异常：{}", finalMsg);
        return R.fail(400, finalMsg); // 400 = Bad Request
    }

    /**
     * 处理所有未捕获的通用异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("系统异常：", e); // 必须加第二个参数e，打印完整栈信息
        return R.fail("系统内部错误，请联系管理员");
    }
    // ---------------------- 补充自定义异常类（如果项目中未定义） ----------------------
    // 若项目中已存在这些异常类，可删除以下代码
    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}