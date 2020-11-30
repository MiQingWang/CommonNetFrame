package com.mi.qing.common.net.frame;

import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * 框架自有拦截器，处理外部传递header信息
 */
public class NetworkInterceptor implements okhttp3.Interceptor {
    //    private static final String FOR_NAME = "UTF-8";
    private static final String TAG = NetworkInterceptor.class.getSimpleName();
    //    private static final Charset UTF8 = Charset.forName(FOR_NAME);
    private final List<NetworkHeaderParams> networkHeaderParamsList;

    /**
     * 初始化设置外部自定义header信息接口集合
     *
     * @param networkHeaderParamsList 自定义header信息接口集合
     */
    protected NetworkInterceptor(List<NetworkHeaderParams> networkHeaderParamsList) {
        this.networkHeaderParamsList = networkHeaderParamsList;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        //框架内部设置外部自定义header头信息
        if (networkHeaderParamsList != null && networkHeaderParamsList.size() > 0) {
            for (NetworkHeaderParams networkHeaderParams : networkHeaderParamsList) {
                addHeaderParams(request, requestBuilder, networkHeaderParams);
            }
        }
        return chain.proceed(requestBuilder.build());

    }

    /**
     * 封装的设置header头信息方法
     *
     * @param request             HTTP 请求
     * @param networkHeaderParams 自定义header信息接口
     */
    private void addHeaderParams(Request request, Request.Builder requestBuilder, NetworkHeaderParams networkHeaderParams) {
        String paramStr = request.header(networkHeaderParams.paramsName());
        boolean isAddHeader = false;
        if (!TextUtils.isEmpty(paramStr)
                && NetworkLoader.DEFAULT_IS_ADD_HEADER.equals(paramStr)) {
            isAddHeader = true;
        }
        if (isAddHeader) {
            requestBuilder
                    .addHeader(networkHeaderParams.headerName(), networkHeaderParams.headerValue())
                    .removeHeader(networkHeaderParams.paramsName());
        } else {
            requestBuilder
                    .removeHeader(networkHeaderParams.paramsName());
        }
    }

}