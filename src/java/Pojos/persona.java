package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class persona extends Persistencia implements Serializable {

    int idPersona;
    String Nombre;
    String Apellido;
    private String NombreCompleto;
    String Documento;
    Date fechaNacimiento;
    String sexo;
    String Telefono1;
    String Telefono2;
    int idTipoDoc;
    private String estado;

    List<mtipodocumento> List_mtipodocumento = new ArrayList();

    public persona() {
        super();
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String Documento) {
        this.Documento = Documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono1() {
        return Telefono1;
    }

    public void setTelefono1(String Telefono1) {
        this.Telefono1 = Telefono1;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public void setTelefono2(String Telefono2) {
        this.Telefono2 = Telefono2;
    }

    public int getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into persona (idPersona,Nombre,Apellido,NombreCompleto,Documento,fechaNacimiento,sexo,Telefono1,Telefono2,idTipoDoc) values (" + idPersona + "," + "'" + Nombre + "'" + "," + "'" + Apellido + "'" + "," + "'" + NombreCompleto + "'" + "," + "'" + Documento + "'" + "," + fechaNacimiento + "," + "'" + sexo + "'" + "," + "'" + Telefono1 + "'" + "," + "'" + Telefono2 + "'" + "," + idTipoDoc + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//          transaccion=persona.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update persona set " + "Nombre=" + "'" + Nombre + "'" + "," + "Apellido=" + "'" + Apellido + "'" + "," + "NombreCompleto=" + "'" + NombreCompleto + "'" + "," + "Documento=" + "'" + Documento + "'" + "," + "fechaNacimiento=" + fechaNacimiento + "," + "sexo=" + "'" + sexo + "'" + "," + "Telefono1=" + "'" + Telefono1 + "'" + "," + "Telefono2=" + "'" + Telefono2 + "'" + "," + "idTipoDoc=" + idTipoDoc + " where " + "idPersona=" + idPersona;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//           transaccion=persona.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  persona where " + "idPersona=" + idPersona;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=persona.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<persona> List() {
        ArrayList<persona> listpersona = new ArrayList();
        String prepareQuery = "select * from persona";
        try {
            ResultSet rs = persona.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                persona tabla = new persona();
                tabla.setIdPersona(rs.getInt(1));
                tabla.setNombre(rs.getString(2));
                tabla.setApellido(rs.getString(3));
                tabla.setNombreCompleto(rs.getString(4));
                tabla.setDocumento(rs.getString(5));
                tabla.setFechaNacimiento(rs.getDate(6));
                tabla.setSexo(rs.getString(7));
                tabla.setTelefono1(rs.getString(8));
                tabla.setTelefono2(rs.getString(9));
                tabla.setIdTipoDoc(rs.getInt(10));

                listpersona.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listpersona;
    }
    
    
    public int getIdPersonaByDocumento(String doc) {
        int id= 0;
        String prepareQuery = "select idPersona from persona where Documento = '"+doc+"'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = persona.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                id = rs.getInt(1);
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
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
