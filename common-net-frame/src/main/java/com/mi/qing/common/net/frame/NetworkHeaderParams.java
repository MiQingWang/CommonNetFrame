package com.mi.qing.common.net.frame;

public interface NetworkHeaderParams {

    //retrofit header中配置的参数名称
    //  @Headers({
    //            "token:true"
    //    })
    String paramsName();

    //网络请求头中设置的header头参数名
    String headerName();

    //网络请求头中设置的header头中的值
    String headerValue();

}
