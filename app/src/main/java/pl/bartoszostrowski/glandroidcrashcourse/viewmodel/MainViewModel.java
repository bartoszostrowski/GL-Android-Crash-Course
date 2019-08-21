package pl.bartoszostrowski.glandroidcrashcourse.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import pl.bartoszostrowski.glandroidcrashcourse.service.model.Movie;
import pl.bartoszostrowski.glandroidcrashcourse.service.repository.MovieDatabaseSource;
import pl.bartoszostrowski.glandroidcrashcourse.service.repository.MovieRepository;

public class MainViewModel extends AndroidViewModel implements MovieDatabaseSource.GetMovieCallback {
    private static final String TAG = "MainActivity";

    private MovieRepository mMovieRepository;

    public MutableLiveData<Integer> noOfVotesLive;

    public MutableLiveData<Integer> totalPointsLive;

    public MutableLiveData<String> summary;

    public MainViewModel(@NonNull Application application, MovieRepository movieRepository) {
        super(application);

        mMovieRepository = movieRepository;

        totalPointsLive = new MutableLiveData<>();
        noOfVotesLive = new MutableLiveData<>();

        summary = new MutableLiveData<>();

        totalPointsLive.setValue(0);
        noOfVotesLive.setValue(0);
    }

    public void onVoteUp() {
        Log.d(TAG, "onVoteUp: ");

        noOfVotesLive.setValue(noOfVotesLive.getValue() + 1);
        totalPointsLive.setValue(totalPointsLive.getValue() + 1);
    }

    public void onVoteDown() {
        Log.d(TAG, "onVoteDown: ");

        noOfVotesLive.setValue(noOfVotesLive.getValue() + 1);
    }

    public void onReset() {
        Log.d(TAG, "onReset: ");
        noOfVotesLive.setValue(0);
        totalPointsLive.setValue(0);
    }

    public void getSummary(String id) {
        mMovieRepository.getMovieById(id, this);
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        Log.d(TAG, "onMovieLoaded: Overview = " + movie.getOverview());
        summary.setValue(movie.getTagline());
    }

    @Override
    public void onMovieNotAvailable() {
        summary.setValue("Description not available");
    }
}
