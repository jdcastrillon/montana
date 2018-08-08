/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.MTallas;
import Pojos.Mcolor;
import Pojos.categoria;
import Pojos.hormas;
import Pojos.insumos;
import Pojos.producto;
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

/**
 *
 * @author JuanDavid
 */
@Named(value = "productosBeans")
@SessionScoped
public class ProductosBeans implements Serializable {

    private List<producto> listProductos = new ArrayList();
    private List<insumos> listInsumos = new ArrayList();
    private List<categoria> listCategoria = new ArrayList();
    private List<hormas> listHormas = new ArrayList();
    private List<MTallas> ListTallas = new ArrayList();
    private List<Mcolor> listColores = new ArrayList();

    private producto objProducto;
    private insumos objInsumo;
    private producto selectionProducto;
    private Mcolor colores;

    private String nombrecolor;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-M-dd");
    Growl growl = new Growl();

    public ProductosBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listProductos.clear();
        listInsumos.clear();
        listHormas.clear();
        try {
            getSelected();
            getObjInsumo();
            getObjProducto();
            listaProductos();
            objProducto.getListInsumos().clear();
            objProducto.getListHormas().clear();
            objProducto.CodigoNext();
            parametrosIniciales();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void parametrosIniciales() {
        getObjInsumo();
        getObjProducto();
        getColores();
        listHormas = objProducto.getObjHorma().List();
        listInsumos = objInsumo.List();
        ListTallas = objProducto.getObjTallas().List();
        listColores = colores.List();

    }

    public String eliminarObjTabla(insumos insu) {
        for (insumos obj : listInsumos) {
            if (obj.getIdInsumo().intValue() == insu.getIdInsumo().intValue()) {
                obj.setSeleccion(false);
            }
        }
        objProducto.getListInsumos().remove(insu);
        return "productosCrearP2";
    }

    public String eliminarObjTablaHorma(hormas horma) {
        for (hormas obj : listHormas) {
            if (obj.getIdHorma().intValue() == horma.getIdHorma().intValue()) {
                obj.setSeleccion(false);
            }
        }
        objProducto.getListHormas().remove(horma);
        return "productosCrearP2";
    }

    public String objSeleccion(producto insu, int condicion) {

        this.selectionProducto = insu;
        if (condicion == 1) {
            for (hormas objecto1 : selectionProducto.getListHormas()) {
                for (hormas objecto2 : listHormas) {
                    if (objecto1.getIdHorma().intValue() == objecto2.getIdHorma().intValue()) {
                        objecto2.setSeleccion(true);
                        break;
                    }
                }
            }

            for (insumos objecto1 : selectionProducto.getListInsumos()) {
                for (insumos objecto2 : listInsumos) {
                    if (objecto1.getIdInsumo().intValue() == objecto2.getIdInsumo().intValue()) {
                        objecto2.setSeleccion(true);
                        break;
                    }
                }
            }

        }
        return condicion == 1 ? "productoEditP1" : "productoDelete";
    }

    public void listaProductos() throws SQLException {
        listProductos = objProducto.List();
    }

    public void pasarInsumosAtabla(insumos insu, int condicion) throws IOException {
        boolean r = false;
        insumos temp = null;
        if (condicion == 1) {
            for (insumos objecto : objProducto.getListInsumos()) {
                if (objecto.getIdInsumo().intValue() == insu.getIdInsumo().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                objProducto.getListInsumos().add(insu);
            } else {
                objProducto.getListInsumos().remove(temp);
            }
        } else if (condicion == 2) {
            for (insumos objecto : selectionProducto.getListInsumos()) {
                if (objecto.getIdInsumo().intValue() == insu.getIdInsumo().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                selectionProducto.getListInsumos().add(insu);
            } else {
                selectionProducto.getListInsumos().remove(temp);
            }
        }

    }

    public void pasarHormasAtabla(hormas horma, int condicion) throws IOException {
        boolean r = false;
        hormas temp = null;
        if (condicion == 1) {
            for (hormas objecto : objProducto.getListHormas()) {
                if (objecto.getIdHorma().intValue() == horma.getIdHorma().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                objProducto.getListHormas().add(horma);
            } else {
                objProducto.getListHormas().remove(temp);
            }
        } else if (condicion == 2) {
            for (hormas objecto : selectionProducto.getListHormas()) {
                if (objecto.getIdHorma().intValue() == horma.getIdHorma().intValue()) {
                    temp = objecto;
                    r = true;
                    break;
                }
            }
            if (r == false) {
                selectionProducto.getListHormas().add(horma);
            } else {
                selectionProducto.getListHormas().remove(temp);
            }
        }

    }

    public void home() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("productosList.xhtml");
    }

    public void procesoUno(int condicion) throws IOException {
        if (condicion == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("productosCrearP2.xhtml");
        } else if (condicion == 2) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("productoEditP1.xhtml");
        }
    }

    public void procesoInicial() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("productosCrearP1.xhtml");
    }

    public void procesoDos() throws IOException {
        //Reviciones
        boolean r = false;
        if (objProducto.getListInsumos().size() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se debe seleccionar al menos un insumo"));
            r = true;
        }
//        for (insumos objecto : objProducto.getListInsumos()) {
//            if (objecto.getCantidadAUtilizar().intValue() <= 0) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Las cantidades deben ser mayores a cero"));
//                r = true;
//                break;
//            }
//        }
        //Hormas
        if (objProducto.getListHormas().size() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se debe seleccionar al menos una horma"));
            r = true;
        }

        if (r == false) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("productosCrearP3.xhtml");
        }
    }

