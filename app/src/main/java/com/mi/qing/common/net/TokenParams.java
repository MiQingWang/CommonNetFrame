package com.mi.qing.common.net;

import com.mi.qing.common.net.frame.NetworkHeaderParams;

public class TokenParams implements NetworkHeaderParams {
    @Override
    public String paramsName() {
        return "token";
    }

    @Override
    public String headerName() {
        return "CustomToken";
    }

    @Override
    public String headerValue() {
        return "{"+System.currentTimeMillis() +"}";
    }
}
