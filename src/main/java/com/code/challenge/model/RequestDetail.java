package com.code.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestDetail {

    private RequestType type;

    private String firstParam;

    private String secondParam;

    public static RequestDetail of(RequestType type, String firstParam, String secondParam) {
        return new RequestDetail(type, firstParam, secondParam);
    }
}
