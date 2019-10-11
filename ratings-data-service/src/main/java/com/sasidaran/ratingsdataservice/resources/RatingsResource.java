package com.sasidaran.ratingsdataservice.resources;

import com.sasidaran.ratingsdataservice.model.Movie;
import com.sasidaran.ratingsdataservice.model.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public List<Rating> getUserRatings(@PathVariable("userId") String userId) {
        return Arrays.asList(
                new Rating("1234", 3),
                new Rating("1235", 4)
        );
    }
}
