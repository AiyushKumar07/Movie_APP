package dev.aiyush.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> allmovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public List<Review> getReviewsByImdbId(String imdbId) {
        Optional<Movie> movieOptional = movieRepository.findMovieByImdbId(imdbId);
        if(movieOptional.isPresent()) {

            Movie movie = movieOptional.get();


            if (movie != null && movie.getReviewIds() != null && !movie.getReviewIds().isEmpty()) {
                return movie.getReviewIds();
            }
        }

        return Collections.emptyList();
    }
}
