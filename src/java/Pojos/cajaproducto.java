package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

public class cajaproducto extends Persistencia implements Serializable {

    public BigDecimal idCajaProducto;
    public BigDecimal idCaja;
    public BigDecimal idProducto;
    public BigDecimal idSubCategoria;
    public BigDecimal idCategoria;
    public BigDecimal Cantidad;

    List<cajas> List_cajas = new ArrayList();
    List<producto> List_producto = new ArrayList();

    public cajaproducto() {
        super();
    }

    public BigDecimal getIdCajaProducto() {
        return idCajaProducto;
    }

    public void setIdCajaProducto(BigDecimal idCajaProducto) {
        this.idCajaProducto = idCajaProducto;
    }

    public BigDecimal getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(BigDecimal idCaja) {
        this.idCaja = idCaja;
    }

    public BigDecimal getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(BigDecimal idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(BigDecimal idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
    }

    public BigDecimal getCantidad() {
        return Cantidad;
    }

    public void setCantidad(BigDecimal Cantidad) {
        this.Cantidad = Cantidad;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into cajaproducto (idCaja,idProducto,idSubCategoria,idCategoria,Cantidad) values "
                + "(?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idCaja);
            preparedStatement.setBigDecimal(2, idProducto);
            preparedStatement.setBigDecimal(3, idSubCategoria);
            preparedStatement.setBigDecimal(4, idCategoria);
            preparedStatement.setBigDecimal(5, Cantidad);

            transaccion = cajaproducto.this.getConecion().transaccion(preparedStatement);
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
    public int edit() {
        int transaccion = -1;
        String prepareEdit = "update cajaproducto set " + "Cantidad=? where " + "idCajaProducto=? and "
                + "idCaja=? and " + "idProducto=? and " + "idSubCategoria=? and " + "idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setBigDecimal(1, Cantidad);
            preparedStatement.setBigDecimal(2, idCajaProducto);
            preparedStatement.setBigDecimal(3, idCaja);
            preparedStatement.setBigDecimal(4, idProducto);
            preparedStatement.setBigDecimal(5, idSubCategoria);
            preparedStatement.setBigDecimal(6, idCategoria);

            transaccion = cajaproducto.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  cajaproducto where " + "idCajaProducto=? and " + "idCaja=? and "
                + "idProducto=? and " + "idSubCategoria=? and " + "idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idCajaProducto);
            preparedStatement.setBigDecimal(2, idCaja);
            preparedStatement.setBigDecimal(3, idProducto);
            preparedStatement.setBigDecimal(4, idSubCategoria);
            preparedStatement.setBigDecimal(5, idCategoria);
            transaccion = cajaproducto.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<cajaproducto> List() {
        ArrayList<cajaproducto> listcajaproducto = new ArrayList();
        String prepareQuery = "select * from cajaproducto";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = cajaproducto.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                cajaproducto tabla = new cajaproducto();
                tabla.setIdCajaProducto(rs.getBigDecimal(1));
                tabla.setIdCaja(rs.getBigDecimal(2));
                tabla.setIdProducto(rs.getBigDecimal(3));
                tabla.setIdSubCategoria(rs.getBigDecimal(4));
                tabla.setIdCategoria(rs.getBigDecimal(5));
                tabla.setCantidad(rs.getBigDecimal(6));

                listcajaproducto.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listcajaproducto;
    }
}
