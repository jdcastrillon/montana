/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanDavid
 */
public class Mcolor extends Persistencia implements Serializable {

    private BigDecimal idClor;
    private String nombre;
    private String silas;
    private String estado;
    private int current;

    public Mcolor() {
        super();
    }

    public BigDecimal getIdClor() {
        return idClor;
    }

    public void setIdClor(BigDecimal idClor) {
        this.idClor = idClor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSilas() {
        return silas;
    }

    public void setSilas(String silas) {
        this.silas = silas;
    }

    @Override
    public int create() {
        int transaccion = -1;
        System.out.println("Entro a crear Color");
        String prepareColor = "insert into mcolores (nombre ,estado)"
                + " values (?,?)";
        System.out.println("to : " + toString());
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //Guardamos Producto
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareColor);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, estado);
            transaccion = Mcolor.this.getConecion().transaccion(preparedStatement);

            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = Mcolor.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                current = rs.getInt(1);
            }
            //Guardamos hormas
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(producto.class.getName()).log(Level.SEVERE, null, ex1);
            }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.util.List List() {
        ArrayList<Mcolor> listColor = new ArrayList();
        String prepareQuery = "select idcolor,nombre,siglas from mcolores where estado='A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Mcolor.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                Mcolor tabla = new Mcolor();
                tabla.setIdClor(rs.getBigDecimal(1));
                tabla.setNombre(rs.getString(2));
                tabla.setSilas(rs.getString(3));

                listColor.add(tabla);
            }

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        System.out.println("Recupero colores : " + listColor.size());
        return listColor;

    }

    public Mcolor buscarColor(String referencia) {
        Mcolor color = new Mcolor();
        System.out.println("Entro a buscar color : " + referencia);
        boolean r = false;
        String prepareQuery = "select * from mcolores where nombre ='" + referencia + "'";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = Mcolor.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                System.out.println("Existe Color");
                this.idClor = rs.getBigDecimal(1);
                this.nombre = rs.getString(2);
                this.estado = rs.getString(3);
                color = this;
                r = true;
            }
            if (r == false) {
                System.out.println("No existe Color");
                this.setNombre(referencia);
                this.setEstado("A");
                create();
                color = this;
            }
        } catch (SQLException ex) {
            System.out.println("Error Producto : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return color;
    }

    @Override
    public String toString() {
        return nombre;
    }

 

}