    public String nuevoproducto() {
        System.out.println("-- " + objProducto.toString());
        for (Mcolor obj : listColores) {
            if (obj.getNombre().trim().equalsIgnoreCase(nombrecolor)) {
                objProducto.setIdColor(obj.getIdClor().intValue());
                break;
            }
        }

        if (objProducto.create() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Producto Creado"));
            try {
                listaProductos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Producto"));
        }
        return "productosList";
    }

    public String editproducto() {
        System.out.println("-- " + selectionProducto.toString());
        if (selectionProducto.edit() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Insumo Modificado"));
            try {
                listaProductos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificado Insumo"));
        }
        return "productosList";
    }

    public void deleteProducto() throws IOException {
        System.out.println("-- " + selectionProducto.toString());
        if (selectionProducto.remove() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Insumo Creado"));
            try {
                listaProductos();
            } catch (SQLException ex) {
                Logger.getLogger(InsumoBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al crear Insumo"));
        }
        home();
    }

    public String prepareCreate() {
        if (objProducto == null) {
            System.out.println("Creo Objecto Insumo desde prepare");
            objProducto = new producto();
        }
        objProducto.getListHormas().clear();
        objProducto.getListInsumos().clear();
        objProducto.setNombreProducto("");
        objProducto.setIdTalla(null);
        return "productosCrearP1";
    }

    public producto getSelected() {
        System.out.println("Entro a producto");
        if (objProducto == null) {
            System.out.println("Creo Objecto producto");
            objProducto = new producto();
            listCategoria = objProducto.getObjCategoria().List();
            System.out.println("Lista Categoria :::: " + listCategoria.size());
        } else {
            System.out.println("No Creo Objecto producto");
        }
        return objProducto;
    }

    public List<producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<producto> listProductos) {
        this.listProductos = listProductos;
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

    public producto getSelectionProducto() {
        System.out.println("Datos");
        if (selectionProducto == null) {
            System.out.println("Creo Selection nuevo");
            selectionProducto = new producto();
        }
        return selectionProducto;
    }

    public void setSelectionProducto(producto selectionProducto) {
        this.selectionProducto = selectionProducto;
    }

    public List<insumos> getListInsumos() {
        return listInsumos;
    }

    public void setListInsumos(List<insumos> listInsumos) {
        this.listInsumos = listInsumos;
    }

    public producto getObjProducto() {
        if (objProducto == null) {
            objProducto = new producto();
        }
        return objProducto;
    }

    public void setObjProducto(producto objProducto) {
        this.objProducto = objProducto;
    }

    public insumos getObjInsumo() {
        if (objInsumo == null) {
            System.out.println("Creo objecto insumo");
            objInsumo = new insumos();
        }

        return objInsumo;
    }

    public void setObjInsumo(insumos objInsumo) {
        this.objInsumo = objInsumo;
    }

    public List<categoria> getListCategoria() {
        return listCategoria;
    }

    public void setListCategoria(List<categoria> listCategoria) {
        this.listCategoria = listCategoria;
    }

    public List<hormas> getListHormas() {
        return listHormas;
    }

    public void setListHormas(List<hormas> listHormas) {
        this.listHormas = listHormas;
    }

    public List<MTallas> getListTallas() {
        return ListTallas;
    }

    public void setListTallas(List<MTallas> ListTallas) {
        this.ListTallas = ListTallas;
    }

    public List<Mcolor> getListColores() {
        return listColores;
    }

    public void setListColores(List<Mcolor> listColores) {
        this.listColores = listColores;
    }

    public Mcolor getColores() {
        if (colores == null) {
            colores = new Mcolor();
        }
        return colores;
    }

    public void setColores(Mcolor colores) {
        this.colores = colores;
    }

    public String getNombrecolor() {
        return nombrecolor;
    }

    public void setNombrecolor(String nombrecolor) {
        this.nombrecolor = nombrecolor;
    }

}
