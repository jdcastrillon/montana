package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

public class insumoproducto extends Persistencia implements Serializable {

    public BigDecimal idInsumoProducto;
    public BigDecimal idInsumo;
    public BigDecimal idProducto;
    public BigDecimal idSubCategoria;
    public BigDecimal idCategoria;
    public BigDecimal cantidad;

    List<insumos> List_insumos = new ArrayList();
    List<producto> List_producto = new ArrayList();

    public insumoproducto() {
        super();
    }

    public BigDecimal getIdInsumoProducto() {
        return idInsumoProducto;
    }

    public void setIdInsumoProducto(BigDecimal idInsumoProducto) {
        this.idInsumoProducto = idInsumoProducto;
    }

    public BigDecimal getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(BigDecimal idInsumo) {
        this.idInsumo = idInsumo;
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
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into insumoproducto (idInsumo,idProducto,idSubCategoria,idCategoria,cantidad)"
                + " values (?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idInsumo);
            preparedStatement.setBigDecimal(2, idProducto);
            preparedStatement.setBigDecimal(3, idSubCategoria);
            preparedStatement.setBigDecimal(4, idCategoria);
            preparedStatement.setBigDecimal(5, cantidad);
            transaccion = insumoproducto.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update insumoproducto set cantidad=? where idInsumoProducto=? and "
                + "" + "idInsumo=? and " + "idProducto=? and " + "idSubCategoria=? and " + "idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setBigDecimal(1, cantidad);
            preparedStatement.setBigDecimal(2, idInsumoProducto);
            preparedStatement.setBigDecimal(3, idInsumo);
            preparedStatement.setBigDecimal(4, idProducto);
            preparedStatement.setBigDecimal(5, idSubCategoria);
            preparedStatement.setBigDecimal(6, idCategoria);
            transaccion = insumoproducto.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  insumoproducto where idInsumoProducto=? and idInsumo=? "
                + "and idProducto=? and idSubCategoria=? and " + "idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);            
            preparedStatement.setBigDecimal(1, idInsumoProducto);
            preparedStatement.setBigDecimal(2, idInsumo);
            preparedStatement.setBigDecimal(3, idProducto);
            preparedStatement.setBigDecimal(4, idSubCategoria);
            preparedStatement.setBigDecimal(5, idCategoria);
            transaccion = insumoproducto.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<insumoproducto> List() {
        ArrayList<insumoproducto> listinsumoproducto = new ArrayList();
        String prepareQuery = "select * from insumoproducto";
        try {
            ResultSet rs = insumoproducto.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                insumoproducto tabla = new insumoproducto();
                tabla.setIdInsumoProducto(rs.getBigDecimal(1));
                tabla.setIdInsumo(rs.getBigDecimal(2));
                tabla.setIdProducto(rs.getBigDecimal(3));
                tabla.setIdSubCategoria(rs.getBigDecimal(4));
                tabla.setIdCategoria(rs.getBigDecimal(5));
                tabla.setCantidad(rs.getBigDecimal(6));

                listinsumoproducto.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listinsumoproducto;
    }
}
