package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class usuario extends Persistencia implements Serializable {

    private String Usuario;
    private int idPersona;
    private String NickName;
    private int Current;
    private String clave;
    private List<persona> List_persona = new ArrayList();
    private List<rolxuser> lisRolxUser = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
    private persona objpersona;
    private rolxuser objrolxuser;

    public usuario() {
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsertPersona = "insert into persona (Nombre,Apellido,NombreCompleto,Documento,fechaNacimiento,sexo,Telefono1,Telefono2,idTipoDoc) values (?,?,?,?,?,?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsertPersona);
            preparedStatement.setString(1, objpersona.Nombre);
            preparedStatement.setString(2, objpersona.Apellido);
            preparedStatement.setString(3, objpersona.Nombre + " " + objpersona.Apellido);
            preparedStatement.setString(4, objpersona.Documento);
            preparedStatement.setString(5, sdf.format(objpersona.fechaNacimiento));
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, objpersona.Telefono1);
            preparedStatement.setString(8, objpersona.Telefono2);
            preparedStatement.setInt(9, objpersona.idTipoDoc);
            transaccion = usuario.this.getConecion().transaccion(preparedStatement);
            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = usuario.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                Current = rs.getInt(1);
            }
            if (objrolxuser.idRol == 2) {
                Usuario = objpersona.Documento;
                String pstmInsertCliente = "insert into cliente (idPersona,idempresa) values (?,?)";
                preparedStatement = this.getConecion().con.prepareStatement(pstmInsertCliente);
                preparedStatement.setInt(1, Current);
                preparedStatement.setInt(2, 0);
                transaccion = usuario.this.getConecion().transaccion(preparedStatement);
            }
            String prepareInsert = "insert into usuario (Usuario,idPersona,NickName,clave) values (?,?,?,?)";
            preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, Usuario);
            preparedStatement.setInt(2, Current);
            preparedStatement.setString(3, objpersona.Nombre + " " + objpersona.Apellido);
            preparedStatement.setString(4, clave);
            transaccion = usuario.this.getConecion().transaccion(preparedStatement);
            //RolxUser.
            int next = 0;
            String sql2 = "select case when max(idRolXUser) is null then 1 else (max(idRolXUser)+1) end as idRolXuser from rolxuser";
            ResultSet rs2 = usuario.super.getConecion().query(sql2);
            if (rs2.absolute(1)) {
                next = rs2.getInt(1);
            }
            String pstmInsertRolxuser = "insert into rolxuser (Usuario,idPersona,idRol,idRolXUser) values (?,?,?,?)";
            preparedStatement = this.getConecion().con.prepareStatement(pstmInsertRolxuser);
            preparedStatement.setString(1, Usuario);
            preparedStatement.setInt(2, Current);
            preparedStatement.setInt(3, objrolxuser.idRol);
            preparedStatement.setInt(4, next);
            transaccion = usuario.this.getConecion().transaccion(preparedStatement);

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
        String prepareInsertPersona = "update persona set Nombre = ?, "
                + "Apellido = ?, NombreCompleto = ?, Documento = ?, "
                + "fechaNacimiento = ?, "
                + "sexo = ?, Telefono1 = ?, Telefono2 = ?, idTipoDoc = ? where idPersona = ?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsertPersona);
            preparedStatement.setString(1, objpersona.Nombre);
            preparedStatement.setString(2, objpersona.Apellido);
            preparedStatement.setString(3, objpersona.Nombre + " " + objpersona.Apellido);
            preparedStatement.setString(4, objpersona.Documento);
            preparedStatement.setString(5, sdf.format(objpersona.fechaNacimiento));
            preparedStatement.setString(6, objpersona.sexo);
            preparedStatement.setString(7, objpersona.Telefono1);
            preparedStatement.setString(8, objpersona.Telefono2);
            preparedStatement.setInt(9, objpersona.idTipoDoc);
            preparedStatement.setInt(10, objpersona.idPersona);
            transaccion = usuario.this.getConecion().transaccion(preparedStatement);
            if (objrolxuser.idRol > 1) {
                String prepareInsert = "update usuario set Usuario = ?, idPersona = ?, NickName = ?, clave = ? "
                        + "where Usuario = ? and idPersona = ?";
                preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
                preparedStatement.setString(1, Usuario);
                preparedStatement.setInt(2, objpersona.idPersona);
                preparedStatement.setString(3, objpersona.Nombre + " " + objpersona.Apellido);
                preparedStatement.setString(4, clave);
                preparedStatement.setString(5, Usuario);
                preparedStatement.setInt(6, objpersona.idPersona);
                transaccion = usuario.this.getConecion().transaccion(preparedStatement);

                String pstmInsertRolxuser = "update rolxuser set Usuario = ?, idPersona = ?, "
                        + "idRol = ?, idRolXUser = ? where Usuario = ? and idPersona = ? and idRol = ?";
                preparedStatement = this.getConecion().con.prepareStatement(pstmInsertRolxuser);
                preparedStatement.setString(1, Usuario);
                preparedStatement.setInt(2, objrolxuser.idPersona);
                preparedStatement.setInt(3, objrolxuser.idRol);
                preparedStatement.setInt(4, objrolxuser.idRolXUser);
                preparedStatement.setString(5, Usuario);
                preparedStatement.setInt(6, objrolxuser.idPersona);
                preparedStatement.setInt(7, objrolxuser.idRol);
                transaccion = usuario.this.getConecion().transaccion(preparedStatement);
            }
        } catch (SQLException ex) {
            transaccion = -1;
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
    public int remove() {
        int transaccion = -1;
        String prepareDelete = "update persona set estado = 'I'  where idPersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setInt(1, idPersona);
            transaccion = usuario.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<usuario> List() {
        ArrayList<usuario> listusuario = new ArrayList();
        String prepareQuery = "select * from usuario u, persona p where u.idPersona = p.idPersona and p.estado = 'A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = usuario.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                usuario tabla = new usuario();
                persona p = new persona();
                tabla.setUsuario(rs.getString(1));
                tabla.setIdPersona(rs.getInt(2));
                tabla.setNickName(rs.getString(3));
                tabla.setClave(rs.getString(4));

                p.setDocumento(rs.getString(9));
                p.setNombreCompleto(rs.getString(8));
                tabla.setObjpersona(p);

                listusuario.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listusuario;
    }

    public usuario getUserById(String usuario, int idpersona) {
        usuario user = null;
        persona p = null;
        rolxuser rxu = null;
        String prepareQuery = "select * from usuario u, persona p, rolxuser r "
                + "where u.idPersona = p.idPersona and r.idPersona = p.idPersona "
                + "and u.Usuario = '" + usuario + "' and u.idPersona = " + idpersona;
        System.out.println("consultando usuario " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = usuario.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                user = new usuario();
                p = new persona();
                rxu = new rolxuser();
                user.setUsuario(rs.getString(1));
                user.setIdPersona(rs.getInt(2));
                user.setNickName(rs.getString(3));
                user.setClave(rs.getString(4));
                p.setIdPersona(rs.getInt(5));
                p.setNombre(rs.getString(6));
                p.setApellido(rs.getString(7));
                p.setNombreCompleto(rs.getString(8));
                p.setDocumento(rs.getString(9));
                p.setFechaNacimiento(rs.getDate(10));
                p.setSexo(rs.getString(11));
                p.setTelefono1(rs.getString(12));
                p.setTelefono2(rs.getString(13));
                p.setIdTipoDoc(rs.getInt(14));
                p.setEstado(rs.getString(15));
                user.setObjpersona(p);
                rxu.setUsuario(rs.getString(16));
                rxu.setIdPersona(rs.getInt(17));
                rxu.setIdRol(rs.getInt(18));
                rxu.setIdRolXUser(rs.getInt(19));
                user.setObjrolxuser(rxu);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            p = null;
            rxu = null;
        }
        return user;
    }

    public List<usuario> ListUsuarios(int idrol) {//1 cliente, 2 etc
        List<usuario> listuser = new ArrayList();
        persona p = null;
        rolxuser rxu = null;
        usuario user = null;
        String prepareQuery = "select * from usuario u, persona p, rolxuser r "
                + "where u.idPersona = p.idPersona and r.idPersona = p.idPersona "
                + "and r.idRol = " + idrol;
        System.out.println("consultando usuario " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = usuario.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                user = new usuario();
                p = new persona();
                rxu = new rolxuser();
                user.setUsuario(rs.getString(1));
                user.setIdPersona(rs.getInt(2));
                user.setNickName(rs.getString(3));
                user.setClave(rs.getString(4));
                p.setIdPersona(rs.getInt(5));
                p.setNombre(rs.getString(6));
                p.setApellido(rs.getString(7));
                p.setNombreCompleto(rs.getString(8));
                p.setDocumento(rs.getString(9));
                p.setFechaNacimiento(rs.getDate(10));
                p.setSexo(rs.getString(11));
                p.setTelefono1(rs.getString(12));
                p.setTelefono2(rs.getString(13));
                p.setIdTipoDoc(rs.getInt(14));
                p.setEstado(rs.getString(15));
                user.setObjpersona(p);
                rxu.setUsuario(rs.getString(16));
                rxu.setIdPersona(rs.getInt(17));
                rxu.setIdRol(rs.getInt(18));
                rxu.setIdRolXUser(rs.getInt(19));
                user.setObjrolxuser(rxu);
                listuser.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            p = null;
            rxu = null;
        }
        return listuser;
    }

    public String getUserByDocumento(String documento) {
        String datos = "";
        String query = "SELECT p.idPersona, c.idCliente from persona p "
                + "inner join cliente c on p.idPersona = c.idPersona "
                + "where p.Documento = '" + documento + "'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = usuario.super.getConecion().query(query);
            if (rs.next()) {
                datos = rs.getInt(1) + "," + rs.getInt(2);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
        return datos;
    }

    public boolean InicioSesion() {
        boolean r = false;
        String prepareQuery = "select * from usuario where usuario='" + Usuario + "' and clave='" + clave + "'";
        System.out.println("quwery : " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = usuario.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                this.Usuario = (rs.getString(1));
                this.idPersona = (rs.getInt(2));
                this.NickName = (rs.getString(3));
                r = true;
            } else {
                r = false;
            }
            System.out.println("Termino");
        } catch (SQLException ex) {
            r = false;
            System.out.println("Error Consulta usuario inicio sesion: " + ex.toString());
        }
        System.out.println("--- : " + r);
        return r;
    }

    public List<persona> getList_persona() {
        return List_persona;
    }

    public void setList_persona(List<persona> List_persona) {
        this.List_persona = List_persona;
    }

    public List<rolxuser> getLisRolxUser() {
        return lisRolxUser;
    }

    public void setLisRolxUser(List<rolxuser> lisRolxUser) {
        this.lisRolxUser = lisRolxUser;
    }

    public void ResetValores() {
        Usuario = "";
        NickName = "";
        clave = "";
        objpersona = null;
        objrolxuser = null;
        List_persona.clear();
        lisRolxUser.clear();
    }

    public persona getObjpersona() {
        if (objpersona == null) {
            objpersona = new persona();
        }
        return objpersona;
    }

    public void setObjpersona(persona objpersona) {
        this.objpersona = objpersona;
    }

    public rolxuser getObjrolxuser() {
        if (objrolxuser == null) {
            objrolxuser = new rolxuser();
        }
        return objrolxuser;
    }

    public void setObjrolxuser(rolxuser objrolxuser) {
        this.objrolxuser = objrolxuser;
    }

    @Override
    public String toString() {
        return getObjpersona().getDocumento() + " " + getObjpersona().getNombreCompleto();
    }

}
