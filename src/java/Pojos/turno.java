package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class turno extends Persistencia implements Serializable {

    public int idTurno;
    public String Usuario;
    public int idPersona;
    public Date FechaTurno;
    public String estado;
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    List<usuario> List_usuario = new ArrayList();

    public turno() {
        super();
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
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

    public Date getFechaTurno() {
        return FechaTurno;
    }

    public void setFechaTurno(Date FechaTurno) {
        if (FechaTurno == null) {
            FechaTurno = new Date();
        }
        this.FechaTurno = FechaTurno;
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
        String prepareInsert = "insert into turno (Usuario,idPersona,FechaTurno,estado) values (?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Usuario);
            preparedStatement.setInt(2, idPersona);
            preparedStatement.setString(3, sdf.format(FechaTurno));
            preparedStatement.setString(4, estado);
            transaccion = turno.this.getConecion().transaccion(preparedStatement);

            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = turno.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                idTurno = rs.getInt(1);
            }
        } catch (SQLException ex) {
            try {
                this.getConecion().getconecion().rollback();
            } catch (SQLException ex1) {
                System.out.println("Error " + ex1);
            }
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
        String prepareEdit = "update turno set " + "FechaTurno=" + "'" + FechaTurno + "'" + "," + "estado=" + "'" + estado + "'" + " where " + "idTurno=" + idTurno + " and " + "Usuario=" + "'" + Usuario + "'" + " and " + "idPersona=" + idPersona;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = turno.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  turno where " + "idTurno=" + idTurno + " and " + "Usuario=" + "'" + Usuario + "'" + " and " + "idPersona=" + idPersona;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = turno.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<turno> List() {
        ArrayList<turno> listturno = new ArrayList();
        String prepareQuery = "select * from turno";
        System.out.println("ssss " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = turno.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                turno tabla = new turno();
                tabla.setIdTurno(rs.getInt(1));
                tabla.setUsuario(rs.getString(2));
                tabla.setIdPersona(rs.getInt(3));
                tabla.setFechaTurno(rs.getDate(4));
                tabla.setEstado(rs.getString(5));
                listturno.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("Error Consulta : " + ex.toString());
            }
        }
        return listturno;
    }

    public turno getMiturno(String user) {
        turno t = null;
        String prepareQuery = "select idTurno, Usuario, idPersona, FechaTurno from turno where usuario='" + user + "' and estado='A' order by FechaTurno desc limit 1";
        System.out.println("imprimiendo turno " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = turno.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                t = new turno();
                t.setIdTurno(rs.getInt(1));
                t.setUsuario(rs.getString(2));
                t.setIdPersona(rs.getInt(3));
                t.setFechaTurno(rs.getDate(4));
            }else{
               t=null; 
            }

        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("Error Consulta : " + ex.toString());
            }
        }
        return t;
    }

    public java.util.List<turnoDetalle> ListXUser(int turno) {
        ArrayList<turnoDetalle> listDetalle = new ArrayList();
        try {
//            System.out.println("Turno : " + turno);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call ListaDetXturno (?)}");
            this.getConecion().cstmt.setInt(1, turno);
            ResultSet rs = turno.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Cargo");
                turnoDetalle t = new turnoDetalle();
                t.setNombre(rs.getString(1));
                t.setCodigo(rs.getInt(2));
                t.setDocumento(rs.getString(3));
                t.setNombreCompleto(rs.getString(4));
                t.setFechaRegistro(rs.getDate(5));
                t.setFechaEntrega(rs.getDate(6));

                listDetalle.add(t);
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
        return listDetalle;
    }

    public int getIdTurnoByUser(String Usuario) {
        int IdTurno = -1;
        String prepareQuery = "select max(idTurno) idTurno from turno where Usuario = '" + Usuario + "' and estado='A'";
        System.out.println("ssss " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = turno.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                IdTurno = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("Error Consulta : " + ex.toString());
            }
        }
        return IdTurno;
    }

    public int CerrarCaja() {
        int transaccion = -1;
        String prepareEdit = "update turno set estado='C' where idTurno=? and Usuario=?";
        try {
            System.out.println("idturno : " +idTurno);
            System.out.println("Usuario : " +Usuario);
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);//    
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setInt(1, idTurno);
            preparedStatement.setString(2, Usuario);

            transaccion = turno.this.getConecion().transaccion(preparedStatement);
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                this.getConecion().getconecion().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(turno.class.getName()).log(Level.SEVERE, null, ex1);
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

    public void ResetValores() {

    }

    @Override
    public String toString() {
        return "turno{" + "idTurno=" + idTurno + ", Usuario=" + Usuario + ", idPersona=" + idPersona + ", FechaTurno=" + FechaTurno + ", estado=" + estado + ", List_usuario=" + List_usuario + '}';
    }

}
