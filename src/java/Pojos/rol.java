package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;

public class rol extends Persistencia implements Serializable {

    public int idRol;
    public String Descripcion;
    public String estado;

    public rol() {
        super();
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into rol (idRol,Descripcion,estado) values (" + idRol + "," + "'" + Descripcion + "'" + "," + "'" + estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=rol.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update rol set " + "Descripcion=" + "'" + Descripcion + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "idRol=" + idRol;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=rol.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  rol where " + "idRol=" + idRol;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=rol.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<rol> List() {
        ArrayList<rol> listrol = new ArrayList();
        String prepareQuery = "select * from rol";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = rol.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                rol tabla = new rol();
                tabla.setIdRol(rs.getInt(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));

                listrol.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listrol;
    }

    public void ResetValores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
