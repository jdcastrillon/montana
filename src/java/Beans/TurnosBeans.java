/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.turno;
import Pojos.turnoDetalle;
import Pojos.usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.growl.Growl;
import org.primefaces.context.RequestContext;

/**
 *
 * @author JuanDavid
 */
@Named(value = "turnosBeans")
@SessionScoped
public class TurnosBeans implements Serializable {

    private List<turno> listTurnos = new ArrayList();
    private List<turnoDetalle> listTurnosDet = new ArrayList();

    private turnoDetalle objSeleccionDet;
    private turno objTurno;
    private turno selectionTurno;
    private String mnsTurno;
    private String mnsDialogTurno;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();
    RequestContext context;

    public TurnosBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listTurnos.clear();
        try {
            getSelected();
            MiCaja();
            listarTurnos();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void turnosListLink() throws SQLException, IOException {
        listarTurnos();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/turnos/TurnosList.xhtml");
    }

    public String objSeleccion(String user, int idpersona, int opc) {
        System.out.println("Entro al metodo");
//        this.selectionTurno = getSelectionUsuario().getUserById(user, idpersona);
        return opc == 1 ? "UsuarioEdit" : "UsuarioDelete";

    }

    public String home() {
        return "UsuariosList";
    }

    private void listarTurnos() throws SQLException {
        listTurnos.clear();
        listTurnos = objTurno.List();
    }

    public void MiCaja() {
        getSelected();
        usuario user = (usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        turno Miturno = objTurno.getMiturno(user.getUsuario());

        if (Miturno == null) {
            System.out.println("Caja abierta-----");
            this.objTurno = Miturno;
            this.mnsTurno = "Abrir Turno";
            this.mnsDialogTurno = "¿Seguro Que Desea Abrir el Turno?";
        } else {
            System.out.println("Caja abierta");
            this.mnsTurno = "Cerrar Turno";
            listTurnosDet = objTurno.ListXUser(Miturno.getIdTurno());
            this.mnsDialogTurno = "¿Seguro Que Desea Cerrar el turno?";
            System.out.println("turno : " + Miturno.getIdTurno());
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("turno") == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("turno", Miturno);
            }
        }
        
    }

    public turno getSelected() {
        if (objTurno == null) {
            System.out.println("Creo Objecto Insumo");
            objTurno = new turno();
        } else {
            System.out.println("No Creo Objecto Insumo");
        }
        return objTurno;
    }

    public String redireccion(String vista) {
        String menu = "";
        switch (vista) {
            case "pedidos":
                menu = "GenerarPedido";
                break;
            case "despacho":
                menu = "GenerarDespacho";
                break;
            case "hormas":
                menu = "/montana/faces/vistas/hormas/hormasList.xhtml";
                break;
            case "home":
                menu = "TurnosListXUser";
                break;

        }
        return menu;
    }

    public void homeList() throws IOException {
        usuario user = (usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        turno Miturno = objTurno.getMiturno(user.getUsuario());
        listTurnosDet = objTurno.ListXUser(Miturno.getIdTurno());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/pedidos/TurnosListXUser.xhtml");
    }

    public String nuevoTurno() {
        if (this.mnsTurno.equalsIgnoreCase("Abrir Turno")) {
            usuario user = (usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            getSelected();
            objTurno.setFechaTurno(new Date());
            objTurno.setUsuario(user.getUsuario());
            objTurno.setEstado("A");
            objTurno.setIdPersona(user.getIdPersona());
            System.out.println("-- " + objTurno.toString());
            if (objTurno.create() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Turno abierto correctamente..!"));
                this.mnsTurno = "Cerrar Turno";
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("turno", objTurno);
                try {
                    listarTurnos();
                } catch (SQLException ex) {
                    System.out.println("error " + ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al abrir turno"));
            }
            objTurno = null;
        }
        MiCaja();

        return "TurnosList";
    }

    public String editarUsuario() {
//        System.out.println("-- " + objUsuario.toString());
        if (selectionTurno.edit() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Modificado con exito..!"));
            selectionTurno = null;
            try {
                listarTurnos();
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
        if (objTurno == null) {
            System.out.println("Creo Objecto usuario desde prepare");
            objTurno = new turno();
        }
        objTurno.ResetValores();
        return "TurnosCrear";
    }

    public void deleteusuario() throws IOException {
        if (selectionTurno.remove() > 0) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/usuarios/UsuariosList.xhtml");
            try {
                listarTurnos();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error Eliminando usuario"));
        }
    }

    public void setOjetoTurno(turno objTurno) {
        this.objTurno = objTurno;
    }

    public List<turno> getListTurnos() {
        return listTurnos;
    }

    public void setListTurnos(List<turno> listTurnos) {
        this.listTurnos = listTurnos;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public turno getSelectionTurno() {
        System.out.println("Datos");
        if (selectionTurno == null) {
            System.out.println("Creo Selection nuevo");
            selectionTurno = new turno();
        }
        return selectionTurno;
    }

    public void setSelectionTurno(turno selectionTurno) {
        this.selectionTurno = selectionTurno;
    }

    public String getMnsTurno() {
        return mnsTurno;
    }

    public void setMnsTurno(String mnsTurno) {
        this.mnsTurno = mnsTurno;
    }

    public List<turnoDetalle> getListTurnosDet() {
        return listTurnosDet;
    }

    public void setListTurnosDet(List<turnoDetalle> listTurnosDet) {
        this.listTurnosDet = listTurnosDet;
    }

    public turno getObjTurno() {
        if (objTurno == null) {
            objTurno = new turno();
        }
        return objTurno;
    }

    public void setObjTurno(turno objTurno) {
        this.objTurno = objTurno;
    }

    public turnoDetalle getObjSeleccionDet() {
        if (objSeleccionDet == null) {
            objSeleccionDet = new turnoDetalle();
        }
        return objSeleccionDet;
    }

    public void setObjSeleccionDet(turnoDetalle objSeleccionDet) {
        this.objSeleccionDet = objSeleccionDet;
    }

    public String getMnsDialogTurno() {
        return mnsDialogTurno;
    }

    public void setMnsDialogTurno(String mnsDialogTurno) {
        this.mnsDialogTurno = mnsDialogTurno;
    }

}
