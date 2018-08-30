/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.util.ArrayList;

/**
 *
 * @author JuanDavid
 */
public class CajaModel {

    ArrayList<String> listaCajas = new ArrayList();
    ArrayList<String> listacajaDet = new ArrayList();

    public CajaModel() {
//        llenado();
    }

    public void llenado() {
        listaCajas.clear();
        listacajaDet.clear();
        listaCajas.add("Producto");
        listaCajas.add("Caja 1");
        listaCajas.add("Caja 3");
        listaCajas.add("Caja 3");
        listaCajas.add("Caja 3");
        int a = 0;
        for (String listaCaja : listaCajas) {
            a=0;
            for (String listaCaja2 : listaCajas) {
                if (a == 0) {
                    listacajaDet.add("Pepsi");
                } else {
                    listacajaDet.add("0");
                }
                a++;
            }

        }
    }

    public ArrayList<String> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(ArrayList<String> listaCajas) {
        this.listaCajas = listaCajas;
    }

    public ArrayList<String> getListacajaDet() {
        return listacajaDet;
    }

    public void setListacajaDet(ArrayList<String> listacajaDet) {
        this.listacajaDet = listacajaDet;
    }

}
