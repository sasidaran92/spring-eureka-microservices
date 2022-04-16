package com.sasidaran.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sasidaran.moviecatalogservice.models.CatalogItem;
import com.sasidaran.moviecatalogservice.models.Movie;
import com.sasidaran.moviecatalogservice.models.Rating;
import com.sasidaran.moviecatalogservice.services.MovieService;
import com.sasidaran.moviecatalogservice.services.RatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RatingService ratingService;

	@Autowired
	MovieService movieService;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		List<Rating> ratings = ratingService.getRatings(userId);
		// return Collections.singletonList(new CatalogItem("Roja", "1992", 5));
		return ratings.stream().map(rating -> {
			// Movie movie = restTemplate.getForObject("http://localhost:8083/movies/" +
			// rating.getMovieId(), Movie.class);
			Movie movie = movieService.getMovie(rating);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());

	}

}
