package com.github.marceloleite2604.pitanga.configurartion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class PitangaConfiguration {

    @Bean(BeanNames.RANDOM)
    public Random createRandom() {
        return new Random();
    }
}
