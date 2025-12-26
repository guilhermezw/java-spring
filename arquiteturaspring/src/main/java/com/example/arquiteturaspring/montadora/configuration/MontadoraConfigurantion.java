package com.example.arquiteturaspring.montadora.configuration;

import com.example.arquiteturaspring.montadora.Motor;
import com.example.arquiteturaspring.montadora.TipoMotor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.beans.beancontext.BeanContext;

@Configuration
public class MontadoraConfigurantion {

    // Um bean é qualquer objeto que é instanciado, montado e gerenciado pelo container Spring
    // Para declarar um bean, você geralmente utiliza a anotação @Bean dentro de uma classe anotada com @Configuration

    @Bean(name = "motorAspirado")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Motor motorAspirado(@Value("${app.montadora.motor-padrao}") Integer cavalos){
        var motor = new Motor();
        motor.setCavalo(cavalos);
        motor.setCilindro(4);
        motor.setModelo("TSX-01");
        motor.setLitragem(2.0);
        motor.setMotor(TipoMotor.ASPIRADO);
        return motor;
    }


    @Bean(name = "motorEletrico")
    public Motor motorEletrico(){
        var motor = new Motor();
        motor.setCavalo(150);
        motor.setCilindro(3);
        motor.setModelo("TSX-02");
        motor.setLitragem(1.0);
        motor.setMotor(TipoMotor.ELETRICO);
        return motor;
    }

    @Primary // no Spring é utilizada para definir um bean padrão quando há múltiplas instâncias do mesmo tipo disponíveis para injeção.
    @Bean(name = "motorTurbo")
    public Motor motorTurbo(){
        var motor = new Motor();
        motor.setCavalo(350);
        motor.setCilindro(5);
        motor.setModelo("TSX-02");
        motor.setLitragem(2.0);
        motor.setMotor(TipoMotor.TURBO);
        return motor;
    }
}
