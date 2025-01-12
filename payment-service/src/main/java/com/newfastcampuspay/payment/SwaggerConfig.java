package com.newfastcampuspay.payment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("MyFastcampusPay API")
                .description("MyFastcampusPay API 목록입니다.");

//        SecurityScheme accessTokensecurityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER).name("Authorization");
//
//        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("Authorization");
//
//        SecurityRequirement securityRequirement = new SecurityRequirement()
//                .addList("bearerAuth")
//                .addList("refreshTokenAuth");
//
//        Components components = new Components()
//                .addSecuritySchemes("bearerAuth", accessTokensecurityScheme)
//                .addSecuritySchemes("refreshTokenAuth", refreshTokenSecurityScheme);

        return new OpenAPI()
//                .components(components)
//                .security(Arrays.asList(securityRequirement))
                .info(info);
    }

//    @Bean
//    ForwardedHeaderFilter forwardedHeaderFilter() {
//        return new ForwardedHeaderFilter();
//    }
}
