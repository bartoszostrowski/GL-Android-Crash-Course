package pl.bartoszostrowski.glandroidcrashcourse.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import pl.bartoszostrowski.glandroidcrashcourse.R;
import pl.bartoszostrowski.glandroidcrashcourse.databinding.ActivityMainBinding;
import pl.bartoszostrowski.glandroidcrashcourse.viewmodel.MainViewModel;
import pl.bartoszostrowski.glandroidcrashcourse.viewmodel.ViewModelFactory;

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

        viewModel = obtainViewModel(this);

        binding.setModel(viewModel);

        binding.setLifecycleOwner(this);

        viewModel.getNoOfVotesLive().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer votes) {
                Log.d(TAG, "onChanged: No of votes = " + votes);
            }
        });

        viewModel.getTotalPointsLive().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer points) {
                Log.d(TAG, "onChanged: Total points = " + points);
            }
        });

        viewModel.getSummary("278");
    }

    protected static MainViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }
}
