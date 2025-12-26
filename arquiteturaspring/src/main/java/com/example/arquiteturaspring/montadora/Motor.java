package com.example.arquiteturaspring.montadora;

public class Motor {

    private String modelo;
    private Integer cavalo;
    private Integer cilindro;
    private Double litragem;
    private TipoMotor motor;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCavalo() {
        return cavalo;
    }

    public void setCavalo(Integer cavalo) {
        this.cavalo = cavalo;
    }

    public Integer getCilindro() {
        return cilindro;
    }

    public void setCilindro(Integer cilindro) {
        this.cilindro = cilindro;
    }

    public Double getLitragem() {
        return litragem;
    }

    public void setLitragem(Double litragem) {
        this.litragem = litragem;
    }

    public TipoMotor getMotor() {
        return motor;
    }

    public void setMotor(TipoMotor motor) {
        this.motor = motor;
    }

    @Override
    public String toString() {
        return "Motor [" +
                "Modelo = " + modelo + '\'' +
                ", Cavalo = " + cavalo +
                ", Cilindro = " + cilindro +
                ", Litragem = " + litragem +
                ", Motor = " + motor +
                ']';
    }
}
