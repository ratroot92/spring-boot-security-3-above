package com.example.configvaultserver.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCreateEntity {
    // private String message;
    private Object data;

    ApiCreateEntity(Object data) {
        // this.message = message;
        this.data = data;
    }

}
