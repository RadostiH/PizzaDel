package sapproject.pizzadelivery.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sapproject.pizzadelivery.domain.models.service.CategoryServiceModel;
import sapproject.pizzadelivery.mappings.MappingsInitializer;
import sapproject.pizzadelivery.service.CategoryService;

@Configuration
public class AplicationBeanConfiguration {

    static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        MappingsInitializer.initMappings(mapper);
    }

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }



}
