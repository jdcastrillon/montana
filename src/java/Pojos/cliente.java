package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

public class cliente extends Persistencia implements Serializable {

    public BigDecimal idCliente;
    public BigDecimal idPersona;
    public BigDecimal idempresa;

    List<persona> List_persona = new ArrayList();

    public cliente() {
        super();
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public BigDecimal getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(BigDecimal idempresa) {
        this.idempresa = idempresa;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into cliente (idPersona,idempresa) values (?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idPersona);
            preparedStatement.setBigDecimal(2, idempresa);

            transaccion = cliente.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update cliente set " + "idempresa=? where " + "idCliente=? and " + "idPersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setBigDecimal(1, idempresa);
            preparedStatement.setBigDecimal(2, idCliente);
            preparedStatement.setBigDecimal(3, idPersona);

            transaccion = cliente.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  cliente where " + "idCliente=? and " + "idPersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idCliente);
            preparedStatement.setBigDecimal(2, idPersona);            

            transaccion = cliente.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<cliente> List() {
        ArrayList<cliente> listcliente = new ArrayList();
        String prepareQuery = "select * from cliente";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = cliente.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                cliente tabla = new cliente();
                tabla.setIdCliente(rs.getBigDecimal(1));
                tabla.setIdPersona(rs.getBigDecimal(2));
                tabla.setIdempresa(rs.getBigDecimal(3));

                listcliente.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listcliente;
    }
}
