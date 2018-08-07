/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.Mcolor;
import Pojos.cajas;
import Pojos.pedido;
import Pojos.pedido_detalle;
import Pojos.persona;
import Pojos.producto;
import Pojos.turno;
import Pojos.usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Named(value = "pedidoBeans")
@SessionScoped
public class PedidosBeans implements Serializable {

    private List<pedido> listPedidos = new ArrayList();
    private List<pedido> listPedidosPendientes = new ArrayList();
    private List<pedido_detalle> listdetallePedido;
    private List<usuario> listUsersClientes = new ArrayList();
    private List<producto> listProductos = new ArrayList();
    private List<Mcolor> listColores = new ArrayList();
    private List<cajas> ListCajas = new ArrayList();
    private pedido objPedido;
    private producto referencia;
    private pedido selectionPedido;
    private Mcolor colores;
    private usuario selectionCliente;
    private cajas MisCajas;
    private String horma;
    private String insumo;
    private int idProducto;
    private String observacionPedido = "";
    private int totalPedido = 0;
    int contadorPedidos = 0;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Growl growl = new Growl();
    RequestContext context;
    private int talla1;
    private int talla2;
    private int talla3;
    private int talla4;
    private int talla5;
    private int talla6;
    private int talla7;
    private int talla8;
    private int talla9;
    private int talla10;
    private int talla11;
    private Mcolor micolor;
    private String nombrecolor = "";
    private String selectUser = "";
    private String selectProducto = "";
    private String disabled = "true";
    private String Mensaje = "";
    private String idProductoP = "";
    private int gtalla;
    private int pedidos;

    public PedidosBeans() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listPedidos.clear();
        listColores.clear();
        ListCajas.clear();
        System.out.println("***************************");
        try {
            getObjPedido();
            listarClientes();
            ListarProductos();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.toString());
        }
    }

    public void pedidosListLink() throws SQLException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/pedidos/GenerarPedido.xhtml");
    }

