package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

public class hormasprod extends Persistencia implements Serializable {

    public BigDecimal idHormaProd;
    public BigDecimal idProducto;
    public BigDecimal idSubCategoria;
    public BigDecimal idCategoria;
    public BigDecimal idHorma;

    List<hormas> List_hormas = new ArrayList();
    List<producto> List_producto = new ArrayList();

    public hormasprod() {
        super();
    }

    public BigDecimal getIdHormaProd() {
        return idHormaProd;
    }

    public void setIdHormaProd(BigDecimal idHormaProd) {
        this.idHormaProd = idHormaProd;
    }

    public BigDecimal getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(BigDecimal idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(BigDecimal idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
    }

    public BigDecimal getIdHorma() {
        return idHorma;
    }

    public void setIdHorma(BigDecimal idHorma) {
        this.idHorma = idHorma;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into hormasprod (idProducto,idSubCategoria,idCategoria,idHorma) values (?,?,?,?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idProducto);
            preparedStatement.setBigDecimal(2, idSubCategoria);
            preparedStatement.setBigDecimal(3, idCategoria);
            preparedStatement.setBigDecimal(4, idHorma);

            transaccion = hormasprod.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);

            transaccion = hormasprod.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  hormasprod where idHormaProd=? and " + "idProducto=? and "
                + "" + "idSubCategoria=? and " + "idCategoria=? and " + "idHorma=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idHormaProd);
            preparedStatement.setBigDecimal(2, idProducto);
            preparedStatement.setBigDecimal(3, idSubCategoria);
            preparedStatement.setBigDecimal(4, idCategoria);
            preparedStatement.setBigDecimal(5, idHorma);
            transaccion = hormasprod.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<hormasprod> List() {
        ArrayList<hormasprod> listhormasprod = new ArrayList();
        String prepareQuery = "select * from hormasprod";
        try {
            ResultSet rs = hormasprod.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                hormasprod tabla = new hormasprod();
                tabla.setIdHormaProd(rs.getBigDecimal(1));
                tabla.setIdProducto(rs.getBigDecimal(2));
                tabla.setIdSubCategoria(rs.getBigDecimal(3));
                tabla.setIdCategoria(rs.getBigDecimal(4));
                tabla.setIdHorma(rs.getBigDecimal(5));

                listhormasprod.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listhormasprod;
    }
}
