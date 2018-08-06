/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.mtipodocumento;
import Pojos.usuario;
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
@Named(value = "tipoDocumentoBeans")
@SessionScoped
public class TipoDocumentoBenas implements Serializable {

    private List<mtipodocumento> listTipoDocumento = new ArrayList();

    private mtipodocumento objMtipodocumento;
    private mtipodocumento selectionMtipodocumento;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();
    RequestContext context;

    public TipoDocumentoBenas() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listTipoDocumento.clear();
        System.out.println("***************************");
        try {
            getSelected();
            listarTiposDocumento();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void listarMtiposDocumentos() throws SQLException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/Maestros/TiposDocumentosList.xhtml");
    }

    public String objSeleccion(mtipodocumento MtipoDocumento) {
        System.out.println("Entro al metodo");
        this.selectionMtipodocumento = MtipoDocumento;
        return "UsuarioEdit";
    }

    public String home() {
        return "InsumisList";
    }

    private void listarTiposDocumento() throws SQLException {
        listTipoDocumento = objMtipodocumento.List();
    }

    public mtipodocumento getSelected() {
        if (objMtipodocumento == null) {
            System.out.println("Creo Objecto Insumo");
            objMtipodocumento = new mtipodocumento();
        } else {
            System.out.println("No Creo Objecto Insumo");
        }
        return objMtipodocumento;
    }

    public String nuevoUsuario() {
        System.out.println("-- " + objMtipodocumento.toString());
        if (objMtipodocumento.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Creado"));
            try {
                listarTiposDocumento();
            } catch (SQLException ex) {
                Logger.getLogger(TipoDocumentoBenas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
//        context.update(":form:tacInsumo");
        return "UsuariosList";
    }

    public String prepareCreate() {
        if (objMtipodocumento == null) {
            System.out.println("Creo Objecto usuario desde prepare");
            objMtipodocumento = new mtipodocumento();
        }      
        objMtipodocumento.ResetValores();
        return "UsuariosCrear";
    }   


    public void setOjetoMtipoDocumeto(mtipodocumento objMtipodocumento) {
        this.objMtipodocumento = objMtipodocumento;
    }

    public List<mtipodocumento> getListTipoDocumento() {
        return listTipoDocumento;
    }

    public void setListTipoDocumento(List<mtipodocumento> listTipoDocumento) {
        this.listTipoDocumento = listTipoDocumento;
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

    public mtipodocumento getSelectionMtipoDocumento() {
        System.out.println("Datos");
        if (selectionMtipodocumento == null) {
            System.out.println("Creo Selection nuevo");
            selectionMtipodocumento = new mtipodocumento();
        }
        return selectionMtipodocumento;
    }

    public void setSelectionMtipoDocumento(mtipodocumento selectionMtipodocumento) {
        this.selectionMtipodocumento = selectionMtipodocumento;
    }

}
