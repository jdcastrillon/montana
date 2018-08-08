/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Pojos.despacho;
import Pojos.despachoproducto;
import Pojos.hormas;
import Pojos.insumos;
import Pojos.mvariables;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.growl.Growl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author JuanDavid
 */
@Named(value = "despachosBean")
@SessionScoped
public class DespachosBean implements Serializable {

    private List<pedido> listPedidos = new ArrayList();

    private List<pedido_detalle> listdetallePedido;
    private List<usuario> listUsersClientes = new ArrayList();
    private List<producto> listProductos = new ArrayList();
    private List<insumos> listMP = new ArrayList();
    private List<hormas> listHormas = new ArrayList();
    private List<mvariables> listVariables = new ArrayList();
    private pedido objPedido;
    private producto referencia;
    private pedido selectionPedido;
    private usuario selectionCliente;
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
    private String selectUser = "";
    private String selectMP = "";
    private String selectHR = "";
    private String selectProducto = "";
    private String disabled = "true";
    private String Mensaje = "";
    private String idProductoP = "";
    private int pedidos;
    private Date fechaPedido;
    private Date fdespacho;
    private int idPedido;
    private int total;
    private int estadopedido = 0;
    private boolean showSelect = false;
    Map<String, String> filtros = new HashMap<>();

    public DespachosBean() {
    }

