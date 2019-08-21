package pl.bartoszostrowski.glandroidcrashcourse.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.bartoszostrowski.glandroidcrashcourse.service.model.Movie;
import pl.bartoszostrowski.glandroidcrashcourse.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static final String TAG = "MovieRepository";

    private MovieDatabaseApi movieDatabaseApi;

    private volatile static MovieRepository INSTANCE = null;

    private long cacheSize = 5*1024*1024;

    private Context context;

    private Cache myCache;

    private OkHttpClient okHttpClient;

    private MovieRepository(final Context context) {
        Log.d(TAG, "MovieRepository: C'tor");

        this.context = context;

        myCache = new Cache(context.getCacheDir(), cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        if (!NetworkUtils.Companion.hasNetwork(context)) {
                            builder.cacheControl(CacheControl.FORCE_CACHE);
                        }

                        return chain.proceed(builder.build());
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseApiConst.API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        movieDatabaseApi = retrofit.create(MovieDatabaseApi.class);
    }

    public synchronized static MovieRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(context);
                }
            }
        }

        return INSTANCE;
    }

    public MutableLiveData<Movie> getMovieById(String id, @NonNull final MovieDatabaseSource.GetMovieCallback callback) {
        Log.d(TAG, "getMovieById: " + id);

        final MutableLiveData<Movie> data = new MutableLiveData<>();

        movieDatabaseApi.getMovieById(id,
                MovieDatabaseApiConst.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                data.setValue(response.body());
                Log.d(TAG, "onResponse: Code = " + response.code());

                callback.onMovieLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                data.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
                callback.onMovieNotAvailable();
            }
        });

        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
