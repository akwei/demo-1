package com.github.akwei.demo1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrResponse {
    private Integer httpCode;
    private Integer errorCode;
    private String httpMessage;
    private String errorMessage;
}
