package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.List;

public class pedidovariables extends Persistencia implements Serializable {

    public BigDecimal idPedidoVariable;
    public BigDecimal idPedido;
    public BigDecimal idCliente;
    public BigDecimal idPersona;
    public BigDecimal id_variables;
    public String Estado;

    List<mvariables> List_mvariables = new ArrayList();
    List<pedido> List_pedido = new ArrayList();

    public pedidovariables() {
        super();
    }

    public BigDecimal getIdPedidoVariable() {
        return idPedidoVariable;
    }

    public void setIdPedidoVariable(BigDecimal idPedidoVariable) {
        this.idPedidoVariable = idPedidoVariable;
    }

    public BigDecimal getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(BigDecimal idPedido) {
        this.idPedido = idPedido;
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

    public BigDecimal getId_variables() {
        return id_variables;
    }

    public void setId_variables(BigDecimal id_variables) {
        this.id_variables = id_variables;
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
        String prepareInsert = "insert into pedidovariables (idPedidoVariable,idPedido,idCliente,idPersona,id_variables,Estado) values (" + idPedidoVariable + "," + idPedido + "," + idCliente + "," + idPersona + "," + id_variables + "," + "'" + Estado + "'" + ")";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = pedidovariables.this.getConecion().transaccion(prepareInsert);
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
        String prepareEdit = "update pedidovariables set " + "Estado=" + "'" + Estado + "'" + " where " + "idPedidoVariable=" + idPedidoVariable + " and " + "idPedido=" + idPedido + " and " + "idCliente=" + idCliente + " and " + "idPersona=" + idPersona + " and " + "id_variables=" + id_variables;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = pedidovariables.this.getConecion().transaccion(prepareEdit);
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
        String prepareDelete = "delete from  pedidovariables where " + "idPedidoVariable=" + idPedidoVariable + " and " + "idPedido=" + idPedido + " and " + "idCliente=" + idCliente + " and " + "idPersona=" + idPersona + " and " + "id_variables=" + id_variables;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion = pedidovariables.this.getConecion().transaccion(prepareDelete);
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
    public java.util.List<pedidovariables> List() {
        ArrayList<pedidovariables> listpedidovariables = new ArrayList();
        String prepareQuery = "select * from pedidovariables";
        try {
            ResultSet rs = pedidovariables.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                pedidovariables tabla = new pedidovariables();
                tabla.setIdPedidoVariable(rs.getBigDecimal(1));
                tabla.setIdPedido(rs.getBigDecimal(2));
                tabla.setIdCliente(rs.getBigDecimal(3));
                tabla.setIdPersona(rs.getBigDecimal(4));
                tabla.setId_variables(rs.getBigDecimal(5));
                tabla.setEstado(rs.getString(6));

                listpedidovariables.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listpedidovariables;
    }
}
