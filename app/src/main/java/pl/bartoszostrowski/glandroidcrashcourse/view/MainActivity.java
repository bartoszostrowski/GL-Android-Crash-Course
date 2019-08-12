package pl.bartoszostrowski.glandroidcrashcourse.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import pl.bartoszostrowski.glandroidcrashcourse.R;
import pl.bartoszostrowski.glandroidcrashcourse.databinding.ActivityMainBinding;
import pl.bartoszostrowski.glandroidcrashcourse.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @BindingAdapter("app:goneUnless")
    public static void goneUnless(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setModel(viewModel);

        binding.setLifecycleOwner(this);

        // 1. Worst approach: findViewById()
//         TextView titleView = findViewById(R.id.textViewMovieTitle);
//         titleView.setText("Goodfellas");

        // 2. Better approach
//        binding.textViewMovieTitle.setText("Goodfellas");
//        binding.imageViewPoster.setImageResource(R.drawable.poster_goodfellas);
//        binding.buttonVoteUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: The best movie I have watched!");
//            }
//        });

        viewModel.noOfVotesLive.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer votes) {
                Log.d(TAG, "onChanged: No of votes = " + votes);
            }
        });

        viewModel.totalPointsLive.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer points) {
                Log.d(TAG, "onChanged: Total points = " + points);
            }
        });

        // The best approach, XML file
        // ...
    }
}
