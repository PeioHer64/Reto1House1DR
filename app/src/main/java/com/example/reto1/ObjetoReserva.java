package com.example.reto1;

import java.util.Date;

public class ObjetoReserva {
    private String correoObjeto;
    private String citaObjeto;
    private String fechaObjeto;
    private String motivoCitaObjeto;

    public ObjetoReserva(){}
    public ObjetoReserva(String correoObjeto,String citaObjeto,String fechaObjeto, String motivoCitaObjeto){
        correoObjeto=this.correoObjeto;
        citaObjeto=this.citaObjeto;
        fechaObjeto=this.fechaObjeto;
        motivoCitaObjeto=this.motivoCitaObjeto;
    }

    public String getCorreoObjeto() {
        return correoObjeto;
    }

    public void setCorreoObjeto(String correoObjeto) {
        this.correoObjeto = correoObjeto;
    }

    public String getCitaObjeto() {
        return citaObjeto;
    }

    public void setCitaObjeto(String citaObjeto) {
        this.citaObjeto = citaObjeto;
    }

    public String getFechaObjeto() {
        return fechaObjeto;
    }

    public void setFechaObjeto(String fechaObjeto) {
        this.fechaObjeto = fechaObjeto;
    }

    public String getMotivoCitaObjeto() {
        return motivoCitaObjeto;
    }

    public void setMotivoCitaObjeto(String motivoCitaObjeto) {
        this.motivoCitaObjeto = motivoCitaObjeto;
    }
}
