package com.sasidaran.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sasidaran.moviecatalogservice.models.Movie;
import com.sasidaran.moviecatalogservice.models.Rating;

@Service
public class MovieService {

	@Autowired
	WebClient.Builder webClientBuilder;

	@HystrixCommand(fallbackMethod = "getFallbackMovie", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), })
	public Movie getMovie(Rating rating) {
		Movie movie = webClientBuilder.build().get().uri("http://movie-info-service/movies/" + rating.getMovieId())
				.retrieve().bodyToMono(Movie.class).block();
		return movie;
	}

	public Movie getFallbackMovie(Rating rating) {
		return new Movie("Movie name not found", "");
	}

}
