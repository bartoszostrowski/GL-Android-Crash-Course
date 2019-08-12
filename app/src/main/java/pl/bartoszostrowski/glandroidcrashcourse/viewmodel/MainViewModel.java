package pl.bartoszostrowski.glandroidcrashcourse.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivity";

//    public int noOfVotes = 0;
//    public int totalPoints = 0;

    public MutableLiveData<Integer> noOfVotesLive;

    public MutableLiveData<Integer> totalPointsLive;

    public MainViewModel(@NonNull Application application) {
        super(application);

        totalPointsLive = new MutableLiveData<>();
        noOfVotesLive = new MutableLiveData<>();

        totalPointsLive.setValue(0);
        noOfVotesLive.setValue(0);
    }

    public void onVoteUp() {
        Log.d(TAG, "onVoteUp: ");

        // Increase number of votes and total points
//        noOfVotes++;
//        totalPoints++;

        //LiveData
        noOfVotesLive.setValue(noOfVotesLive.getValue() + 1);
        totalPointsLive.setValue(totalPointsLive.getValue() + 1);
    }

    public void onVoteDown() {
        Log.d(TAG, "onVoteDown: ");

        // Increase number of votes only
//        noOfVotes++;

        //LiveData
        noOfVotesLive.setValue(noOfVotesLive.getValue() + 1);
    }

    public void onReset() {
        Log.d(TAG, "onReset: ");
        noOfVotesLive.setValue(0);
        totalPointsLive.setValue(0);
    }
}
