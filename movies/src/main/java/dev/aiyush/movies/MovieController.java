package dev.aiyush.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "http://localhost:3000/")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.allmovies() , HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    @GetMapping("/reviews")
    //http://localhost:8080/api/v1/movies/reviews?imdbId=tt3915174 -->> Example
    public List<Review> getReviewFromMovieByImdb(@RequestParam String imdbId) {
        return movieService.getReviewsByImdbId(imdbId);
    }
}
