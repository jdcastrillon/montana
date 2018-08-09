/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.cajas;
import Pojos.insumos;
import Pojos.producto;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.growl.Growl;

/**
 *
 * @author JuanDavid
 */
@Named(value = "cajaProductos")
@SessionScoped
public class CajaProductos implements Serializable {

    private List<producto> listProductos = new ArrayList();
    private List<cajas> listcajas = new ArrayList();
    private cajas ObjCaja;
    private cajas selectionCaja;

    Growl growl = new Growl();

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listProductos.clear();
        getSelected();
        ObjCaja.CodigoNext();
        parametrosIniciales();
    }

    public void parametrosIniciales() {
        listProductos = ObjCaja.getObjProducto().List();
        System.out.println("trajo Size : " + listProductos.size());
        listcajas = ObjCaja.List();
    }

    public String objSeleccion(cajas caj, int condicion) {

        this.selectionCaja = caj;
        if (condicion == 1) {
            for (producto objecto1 : selectionCaja.getListProductos()) {
                for (producto objecto2 : listProductos) {
                    if (objecto1.getIdProducto().intValue() == objecto2.getIdProducto().intValue()) {
                        objecto2.setSeleccion(true);
                        break;
                    }
                }
            }
        }
        return condicion == 1 ? "CajaEdit" : "CajaDelete";
    }

    public CajaProductos() {
    }

    public String prepareCreate() {
        if (ObjCaja == null) {
            ObjCaja = new cajas();
        }

        return "CajaCrearP1";
    }

    public void home() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("CajasList.xhtml");
    }

    public String eliminarObjTabla(producto pro) {
        for (producto obj : listProductos) {
            if (obj.getIdProducto().intValue() == pro.getIdProducto().intValue()) {
                obj.setSeleccion(false);
            }
        }
        ObjCaja.getListProductos().remove(pro);
        return "CajasCrearP2";
    }

    public String eliminarObjTabla2(producto pro) {
        for (producto obj : listProductos) {
            if (obj.getIdProducto().intValue() == pro.getIdProducto().intValue()) {
                obj.setSeleccion(false);
            }
        }
        selectionCaja.getListProductos().remove(pro);
        return "CajaEdit";
    }

    public void pasarProductosAtabla(producto pro, int condicion) throws IOException {
        boolean r = false;
        System.out.println("Entro a pasar prodcuto");
        System.out.println("-- " + pro.toString());
        producto temp = null;
        if (condicion == 1) {
            for (producto objecto : ObjCaja.getListProductos()) {
                if (objecto.getIdProducto().intValue() == pro.getIdProducto().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                ObjCaja.getListProductos().add(pro);
            } else {
                ObjCaja.getListProductos().remove(temp);
            }
        } else if (condicion == 2) {
            for (producto objecto : selectionCaja.getListProductos()) {
                System.out.println(": " + objecto.getIdProducto().intValue() + "=" + pro.getIdProducto().intValue());
                if (objecto.getIdProducto().intValue() == pro.getIdProducto().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                System.out.println("Agrego");
                selectionCaja.getListProductos().add(pro);
            } else {
                System.out.println("Borro");
                selectionCaja.getListProductos().remove(temp);
            }
        } else if (condicion == 3) {
            System.out.println("Entro a borrar producto...................");
            temp = pro;
            selectionCaja.getListProductos().remove(temp);
        }

    }

    public void procesoUno(int condicion) throws IOException {
        if (condicion == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CajasCrearP2.xhtml");
        } else if (condicion == 2) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CajaEdit.xhtml");
        }
    }

    public List<producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<producto> listProductos) {
        this.listProductos = listProductos;
    }

    public void procesoInicial() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("CajaCrearP1.xhtml");
    }

    public void procesoDos() throws IOException {
//        if (ValidacionCantidades(1) == false) {  //Valida las cantidades de los productos 
        FacesContext.getCurrentInstance().getExternalContext().redirect("CajasCrearP3.xhtml");
//        }
    }

    public String nuevoproducto() {
        System.out.println("-- " + ObjCaja.toString());
        if (ObjCaja.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Caja Creada"));
            parametrosIniciales();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Caja"));
        }
        return "CajasList";
    }

    public boolean ValidacionCantidades(int condicion) {
        boolean r = false;
        int cantidad = 0;
        if (condicion == 1) {
            System.out.println("-----------");
            if (ObjCaja.getListProductos().size() <= 0) {
                r = true;
            }
            for (producto objecto : ObjCaja.getListProductos()) {
                if (objecto.getCantidadCaja() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Las cantidades deben ser mayores a cero"));
                    r = true;
                    break;
                }
            }

            if (r == false) {
                for (producto objecto : ObjCaja.getListProductos()) {
                    cantidad = objecto.getCantidadCaja();
                }
            }
            if (ObjCaja.getCantidad().intValue() != cantidad) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La suma de las unidades no da igual al seleccionado para la caja"));
                r = true;
            }
        } else if (condicion == 2) {
            if (selectionCaja.getListProductos().size() <= 0) {
                r = true;
            }
            for (producto objecto : selectionCaja.getListProductos()) {
                if (objecto.getCantidadCaja() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Las cantidades deben ser mayores a cero"));
                    r = true;
                    break;
                }
            }

            if (r == false) {
                for (producto objecto : selectionCaja.getListProductos()) {
                    cantidad = objecto.getCantidadCaja();
                }
            }
            if (selectionCaja.getCantidad().intValue() != cantidad) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La suma de las unidades no da igual al seleccionado para la caja"));
                r = true;
            }
        }
        return r;
    }

    public void elimnarObjseleccionado(producto pro, int condicional) {
        boolean r = false;
        producto temp = null;
        for (producto objecto : selectionCaja.getListProductos()) {
            System.out.println(": " + objecto.getIdProducto().intValue() + "=" + pro.getIdProducto().intValue());
            if (objecto.getIdProducto().intValue() == pro.getIdProducto().intValue()) {
                temp = objecto;
                r = true;
                break;
            }
        }
        if (r == false) {
            System.out.println("Agrego");
            selectionCaja.getListProductos().add(pro);
        } else {
            System.out.println("Borro");
            selectionCaja.getListProductos().remove(temp);
        }
    }

    public String editproducto() {
//        if (ValidacionCantidades(2) == false) {
            if (selectionCaja.edit() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Caja Modificada"));
                parametrosIniciales();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificado Caja"));
            }
            return "CajasList";
//        } else {
//            return "CajaEdit";
//        }

    }
//

    public void deleteProducto() throws IOException {
        if (selectionCaja.remove() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Caja Eliminada Con Exito"));
            parametrosIniciales();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Eliminar Caja"));
        }
        home();
    }

    public cajas getSelected() {
        System.out.println("Entro a producto");
        if (ObjCaja == null) {
            System.out.println("Creo Objecto producto");
            ObjCaja = new cajas();
        } else {
            System.out.println("No Creo Objecto producto");
        }
        return ObjCaja;
    }

    public void setObjCaja(cajas ObjCaja) {
        this.ObjCaja = ObjCaja;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public cajas getSelectionCaja() {
        if (selectionCaja == null) {
            selectionCaja = new cajas();
        }
        return selectionCaja;
    }

    public void setSelectionCaja(cajas selectionCaja) {
        this.selectionCaja = selectionCaja;
    }

    public List<cajas> getListcajas() {
        return listcajas;
    }

    public void setListcajas(List<cajas> listcajas) {
        this.listcajas = listcajas;
    }

}
