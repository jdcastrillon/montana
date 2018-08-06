package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.List;

public class subcategoria extends Persistencia implements Serializable {

    public BigDecimal idSubCategoria;
    public BigDecimal idCategoria;
    public String descripcion;

    List<categoria> List_categoria = new ArrayList();

    public subcategoria() {
        super();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into subcategoria (idSubCategoria,idCategoria,descripcion) values (" + idSubCategoria + "," + idCategoria + "," + "'" + descripcion + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = subcategoria.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update subcategoria set " + "descripcion=" + "'" + descripcion + "'" + " where " + "idSubCategoria=" + idSubCategoria + " and " + "idCategoria=" + idCategoria;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = subcategoria.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  subcategoria where " + "idSubCategoria=" + idSubCategoria + " and " + "idCategoria=" + idCategoria;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = subcategoria.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<subcategoria> List() {
        ArrayList<subcategoria> listsubcategoria = new ArrayList();
        String prepareQuery = "select * from subcategoria";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = subcategoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                subcategoria tabla = new subcategoria();
                tabla.setIdSubCategoria(rs.getBigDecimal(1));
                tabla.setIdCategoria(rs.getBigDecimal(2));
                tabla.setDescripcion(rs.getString(3));

                listsubcategoria.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listsubcategoria;
    }

    public java.util.List<subcategoria> ListXCategoria(BigDecimal idCategoria) {
        ArrayList<subcategoria> listsubcategoria = new ArrayList();
        String prepareQuery = "select * from subcategoria where idCategoria=" + idCategoria;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = subcategoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                subcategoria tabla = new subcategoria();
                tabla.setIdSubCategoria(rs.getBigDecimal(1));
                tabla.setIdCategoria(rs.getBigDecimal(2));
                tabla.setDescripcion(rs.getString(3));

                listsubcategoria.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listsubcategoria;
    }
}
