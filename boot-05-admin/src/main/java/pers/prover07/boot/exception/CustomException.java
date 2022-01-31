package pers.prover07.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常，通过 @ResponseStatus 注解标识如果出现该异常应该如何响应(code: 响应码, reason: 响应信息)
 * @author by Prover07
 * @classname CustomeException
 * @description TODO
 * @date 2022/1/31 16:00
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "这是一个错误的请求")
public class CustomException extends Exception{

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}
