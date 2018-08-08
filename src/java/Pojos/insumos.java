package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class insumos extends Persistencia implements Serializable {

    private boolean seleccion;
    private BigDecimal idInsumo;
    private String NombreInsumo;
    private BigDecimal Cantidad;
    private BigDecimal CantidadAUtilizar;
    private BigDecimal idUnidad;
    unidad unidad;

    public insumos() {
        super();
        unidad = new unidad();
    }

    public BigDecimal getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(BigDecimal idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNombreInsumo() {
        return NombreInsumo;
    }

    public void setNombreInsumo(String NombreInsumo) {
        this.NombreInsumo = NombreInsumo;
    }

    public BigDecimal getCantidad() {
        return Cantidad;
    }

    public void setCantidad(BigDecimal Cantidad) {
        this.Cantidad = Cantidad;
    }

    public BigDecimal getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(BigDecimal idUnidad) {
        this.idUnidad = idUnidad;
    }

    public unidad getUnidad() {
        if (this.unidad == null) {
            this.unidad = new unidad();
        }
        return this.unidad;
    }

    public void setUnidad(unidad unidad) {
        this.unidad = unidad;
    }

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    public BigDecimal getCantidadAUtilizar() {
        return CantidadAUtilizar;
    }

    public void setCantidadAUtilizar(BigDecimal CantidadAUtilizar) {
        this.CantidadAUtilizar = CantidadAUtilizar;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into insumos (NombreInsumo,Cantidad,idUnidad) values (?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, NombreInsumo);
            preparedStatement.setBigDecimal(2, Cantidad);
            preparedStatement.setBigDecimal(3, unidad.getIdUnidad());

            transaccion = insumos.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update insumos set NombreInsumo=?,Cantidad=?,idUnidad=? where idInsumo=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, NombreInsumo);
            preparedStatement.setBigDecimal(2, Cantidad);
            preparedStatement.setBigDecimal(3, unidad.getIdUnidad());
            preparedStatement.setBigDecimal(4, idInsumo);
            transaccion = insumos.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  insumos where idInsumo=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idInsumo);
            transaccion = insumos.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<insumos> List() {
        ArrayList<insumos> listinsumos = new ArrayList();
        String prepareQuery = "select A.idInsumo,A.NombreInsumo,A.Cantidad,B.idUnidad,B.Descripcion,B.siglas "
                + "from insumos A , unidad B where A.idUnidad=B.idUnidad";
        System.out.println("-- " + prepareQuery);
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = insumos.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                insumos tabla = new insumos();
                unidad u = new unidad();
                tabla.setSeleccion(false);
                tabla.setIdInsumo(rs.getBigDecimal(1));
                tabla.setNombreInsumo(rs.getString(2));
                tabla.setCantidad(rs.getBigDecimal(3));
                tabla.setCantidadAUtilizar(new BigDecimal(0));

                u.setIdUnidad(rs.getBigDecimal(4));
                u.setDescripcion(rs.getString(5));
                u.setSiglas(rs.getString(6));

                tabla.setUnidad(u);

                listinsumos.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listinsumos;
    }

    @Override
    public String toString() {
        return idInsumo + " " + NombreInsumo;
    }

    public void ResetValores() {
        this.NombreInsumo = "";
        this.Cantidad = new BigDecimal(0);
    }

}
