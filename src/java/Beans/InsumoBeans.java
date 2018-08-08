/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.insumos;
import Pojos.unidad;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.growl.Growl;
import org.primefaces.context.RequestContext;


/**
 *
 * @author JuanDavid
 */
@Named(value = "insumoBeans")
@SessionScoped
public class InsumoBeans implements Serializable {

    private List<insumos> listInsumos = new ArrayList();
    private List<unidad> listUnidad = new ArrayList();
    private insumos objInsumo;
    private insumos selectionInsumo;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();
    RequestContext context;

    public InsumoBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listInsumos.clear();
        context = RequestContext.getCurrentInstance();
        try {
            getSelected();
            listaInsumos();
            listaUnidad();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public String objSeleccion(insumos insu, int condicion) {
        System.out.println("Entro al metodo");
        this.selectionInsumo = insu;
        return condicion == 1 ? "InsumosEdit" : "InsumosDelete";
    }
    
 

    public void home() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("InsumisList.xhtml");
    }

    private void listaInsumos() throws SQLException {
        listInsumos = objInsumo.List();
    }

    private void listaUnidad() throws SQLException {
        listUnidad = objInsumo.getUnidad().List();
    }

    public String nuevoinsumo() {
        System.out.println("-- " + objInsumo.toString());
        if (objInsumo.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Insumo Creado"));
            try {
                listaInsumos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
        return "InsumisList";
    }
    
        public String editinsumo() {
        System.out.println("-- " + selectionInsumo.toString());
        if (selectionInsumo.edit()> 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Insumo Modificado"));
            try {
                listaInsumos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificado Insumo"));
        }
        return "InsumisList";
    }

    public void deleteInsumo() throws IOException {
        System.out.println("-- " + selectionInsumo.toString());
        if (selectionInsumo.remove()> 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Insumo Creado"));
            try {
                listaInsumos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
        home();
        
//return "InsumisList";
    }

    public String prepareCreate() {
        if (objInsumo == null) {
            System.out.println("Creo Objecto Insumo desde prepare");
            objInsumo = new insumos();
        }
        objInsumo.ResetValores();
        return "InsumosCrear";
    }

    public insumos getSelected() {
        System.out.println("Entro a getObjInsumo");
        if (objInsumo == null) {
            System.out.println("Creo Objecto Insumo");
            objInsumo = new insumos();
        } else {
            System.out.println("No Creo Objecto Insumo");
        }
        return objInsumo;
    }

    public void setObjInsumo(insumos objInsumo) {
        this.objInsumo = objInsumo;
    }

    public List<insumos> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<insumos> listInsumos) {
        this.listInsumos = listInsumos;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public SimpleDateFormat getFormat2() {
        return format2;
    }

    public void setFormat2(SimpleDateFormat format2) {
        this.format2 = format2;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public insumos getSelectionInsumo() {
        System.out.println("Datos");
        if (selectionInsumo == null) {
            System.out.println("Creo Selection nuevo");
            selectionInsumo = new insumos();
        }
        return selectionInsumo;
    }

    public void setSelectionInsumo(insumos selectionInsumo) {
        this.selectionInsumo = selectionInsumo;
    }

    public List<unidad> getListUnidad() {
        return listUnidad;
    }

    public void setListUnidad(List<unidad> listUnidad) {
        this.listUnidad = listUnidad;
    }

}
