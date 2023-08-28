package dev.aiyush.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewBody, String imdbId){
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }
    public List<Review> getReviewsByImdbId(String imdbId) {
        Movie movie = mongoTemplate.findOne(
                Query.query(Criteria.where("imdbId").is(imdbId)),
                Movie.class
        );

        if (movie != null && movie.getReviewIds() != null && !movie.getReviewIds().isEmpty()) {
            List<Review> reviewIds = movie.getReviewIds();
            return mongoTemplate.find(
                    Query.query(Criteria.where("id").in(reviewIds)),
                    Review.class
            );
        }

        return Collections.emptyList();
    }

}
