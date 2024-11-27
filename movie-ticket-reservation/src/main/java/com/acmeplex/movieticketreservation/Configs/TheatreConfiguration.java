package com.acmeplex.movieticketreservation.Configs;

import com.acmeplex.movieticketreservation.Model.Theatre;
import com.acmeplex.movieticketreservation.Repository.TheatreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TheatreConfiguration {
    @Bean
    @Order(2)
    CommandLineRunner loadTheatreData(TheatreRepository theatreRepository) {
        return args -> {
            theatreRepository.save(new Theatre("Cineplex Chinook", "6455 Macleod Trail SW", 300));
            theatreRepository.save(new Theatre("Scotiabank Theatre", "120 8 Ave SW", 350));
            theatreRepository.save(new Theatre("Landmark Cinemas", "16061 Macleod Trail SE", 200));
            theatreRepository.save(new Theatre("Globe Cinema", "617 8 Ave SW", 150));
            theatreRepository.save(new Theatre("The Plaza Theatre", "1133 Kensington Rd NW", 100));
            theatreRepository.save(new Theatre("Canyon Meadows Cinemas", "13226 Macleod Trail SE", 250));
            theatreRepository.save(new Theatre("Country Hills Cineplex", "388 Country Hills Blvd NW", 220));
            theatreRepository.save(new Theatre("Eau Claire Market Cinemas", "200 Barclay Parade SW", 180));
            theatreRepository.save(new Theatre("Empire Theatres", "500 Centre St SE", 300));
            theatreRepository.save(new Theatre("Shawnessy Cineplex", "16061 Macleod Trail SE", 280));
        };
    }
}
