package ru.diasoft.friendsphoto.network.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.diasoft.friendsphoto.utils.ConstantManager;

/**
 * Class realizes singleton for retrofit
 */
public class RetrofitService {

    private static RetrofitService mInstance;
    private static Retrofit.Builder sBuilder =
        new Retrofit.Builder()
                .baseUrl(ConstantManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    private Retrofit mRetrofit;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private RetrofitService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        mRetrofit = sBuilder
                .client(httpClient.build())
                .build();
    }


    public static RetrofitService getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitService();
        }
        return mInstance;
    }

    public RestService getJSONApi() {
        return mRetrofit.create(RestService.class);
    }
}
