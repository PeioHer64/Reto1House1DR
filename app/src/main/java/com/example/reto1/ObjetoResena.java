package com.example.reto1;

import java.util.Date;

public class ObjetoResena {
    private String correoObjeto;
    private String resenaObjeto;
    private float puntuacionObjeto;
    private Date fechaObjeto;

    public ObjetoResena(){}
    public ObjetoResena(String correoObjeto,String resenaObjeto,float puntuacionObjeto,Date fechaObjeto){
        correoObjeto=this.correoObjeto;
        resenaObjeto=this.resenaObjeto;
        puntuacionObjeto=this.puntuacionObjeto;
        fechaObjeto=this.fechaObjeto;
    }

    public String getCorreoObjeto() {
        return correoObjeto;
    }

    public void setCorreoObjeto(String correoObjeto) {
        this.correoObjeto = correoObjeto;
    }

    public String getResenaObjeto() {
        return resenaObjeto;
    }

    public void setResenaObjeto(String resenaObjeto) {
        this.resenaObjeto = resenaObjeto;
    }

    public float getPuntuacionObjeto() {
        return puntuacionObjeto;
    }

    public void setPuntuacionObjeto(float puntuacionObjeto) {
        this.puntuacionObjeto = puntuacionObjeto;
    }

    public Date getFechaObjeto() {
        return fechaObjeto;
    }

    public void setFechaObjeto(Date fechaObjeto) {
        this.fechaObjeto = fechaObjeto;
    }
}
