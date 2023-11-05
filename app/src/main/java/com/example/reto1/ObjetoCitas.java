package com.example.reto1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjetoCitas {

    private List<String> arrayFechas = new ArrayList<String>();

    private String nombreCitaObjeto;



    public ObjetoCitas(){}

    public ObjetoCitas(List<String> arrayFechas, String nombreCitaObjeto){
        arrayFechas = this.arrayFechas;
        nombreCitaObjeto = this.nombreCitaObjeto;
    }


    public String getNombreCitaObjeto() {
        return nombreCitaObjeto;
    }

    public void setNombreCitaObjeto(String nombreCitaObjeto) {
        this.nombreCitaObjeto = nombreCitaObjeto;
    }

    public List<String> getArrayFechas() {
        return arrayFechas;
    }

    public void setArrayFechas(List<String> arrayFechas) {
        this.arrayFechas = arrayFechas;
    }
}
