/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

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
@Named(value = "usuariosBeans")
@SessionScoped
public class UsuariosBeans implements Serializable {

    private List<usuario> listUsuario = new ArrayList();

    private usuario objUsuario;
    private usuario selectionUsuario;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();
    RequestContext context;

    public UsuariosBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listUsuario.clear();
        System.out.println("***************************");
        try {
            getSelected();
            listarUsuarios();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void listarUsuariosLink() throws SQLException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/usuarios/UsuariosList.xhtml");
    }

    public void menuDinamico(String vista) {
        String menu = "";
        switch (vista) {
            case "usuarios":
                menu = "/montana/faces/vistas/usuarios/UsuariosList.xhtml";
                break;
            case "insumos":
                menu = "/montana/faces/vistas/insumos/InsumisList.xhtml";
                break;
            case "hormas":
                menu = "/montana/faces/vistas/hormas/hormasList.xhtml";
                break;
            case "productos":
                menu = "/montana/faces/vistas/productos/productosList.xhtml";
                break;
            case "cajas":
                menu = "/montana/faces/vistas/cajaProductos/CajasList.xhtml";
                break;
            case "pedido":
                menu = "/montana/faces/vistas/pedidos/GenerarPedido.xhtml";
                break;
            case "despacho":
                menu = "/montana/faces/vistas/despachos/GenerarDespacho.xhtml";
                break;
        }
        try {
            if (menu.length() > 1) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(menu);
            }
        } catch (IOException ex) {
            Logger.getLogger(UsuariosBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String objSeleccion(String user, int idpersona, int opc) {
        System.out.println("Entro al metodo");
        this.selectionUsuario = getSelectionUsuario().getUserById(user, idpersona);
        return opc == 1 ? "UsuarioEdit" : "UsuarioDelete";

    }

    public String home() {
        return "UsuariosList";
    }

    private void listarUsuarios() throws SQLException {
        listUsuario = objUsuario.List();
    }

    public usuario getSelected() {
        if (objUsuario == null) {
            System.out.println("Creo Objecto Insumo");
            objUsuario = new usuario();
        } else {
            System.out.println("No Creo Objecto Insumo");
        }
        return objUsuario;
    }

    public String nuevoUsuario() {
//        System.out.println("-- " + objUsuario.toString());
        if (objUsuario.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Creado"));
            objUsuario = null;
            try {
                listarUsuarios();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
//        context.update(":form:tacInsumo");
        return "UsuariosList";
    }

    public String editarUsuario() {
//        System.out.println("-- " + objUsuario.toString());
        if (selectionUsuario.edit() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Modificado con exito..!"));
            selectionUsuario = null;
            try {
                listarUsuarios();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificar usuario"));
        }
//        context.update(":form:tacInsumo");
        return "UsuariosList";
    }

    public String prepareCreate() {
        if (objUsuario == null) {
            System.out.println("Creo Objecto usuario desde prepare");
            objUsuario = new usuario();
        }
        objUsuario.ResetValores();
        return "UsuariosCrear";
    }

    public void deleteusuario() throws IOException {
        if (selectionUsuario.remove() > 0) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/usuarios/UsuariosList.xhtml");
            try {
                listarUsuarios();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error Eliminando usuario"));
        }
    }

    public void setOjetoUsuario(usuario objUsuario) {
        this.objUsuario = objUsuario;
    }

    public List<usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListInsumos(List<usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public usuario getSelectionUsuario() {
        System.out.println("Datos");
        if (selectionUsuario == null) {
            System.out.println("Creo Selection nuevo");
            selectionUsuario = new usuario();
        }
        return selectionUsuario;
    }

    public void setSelectionUsuario(usuario selectionUsuario) {
        this.selectionUsuario = selectionUsuario;
    }

}
