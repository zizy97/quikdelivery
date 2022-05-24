package com.quikdeliver.advice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class APIError implements Serializable {
    @Override
    public String toString() {
        return "APIError{" +
                "fieldsErrors=" + fieldsErrors +
                ", globalErrors=" + globalErrors +
                '}';
    }

    private static final long serialVersionUID = -5130054537377087224L;
    private List<FieldError> fieldsErrors = new ArrayList<>();
    private List<GlobalError> globalErrors = new ArrayList<>();
    private CommonError commonError;

    public APIError addFieldError(String code, String fieldName, String userMessage, String rejectedValue) {
        this.fieldsErrors.add(new FieldError(code, fieldName, userMessage, rejectedValue));
        return this;
    }

    public APIError addGlobalError(String code, List<String> fields, String userMessage) {
        this.globalErrors.add(new GlobalError(code, fields, userMessage));
        return this;
    }

    public APIError addCommonError(String cause) {
        this.commonError=new CommonError(cause);
        return this;
    }
}
