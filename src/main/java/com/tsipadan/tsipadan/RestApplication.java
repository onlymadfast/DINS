package com.tsipadan.tsipadan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RestApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestApplication.class, args);
  }

  /**
   * Adding swagger bean, for more representation
   * of Rest API methods with pretty documentation
   *
   * @return Docket object
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.tsipadan.tsipadan"))
        .paths(PathSelectors.any())
        .build().apiInfo(metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Тестовое задание в DINS (Intern Java Developer (Collaboration Tool))\n")
        .description("Программа должна предоставлять REST API для:\n"
            + "* получения списка всех пользователей (владельцев телефонных книжек)\n"
            + "* создания, получения (по id), удаления, редактирования пользователя\n"
            + "* создания, получения (по id), удаления, редактирования записи в телефонной книжке\n"
            + "* получения списка всех записей в телефонной книжке пользователя\n"
            + "* поиск пользователей по имени (или его части)*, поиск телефонной записи по номеру телефона\n")
        .version("1.0")
        .build();
  }

}
