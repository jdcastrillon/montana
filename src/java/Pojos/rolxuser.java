package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.List;

public class rolxuser extends Persistencia implements Serializable {

    public String Usuario;
    public int idPersona;
    public int idRol;
    public int idRolXUser;

    List<rol> List_rol = new ArrayList();
    List<usuario> List_usuario = new ArrayList();

    public rolxuser() {
        super();
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdRolXUser() {
        return idRolXUser;
    }

    public void setIdRolXUser(int idRolXUser) {
        this.idRolXUser = idRolXUser;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into rolxuser (Usuario,idPersona,idRol,idRolXUser) values (" + "'" + Usuario + "'" + "," + idPersona + "," + idRol + "," + idRolXUser + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = rolxuser.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update rolxuser set " + "idRolXUser=" + idRolXUser + " where " + "Usuario=" + "'" + Usuario + "'" + " and " + "idPersona=" + idPersona + " and " + "idRol=" + idRol;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = rolxuser.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  rolxuser where " + "Usuario=" + "'" + Usuario + "'" + " and " + "idPersona=" + idPersona + " and " + "idRol=" + idRol;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = rolxuser.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<rolxuser> List() {
        ArrayList<rolxuser> listrolxuser = new ArrayList();
        String prepareQuery = "select * from rolxuser";
        try {
            ResultSet rs = rolxuser.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                rolxuser tabla = new rolxuser();
                tabla.setUsuario(rs.getString(1));
                tabla.setIdPersona(rs.getInt(2));
                tabla.setIdRol(rs.getInt(3));
                tabla.setIdRolXUser(rs.getInt(4));
                listrolxuser.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listrolxuser;
    }
}
