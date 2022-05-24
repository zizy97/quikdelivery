package com.quikdeliver.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GlobalError implements Serializable {
    @Override
    public String toString() {
        return "GlobalError{" +
                "code='" + code + '\'' +
                ", fields=" + fields +
                ", userMessage='" + userMessage + '\'' +
                '}';
    }

    private static final long serialVersionUID = 8155630775174930513L;
    private String code;
    private List<String> fields;
    private String userMessage;
}
