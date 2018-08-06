package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class mciudades extends Persistencia implements Serializable {

    public BigDecimal idCiudad;
    public String Nombre;
    public String Estado;

    public mciudades() {
        super();
    }

    public BigDecimal getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into mciudades (Nombre,Estado) values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Nombre);
            preparedStatement.setString(2, Estado);
            transaccion = mciudades.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update mciudades set Nombre=?,Estado=? where idCiudad=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, Nombre);
            preparedStatement.setString(2, Estado);
            preparedStatement.setBigDecimal(3, idCiudad);
            transaccion = mciudades.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  mciudades where idCiudad=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idCiudad);
            transaccion = mciudades.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<mciudades> List() {
        ArrayList<mciudades> listmciudades = new ArrayList();
        String prepareQuery = "select * from mciudades";
        try {
            ResultSet rs = mciudades.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                mciudades tabla = new mciudades();
                tabla.setIdCiudad(rs.getBigDecimal(1));
                tabla.setNombre(rs.getString(2));
                tabla.setEstado(rs.getString(3));

                listmciudades.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listmciudades;
    }
}
