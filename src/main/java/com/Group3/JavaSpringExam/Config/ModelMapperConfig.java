package com.Group3.JavaSpringExam.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
        .setSkipNullEnabled(true)  // Skip null fields during mapping
        .setMatchingStrategy(MatchingStrategies.STRICT);
    return mapper;
  }
}
