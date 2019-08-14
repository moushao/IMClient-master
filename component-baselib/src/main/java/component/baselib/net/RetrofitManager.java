package component.baselib.net;


import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import component.baselib.BuildConfig;
import component.baselib.net.convert.CustomGsonConverterFactory;
import component.baselib.net.convert.CustomInterceptor;
import component.data.common.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 类名: {@link RetrofitManager}
 * <br/> 功能描述:集中处理Api相关配置的Manager类
 * <br/> 作者: MouShao
 * <br/> 时间: 2016/9/8
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class RetrofitManager {

    public OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    public RetrofitManager() {

    }


    @NonNull
    public Retrofit getRetrofit() {
        String baseUrl = BuildConfig.DEBUG ? Constants.BASE_URL_DEBUG : Constants.BASE_URL_RELEASE;
        return new Retrofit.Builder()
                .client(mClient)
                .baseUrl(baseUrl)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
