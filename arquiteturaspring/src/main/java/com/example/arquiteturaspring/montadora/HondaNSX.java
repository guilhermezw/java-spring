package com.example.arquiteturaspring.montadora;

import java.awt.*;

public class HondaNSX extends Carro {

    public HondaNSX(Motor motor) {
        super(motor);
        setModelo("NSX");
        setCor((Color.BLACK));
        setMontadora(Montadora.HONDA);

    }


}
