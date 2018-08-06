package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class mvariables extends Persistencia implements Serializable {

    public BigDecimal id_variables;
    public String Descripcion;
    public String estado;

    public mvariables() {
        super();
    }

    public BigDecimal getId_variables() {
        return id_variables;
    }

    public void setId_variables(BigDecimal id_variables) {
        this.id_variables = id_variables;
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
        String prepareInsert = "insert into mvariables (id_variables,Descripcion,estado) values (" + id_variables + "," + "'" + Descripcion + "'" + "," + "'" + estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=mvariables.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update mvariables set " + "Descripcion=" + "'" + Descripcion + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "id_variables=" + id_variables;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=mvariables.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  mvariables where " + "id_variables=" + id_variables;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=mvariables.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<mvariables> List() {
        ArrayList<mvariables> listmvariables = new ArrayList();
        String prepareQuery = "select * from mvariables where estado = 'A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = mvariables.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                mvariables tabla = new mvariables();
                tabla.setId_variables(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));
                listmvariables.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }finally {
            try {            
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return listmvariables;
    }
}
