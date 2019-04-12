package ru.diasoft.friendsphoto.network.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.diasoft.friendsphoto.utils.ConstantManager;

public class RetrofitService {

    private static RetrofitService mInstance;
//    private static final String BASE_URL = "http://api.beauty.dikidi.ru/";
    private Retrofit mRetrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private RetrofitService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstantManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.addInterceptor(interceptor).build())
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
