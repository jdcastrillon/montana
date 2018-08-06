package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class despachoproducto extends Persistencia implements Serializable {

    public int idDespachoProducto;
    public int idDespacho;
    public int idProducto;
    public int idTalla;
    public int cantidad;

    public int getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(int idTalla) {
        this.idTalla = idTalla;
    }

    public despachoproducto() {
        super();
    }

    public int getIdDespachoProducto() {
        return idDespachoProducto;
    }

    public void setIdDespachoProducto(int idDespachoProducto) {
        this.idDespachoProducto = idDespachoProducto;
    }

    public int getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(int idDespacho) {
        this.idDespacho = idDespacho;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

 

  

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into despachoproducto "
                + "(idDespacho,idTurno,Usuario,idPersona,idProducto,idSubCategoria,idCategoria,idCaja,cantidad,"
                + "TipoProducto) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, idDespacho);
            preparedStatement.setInt(5, idProducto);
            preparedStatement.setInt(9, cantidad);

            transaccion = despachoproducto.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update despachoproducto set " + "cantidad=?, TipoProducto=? where "
                + "idDespachoProducto=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(3, idDespachoProducto);

            transaccion = despachoproducto.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  despachoproducto where idDespachoProducto=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(3, idDespachoProducto);
            transaccion = despachoproducto.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<despachoproducto> List() {
        ArrayList<despachoproducto> listdespachoproducto = new ArrayList();
        String prepareQuery = "select * from despachoproducto";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = despachoproducto.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                despachoproducto tabla = new despachoproducto();
                tabla.setIdDespachoProducto(rs.getInt(1));
                tabla.setIdDespacho(rs.getInt(2));      
                listdespachoproducto.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listdespachoproducto;
    }
}
