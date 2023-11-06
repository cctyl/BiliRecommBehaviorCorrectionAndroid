package io.github.bilirecommand.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceCreator {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://10.0.8.10:9000";
    private static final Map<Class,Object> serviceCache = new HashMap<>();
    static {
        //设置全局的请求头
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                ;
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        //构建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    private RetrofitServiceCreator() {
    }

    /**
     * 服务构建器
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static<T> T create(Class<T> serviceClass){
        Object o = serviceCache.get(serviceClass);
        if (o!=null){
            return (T) o;
        }else {
            return retrofit.create(serviceClass);
        }
    }
}
