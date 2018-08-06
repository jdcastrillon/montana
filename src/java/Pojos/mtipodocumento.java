package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;

public class mtipodocumento extends Persistencia implements Serializable {

    public int idTipoDoc;
    public String descripcion;
    public String estado;

    public mtipodocumento() {
        super();
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        String prepareInsert = "insert into mtipodocumento (idTipoDoc,descripcion,estado) values (" + idTipoDoc + "," + "'" + descripcion + "'" + "," + "'" + estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=mtipodocumento.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update mtipodocumento set " + "descripcion=" + "'" + descripcion + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "idTipoDoc=" + idTipoDoc;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=mtipodocumento.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  mtipodocumento where " + "idTipoDoc=" + idTipoDoc;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=mtipodocumento.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<mtipodocumento> List() {
        ArrayList<mtipodocumento> listmtipodocumento = new ArrayList();
        String prepareQuery = "select * from mtipodocumento";
        try {
            ResultSet rs = mtipodocumento.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                mtipodocumento tabla = new mtipodocumento();
                tabla.setIdTipoDoc(rs.getInt(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));
                listmtipodocumento.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listmtipodocumento;
    }

    public void ResetValores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
