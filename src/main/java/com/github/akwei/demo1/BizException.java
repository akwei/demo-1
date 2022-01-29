package com.github.akwei.demo1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
