package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class tipodespacho extends Persistencia implements Serializable {

    public BigDecimal idTipoDespacho;
    public String Descripcion;
    public String estado;

    public tipodespacho() {
        super();
    }

    public BigDecimal getIdTipoDespacho() {
        return idTipoDespacho;
    }

    public void setIdTipoDespacho(BigDecimal idTipoDespacho) {
        this.idTipoDespacho = idTipoDespacho;
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
        String prepareInsert = "insert into tipodespacho (idTipoDespacho,Descripcion,estado) values (" + idTipoDespacho + "," + "'" + Descripcion + "'" + "," + "'" + estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=tipodespacho.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update tipodespacho set " + "Descripcion=" + "'" + Descripcion + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "idTipoDespacho=" + idTipoDespacho;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=tipodespacho.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  tipodespacho where " + "idTipoDespacho=" + idTipoDespacho;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=tipodespacho.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<tipodespacho> List() {
        ArrayList<tipodespacho> listtipodespacho = new ArrayList();
        String prepareQuery = "select * from tipodespacho";
        try {
            ResultSet rs = tipodespacho.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                tipodespacho tabla = new tipodespacho();
                tabla.setIdTipoDespacho(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));

                listtipodespacho.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listtipodespacho;
    }
}
