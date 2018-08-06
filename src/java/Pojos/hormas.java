package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class hormas extends Persistencia implements Serializable {

    private boolean seleccion;
    private BigDecimal idHorma;
    private String descripcion;
    private String estado;

    public hormas() {
        super();
    }

    public BigDecimal getIdHorma() {
        return idHorma;
    }

    public void setIdHorma(BigDecimal idHorma) {
        this.idHorma = idHorma;
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

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into hormas (descripcion,estado) values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setString(2, "A");
            transaccion = hormas.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update hormas set descripcion=?,estado=? where idHorma=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setString(2, estado);
            preparedStatement.setBigDecimal(3, idHorma);
            transaccion = hormas.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  hormas where " + "idHorma=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idHorma);
            transaccion = hormas.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<hormas> List() {
        ArrayList<hormas> listhormas = new ArrayList();
        String prepareQuery = "select * from hormas";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = hormas.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                hormas tabla = new hormas();
                tabla.setIdHorma(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));
                tabla.setEstado(rs.getString(3));

                listhormas.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                Logger.getLogger(hormas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listhormas;
    }

    public void ResetValores() {
        this.descripcion = "";
    }

    @Override
    public String toString() {
        return idHorma + " " + descripcion;
    }

}
