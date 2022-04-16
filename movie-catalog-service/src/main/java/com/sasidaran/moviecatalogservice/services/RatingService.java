package com.sasidaran.moviecatalogservice.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sasidaran.moviecatalogservice.models.Rating;

@Service
public class RatingService {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackRatings", threadPoolKey = "movieRatingsPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"), @HystrixProperty(name = "maxQueueSize", value = "10") })
	public List<Rating> getRatings(String userId) {
		Rating[] ratingsArr = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId,
				Rating[].class);
		return Arrays.asList(ratingsArr);
	}

	public List<Rating> getFallbackRatings(String userId) {
		Rating rating = new Rating();
		rating.setRating(0);
		return Arrays.asList(rating);
	}
}
