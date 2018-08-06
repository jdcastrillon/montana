package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;

public class despacho extends Persistencia implements Serializable {

    public BigDecimal idDespacho;
    public int idTurno;
    public String Usuario;
    public int idPersona;
    public int idPedido;
    public String factura;
    public Date FechaEntrega;
    public int idCiudad;
    public String NumeroGuia;
    public int idTipoDespacho;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private int Current;
    List<tipodespacho> List_tipodespacho = new ArrayList();
    public List<despachoproducto> List_despachoproducto = new ArrayList();
    List<turno> List_turno = new ArrayList();
    public int estadoPedido;
    public int IdCliente;

    public despacho() {
        super();
    }

    public BigDecimal getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(BigDecimal idDespacho) {
        this.idDespacho = idDespacho;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Date getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Date FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNumeroGuia() {
        return NumeroGuia;
    }

    public void setNumeroGuia(String NumeroGuia) {
        this.NumeroGuia = NumeroGuia;
    }

    public int getIdTipoDespacho() {
        return idTipoDespacho;
    }

    public void setIdTipoDespacho(int idTipoDespacho) {
        this.idTipoDespacho = idTipoDespacho;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into despacho (idTurno,Usuario,idPersona,idPedido,factura,"
                + "FechaEntrega,idCiudad,NumeroGuia,idTipoDespacho) values (?,?,?,?,?,?,?,?,?)";
        System.out.println("Usuario = " + Usuario);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setInt(1, idTurno);
            preparedStatement.setString(2, Usuario);
            preparedStatement.setInt(3, idPersona);
            preparedStatement.setInt(4, idPedido);
            preparedStatement.setString(5, factura);
            preparedStatement.setString(6, sdf.format(FechaEntrega));
            preparedStatement.setInt(7, idCiudad);
            preparedStatement.setString(8, NumeroGuia);
            preparedStatement.setInt(9, idTipoDespacho);
            transaccion = despacho.this.getConecion().transaccion(preparedStatement);
            String sql = "Select LAST_INSERT_ID()";
            ResultSet rs = despacho.super.getConecion().query(sql);
            if (rs.absolute(1)) {
                Current = rs.getInt(1);
            }
            for (int i = 0; i < getList_despachoproducto().size(); i++) {
                String prepareInsertD = "insert into despachoproducto "
                        + "(idDespacho,idProducto,idTalla,cantidad) values (?,?,?,?)";
                PreparedStatement insertDetallestm = this.getConecion().con.prepareStatement(prepareInsertD);
                insertDetallestm.setInt(1, Current);
                insertDetallestm.setInt(2, getList_despachoproducto().get(i).getIdProducto());
                insertDetallestm.setInt(3, getList_despachoproducto().get(i).getIdTalla());
                insertDetallestm.setInt(4, getList_despachoproducto().get(i).getCantidad());
                transaccion += despacho.this.getConecion().transaccion(insertDetallestm);
            }
            if (estadoPedido > 0) {
                String queryPV = "insert into pedidovariables (idPedido,idCliente,idPersona,id_variables,Estado,fecha,idDespacho) "
                        + "values (?,?,?,?,?,?,?)";
                PreparedStatement pstmVariable = this.getConecion().con.prepareStatement(queryPV);
                pstmVariable.setInt(1, idPedido);
                pstmVariable.setInt(2, IdCliente);
                pstmVariable.setInt(3, idPersona);
                pstmVariable.setInt(4, estadoPedido);
                pstmVariable.setString(5, "A");
                pstmVariable.setString(6, sdf.format(new Date()));
                pstmVariable.setInt(7, Current);
                transaccion += despacho.this.getConecion().transaccion(pstmVariable);
            }

//            String queryUpdate = "update pedido_detalle pdetalle "
//                    + "inner join pedidovariables p "
//                    + "INNER join despacho d on d.idDespacho = p.idDespacho "
//                    + "inner join despachoproducto dp on d.idDespacho = p.idDespacho "
//                    + "set pdetalle.despachado = case when dp.cantidad <  pdetalle.cantidad then 'F' "
//                    + "when dp.cantidad >  pdetalle.cantidad then 'A' "
//                    + "else 'S' end "
//                    + "where p.idDespacho = " + Current + " and p.idPedido = " + idPedido +" "
//                    + "and  pdetalle.idPedido = p.idPedido "
//                    + "and pdetalle.idTalla = dp.idTalla and pdetalle.idProducto = dp.idProducto";
//            PreparedStatement pstUpdate = this.getConecion().con.prepareStatement(queryUpdate);
//            transaccion += despacho.this.getConecion().transaccion(pstUpdate);

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            transaccion = -1;
            try {
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                System.out.println("error " + ex1);
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
        int transaccion = -1;
        String prepareEdit = "update despacho set idPedido=?,factura=?,Estado=? ,FechaEntrega=? ,idCiudad=?,NumeroGuia=?"
                + ",idTipoDespacho=? where idDespacho=? and idTurno=? and Usuario=? and idPersona=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setInt(1, idPedido);
            preparedStatement.setString(2, factura);
            preparedStatement.setDate(3, (java.sql.Date) FechaEntrega);
            preparedStatement.setInt(4, idCiudad);
            preparedStatement.setString(5, NumeroGuia);
            preparedStatement.setInt(6, idTipoDespacho);
            preparedStatement.setBigDecimal(7, idDespacho);
            preparedStatement.setInt(8, idTurno);
            preparedStatement.setString(9, Usuario);
            preparedStatement.setInt(10, idPedido);

            transaccion = despacho.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  despacho where idDespacho=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idDespacho);
            transaccion = despacho.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<despacho> List() {
        ArrayList<despacho> listdespacho = new ArrayList();
        String prepareQuery = "select * from despacho";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = despacho.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                despacho tabla = new despacho();
                tabla.setIdDespacho(rs.getBigDecimal(1));
                tabla.setIdTurno(rs.getInt(2));
                tabla.setUsuario(rs.getString(3));
                tabla.setIdPersona(rs.getInt(4));
                tabla.setIdPedido(rs.getInt(5));
                tabla.setFactura(rs.getString(6));
                tabla.setFechaEntrega(rs.getDate(7));
                tabla.setIdCiudad(rs.getInt(8));
                tabla.setNumeroGuia(rs.getString(9));
                tabla.setIdTipoDespacho(rs.getInt(10));

                listdespacho.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listdespacho;
    }

    public List<despachoproducto> getList_despachoproducto() {
        return List_despachoproducto;
    }

    public void setList_despachoproducto(List<despachoproducto> List_despachoproducto) {
        this.List_despachoproducto = List_despachoproducto;
    }

    public int getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(int estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

}
