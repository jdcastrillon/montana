package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cajas extends Persistencia implements Serializable {

    private BigDecimal idCaja;
    private String NumeroCaja;
    private String NombreCaja;
    private BigDecimal cantidad;
    private producto objProducto;
    private String estado;

    ArrayList<producto> ListProductos = new ArrayList();

    public cajas() {
        super();
        objProducto = new producto();
    }

    public BigDecimal getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(BigDecimal idCaja) {
        this.idCaja = idCaja;
    }

    public String getNumeroCaja() {
        return NumeroCaja;
    }

    public void setNumeroCaja(String NumeroCaja) {
        this.NumeroCaja = NumeroCaja;
    }

    public String getNombreCaja() {
        return NombreCaja;
    }

    public void setNombreCaja(String NombreCaja) {
        this.NombreCaja = NombreCaja;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public producto getObjProducto() {
        if (objProducto == null) {
            objProducto = new producto();
        }
        return objProducto;
    }

    public void setObjProducto(producto objProducto) {
        this.objProducto = objProducto;
    }

    public ArrayList<producto> getListProductos() {
        System.err.println("-");
        return ListProductos;
    }

    public void setListProductos(ArrayList<producto> ListProductos) {
        this.ListProductos = ListProductos;
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
        String prepareInsert = "insert into cajas (idCaja,NumeroCaja,NombreCaja,cantidad,estado) values (?,?,?,?,?)";

        String prepareInsertProductos = "insert into cajaproducto (idCaja,idProducto,idtalla,idColor,idCategoria,Cantidad) values (?,?,?,?,?,?)";

//        String prepareUpdateProductos = "update producto set cantidad=(cantidad-?) where idProducto=? and idTalla=? and idColor=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setBigDecimal(1, idCaja);
            preparedStatement.setString(2, idCaja.toString());
            preparedStatement.setString(3, NombreCaja);
            preparedStatement.setBigDecimal(4, cantidad);
            preparedStatement.setString(5, "A");

            transaccion = cajas.this.getConecion().transaccion(preparedStatement);

            //Productos
            System.out.println("Lista de Prodcutos : " + ListProductos.size());
            for (producto obj : ListProductos) {
                System.out.println("Categoria : " + obj.getObjCategoria().getIdCategoria());
                PreparedStatement preparedStatement2 = this.getConecion().con.prepareStatement(prepareInsertProductos);
                preparedStatement2.setBigDecimal(1, idCaja);
                preparedStatement2.setBigDecimal(2, obj.getIdProducto());
                preparedStatement2.setBigDecimal(3, obj.getIdTalla());
                preparedStatement2.setInt(4, obj.getIdColor());
                preparedStatement2.setBigDecimal(5, obj.getObjCategoria().getIdCategoria());
                preparedStatement2.setInt(6, obj.getCantidadCaja());

                transaccion = cajas.this.getConecion().transaccion(preparedStatement2);
                //update
//                PreparedStatement preparedStatement3 = this.getConecion().con.prepareStatement(prepareUpdateProductos);
//                preparedStatement3.setInt(1, obj.getCantidadCaja());
//                preparedStatement3.setBigDecimal(2, obj.getIdProducto());
//                preparedStatement3.setBigDecimal(3, obj.getIdTalla());
//                preparedStatement3.setInt(4, obj.getIdColor());
//
//                transaccion = cajas.this.getConecion().transaccion(preparedStatement3);

            }
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                transaccion = -1;
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                System.out.println("Error en rollback " + ex1.toString());
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
        String prepareEdit = "update cajas set NumeroCaja=?,NombreCaja=?,cantidad=? where idCaja=?";

        String prepareDeleteProductos = "delete from cajaproducto where idCaja=?";

        String prepareInsertProductos = "insert into cajaproducto (idCaja,idProducto,idtalla,idColor,idCategoria,Cantidad) values (?,?,?,?,?,?)";

//        String prepareUpdateProductos = "update producto set cantidad=(cantidad-?) where idProducto=? and idTalla=? and idColor=?";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, NumeroCaja);
            preparedStatement.setString(2, NombreCaja);
            preparedStatement.setBigDecimal(3, cantidad);
            preparedStatement.setBigDecimal(4, idCaja);

            transaccion = cajas.this.getConecion().transaccion(preparedStatement);

//            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call AjusteValores (?)}");
//            this.getConecion().cstmt.setBigDecimal(1, idCaja);
//            cajas.super.getConecion().cstmt.executeUpdate();

            PreparedStatement preparedStatement2 = this.getConecion().con.prepareStatement(prepareDeleteProductos);
            preparedStatement2.setBigDecimal(1, idCaja);

            transaccion = cajas.this.getConecion().transaccion(preparedStatement2);

            for (producto obj : ListProductos) {
                System.out.println("Categoria : " + obj.getIdCategoria());
                PreparedStatement preparedStatement3 = this.getConecion().con.prepareStatement(prepareInsertProductos);
                preparedStatement3.setBigDecimal(1, idCaja);
                preparedStatement3.setBigDecimal(2, obj.getIdProducto());
                preparedStatement3.setBigDecimal(3, obj.getIdTalla());
                preparedStatement3.setInt(4, obj.getIdColor());
                preparedStatement3.setBigDecimal(5,new BigDecimal(1));
                preparedStatement3.setInt(6, obj.getCantidadCaja());

                transaccion = cajas.this.getConecion().transaccion(preparedStatement3);
                //update
//                PreparedStatement preparedStatement4 = this.getConecion().con.prepareStatement(prepareUpdateProductos);
//                preparedStatement4.setInt(1, obj.getCantidadCaja());
//                preparedStatement4.setBigDecimal(2, obj.getIdProducto());
//                preparedStatement4.setBigDecimal(3, obj.getIdTalla());
//                preparedStatement4.setInt(4, obj.getIdColor());
//
//                transaccion = cajas.this.getConecion().transaccion(preparedStatement4);

            }

        } catch (SQLException ex) {
            try {
                System.out.println("Error SQL : " + ex.toString());
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(cajas.class.getName()).log(Level.SEVERE, null, ex1);
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
    public int remove() {
        int transaccion = -1;
        String prepareEdit = "update cajas set estado=? where idCaja=?";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, "N");
            preparedStatement.setBigDecimal(2, idCaja);
            transaccion = cajas.this.getConecion().transaccion(preparedStatement);

//            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call AjusteValores (?)}");
//            this.getConecion().cstmt.setBigDecimal(1, idCaja);
//            cajas.super.getConecion().cstmt.executeUpdate();
        } catch (SQLException ex) {
            try {
                System.out.println("Error SQL : " + ex.toString());
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(cajas.class.getName()).log(Level.SEVERE, null, ex1);
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
    public java.util.List<cajas> List() {
        ArrayList<cajas> listcajas = new ArrayList();
        String prepareQuery = "select * from cajas where estado='A'";

        String prepareProdCajas = "SELECT B.*,A.Cantidad FROM cajaproducto A , producto B where A.idProducto=B.idProducto and A.idCaja=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = cajas.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                cajas tabla = new cajas();
                tabla.setIdCaja(rs.getBigDecimal(1));
                tabla.setNumeroCaja(rs.getString(2));
                tabla.setNombreCaja(rs.getString(3));
                tabla.setCantidad(rs.getBigDecimal(4));

                listcajas.add(tabla);
            }

            for (cajas obj : listcajas) {
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareProdCajas);
                preparedStatement.setBigDecimal(1, obj.getIdCaja());
                System.out.println("--- " + obj.getIdCaja());
                ResultSet rs2 = cajas.super.getConecion().queryprepared(preparedStatement);

                while (rs2.next()) {
                    System.out.println("Entro");
                    producto p = new producto();
                    p.setIdProducto(rs2.getBigDecimal(1));
                    p.setIdTalla(rs2.getBigDecimal(2));
                    p.setIdColor(rs2.getInt(3));
                    p.setIdCategoria(rs2.getBigDecimal(4));
                    p.setNombreProducto(rs2.getString(5));
                    p.setCantidadCaja(rs2.getInt(8));
                    obj.getListProductos().add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listcajas;
    }

    public void CodigoNext() {
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call GetNumerador (?)}");
            this.getConecion().cstmt.setString(1, "Cajas");
            ResultSet rs = cajas.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                idCaja = rs.getBigDecimal(1);
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
    }
}
