/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.pedido;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.primefaces.component.growl.Growl;

@Named(value = "bodegaBeans")
@SessionScoped
public class BodegaBeans implements Serializable {

    private pedido objpedido;
    private pedido seleccionPedido;
    private String condicion;
    Growl growl = new Growl();

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        getSelected();
    }

    public BodegaBeans() {
    }

    public void buscarXfecha() {
        objpedido.ListaPedidosXfechas(condicion);
    }

    public pedido getSelected() {
        if (objpedido == null) {
            objpedido = new pedido();
        }
        return objpedido;
    }

    public void setObjpedido(pedido objpedido) {
        this.objpedido = objpedido;
    }

    public pedido getSeleccionPedido() {
        return seleccionPedido;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public void setSeleccionPedido(pedido seleccionPedido) {
        this.seleccionPedido = seleccionPedido;
    }

}
