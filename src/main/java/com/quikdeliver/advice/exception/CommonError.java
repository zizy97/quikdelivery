package com.quikdeliver.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommonError implements Serializable {
    private String cause;
}
