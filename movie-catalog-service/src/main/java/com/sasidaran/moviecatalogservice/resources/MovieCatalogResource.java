package com.sasidaran.moviecatalogservice.resources;

import com.sasidaran.moviecatalogservice.models.CatalogItem;
import com.sasidaran.moviecatalogservice.models.Movie;
import com.sasidaran.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        Rating[] ratingsArr = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, Rating[].class);
        List<Rating> ratings = Arrays.asList(ratingsArr);
        //return Collections.singletonList(new CatalogItem("Roja", "1992", 5));
        return ratings.stream().map(rating -> {
            //Movie movie = restTemplate.getForObject("http://localhost:8083/movies/" + rating.getMovieId(), Movie.class);
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://movie-info-service/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
            return new CatalogItem(movie.getName(), "1992", rating.getRating());
        }).collect(Collectors.toList());



    }
}
