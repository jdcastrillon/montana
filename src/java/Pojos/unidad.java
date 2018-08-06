package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class unidad extends Persistencia implements Serializable {

    public BigDecimal idUnidad;
    public String Descripcion;
    public String siglas;
    public String estado;

    public unidad() {
        super();
    }

    public BigDecimal getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(BigDecimal idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
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
        String prepareInsert = "insert into unidad (idUnidad,Descripcion,siglas,estado) values (" + idUnidad + "," + "'" + Descripcion + "'" + "," + "'" + siglas + "'" + "," + "'" + estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=unidad.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update unidad set " + "Descripcion=" + "'" + Descripcion + "'" + "," + "siglas=" + "'" + siglas + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "idUnidad=" + idUnidad;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=unidad.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  unidad where " + "idUnidad=" + idUnidad;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=unidad.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<unidad> List() {
        ArrayList<unidad> listunidad = new ArrayList();
        String prepareQuery = "select * from unidad";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = unidad.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                unidad tabla = new unidad();
                tabla.setIdUnidad(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setSiglas(rs.getString(3));
                tabla.setEstado(rs.getString(4));

                listunidad.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                Logger.getLogger(unidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listunidad;
    }

    @Override
    public String toString() {
        return "unidad{" + "idUnidad=" + idUnidad + ", Descripcion=" + Descripcion + ", siglas=" + siglas + ", estado=" + estado + '}';
    }
    
}
