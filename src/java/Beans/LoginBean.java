package Beans;

import Pojos.usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.bootsfaces.component.growl.Growl;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Variable privada: user. Contendra los datos del usuario que esta logueado
     * actualmente
     */
    private usuario user;
    /**
     * Variable: growl. Variable que instancia el contenedor de mensajes en las
     * vistas
     */
    private String verdoc = "";
    private Date createAt = null;
    Growl growl = new Growl();
    private String logOUT;

    @PostConstruct
    public void init() {
        destroysession();
    }

    public LoginBean() {

    }

    /**
     * Método que eliminara el usuairo de la sesion al iniciar si existe una
     * sesion activa.
     *
     * @since incluido desde la version 1.0
     */
    public void destroysession() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        }
    }

    /**
     * Método que verifica si hay o no sesiones activas, si no hay una sesion
     * activa sera redirigido a la vista del login.xhtml
     *
     * @since incluido desde la version 1.0
     */
    public void validarsession() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/login.xhtml");
            }
        } catch (IOException ex) {
            System.out.println("Error Redirect : " + ex.toString());
        }
    }

    /**
     * Método que verifica los datos del usuario para iniciar sesion, si los
     * datos son correctos almacena el usuario recuperado de la base de datos y
     * lo establece como variable de sesion
     *
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @since incluido desde la version 1.0
     */
    public void login() throws IOException, SQLException {
        getUser();
        if (user.getUsuario().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Usuario no debe estar vacio..!"));
        } else if (user.getClave().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Campo Clave no debe estar vacio..!"));
        } else {
            if (user.InicioSesion()) {
                System.out.println("Conecto");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/home.xhtml");
                user = null;

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "El Usuario No Existe..!"));
            }
        }
    }

    public void redireccion(String menu) throws SQLException, IOException {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/productos/productosList.xhtml");
    }

    public String getVerdoc() throws SQLException {
        return verdoc;
    }

    public void setVerdoc(String verdoc) {
        this.verdoc = verdoc;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * Método que remueve el suaurio almacenado en la variable de sesion y
     * redirige a la vista login.xhtml
     *
     * @throws java.io.IOException
     * @since incluido desde la version 1.0
     */
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/login.xhtml");
        } catch (IOException e) {
            System.out.println("error " + e);
        }

    }

    /**
     * Método que recupera el nombre del usuario de sesion.
     *
     * @return nombre del usuario logueado
     * @since incluido desde la version 1.0
     */
//    public String getNomUserLog() {
//        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
//        return l.get(0).getStr2() + " " + l.get(0).getStr3();
//    }
//
//    /**
//     * Método que recupera el documento del usuario de sesion.
//     *
//     * @return Documento del usuario logueado
//     * @since incluido desde la version 1.0
//     */
//    public String getDocumentoUserLog() {
//        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
//        return l.get(0).getStr1();
//    }
//
//    /**
//     * Método que recupera el rol del usuario de sesion.
//     *
//     * @return Rol del usuario logueado
//     * @since incluido desde la version 1.0
//     */
//    public String getRol() {
////        ArrayList<ConsultaGeneral> l = new ArrayList<>();
////        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
////        return l.get(0).getStr4();
//    }
//
//    /**
//     * Método que recupera el id del usuario de sesion.
//     *
//     * @return Id del usuario logueado
//     * @since incluido desde la version 1.0
//     */
//    public int getIdUserLog() {
//        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
//        return l.get(0).getNum1();
//    }
//
//    /**
//     * Método que recupera el id_empresa del usuario de sesion.
//     *
//     * @return id_empresa del usuario logueado
//     * @since incluido desde la version 1.0
//     */
//    public int getIdEmpresa() {
//        ArrayList<ConsultaGeneral> l = new ArrayList<>();
//        l = (ArrayList) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
//        return l.get(0).getNum3();
//    }
    public usuario getDatosUser() {
        setUser((usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        return user;
    }

    public void logout2() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
    }

    public usuario getUser() {
        if (user == null) {
            user = new usuario();
        }
        return user;
    }

    public void setUser(usuario user) {
        this.user = user;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

}
