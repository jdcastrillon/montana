/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.hormas;
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
@Named(value = "hormasBeans")
@SessionScoped
public class HormasBeans implements Serializable {

    private List<hormas> listHormas = new ArrayList();
    private hormas objhorma;
    private hormas selectionhorma;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    RequestContext context;

    public HormasBeans() {
    }

    @PostConstruct
    public void init() {
        listHormas.clear();
        context = RequestContext.getCurrentInstance();
        try {
            getSelected();
            listaHormas();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public String objSeleccion(hormas insu, int condicion) {
        System.out.println("Entro al metodo");
        this.selectionhorma = insu;
        return condicion == 1 ? "hormasEdit" : "hormasDelete";
    }

    public String home() throws IOException {
        return "hormasList";
    }

    private void listaHormas() throws SQLException {
        listHormas = objhorma.List();
    }

    public String nuevohorma() {
        System.out.println("-- " + objhorma.toString());
        if (objhorma.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Horma Creado"));
            try {
                listaHormas();
            } catch (SQLException ex) {
                Logger.getLogger(HormasBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Horma"));
        }
        return "hormasList";
    }

    public void deleteHorma() throws IOException {
        System.out.println("-- " + selectionhorma.toString());
        if (selectionhorma.remove() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Horma Creado"));
            try {
                listaHormas();
            } catch (SQLException ex) {
                Logger.getLogger(HormasBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Horma"));
        }
        home();
    }

    public String editHorma() {        
        if (selectionhorma.edit() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Horma Modificado"));
            try {
                listaHormas();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificado Horma"));
        }
        return "hormasList";
    }

    public String prepareCreate() {
        if (objhorma == null) {
            objhorma = new hormas();
        }
        objhorma.ResetValores();
        return "hormasCrear";
    }

    public hormas getSelected() {
        System.out.println("Entro a getObjHorma");
        if (objhorma == null) {
            System.out.println("Creo Objecto Horma");
            objhorma = new hormas();
        } else {
            System.out.println("No Creo Objecto Horma");
        }
        return objhorma;
    }

    public void setObjHorma(hormas objhorma) {
        this.objhorma = objhorma;
    }

    public List<hormas> getListHormas() {
        return listHormas;
    }

    public void setListHormas(List<hormas> listHormas) {
        this.listHormas = listHormas;
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

    public hormas getSelectionhorma() {
        System.out.println("Datos");
        if (selectionhorma == null) {
            System.out.println("Creo Selection nuevo");
            selectionhorma = new hormas();
        }
        return selectionhorma;
    }

    public void setSelectionhorma(hormas selectionhorma) {
        this.selectionhorma = selectionhorma;
    }

}
