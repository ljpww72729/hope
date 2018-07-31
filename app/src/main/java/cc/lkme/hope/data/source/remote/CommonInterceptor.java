package cc.lkme.hope.data.source.remote;


import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import cc.lkme.hope.utils.SecurityUtils;
import cc.lkme.hope.utils.SystemInfo;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class CommonInterceptor implements Interceptor {

    @Inject
    SystemInfo systemInfo;
    @Inject
    SecurityUtils securityUtils;

    @Inject
    public CommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder builder = oldRequest.url().newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("timestamp", "1477036774")
                .addQueryParameter("nonce", "5852052")
                .addQueryParameter("signature", securityUtils.getSnssdkSignature("", "", ""))
                .addQueryParameter("partner", "tt")
                .addQueryParameter("udid", systemInfo.getUdid())
                .addQueryParameter("openudid", systemInfo.getOpenUDID())
                .addQueryParameter("os", systemInfo.getOS())
                .addQueryParameter("os_version", systemInfo.getOSVersion())
                .addQueryParameter("device_model", systemInfo.getDeviceModel())
                .addQueryParameter("access_token", "11153829655954023967922");
        Request newRequest = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body())
                .url(builder.build()).build();
        if (oldRequest.url().toString().contains("stream")) {
            newRequest = new Request.Builder().url("http://www.365rili.com/toutiao/articles.do?ac=unknown&udid=12f0d4f54ac72673e6879d6dc90db572&category=__all__").build();
        }
        return chain.proceed(newRequest);
    }
}
