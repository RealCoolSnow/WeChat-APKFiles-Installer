package app.wxapkfiles.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.wxapkfiles.Constants;
import app.wxapkfiles.api.IApi;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by coolsnow on 2019/2/26.
 */
public class RetrofitFactory {
    private static final long TIMEOUT = 20;

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
                Request oldRequest = chain.request();
                HttpUrl.Builder httpUrlBuilder = chain.request().url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host());
//                if (!oldRequest.url().toString().contains("login/api")) {
//                    httpUrlBuilder.addQueryParameter(AccountUtil.getInstance().getSessionName(), AccountUtil.getInstance().getSessionId());
//                }
//                String lang = LocaleHelper.getLang(Applicatioin.getInstance()).toString();
//                httpUrlBuilder.addQueryParameter("lang", lang);
                Request request = chain.request().newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(httpUrlBuilder.build())
//                        .addHeader("lang", lang)
//                        .addHeader("app", Application.getInstance().getPackageName())
//                        .addHeader("version", VersionUtil.getVersionName(Application.getInstance()))
//                        .addHeader("appid", Constants.APPID)
                        .build();
                return chain.proceed(request);
            })
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(Constants.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    Logger.d("cookie", "saveCookieFromResponse " + url.toString() + " " + cookies.toString());
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = new ArrayList<>();
//                    String strCookie = AccountUtil.getInstance().getSessionId();
//                    if (!TextUtils.isEmpty(strCookie)) {
//                        cookies.add(new Cookie.Builder().name(AccountUtil.getInstance().getSessionName()).value(strCookie).domain(url.host()).build());
//                    }
                    Logger.d("cookie", "loadCookieForRequest " + url.host() + "  " + (cookies == null ? "" : cookies.toString()));
                    return cookies;
                }
            });
    private static OkHttpClient httpClient = RetrofitUrlManager.getInstance().with(httpClientBuilder)
            .build();
    private static IApi retrofitService = new Retrofit.Builder()
            .baseUrl(Constants.getHost())
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(IApi.class);

    public static IApi getInstance() {
        return retrofitService;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }
}
