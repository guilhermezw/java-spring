package com.example.arquiteturaspring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class ArquiteturaspringApplication {

    public static void main(String[] args) {

        // 1. Capturamos o Container IoC (ApplicationContext) que o run() retorna
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ArquiteturaspringApplication.class, args);

        // 2. Agora podemos usar o objeto applicationContext para buscar o Bean
        ExemploValue value = applicationContext.getBean(ExemploValue.class);
        value.imprimirVariavel();

        AppProperties properties = applicationContext.getBean(AppProperties.class);
        System.out.println(properties.getText());

        // Boas Práticas: Sempre feche o contexto após terminar o uso no main.
        applicationContext.close();
    }
}