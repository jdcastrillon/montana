package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

public class pedido extends Persistencia implements Serializable {

    public int idPedido;
    public int idCliente;
    public int idPersona;
    public Date FechaRegistro;
    public Date FechaEntrega;
    public int Estado;
    public int idTurno;
    public String Usuario;
    public String Observacion;
    private usuario objetoCliente;
    private String horma;
    private String materiap;
    private String referencia;
    private int totalPedido;
    private int Current;
    private String fechaRegistroStr;
    private boolean selected;
    private String control = "unchek";
    private boolean pedidoPasado;
    private int idProducto;
    private int idPersonaTurno;
    private String UsuarioTurno;
    private int totalDespachado;
    private String clienteStr;

    private Mcolor color;
    private String nombrecolor;

    private persona objPersona;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private List<pedido_detalle> listdetallePedido = new ArrayList();
    private List<pedido> listPedido = new ArrayList();

    List<cliente> List_cliente = new ArrayList();

    public pedido() {
        super();
        color = new Mcolor();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Date getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(Date FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public Date getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Date FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public persona getObjPersona() {
        if (objPersona == null) {
            objPersona = new persona();
        }
        return objPersona;
    }

    public void setObjPersona(persona objPersona) {
        this.objPersona = objPersona;
    }

    public boolean isPedidoPasado() {
        return pedidoPasado;
    }

    public void setPedidoPasado(boolean pedidoPasado) {
        this.pedidoPasado = pedidoPasado;
    }

    @Override
    public String toString() {
        return "pedido{" + "idProducto=" + idProducto + " " + control + '}';
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into pedido (idCliente,idPersonacliente,FechaRegistro,FechaEntrega,"
                + "idTurno,UsuarioTurno,Observacion,idPersonaTurno) "
                + "values (?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setInt(2, idPersona);
            preparedStatement.setString(3, sdf.format(FechaRegistro));
            preparedStatement.setString(4, null);
            preparedStatement.setInt(5, idTurno);
            preparedStatement.setString(6, Usuario);
            preparedStatement.setString(7, Observacion);
            preparedStatement.setInt(8, idPersonaTurno);
            transaccion = pedido.this.getConecion().transaccion(preparedStatement);
            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = pedido.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                Current = rs.getInt(1);
            }
            for (pedido_detalle detalle : listdetallePedido) {
                if (detalle.getCantidad() > 0) {
                    String prepareInsertD = "insert into pedido_detalle "
                            + "(idPedido,idProducto,idTalla,idColor,cantidad,despachado,observacion) "
                            + "values (?,?,?,?,?,?,?)";
                    PreparedStatement insertDetallestm = this.getConecion().con.prepareStatement(prepareInsertD);
                    insertDetallestm.setInt(1, Current);
                    insertDetallestm.setInt(2, detalle.getIdProducto());
                    insertDetallestm.setInt(3, detalle.getIdTalla());
                    insertDetallestm.setInt(4, detalle.getIdColor());
                    insertDetallestm.setInt(5, detalle.getCantidad());
                    insertDetallestm.setString(6, "N");
                    insertDetallestm.setString(7, detalle.getDetalle());
                    transaccion = pedido.this.getConecion().transaccion(insertDetallestm);
                    System.out.println(detalle.toString());
                }
            }

        } catch (SQLException ex) {
            try {
                System.out.println("Error SQL : " + ex.toString());
                this.getConecion().getconecion().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                this.getConecion().getconecion().commit();
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public int edit() {
        int transaccion = -1;
        String prepareEdit = "update pedido set NumeroPedido=?,FechaRegistro=?,FechaEntrega=?,Estado=?,idTurno=?, Usuario=?,Observacion=? where "
                + "idPedido=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setDate(1, (java.sql.Date) FechaRegistro);
            preparedStatement.setDate(2, (java.sql.Date) FechaEntrega);
            preparedStatement.setInt(3, Estado);
            preparedStatement.setInt(4, idTurno);
            preparedStatement.setString(5, Usuario);
            preparedStatement.setString(6, Observacion);
            preparedStatement.setInt(7, idPedido);
            transaccion = pedido.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().commit();
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public int remove() {
        int transaccion = -1;
        String prepareDelete = "delete from  pedido where idPedido=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setInt(1, idPedido);
            transaccion = pedido.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
        } finally {
            try {
                this.getConecion().getconecion().commit();
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return transaccion;
    }

    @Override
    public java.util.List<pedido> List() {
        ArrayList<pedido> listpedido = new ArrayList();
        String prepareQuery = "select * from pedido";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = pedido.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                pedido tabla = new pedido();
                tabla.setIdPedido(rs.getInt(1));
                tabla.setIdCliente(rs.getInt(2));
                tabla.setIdPersona(rs.getInt(3));
                tabla.setFechaRegistro(rs.getDate(5));
                tabla.setFechaEntrega(rs.getDate(6));
                tabla.setEstado(rs.getInt(7));
                tabla.setIdTurno(rs.getInt(8));
                tabla.setUsuario(rs.getString(9));
                tabla.setObservacion(rs.getString(10));
                listpedido.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
        return listpedido;
    }

    public java.util.List<pedido> ListPedidoByCliente(int idPersona) {
        ArrayList<pedido> listpedido = new ArrayList();
        ArrayList<pedido_detalle> listpedidoDetalle;
        String prepareQuery = "select distinct A.* from pedido A ,pedido_detalle B where "
                + " a.idPedido=B.idPedido and "
                + "a.idPersonacliente = " + idPersona + "  ";
        System.out.println("prepareQuery pedido " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = pedido.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                pedido tabla = new pedido();
                tabla.setIdPedido(rs.getInt(1));
                tabla.setIdCliente(rs.getInt(2));
                tabla.setIdPersona(rs.getInt(3));
                tabla.setFechaRegistro(rs.getDate(4));
                tabla.setFechaRegistroStr(sdf2.format(rs.getDate(5)));
                tabla.setIdTurno(rs.getInt(6));
                tabla.setUsuario(rs.getString(7));
                tabla.setObservacion(rs.getString(8));
                tabla.setTotalPedido(rs.getInt(9));
                tabla.setPedidoPasado(true);
                listpedido.add(tabla);
            }
            for (pedido object : listpedido) {
                int Tot = 0;
                listpedidoDetalle = new ArrayList();
                listpedidoDetalle.clear();
                System.out.println("--------------------");
//                for (int i = 0; i < 11; i++) {
//                    pedido_detalle p = new pedido_detalle();
//                    p.setIdTalla(i + 1);
//                    p.setCantidad(0);
//                    listpedidoDetalle.add(p);
//                }
                String prepareQueryDetalle = "select * from pedido_detalle where idPedido =" + object.getIdPedido();
                ResultSet rs2 = pedido.super.getConecion().query(prepareQueryDetalle);
                System.out.println(prepareQueryDetalle);
                while (rs2.next()) {
                    System.out.println("++++++++++++++++++++++");
                    pedido_detalle det = new pedido_detalle();
//                    for (int i = 0; i < listpedidoDetalle.size(); i++) {
//                        if (listpedidoDetalle.get(i).getIdTalla() == rs2.getInt(5)) {

                    det.setIdPedido(rs2.getInt(1));
                    det.setIdPedidoDespacho(rs2.getInt(2));
                    det.setIdProducto(rs2.getInt(3));
                    det.setIdTalla(rs2.getInt(4));
                    det.setIdColor(rs2.getInt(5));
                    det.setCantidad(rs2.getInt(6));
                    det.setDespachado(rs2.getString(7));
                    Tot += rs2.getInt(6);
                    listpedidoDetalle.add(det);
//                        }
//                    }
                }

                String selectDatos = "select h.descripcion, i.NombreInsumo,  prod.nombreProducto from pedido p "
                        + "inner JOIN pedido_detalle pd on p.idPedido = pd.idPedido "
                        + "inner join hormasprod hp on hp.idProducto = pd.idProducto "
                        + "inner join hormas h on h.idHorma = hp.idHorma "
                        + "inner join insumoproducto ip on ip.idProducto = pd.idProducto "
                        + "inner join insumos i on ip.idInsumo = i.idInsumo "
                        + "inner join producto prod on prod.idProducto = pd.idProducto "
                        + "where p.idPedido = " + object.getIdPedido() + " limit 1";
                ResultSet rs3 = pedido.super.getConecion().query(selectDatos);
                System.out.println(selectDatos);
                if (rs3.next()) {
                    object.setHorma(rs3.getString(1));
                    object.setMateriap(rs3.getString(2));
                    object.setReferencia(rs3.getString(3));
                }
                object.setTotalPedido(Tot);
                object.setListdetallePedido(listpedidoDetalle);
                Tot = 0;

            }
            for (pedido object : listpedido) {
                System.out.println("INFORMACION");
                System.out.println("Pedido : " + object.getIdPedido());
                System.out.println("Cant : " + object.getListdetallePedido().size());
            }

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
        return listpedido;
    }

    public java.util.List<pedido> ListPedidoByFilters(Map<String, String> filtros) {
        ArrayList<pedido> listpedido = new ArrayList();
        ArrayList<pedido_detalle> listpedidoDetalle;
        String filtro = "";
        String filtro3 = "";
        for (Map.Entry<String, String> entry : filtros.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "idCliente":
                    filtro += " AND p.idPersona = " + value;
                    break;
                case "fechaPedido":
                    filtro += " AND p.FechaRegistro between '" + value + " 00:00:00' AND '" + value + " 23:59:59'";
                    break;
                case "idPedido":
                    filtro += " AND p.idPedido = " + value;
                    break;
                case "idMP":
                    filtro3 += " AND i.idInsumo = " + value;
                    break;
                case "idHR":
                    filtro3 += " AND h.idHorma = " + value;
                    break;
                case "producto":
                    filtro3 += " AND prod.nombreProducto = '" + value + "'";
                    break;
                case "fechadespacho":
                    filtro += " AND p.FechaEntrega between '" + value + " 00:00:00' AND '" + value + " 23:59:59'";
                    break;
                case "total":
                    filtro += " AND p.total_pedido = " + value;
                    break;
            }
        }
        String prepareQuery = "select p.*, pe.NombreCompleto, pd.idProducto from pedido p "
                + "inner join cliente c on p.idCliente = c.idCliente "
                + "inner join persona pe on c.idPersona = pe.idPersona "
                + "inner join pedido_detalle pd on pd.idPedido = p.idPedido "
                + "where pd.despachado not in ('C','A') and 1=1" + filtro + "";
        System.out.println("prepareQuery pedido " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = pedido.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                pedido tabla = new pedido();
                tabla.setIdPedido(rs.getInt(1));
                tabla.setIdCliente(rs.getInt(2));
                tabla.setIdPersona(rs.getInt(3));
                tabla.setFechaRegistro(rs.getDate(4));
                tabla.setFechaRegistroStr(sdf2.format(rs.getDate(4)));
                tabla.setFechaEntrega(rs.getDate(5));
                tabla.setIdTurno(rs.getInt(6));
                tabla.setUsuario(rs.getString(7));
                tabla.setObservacion(rs.getString(8));
                tabla.setPedidoPasado(true);
                tabla.setTotalPedido(rs.getInt(9));
                tabla.setIdPersonaTurno(rs.getInt(10));
                tabla.setClienteStr(rs.getString(11));
                tabla.setIdProducto(rs.getInt(12));
                listpedido.add(tabla);
            }
            for (pedido object : listpedido) {
                int Tot = 0;
                int Tot2 = 0;
                listpedidoDetalle = new ArrayList();
                listpedidoDetalle.clear();
                for (int i = 0; i < 11; i++) {
                    pedido_detalle p = new pedido_detalle();
                    p.setIdTalla(i + 1);
                    p.setCantidad(0);
                    listpedidoDetalle.add(p);
                }
                String prepareQueryDetalle = "select pd.*, concat_ws(' | ',t.med_in,t.med_amer,t.med_col,t.med_cm), "
                        + "despProd.cantidad from pedido_detalle pd "
                        + "inner join mtallas t on pd.idTalla = t.idTalla "
                        + "inner join despacho desp on desp.idPedido = pd.idPedido "
                        + "inner join despachoproducto despProd on despProd.idDespacho = desp.idDespacho and despProd.idTalla = pd.idTalla "
                        + "and despProd.idProducto = pd.idProducto "
                        + "where pd.idPedido =" + object.getIdPedido() + " and pd.idProducto = " + object.getIdProducto();
                ResultSet rs2 = pedido.super.getConecion().query(prepareQueryDetalle);
                System.out.println(prepareQueryDetalle);
                while (rs2.next()) {
                    for (int i = 0; i < listpedidoDetalle.size(); i++) {
                        if (listpedidoDetalle.get(i).getIdTalla() == rs2.getInt(4)) {
                            listpedidoDetalle.get(i).setIdPedido(rs2.getInt(1));
                            listpedidoDetalle.get(i).setIdPedidoDespacho(rs2.getInt(2));
                            listpedidoDetalle.get(i).setIdProducto(rs2.getInt(3));
                            listpedidoDetalle.get(i).setIdTalla(rs2.getInt(4));
                            listpedidoDetalle.get(i).setIdColor(rs2.getInt(5));
                            listpedidoDetalle.get(i).setCantidad(rs2.getInt(6));
                            listpedidoDetalle.get(i).setDespachado(rs2.getString(7));
                            listpedidoDetalle.get(i).setDescTalla(rs2.getString(8));
                            Tot += rs2.getInt(6);
                            Tot2 += rs2.getInt(9);
                        }
                    }
                }

                String selectDatos = "select h.descripcion, i.NombreInsumo,  prod.nombreProducto from pedido p "
                        + "inner JOIN pedido_detalle pd on p.idPedido = pd.idPedido "
                        + "inner join hormasprod hp on hp.idProducto = pd.idProducto "
                        + "inner join hormas h on h.idHorma = hp.idHorma "
                        + "inner join insumoproducto ip on ip.idProducto = pd.idProducto "
                        + "inner join insumos i on ip.idInsumo = i.idInsumo "
                        + "inner join producto prod on prod.idProducto = pd.idProducto "
                        + "where p.idPedido = " + object.getIdPedido() + " and pd.idProducto = " + object.getIdProducto() + " " + filtro3 + " limit 1";
                ResultSet rs3 = pedido.super.getConecion().query(selectDatos);
                System.out.println(selectDatos);
                if (rs3.next()) {
                    object.setHorma(rs3.getString(1));
                    object.setMateriap(rs3.getString(2));
                    object.setReferencia(rs3.getString(3));
                }
                object.setTotalPedido(Tot);
                object.setTotalDespachado(Tot2);
                object.setListdetallePedido(listpedidoDetalle);
                Tot = 0;
                Tot2 = 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
        return listpedido;
    }

    public void ListaPedidosXfechas(String condicion) {

        String prepareQuery = "";
        Date f1 = null;
        Date f2 = null;
        if (condicion.equalsIgnoreCase("Ingreso")) {
            prepareQuery = "select A.`idPedido`, A.`idCliente`, C.NombreCompleto,A.FechaRegistro,A.FechaEntrega from pedido A , cliente B , persona C where \n"
                    + "A.idCliente=B.idCliente and B.idPersona=C.idPersona and \n"
                    + "FechaRegistro between ? and ?";
            f1 = FechaRegistro;
            f2 = FechaRegistro;
        } else if (condicion.equalsIgnoreCase("Entrega")) {
            prepareQuery = "select A.`idPedido`, A.`idCliente`, C.NombreCompleto,A.FechaRegistro,A.FechaEntrega from pedido A , cliente B , persona C where \n"
                    + "A.idCliente=B.idCliente and B.idPersona=C.idPersona and \n"
                    + "FechaEntrega between ? and ?";
            f1 = FechaEntrega;
            f2 = FechaEntrega;
        }

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareQuery);
            preparedStatement.setDate(1, (java.sql.Date) f1);
            preparedStatement.setDate(2, (java.sql.Date) f2);
            ResultSet rs = pedido.super.getConecion().queryprepared(preparedStatement);

            while (rs.next()) {
                pedido tabla = new pedido();
                persona p = new persona();

                tabla.setIdPedido(rs.getInt(1));
                p.setNombreCompleto(rs.getString(2));
                tabla.setFechaRegistro(rs.getDate(3));
                tabla.setFechaEntrega(rs.getDate(4));

                tabla.setObjPersona(p);
                listPedido.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
    }

    public pedido ListPedidosXId(int codigo) {
        pedido objpedido = new pedido();
        ArrayList<pedido_detalle> detPedido = new ArrayList();
        String prepareQuery = "select * from pedido where idpedido=" + codigo;
        String prepareQueryDet = "select idproducto,idtalla,idcolor,cantidad from pedido_detalle where idpedido=" + codigo;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = pedido.super.getConecion().query(prepareQuery);
            if (rs.next()) {

                objpedido.setIdPedido(rs.getInt(1));
                objpedido.setIdCliente(rs.getInt(2));
                objpedido.setIdPedido(rs.getInt(3));
                objpedido.setFechaRegistro(rs.getDate(4));
                objpedido.setFechaEntrega(rs.getDate(5));
                objpedido.setIdTurno(rs.getInt(6));
                objpedido.setUsuario(rs.getString(7));
                objpedido.setObservacion(rs.getString(8));
                objpedido.setTotalPedido(rs.getInt(9));
                objpedido.setUsuarioTurno(rs.getString(10));
                objpedido.setIdPersonaTurno(rs.getInt(11));
            } else {
                objpedido = null;
            }

            if (objpedido != null) {
                ResultSet r2s = pedido.super.getConecion().query(prepareQueryDet);
                while (r2s.next()) {
                    pedido_detalle det = new pedido_detalle();
                    det.setIdProducto(rs.getInt(1));
                    det.setIdTalla(rs.getInt(2));
                    det.setIdColor(rs.getInt(3));
                    det.setCantidad(rs.getInt(4));
                    detPedido.add(det);
                }
                objpedido.setListdetallePedido(detPedido);
            }

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
        return objpedido;
    }

    public List<pedido> getListPedido() {
        return listPedido;
    }

    public void setListPedido(List<pedido> listPedido) {
        this.listPedido = listPedido;
    }

    public usuario getObjetoCliente() {
        if (objetoCliente == null) {
            objetoCliente = new usuario();
        }
        return objetoCliente;
    }

    public void setObjetoCliente(usuario objetoCliente) {
        this.objetoCliente = objetoCliente;
    }

    public List<pedido_detalle> getListdetallePedido() {
        return listdetallePedido;
    }

    public void setListdetallePedido(List<pedido_detalle> listdetallePedido) {
        this.listdetallePedido = listdetallePedido;
    }

    public String getHorma() {
        return horma;
    }

    public void setHorma(String horma) {
        this.horma = horma;
    }

    public String getMateriap() {
        return materiap;
    }

    public void setMateriap(String materiap) {
        this.materiap = materiap;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getTotalPedido() {
        totalPedido = 0;
        for (int i = 0; i < listdetallePedido.size(); i++) {
            totalPedido += listdetallePedido.get(i).getCantidad();
        }
        return totalPedido;
    }
    
    
   
    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getFechaRegistroStr() {
        return fechaRegistroStr;
    }

    public void setFechaRegistroStr(String fechaRegistroStr) {
        this.fechaRegistroStr = fechaRegistroStr;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getClienteStr() {
        return clienteStr;
    }

    public void setClienteStr(String clienteStr) {
        this.clienteStr = clienteStr;
    }

    public Mcolor getColor() {
        if (color == null) {
            color = new Mcolor();
        }
        return color;
    }

    public void setColor(Mcolor color) {
        this.color = color;
    }

    public String getNombrecolor() {
        return nombrecolor;
    }

    public void setNombrecolor(String nombrecolor) {
        this.nombrecolor = nombrecolor;

    }

    public int getIdPersonaTurno() {
        return idPersonaTurno;
    }

    public void setIdPersonaTurno(int idPersonaTurno) {
        this.idPersonaTurno = idPersonaTurno;
    }

    public String getUsuarioTurno() {
        return UsuarioTurno;
    }

    public void setUsuarioTurno(String UsuarioTurno) {
        this.UsuarioTurno = UsuarioTurno;
    }

    public int getTotalDespachado() {
        return totalDespachado;
    }

    public void setTotalDespachado(int totalDespachado) {
        this.totalDespachado = totalDespachado;
        
        
    }

}
