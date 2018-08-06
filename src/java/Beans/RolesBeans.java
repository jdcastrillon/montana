/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.rol;
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
@Named(value = "rolesBeans")
@SessionScoped
public class RolesBeans implements Serializable {

    private List<rol> listRoles = new ArrayList();

    private rol objRol;
    private rol selectionRol;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();
    RequestContext context;

    public RolesBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listRoles.clear();
        System.out.println("***************************");
        try {
            getSelected();
            listarRoles();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void listarMtiposDocumentos() throws SQLException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/Maestros/TiposDocumentosList.xhtml");
    }

    public String objSeleccion(rol selectionRol) {
        System.out.println("Entro al metodo");
        this.selectionRol = selectionRol;
        return "UsuarioEdit";
    }

    public String home() {
        return "InsumisList";
    }

    private void listarRoles() throws SQLException {
        listRoles = objRol.List();
    }

    public rol getSelected() {
        if (objRol == null) {
            System.out.println("Creo Objecto Insumo");
            objRol = new rol();
        } else {
            System.out.println("No Creo Objecto Insumo");
        }
        return objRol;
    }

    public String nuevoRol() {
        System.out.println("-- " + objRol.toString());
        if (objRol.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Creado"));
            try {
                listarRoles();
            } catch (SQLException ex) {
                Logger.getLogger(RolesBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
//        context.update(":form:tacInsumo");
        return "UsuariosList";
    }

    public String prepareCreate() {
        if (objRol == null) {
            System.out.println("Creo Objecto usuario desde prepare");
            objRol = new rol();
        }
        objRol.ResetValores();
        return "UsuariosCrear";
    }

    public void setOjetoMtipoDocumeto(rol objRol) {
        this.objRol = objRol;
    }

    public List<rol> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<rol> listRoles) {
        this.listRoles = listRoles;
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

    public rol getSelectionRol() {
        System.out.println("Datos");
        if (selectionRol == null) {
            System.out.println("Creo Selection nuevo");
            selectionRol = new rol();
        }
        return selectionRol;
    }

    public void setSelectionRol(rol selectionRol) {
        this.selectionRol = selectionRol;
    }

}