    @PostConstruct
    public void init() {
        growl.setLife(5000);
        listPedidos.clear();
        System.out.println("***************************");
        try {
            getObjPedido();
            listarClientes();
            ListarProductos();
            listarMP();
            listarHormas();
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

//    public void nuevaCantidad(int idTalla) {
//        System.out.println("idTalla " + idTalla);
//        for (int i = 0; i < selectionPedido.getListdetallePedido().size(); i++) {
//            System.out.println(selectionPedido.getListdetallePedido().get(i).getIdTalla() + " " + selectionPedido.getListdetallePedido().get(i).getCantidad());
//        }
////        System.out.println("");
//    }
    public String validarDespacho() {
        System.out.println("validando despaho");
        //validar los despachos listpedidos contra lispedidostemp
        int countErrors = 0;
        List<pedido> listPedidostTemp = new ArrayList();
        listPedidostTemp = getObjPedido().ListPedidoByFilters(filtros);
        listVariables.clear();
        mvariables v = new mvariables();
        listVariables = v.List();
        System.out.println(listPedidos.size() + " " + listPedidostTemp.size());
        if (listPedidos.size() != listPedidostTemp.size()) {
            countErrors += 1;
        } else {
            for (int i = 0; i < listPedidostTemp.size(); i++) {
                for (int j = 0; j < listPedidostTemp.get(i).getListdetallePedido().size(); j++) {
                    if (listPedidostTemp.get(i).getListdetallePedido().get(j).getCantidad()
                            != listPedidos.get(i).getListdetallePedido().get(j).getCantidad()) {
                        countErrors += 1;
                    }
                }
            }
        }
        setObjPedido(null);
        if (countErrors > 0) {
            showSelect = true;
            Mensaje = "El pedido ha cambiado, por favor seleccione el estado en que desea dejar el pedido, si no selecciona ninguna opcion el sistema "
                    + "lo dejara en pie..!";
        } else {
            showSelect = false;
            Mensaje = "";
        }
        System.out.println("errores " + countErrors + " " + showSelect);
        return "Generardespacho";
    }

    public void listarPedidos() throws SQLException {
        System.out.println("listando");
        listarClientes();
        ListarProductos();
        listarMP();
        listarHormas();
        listPedidos.clear();
        String[] partesUser = selectUser.split(" ");
        String[] partesMP = selectMP.split(" ");
        String[] partesHR = selectHR.split(" ");
//        System.out.println("select uer " + selectUser + " " + fechaPedido + " " + idPedido + " " + selectMP + " "
//                + selectHR + " " + selectProducto + " " + total + " " + fdespacho);

        if (!selectUser.equals("")) {
            filtros.put("idCliente", Integer.toString(new persona().getIdPersonaByDocumento(partesUser[0])));
        }
        if (fechaPedido != null) {
            filtros.put("fechaPedido", format2.format(fechaPedido));
        }
        if (idPedido > 0) {
            filtros.put("idPedido", Integer.toString(idPedido));
        }
        if (!selectMP.equals("")) {
            filtros.put("idMP", partesMP[0]);
        }
        if (!selectHR.equals("")) {
            filtros.put("idHR", partesHR[0]);
        }
        if (!selectProducto.equals("")) {
            filtros.put("producto", selectProducto);
        }
        if (total > 0) {
            filtros.put("total", Integer.toString(total));
        }
        if (fdespacho != null) {
            filtros.put("fechadespacho", format2.format(fdespacho));
        }

        listPedidos = getObjPedido().ListPedidoByFilters(filtros);

        setObjPedido(null);
//        System.out.println("tot pedidos pend = " + listPedidos.size());
//        System.out.println("select user " + selectUser);
//        this.pedidos = 3;
    }

    private void listarClientes() throws SQLException {
        listUsersClientes.clear();
        usuario u = new usuario();
        listUsersClientes = u.ListUsuarios(2);
        System.out.println("listUsersClientes " + listUsersClientes.size());
    }

    private void listarMP() throws SQLException {
        listMP.clear();
        insumos im = new insumos();
        listMP = im.List();
        System.out.println("listUsersClientes " + listMP.size());
    }

    private void listarHormas() throws SQLException {
        listHormas.clear();
        hormas h = new hormas();
        listHormas = h.List();
        System.out.println("listUsersClientes " + listHormas.size());
    }

    private void ListarProductos() {
        listProductos.clear();
        producto p = new producto();
        listProductos = p.ListSingle();
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

    public List<insumos> completeMP(String query) {
        List<insumos> listMp = getListMP();
        List<insumos> filteredMP = new ArrayList();
        for (int i = 0; i < listMp.size(); i++) {
            insumos im = listMp.get(i);
            if (im.getNombreInsumo().toLowerCase().startsWith(query)) {
                filteredMP.add(im);
            }
        }
        return filteredMP;
    }

    public List<hormas> completeHR(String query) {
        List<hormas> listHR = getListHormas();
        List<hormas> filteredHR = new ArrayList();
        for (int i = 0; i < listHR.size(); i++) {
            hormas hr = listHR.get(i);
            if (hr.getDescripcion().toLowerCase().startsWith(query)) {
                filteredHR.add(hr);
            }
        }
        return filteredHR;
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
        if (p != null) {
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
    }

    public String guardarDespacho() throws IOException {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUsuarionuevo:estadopInner") != null) {
            estadopedido = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formUsuarionuevo:estadopInner"));
        }
        System.out.println("por aqui guardando, estado = " + estadopedido);
        despacho d = new despacho();
        usuario u = (usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        turno t = new turno().getMiturno(u.getUsuario());
        System.out.println("mi turno string " + t.toString());
        d.setIdTurno(t.getIdTurno());
        d.setUsuario(t.getUsuario());
        d.setIdPersona(t.getIdPersona());

//        d.setIdTurno(new turno().getIdTurnoByUser(u.getUsuario()));
        d.setUsuario(u.getUsuario());
        d.setIdPersona(u.getObjpersona().getIdPersona());

        d.setIdPedido(listPedidos.get(0).getIdPedido());
        d.setFactura("");
        d.setFechaEntrega(new Date());
        d.setIdCiudad(0);
        d.setNumeroGuia("");
        d.setIdTipoDespacho(1);
        d.setEstadoPedido(estadopedido);
        d.setIdCliente(listPedidos.get(0).getIdCliente());

        ArrayList<despachoproducto> listd = new ArrayList();
        listPedidos.forEach((listPedido) -> {
            listPedido.getListdetallePedido().stream().filter((object) -> (object.getCantidad() > 0)).map((object) -> {
                despachoproducto dp = new despachoproducto();
                dp.setIdProducto(object.getIdProducto());
                dp.setIdTalla(object.getIdTalla());
                dp.setCantidad(object.getCantidad());
                return dp;
            }).forEachOrdered((dp) -> {
                listd.add(dp);
            });
//                System.out.println("- Pedido " + listPedido.getIdPedido() + " Producto: " + object.getIdProducto() + " Cantidad: " + object.getCantidad());
//            System.out.println("--------------------------------------------------------------------------");
        });
        d.setList_despachoproducto(listd);
//
        int result = -1;
        result = d.create();
        System.out.println("result " + result);
        if (result >= 0) {
            System.out.println("guardado");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Despacho Guardado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "No se pudo guardar el despacho"));

        }
//        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/despachos/GenerarDespacho.xhtml");
        listd.clear();
        listPedidos.clear();
        filtros.clear();
        setObjPedido(null);
        resetDespacho();
        d = null;
        u = null;
        t = null;
        //PENDINTE LIMPIAR EL FORMULARIO, LOS FILTROS, ACTUALIZAR LOS CAMPOS LA TABLA ETC EN LA VISTA
        return "GenerarDespacho";
    }

    public void updatePedido() throws IOException {
        System.out.println("indice " + listPedidos.indexOf(selectionPedido));
        listPedidos.set(listPedidos.indexOf(selectionPedido), selectionPedido);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/montana/faces/vistas/pedidos/GenerarPedido.xhtml");
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("obj Selected" + " " + selectionPedido.toString());
        this.selectionPedido = ((pedido) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        System.out.println("obj Unseletcted" + " " + selectionPedido.toString());
        this.selectionPedido = null;
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

    public List<insumos> getListMP() {
        System.out.println("por aqui cuantos hay " + listMP.size());
        return listMP;
    }

    public List<hormas> getListHormas() {
        System.out.println("por aqui cuantos hay " + listHormas.size());
        return listHormas;
    }

    public void setListMP(List<insumos> ListMP) {
        this.listMP = ListMP;
    }

    public void setListHormas(List<hormas> listHormas) {
        this.listHormas = listHormas;
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

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getSelectMP() {
        return selectMP;
    }

    public void setSelectMP(String selectMP) {
        this.selectMP = selectMP;
    }

    public String getSelectHR() {
        return selectHR;
    }

    public void setSelectHR(String selectHR) {
        this.selectHR = selectHR;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getFdespacho() {
        return fdespacho;
    }

    public void setFdespacho(Date fdespacho) {
        this.fdespacho = fdespacho;
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
        this.selectionPedido = selectionPedido;
    }

    public int getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(int estadopedido) {
        this.estadopedido = estadopedido;
    }

    public boolean isShowSelect() {
        return showSelect;
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    public List<mvariables> getListVariables() {
        return listVariables;
    }

    public void setListVariables(List<mvariables> listVariables) {
        this.listVariables = listVariables;
    }

    public String p() {
        return "GenerarPedido";
    }

    private void resetDespacho() {
        objPedido = null;
        referencia = null;
        selectionPedido = null;
        selectionCliente = null;
        horma = "";
        insumo = "";
        idProducto = 0;
        observacionPedido = "";
        totalPedido = 0;
        contadorPedidos = 0;
        selectUser = "";
        selectMP = "";
        selectHR = "";
        selectProducto = "";
        disabled = "true";
        Mensaje = "";
        idProductoP = "";
        pedidos = 0;
        fechaPedido = null;
        fdespacho = null;
        idPedido = 0;
        total = 0;
        estadopedido = 0;
        showSelect = false;
    }
}
