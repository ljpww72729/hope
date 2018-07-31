package cc.lkme.hope.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import cc.lkme.hope.data.LiveDataCallAdapterFactory;
import cc.lkme.hope.data.source.HopeRetrofitService;
import cc.lkme.hope.data.source.local.HopeDao;
import cc.lkme.hope.data.source.local.HopeDatabase;
import cc.lkme.hope.data.source.remote.CommonInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModuleModule.class)
public class AppModule {
    @Singleton
    @Provides
    public HopeRetrofitService provideHopeRetrofitService(CommonInterceptor commonInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        httpClient.addInterceptor(commonInterceptor);
        return new Retrofit.Builder()
                .baseUrl("http://open.snssdk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
                .create(HopeRetrofitService.class);
    }


    @Singleton
    @Provides
    HopeDatabase provideDatabase(Application app) {
        return Room.databaseBuilder(app,
                HopeDatabase.class, "Hope.db")
                .build();
    }

    @Singleton
    @Provides
    HopeDao provideHopeDao(HopeDatabase db) {
        return db.hopeDao();
    }

}