//    public void objSeleccion(String user, int idpersona, int opc) throws SQLException {
//        System.out.println("Entro al metodo");
////        this.selectionCliente = getSelectionCliente().getUserById(user, idpersona);
////        this.objPedido.getObjetoCliente().getObjpersona().setNombreCompleto(selectionCliente.getObjpersona().getNombreCompleto());
//        listarPedidos(idpersona);
//    }
    public String home() {
        return "UsuariosList";
    }

    public void listarPedidos() throws SQLException {
        listarClientes();
        ListarProductos();
        listPedidosPendientes.clear();
        String[] partes = selectUser.split(" ");
        System.out.println("partes[0] " + partes[0]);
        listPedidosPendientes = getObjPedido().ListPedidoByCliente(new persona().getIdPersonaByDocumento(partes[0]));
        setObjPedido(null);
        System.out.println("tot pedidos pend = " + listPedidos.size());
        System.out.println("select user " + selectUser);
        this.pedidos = listPedidosPendientes.size();
    }

    private void listarClientes() throws SQLException {
        listUsersClientes.clear();
        usuario u = new usuario();
        listUsersClientes = u.ListUsuarios(2);
        System.out.println("listUsersClientes " + listUsersClientes.size());
    }

    private void ListarProductos() {
        listProductos.clear();
        producto p = new producto();
        getColores();
        getMisCajas();
        listProductos = p.ListSingle();
        listColores = colores.List();
        ListCajas=MisCajas.List();
        System.out.println("Relleno los colores : " + listColores.size());
        System.out.println("Cajas : " + ListCajas.size());
    }

    public String nuevoTurno() {
//        LoginBean l = new LoginBean();
//        getSelected();
//        objTurno.setFechaTurno(new Date());
//        objTurno.setUsuario(l.getDatosUser().getUsuario());
//        objTurno.setEstado("A");
//        objTurno.setIdPersona(l.getDatosUser().getIdPersona());
//        System.out.println("-- " + objTurno.toString());
//        if (objTurno.create() > 0) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Turno abierto correctamente..!"));
//            try {
//                listarTurnos();
//            } catch (SQLException ex) {
//                System.out.println("error " + ex);
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al abrir turno"));
//        }
//        objTurno = null;
        return "TurnosList";
    }

    public String editarUsuario() {
////        System.out.println("-- " + objUsuario.toString());
//        if (selectionTurno.edit() > 0) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Usuario Modificado con exito..!"));
//            selectionTurno = null;
//            try {
//                listarTurnos();
//            } catch (SQLException ex) {
//                System.out.println("error " + ex);
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error al Modificar usuario"));
//        }
////        context.update(":form:tacInsumo");
        return "UsuariosList";
    }

    public String prepareCreate() {
//        if (objTurno == null) {
//            System.out.println("Creo Objecto usuario desde prepare");
//            objTurno = new turno();
//        }
//        objTurno.ResetValores();
        return "TurnosCrear";
    }

    public void deleteusuario() throws IOException {
//        if (selectionTurno.remove() > 0) {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/usuarios/UsuariosList.xhtml");
//            try {
//                listarTurnos();
//            } catch (SQLException ex) {
//                System.out.println("error " + ex);
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Error Eliminando usuario"));
//        }
    }

    public List<producto> completeProductos(String query) {
        List<producto> listProducts = getListProductos();
        List<producto> filteredproductos = new ArrayList();
        for (int i = 0; i < listProducts.size(); i++) {
            producto p = listProducts.get(i);
            if (p.getNombreProducto().toLowerCase().contains(query)) {
                filteredproductos.add(p);
            }
        }
        return filteredproductos;
    }

    public void setDatosProducto() {
        System.out.println("reseteando");
        llenarObjedosdetallePedido();
        producto p = new producto();
        String[] detalle = p.getHormaMpbyProducto(selectProducto).split("-");
        setHorma(detalle[0]);
        setInsumo(detalle[1]);
        setIdProducto(Integer.parseInt(detalle[2]));
        setDisabled("false");
        p = null;
    }

    public List<usuario> completeClients(String query) {
        List<usuario> listClientes = getListUsersClientes();
        List<usuario> filteredCliente = new ArrayList();
        for (int i = 0; i < listClientes.size(); i++) {
            usuario u = listClientes.get(i);
            if (u.getObjpersona().getNombreCompleto().toLowerCase().startsWith(query) || u.getObjpersona().getDocumento().toLowerCase().startsWith(query)) {
                filteredCliente.add(u);
            }
        }
        return filteredCliente;
    }

    public void addDetallePedido(int idTalla) {
        listdetallePedido.get(idTalla - 1).setIdTalla(idTalla);
        listdetallePedido.get(idTalla - 1).setDespachado("P");
        switch (idTalla) {
            case 1:
                listdetallePedido.get(idTalla - 1).setCantidad(talla1);
                totalPedido += talla1;
                System.out.println("id talla = " + idTalla + " " + getTalla1());
                break;
            case 2:
                listdetallePedido.get(idTalla - 1).setCantidad(talla2);
                totalPedido += talla2;
                System.out.println("id talla = " + idTalla + " " + getTalla2());
                break;
            case 3:
                listdetallePedido.get(idTalla - 1).setCantidad(talla3);
                totalPedido += talla3;
                System.out.println("id talla = " + idTalla + " " + getTalla3());
                break;
            case 4:
                listdetallePedido.get(idTalla - 1).setCantidad(talla4);
                totalPedido += talla4;
                System.out.println("id talla = " + idTalla + " " + getTalla4());
                break;
            case 5:
                listdetallePedido.get(idTalla - 1).setCantidad(talla5);
                totalPedido += talla5;
                System.out.println("id talla = " + idTalla + " " + getTalla5());
                break;
            case 6:
                listdetallePedido.get(idTalla - 1).setCantidad(talla6);
                totalPedido += talla6;
                System.out.println("id talla = " + idTalla + " " + getTalla6());
                break;
            case 7:
                listdetallePedido.get(idTalla - 1).setCantidad(talla7);
                totalPedido += talla7;
                System.out.println("id talla = " + idTalla + " " + getTalla7());
                break;
            case 8:
                listdetallePedido.get(idTalla - 1).setCantidad(talla8);
                totalPedido += talla8;
                System.out.println("id talla = " + idTalla + " " + getTalla8());
                break;
            case 9:
                listdetallePedido.get(idTalla - 1).setCantidad(talla9);
                totalPedido += talla9;
                System.out.println("id talla = " + idTalla + " " + getTalla9());
                break;
            case 10:
                listdetallePedido.get(idTalla - 1).setCantidad(talla10);
                totalPedido += talla10;
                System.out.println("id talla = " + idTalla + " " + getTalla10());
                break;
            case 11:
                listdetallePedido.get(idTalla - 1).setCantidad(talla11);
                totalPedido += talla11;
                System.out.println("id talla = " + idTalla + " " + getTalla11());
                break;
        }

    }

    public String addPedido() throws SQLException {
        System.out.println("selectprod " + selectProducto);
        getMicolor();
        if (selectProducto.length() > 1) {
            turno Miturno=(turno) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("turno");
            listarClientes();
            ListarProductos();
            boolean add = true;
            String[] datos = getdatosPedido().split(",");
            pedido p = getObjPedido();
            p.setIdPersona(Integer.parseInt(datos[0]));
            p.setIdCliente(Integer.parseInt(datos[1]));
            p.setFechaRegistro(new Date());
            p.setFechaRegistroStr(format2.format(new Date()));
            p.setEstado(1);
            p.setUsuario(Miturno.getUsuario());
            p.setIdTurno(Miturno.getIdTurno());
            p.setListdetallePedido(listdetallePedido);
            p.setHorma(horma);
            p.setMateriap(insumo);
            p.setReferencia(selectProducto);
            p.setTotalPedido(totalPedido);
            p.setObservacion(observacionPedido);
            p.setIdProducto(idProducto);
            p.setColor(micolor);
            p.setNombrecolor(nombrecolor);
            p.setIdPersonaTurno(Miturno.getIdPersona());
            System.out.println("Color : " + nombrecolor);
            for (int i = 0; i < listPedidos.size(); i++) {
                for (int j = 0; j < listPedidos.get(i).getListdetallePedido().size(); j++) {
                    if (listPedidos.get(i).getListdetallePedido().get(j).getIdProducto() != getIdProducto()) {
                        add = true;
                    } else {
                        add = false;
                    }
                    System.out.println("pp " + listPedidos.get(i).getListdetallePedido().get(j).getIdProducto());
                }
            }
            if (add) {
                for (int i = 0; i < p.getListdetallePedido().size(); i++) {
                    p.getListdetallePedido().get(i).setIdProducto(getIdProducto());
                }
                listPedidos.add(p);
            } else {
                System.out.println("no se agrego pedido");
            }
            for (int i = 0; i < listPedidos.size(); i++) {
                for (int j = 0; j < listPedidos.get(i).getListdetallePedido().size(); j++) {
                    System.out.println("productos " + listPedidos.get(i).getListdetallePedido().get(j).getIdProducto());
                }
            }
            resetPedido();
            p = null;
            setObjPedido(null);
            this.selectProducto = "";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Debe Primero seleccionar un producto"));
        }
        return "GenerarPedido";
    }

    public String liberarpedido() {
        System.out.println("-- entro a eliminar");
        if (this.selectionPedido == null) {
            System.out.println("No hay pedido Seleccionado");
        } else {
            System.out.println("pedido");
            System.out.println("- " + this.selectionPedido.toString());
        }
        pedido p = null;
        System.out.println("Tamaño  : " + listPedidos.size());
        for (pedido obj : listPedidos) {
            System.out.println(":: " + obj.toString());
            System.out.println("pedido : " + obj.getIdPedido() + "=" + this.selectionPedido.getIdProducto());
            if (obj.getIdProducto() == this.selectionPedido.getIdProducto()) {
                System.out.println("Encontro producto");
                p = obj;
                break;
            }
        }
        if (p == null) {
            System.out.println("No encontro el pedido");
        } else {
            listPedidos.remove(p);
        }

        return "GenerarPedido";
    }

    public String getdatosPedido() {
        String[] user = selectUser.split(" ");
        usuario u = new usuario();
        return u.getUserByDocumento(user[0]);
    }

    public void llenarObjedosdetallePedido() {
        listdetallePedido = null;
        listdetallePedido = new ArrayList();
        for (int i = 0; i < 11; i++) {
            pedido_detalle p = new pedido_detalle();
            p.setIdTalla(i + 1);
            p.setCantidad(0);
            listdetallePedido.add(p);
        }
    }

    public void resetPedido() {
        llenarObjedosdetallePedido();
        selectProducto = "";
        observacionPedido = "";
        totalPedido = 0;
        horma = "";
        insumo = "";
        disabled = "true";
        talla1 = 0;
        talla2 = 0;
        talla3 = 0;
        talla4 = 0;
        talla5 = 0;
        talla6 = 0;
        talla7 = 0;
        talla8 = 0;
        talla9 = 0;
        talla10 = 0;
        talla11 = 0;
        this.nombrecolor = "Seleccione";

    }

    public void selectionColor() {
        //getMicolor();
        System.out.println("entro al color");
        System.out.println("Color : " + nombrecolor);
        //this.micolor=color;
    }

    public void guardarPedido() throws IOException {
        System.out.println("por aqui guardando");
        if (listPedidos.size() > 0) {

//            for (pedido listPedido : listPedidos) {
//                for (pedido_detalle object : listPedido.getListdetallePedido()) {
//                    System.out.println("- Pedido " + listPedido.getIdPedido());
//                    System.out.println("- Detalle 1: " + object.getIdProducto());
//                    System.out.println("- Detalle 2: " + object.getCantidad());
//                }
//            }
            int result = -1;
            pedido p = null;
            ArrayList<pedido_detalle> listPedidosDet = new ArrayList();

            for (pedido obj : listPedidos) {
                if (p == null) {
                    p = new pedido();
                    p.setIdPersona(obj.getIdPersona());
                    p.setIdCliente(obj.getIdCliente());
                    p.setFechaRegistro(new Date());
                    p.setFechaEntrega(new Date());
                    p.setEstado(1);
                    p.setUsuario(obj.getUsuario());
                    p.setObservacion(obj.getObservacion());
                    p.setTotalPedido(obj.getTotalPedido());    
                    p.setIdTurno(obj.getIdTurno());
                    p.setIdPersonaTurno(obj.getIdPersonaTurno());
                    

                    for (pedido pedido : listPedidos) {
                        for (pedido_detalle object : pedido.getListdetallePedido()) {
                            if (object.getCantidad() > 0) {
                                object.setDetalle(pedido.getObservacion());
                                listPedidosDet.add(object);
                            }
                        }
                    }

                    p.setListdetallePedido(listPedidosDet);

                } else {
                    break;
                }
            }

            result = p.create();
            System.out.println("result " + result);
            if (result >= 0) {
                System.out.println("guardado");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Pedido Guardado correctamente"));
                Mensaje = "Pedido Guardado correctamente";
                resetPedido();
                listPedidos.clear();
                selectUser = "";
                setObjPedido(null);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se pudo guardar el pedido"));
                resetPedido();
                listPedidos.clear();
                selectUser = "";
                Mensaje = "No se pudo guardar el pedido";
                setObjPedido(null);
            }
            //mensaje de guardado
            //resetar todo
//        actualizar el total del cabezal
//        return "GenerarPedido";
            FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/pedidos/GenerarPedido.xhtml");
        } else {
            Mensaje = "";
        }
    }

    public void updateTallePedido(int idTalla) {
        System.out.println("datos " + idTalla + " " + gtalla);
//        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("talla1")
    }

    public void updatePedido() throws IOException {
        System.out.println("indice " + listPedidos.indexOf(selectionPedido));
        listPedidos.set(listPedidos.indexOf(selectionPedido), selectionPedido);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/pedidos/GenerarPedido.xhtml");
    }

    public pedido getSelectionPedido() {
        System.out.println("Datos");
        if (selectionPedido == null) {
            System.out.println("Creo Selection nuevo");
            selectionPedido = new pedido();
        }
        return selectionPedido;
    }

    public void setSelectionPedido(pedido selectionPedido) {
        getSelectionPedido();
        System.out.println("pedidi " + selectionPedido.toString());

//        listPedidos.forEach((listPedido) -> {
//            System.out.println("list--- " + listPedido.toString());
//        });
        if (selectionPedido.getControl().equals("unchek")) {
            selectionPedido.setControl("chek");
        } else {
            selectionPedido.setControl("unchek");
        }

        this.selectionPedido = selectionPedido;
        System.out.println("this.selectionPedido.isSelected() " + this.selectionPedido.getControl());

        for (int i = 0; i < listPedidos.size(); i++) {
            if (listPedidos.get(i) == this.selectionPedido) {
                if (this.selectionPedido.getControl().equals("chek")) {
                    listPedidos.get(i).setSelected(true);
                    listPedidos.get(i).setControl("chek");
                } else {
                    listPedidos.get(i).setSelected(false);
                    listPedidos.get(i).setControl("unchek");
                }
            } else {
                listPedidos.get(i).setSelected(false);
                listPedidos.get(i).setControl("unchek");
            }
        }
        this.selectionPedido = selectionPedido;

    }

    public usuario getSelectionCliente() {
        if (selectionCliente == null) {
            selectionCliente = new usuario();
        }
        return selectionCliente;
    }

    public void setSelectionCliente(usuario selectionCliente) {
        this.selectionCliente = selectionCliente;
    }

    public List<usuario> getListUsersClientes() {
        System.out.println("por aqui cuantos hay " + listUsersClientes.size());
        return listUsersClientes;
    }

    public void setListUsersClientes(List<usuario> listUsersClientes) {
        this.listUsersClientes = listUsersClientes;
    }

    public producto getReferencia() {
        return referencia;
    }

    public void setReferencia(producto referencia) {
        this.referencia = referencia;
    }

    public int getTalla1() {
        return talla1;
    }

    public void setTalla1(int talla1) {
        this.talla1 = talla1;
    }

    public String getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public List<producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<producto> listProductos) {
        this.listProductos = listProductos;
    }

    public String getSelectProducto() {
        return selectProducto;
    }

    public void setSelectProducto(String selectProducto) {
        this.selectProducto = selectProducto;
    }

    public String getHorma() {
        return horma;
    }

    public void setHorma(String horma) {
        this.horma = horma;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public int getTalla2() {
        return talla2;
    }

    public void setTalla2(int talla2) {
        this.talla2 = talla2;
    }

    public int getTalla3() {
        return talla3;
    }

    public void setTalla3(int talla3) {
        this.talla3 = talla3;
    }

    public int getTalla4() {
        return talla4;
    }

    public void setTalla4(int talla4) {
        this.talla4 = talla4;
    }

    public int getTalla5() {
        return talla5;
    }

    public void setTalla5(int talla5) {
        this.talla5 = talla5;
    }

    public int getTalla6() {
        return talla6;
    }

    public void setTalla6(int talla6) {
        this.talla6 = talla6;
    }

    public int getTalla7() {
        return talla7;
    }

    public void setTalla7(int talla7) {
        this.talla7 = talla7;
    }

    public int getTalla8() {
        return talla8;
    }

    public void setTalla8(int talla8) {
        this.talla8 = talla8;
    }

    public int getTalla9() {
        return talla9;
    }

    public void setTalla9(int talla9) {
        this.talla9 = talla9;
    }

    public int getTalla10() {
        return talla10;
    }

    public void setTalla10(int talla10) {
        this.talla10 = talla10;
    }

    public int getTalla11() {
        return talla11;
    }

    public void setTalla11(int talla11) {
        this.talla11 = talla11;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getObservacionPedido() {
        return observacionPedido;
    }

    public void setObservacionPedido(String observacionPedido) {
        this.observacionPedido = observacionPedido;
    }

    public pedido getObjPedido() {
        if (objPedido == null) {
            objPedido = new pedido();
        }
        return objPedido;
    }

    public void setObjPedido(pedido objPedido) {
        this.objPedido = objPedido;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(int totalPedodo) {
        this.totalPedido = totalPedodo;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public int getGtalla() {
        return gtalla;
    }

    public void setGtalla(int gtalla) {
        this.gtalla = gtalla;
    }

    public List<pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListTurnos(List<pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public String getIdProductoP() {
        return idProductoP;
    }

    public void setIdProductoP(String idProductoP) {
        this.idProductoP = idProductoP;
    }

    public Mcolor getMicolor() {
        if (micolor == null) {
            micolor = new Mcolor();
        }
        return micolor;
    }

    public void setMicolor(Mcolor micolor) {
        this.micolor = micolor;
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

    public List<pedido> getListPedidosPendientes() {
        return listPedidosPendientes;
    }

    public void setListPedidosPendientes(List<pedido> listPedidosPendientes) {
        this.listPedidosPendientes = listPedidosPendientes;
    }

    public cajas getMisCajas() {
        if(MisCajas==null){
            MisCajas=new cajas();
        }
        return MisCajas;
    }

    public void setMisCajas(cajas MisCajas) {
        this.MisCajas = MisCajas;
    }

    public List<cajas> getListCajas() {
        return ListCajas;
    }

    public void setListCajas(List<cajas> ListCajas) {
        this.ListCajas = ListCajas;
    }
    

}
