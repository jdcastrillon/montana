/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class MTallas extends Persistencia implements Serializable {

    private BigDecimal idTalla;
    private String talla1;
    private String talla2;
    private String talla3;
    private String estado;
    private String tallaFinal;

    public MTallas() {
        super();
    }

    public BigDecimal getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(BigDecimal idTalla) {
        this.idTalla = idTalla;
    }

    public String getTalla1() {
        return talla1;
    }

    public void setTalla1(String talla1) {
        this.talla1 = talla1;
    }

    public String getTalla2() {
        return talla2;
    }

    public void setTalla2(String talla2) {
        this.talla2 = talla2;
    }

    public String getTalla3() {
        return talla3;
    }

    public void setTalla3(String talla3) {
        this.talla3 = talla3;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int edit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTallaFinal() {
        return tallaFinal;
    }

    public void setTallaFinal(String tallaFinal) {
        this.tallaFinal = tallaFinal;
    }

    @Override
    public java.util.List List() {
        ArrayList<MTallas> listTallas = new ArrayList();        
        String prepareQuery = "select * from mtallas";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = MTallas.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                MTallas tabla = new MTallas();
                tabla.setIdTalla(rs.getBigDecimal(1));
                tabla.setTalla1(rs.getString(2));
                tabla.setTalla2(rs.getString(3));
                tabla.setTalla3(rs.getString(4));
                tabla.setEstado(rs.getString(5));

                listTallas.add(tabla);
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
        return listTallas;
    }

    @Override
    public String toString() {
        return talla1;
    }
    


}
