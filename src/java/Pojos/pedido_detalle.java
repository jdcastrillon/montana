package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class pedido_detalle extends Persistencia implements Serializable {

    public int idPedidoDespacho;
    public int idPedido;
    public int idCaja;
    public int idProducto;
    public String productoJson;
    public int cantidad;
    public String despachado;
    private int idTalla;
    private int idColor;
    private String noTalla;
    private pedido objPedido;
    private String nombreProducto;
    private boolean seleccion;
    private String materia;
    private String horma;

    private String descTalla;

    private String detalle;


    public pedido_detalle() {
        super();
        objPedido = new pedido();
    }

    public int getIdPedidoDespacho() {
        return idPedidoDespacho;
    }

    public void setIdPedidoDespacho(int idPedidoDespacho) {
        this.idPedidoDespacho = idPedidoDespacho;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProductoJson() {
        return productoJson;
    }

    public void setProductoJson(String productoJson) {
        this.productoJson = productoJson;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDespachado() {
        return despachado;
    }

    public void setDespachado(String despachado) {
        this.despachado = despachado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHorma() {
        return horma;
    }

    public void setHorma(String horma) {
        this.horma = horma;
    }
    
    

    @Override

    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into pedido_detalle "
                + "(idPedido,idCaja,idProducto,idTalla,idColor,productoJson,cantidad,despachado) "
                + "values (?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, idPedido);
            preparedStatement.setInt(2, idCaja);
            preparedStatement.setInt(3, idProducto);
            preparedStatement.setInt(4, idTalla);
            preparedStatement.setInt(5, idColor);
            preparedStatement.setString(6, productoJson);
            preparedStatement.setInt(7, cantidad);
            preparedStatement.setString(8, despachado);
            transaccion = pedido_detalle.this.getConecion().transaccion(preparedStatement);
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

    public pedido getObjPedido() {
        if (objPedido == null) {
            objPedido = new pedido();
        }
        return objPedido;
    }

    public void setObjPedido(pedido objPedido) {
        this.objPedido = objPedido;
    }

    @Override
    public int edit() {
        int transaccion = -1;
        String prepareEdit = "update pedido_detalle set idCaja=?,idProducto=?,productoJson=?,cantidad=?,despachado=? "
                + "where idPedidoDespacho=? and idPedido=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setInt(1, idCaja);
            preparedStatement.setInt(3, idProducto);
            preparedStatement.setString(4, productoJson);
            preparedStatement.setInt(5, cantidad);
            preparedStatement.setString(6, despachado);
            preparedStatement.setInt(7, idPedidoDespacho);
            preparedStatement.setInt(8, idPedido);
            transaccion = pedido_detalle.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  pedido_detalle where idPedidoDespacho=? and idPedido=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setInt(1, idPedidoDespacho);
            preparedStatement.setInt(2, idPedido);
            transaccion = pedido_detalle.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<pedido_detalle> List() {
        ArrayList<pedido_detalle> listpedido_detalle = new ArrayList();
        String prepareQuery = "select * from pedido_detalle";
        try {
            ResultSet rs = pedido_detalle.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                pedido_detalle tabla = new pedido_detalle();
                tabla.setIdPedidoDespacho(rs.getInt(1));
                tabla.setIdPedido(rs.getInt(2));
                tabla.setIdCaja(rs.getInt(3));
                tabla.setIdProducto(rs.getInt(4));
                tabla.setProductoJson(rs.getString(5));
                tabla.setCantidad(rs.getInt(6));
                tabla.setDespachado(rs.getString(7));
                listpedido_detalle.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listpedido_detalle;
    }

    public int getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(int idTalla) {
        this.idTalla = idTalla;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    @Override
    public String toString() {
        return "pedido_detalle{" + "idPedidoDespacho=" + idPedidoDespacho + ", idPedido=" + idPedido + ", idCaja=" + idCaja + ", idProducto=" + idProducto + ", productoJson=" + productoJson + ", cantidad=" + cantidad + ", despachado=" + despachado + ", idTalla=" + idTalla + ", idColor=" + idColor + '}';
    }

    public String getNoTalla() {
        switch (idTalla) {
            case 1:
                noTalla = "2 - 51";
                break;
            case 2:
                noTalla = "65/2 - 21/2 - 52";
                break;
            case 3:
                noTalla = "65/8 - S - 3 - 53";
                break;
            case 4:
                noTalla = "63/4 - 31/2 - 54";
                break;
            case 5:
                noTalla = "67/8 - M - 4 - 55";
                break;
            case 6:
                noTalla = "7 - 41/2 - 56";
                break;
            case 7:
                noTalla = "71/8 - L - 5 - 57";
                break;
            case 8:
                noTalla = "71/4 - 51/2 - 58";
                break;
            case 9:
                noTalla = "73/8 - XL - 6 - 59";
                break;
            case 10:
                noTalla = "71/2 - 61/2 - 60";
                break;
            case 11:
                noTalla = "75/8 - XXL - 7 - 61";
                break;
            default:
                noTalla = "";
                break;
        }
        return noTalla;
    }

    public void setNoTalla(String noTalla) {
        this.noTalla = noTalla;
    }


    public String getDescTalla() {
        return descTalla;
    }

    public void setDescTalla(String descTalla) {
        this.descTalla = descTalla;
    }


    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}
