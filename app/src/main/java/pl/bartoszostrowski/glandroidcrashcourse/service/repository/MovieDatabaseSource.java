package pl.bartoszostrowski.glandroidcrashcourse.service.repository;

import pl.bartoszostrowski.glandroidcrashcourse.service.model.Movie;

public interface MovieDatabaseSource {

    interface GetMovieCallback {

        void onMovieLoaded(Movie movie);

        void onMovieNotAvailable();
    }
}