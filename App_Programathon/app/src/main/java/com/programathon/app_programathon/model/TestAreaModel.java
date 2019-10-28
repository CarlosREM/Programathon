package com.programathon.app_programathon.model;

import java.io.Serializable;

public class TestAreaModel implements Serializable {

    private String name;
    private int respuesta1,
                respuesta2,
                respuesta3,
                respuesta4,
                respuesta5,
                respuesta6,
                total;

    private boolean isVerde;

    public TestAreaModel(String name, int respuesta1, int respuesta2, int respuesta3, int respuesta4, int respuesta5, int respuesta6, int total, boolean isVerde) {
        this.name = name;
        this.respuesta1 = respuesta1;
        this.respuesta2 = respuesta2;
        this.respuesta3 = respuesta3;
        this.respuesta4 = respuesta4;
        this.respuesta5 = respuesta5;
        this.respuesta6 = respuesta6;
        this.total = total;
        this.isVerde = isVerde;
    }

    public String getName() { return name; }

    public int getRespuesta(int num) {
        int respuesta = 0;
        switch(num) {
            case 1: respuesta = respuesta1;
            break;
            case 2: respuesta = respuesta2;
            break;
            case 3: respuesta = respuesta3;
            break;
            case 4: respuesta = respuesta4;
                break;
            case 5: respuesta = respuesta5;
                break;
            case 6: respuesta = respuesta6;
                break;
        }
        return respuesta;
    }

    public int getTotal() {
        return total;
    }

    public boolean isVerde() {
        return isVerde;
    }

}
