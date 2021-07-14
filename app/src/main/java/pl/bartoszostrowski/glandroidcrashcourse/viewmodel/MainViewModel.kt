package pl.bartoszostrowski.glandroidcrashcourse.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.bartoszostrowski.glandroidcrashcourse.service.model.Movie
import pl.bartoszostrowski.glandroidcrashcourse.service.repository.MovieDatabaseSource.GetMovieCallback
import pl.bartoszostrowski.glandroidcrashcourse.service.repository.MovieRepository

class MainViewModel(application: Application, private val mMovieRepository: MovieRepository) : AndroidViewModel(application), GetMovieCallback {

    private val _totalPointsLive = MutableLiveData<Int>()
    val totalPointsLive: LiveData<Int>
        get() = _totalPointsLive

    private val _noOfVotesLive = MutableLiveData<Int>()
    val noOfVotesLive: LiveData<Int>
        get() = _noOfVotesLive

    private val _summary = MutableLiveData<String>()
    val summary: LiveData<String>
        get() = _summary

    fun onVoteUp() {
        Log.d(TAG, "onVoteUp: ")
        _noOfVotesLive.postValue(_noOfVotesLive.value!! + 1)
        _totalPointsLive.postValue(_noOfVotesLive.value!! + 1)
    }

    fun onVoteDown() {
        Log.d(TAG, "onVoteDown: ")
        _noOfVotesLive.postValue(noOfVotesLive.value!! + 1)
    }

    fun onReset() {
        Log.d(TAG, "onReset: ")
        _noOfVotesLive.postValue(0)
        _totalPointsLive.postValue(0)
    }

    fun getSummary(id: String) {
//        mMovieRepository.getMovieById(id, this)
    }

    override fun onMovieLoaded(movie: Movie) {
        //TODO: To be checked why it is crashing
//        Log.d(TAG, "onMovieLoaded: Overview = " + movie.getOverview());
//        summary.setValue(movie.getTagline());
    }

    override fun onMovieNotAvailable() {
        _summary.value = "Description not available"
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    init {
        _totalPointsLive.postValue(0)
        _noOfVotesLive.postValue(0)
    }
}