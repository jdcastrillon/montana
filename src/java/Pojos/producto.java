package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class producto extends Persistencia implements Serializable {

    private BigDecimal idProducto;
    private BigDecimal idSubCategoria;
    private BigDecimal idCategoria;
    private BigDecimal idTalla;
    private int idColor;

    private String nombreProducto;
    private int Cantidad;
    private int CantidadCaja;
    private String color;
    private String estado;
    private boolean seleccion;

    categoria objCategoria;
    insumoproducto objInsumoProducto;
    hormasprod objHormaProducto;
    MTallas objTallas;
    hormas objHorma;
    Mcolor objColor;
    cajaproducto objcajaproducto;

    List<categoria> List_subcategoria = new ArrayList();
    List<insumos> ListInsumos = new ArrayList();
    List<hormas> ListHormas = new ArrayList();

    public producto() {
        super();
        objCategoria = new categoria();
        objInsumoProducto = new insumoproducto();
        objHormaProducto = new hormasprod();
        objTallas = new MTallas();
        objColor = new Mcolor();
        objcajaproducto = new cajaproducto();

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

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public categoria getObjCategoria() {
        if (objCategoria == null) {
            objCategoria = new categoria();
        }
        return objCategoria;
    }
  
    public void setObjCategoria(categoria objCategoria) {
        this.objCategoria = objCategoria;
    }

    public insumoproducto getObjInsumoProducto() {
        return objInsumoProducto;
    }

    public void setObjInsumoProducto(insumoproducto objInsumoProducto) {
        this.objInsumoProducto = objInsumoProducto;
    }

    public hormasprod getObjHormaProducto() {
        return objHormaProducto;
    }

    public void setObjHormaProducto(hormasprod objHormaProducto) {
        this.objHormaProducto = objHormaProducto;
    }

    public MTallas getObjTallas() {
        return objTallas;
    }

    public void setObjTallas(MTallas objTallas) {
        this.objTallas = objTallas;
    }

    public List<categoria> getList_subcategoria() {
        return List_subcategoria;
    }

    public void setList_subcategoria(List<categoria> List_subcategoria) {
        this.List_subcategoria = List_subcategoria;
    }

    public List<insumos> getListInsumos() {
        return ListInsumos;
    }

    public void setListInsumos(List<insumos> ListInsumos) {
        this.ListInsumos = ListInsumos;
    }

    public List<hormas> getListHormas() {
        return ListHormas;
    }

    public void setListHormas(List<hormas> ListHormas) {
        this.ListHormas = ListHormas;
    }

    public BigDecimal getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(BigDecimal idTalla) {
        this.idTalla = idTalla;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public hormas getObjHorma() {
        if (objHorma == null) {
            objHorma = new hormas();
        }
        return objHorma;
    }

    public void setObjHorma(hormas objHorma) {
        this.objHorma = objHorma;
    }

    public Mcolor getObjColor() {
        if (objColor == null) {
            objColor = new Mcolor();
        }

        return objColor;
    }

    public void setObjColor(Mcolor objColor) {
        this.objColor = objColor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    public int getCantidadCaja() {
        return CantidadCaja;
    }

    public void setCantidadCaja(int CantidadCaja) {
        this.CantidadCaja = CantidadCaja;
    }

    public cajaproducto getObjcajaproducto() {
        if (objcajaproducto == null) {
            objcajaproducto = new cajaproducto();
        }
        return objcajaproducto;
    }

    public void setObjcajaproducto(cajaproducto objcajaproducto) {
        this.objcajaproducto = objcajaproducto;
    }


    @Override
    public int create() {
        int transaccion = -1;

        idColor = objColor.buscarColor(color).getCurrent();

        String prepareInsertProducto = "insert into producto (idProducto,idCategoria,idTalla,idColor,nombreProducto,Cantidad,estado)"
                + " values (?,?,?,?,?,?,?)";

        String prepareInsertHorma = "insert into hormasprod (idProducto,idCategoria,idHorma)"
                + " values (?,?,?)";

        String prepareInsertInsumo = "insert into insumoproducto (idInsumo,idProducto,idCategoria,cantidad ) values (?,?,?,?)";

      
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //Guardamos Producto
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsertProducto);
            preparedStatement.setBigDecimal(1, idProducto);
            preparedStatement.setBigDecimal(2, idCategoria);
            preparedStatement.setBigDecimal(3, idTalla);
            preparedStatement.setInt(4, idColor);
            preparedStatement.setString(5, nombreProducto);
            preparedStatement.setInt(6, Cantidad);
            preparedStatement.setString(7, "A");

            transaccion = producto.this.getConecion().transaccion(preparedStatement);

            //Guardamos hormas
            PreparedStatement preparedStatement2 = this.getConecion().con.prepareStatement(prepareInsertHorma);
            for (hormas objeto : ListHormas) {

                preparedStatement2.setBigDecimal(1, idProducto);
                preparedStatement2.setBigDecimal(2, idCategoria);
                preparedStatement2.setBigDecimal(3, objeto.getIdHorma());

                transaccion = producto.this.getConecion().transaccion(preparedStatement2);
            }

            //Guardamos Insumos
            PreparedStatement preparedStatement3 = this.getConecion().con.prepareStatement(prepareInsertInsumo);
            for (insumos objeto : ListInsumos) {
                System.out.println("--- : " + objeto.toString());
                preparedStatement3.setBigDecimal(1, objeto.getIdInsumo());
                preparedStatement3.setBigDecimal(2, idProducto);
                preparedStatement3.setBigDecimal(3, idCategoria);
                preparedStatement3.setBigDecimal(4, objeto.getCantidadAUtilizar());

                transaccion = producto.this.getConecion().transaccion(preparedStatement3);
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(producto.class.getName()).log(Level.SEVERE, null, ex1);
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
//        idColor = objColor.buscarColor(color).getCurrent();

        String prepareUpdateProducto = "update  producto set idCategoria=?,idTalla=?,idColor=?,nombreProducto=?,Cantidad=? where idProducto=?";

        String prepareDeleteHorma = "delete FROM `hormasprod` WHERE idProducto=?";

        String prepareDeleteInsumo = "delete from insumoproducto where idProducto=?";

        String prepareInsertHorma = "insert into hormasprod (idProducto,idCategoria,idHorma)"
                + " values (?,?,?)";

        String prepareInsertInsumo = "insert into insumoproducto (idInsumo,idProducto,idCategoria,cantidad ) values (?,?,?,?)";

        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //Limpiamos la tabla
            PreparedStatement prepareddeleteHorma = this.getConecion().con.prepareStatement(prepareDeleteHorma);
            prepareddeleteHorma.setBigDecimal(1, idProducto);

            transaccion = producto.this.getConecion().transaccion(prepareddeleteHorma);

            PreparedStatement prepareddeleteInsumo = this.getConecion().con.prepareStatement(prepareDeleteInsumo);
            prepareddeleteInsumo.setBigDecimal(1, idProducto);

            transaccion = producto.this.getConecion().transaccion(prepareddeleteInsumo);

            //Guardamos Producto
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareUpdateProducto);

            preparedStatement.setBigDecimal(1, objCategoria.getIdCategoria());
            preparedStatement.setBigDecimal(2, idTalla);
            preparedStatement.setInt(3, idColor);
            preparedStatement.setString(4, nombreProducto);
            preparedStatement.setInt(5, Cantidad);
            preparedStatement.setBigDecimal(6, idProducto);

            transaccion = producto.this.getConecion().transaccion(preparedStatement);

            //Guardamos hormas
            PreparedStatement preparedStatement2 = this.getConecion().con.prepareStatement(prepareInsertHorma);
            for (hormas objeto : ListHormas) {

                preparedStatement2.setBigDecimal(1, idProducto);
                preparedStatement2.setBigDecimal(2, objCategoria.getIdCategoria());
                preparedStatement2.setBigDecimal(3, objeto.getIdHorma());

                transaccion = producto.this.getConecion().transaccion(preparedStatement2);
            }

            //Guardamos Insumos
            PreparedStatement preparedStatement3 = this.getConecion().con.prepareStatement(prepareInsertInsumo);
            for (insumos objeto : ListInsumos) {
                System.out.println("--- : " + objeto.toString());
                preparedStatement3.setBigDecimal(1, objeto.getIdInsumo());
                preparedStatement3.setBigDecimal(2, idProducto);
                preparedStatement3.setBigDecimal(3, objCategoria.getIdCategoria());
                preparedStatement3.setBigDecimal(4, objeto.getCantidadAUtilizar());

                transaccion = producto.this.getConecion().transaccion(preparedStatement3);
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(producto.class.getName()).log(Level.SEVERE, null, ex1);
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
//        idColor = objColor.buscarColor(color).getCurrent();

        String prepareUpdateProducto = "update  producto set estado=? where idProducto=? and idCategoria=? and idTalla=? and idColor=?";

//        String prepareDeleteHorma = "delete FROM `hormasprod` WHERE idProducto=?";
//
//        String prepareDeleteInsumo = "delete from insumoproducto where idProducto=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            //Limpiamos la tabla
//            PreparedStatement prepareddeleteHorma = this.getConecion().con.prepareStatement(prepareDeleteHorma);
//            prepareddeleteHorma.setBigDecimal(1, idProducto);
//
//            transaccion = producto.this.getConecion().transaccion(prepareddeleteHorma);
//
//            PreparedStatement prepareddeleteInsumo = this.getConecion().con.prepareStatement(prepareDeleteInsumo);
//            prepareddeleteInsumo.setBigDecimal(1, idProducto);
//
//            transaccion = producto.this.getConecion().transaccion(prepareddeleteInsumo);

            //Guardamos Producto
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareUpdateProducto);
            preparedStatement.setString(1, "N");
            preparedStatement.setBigDecimal(2, idProducto);
            preparedStatement.setBigDecimal(3, objCategoria.getIdCategoria());
            preparedStatement.setBigDecimal(4, idTalla);
            preparedStatement.setInt(5, idColor);

            transaccion = producto.this.getConecion().transaccion(preparedStatement);

        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
            try {
                this.getConecion().con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(producto.class.getName()).log(Level.SEVERE, null, ex1);
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
    public java.util.List<producto> List() {
        ArrayList<producto> listproducto = new ArrayList();

        String prepareQuery = "select A.*,b.refcolor,c.descripcion from producto A , mcolores b,categoria c\n"
                + "where A.idColor=b.idColor and A.idCategoria=c.idCategoria and A.estado='A'";

        String prepareQueryInsumos = "select A.idInsumo,A.NombreInsumo,A.idUnidad,C.Descripcion,B.cantidad from insumos A , insumoproducto B , unidad C\n"
                + "where A.idInsumo=B.idInsumo and B.idProducto=?\n"
                + "and A.idUnidad=C.idUnidad";

        String prepareQueryHormas = "select idHorma,descripcion from hormas where idHorma in (\n"
                + "SELECT idHorma FROM `hormasprod` WHERE idProducto=?)";
        //Ajustar Talla
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = producto.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                producto tabla = new producto();
                Mcolor color = new Mcolor();
                categoria cate = new categoria();

                tabla.setIdProducto(rs.getBigDecimal(1));
                tabla.setIdTalla(rs.getBigDecimal(2));
                tabla.setIdColor(rs.getInt(3));
                cate.setIdCategoria(rs.getBigDecimal(4));

                tabla.setNombreProducto(rs.getString(5));
                tabla.setCantidad(rs.getInt(6));

                color.setNombre(rs.getString(8));
                cate.setDescripcion(rs.getString(9));

                tabla.setObjCategoria(cate);
                tabla.setObjColor(color);

                listproducto.add(tabla);
            }
            //Insumos
            for (producto object : listproducto) {
                System.out.println("Entro Codigo : " + object.getIdProducto());
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareQueryInsumos);
                preparedStatement.setBigDecimal(1, object.getIdProducto());
                ResultSet rs2 = producto.super.getConecion().queryprepared(preparedStatement);

                while (rs2.next()) {
                    System.out.println("Trajo datos para insumos");
                    insumos insu = new insumos();
                    unidad uni = new unidad();
                    insu.setSeleccion(true);
                    insu.setIdInsumo(rs2.getBigDecimal(1));
                    insu.setNombreInsumo(rs2.getString(2));
                    insu.setCantidadAUtilizar(rs2.getBigDecimal(5));

                    uni.setIdUnidad(rs2.getBigDecimal(3));
                    uni.setDescripcion(rs2.getString(4));

                    insu.setUnidad(uni);;
                    object.getListInsumos().add(insu);
                }
                System.out.println("tamaño insumos :  " + object.getListInsumos().size());
            }

            //Hormas
            //Insumos
            for (producto object : listproducto) {
                System.out.println("Entro Codigo : " + object.getIdProducto());
                PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareQueryHormas);
                preparedStatement.setBigDecimal(1, object.getIdProducto());
                ResultSet rs3 = producto.super.getConecion().queryprepared(preparedStatement);

                while (rs3.next()) {
                    System.out.println("Trajo datos para insumos");
                    hormas horma = new hormas();
                    horma.setSeleccion(true);
                    horma.setIdHorma(rs3.getBigDecimal(1));
                    horma.setDescripcion(rs3.getString(2));

                    object.getListHormas().add(horma);
                }
                System.out.println("tamaño insumos :  " + object.getListInsumos().size());
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
        return listproducto;
    }

    public java.util.List<producto> ListSingle() {
        ArrayList<producto> listproducto = new ArrayList();
        String prepareQuery = "select * from producto where estado = 'A'";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = producto.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                producto tabla = new producto();
                tabla.setIdProducto(rs.getBigDecimal(1));
                tabla.setIdTalla(rs.getBigDecimal(2));
                tabla.setIdColor(rs.getInt(3));
                tabla.setIdCategoria(rs.getBigDecimal(4));             
                tabla.setNombreProducto(rs.getString(5));
                tabla.setCantidad(rs.getInt(6));   
                tabla.setEstado(rs.getString(7));     
                listproducto.add(tabla);
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
        return listproducto;
    }


    public void CodigoNext() {
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().cstmt = this.getConecion().con.prepareCall("{call GetNumerador (?)}");
            this.getConecion().cstmt.setString(1, "producto");
            ResultSet rs = producto.super.getConecion().cstmt.executeQuery();
            while (rs.next()) {
                idProducto = rs.getBigDecimal(1);
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
    
      public String getHormaMpbyProducto(String producto) {
        String detalle = "";
        try {
            String prepareQuery = "select h.descripcion,i.NombreInsumo,p.idProducto "
                    + "from producto p "
                    + "inner join hormasprod hp on p.idProducto = hp.idProducto "
                    + "inner join hormas h on hp.idHorma = h.idHorma "
                    + "inner join insumoproducto ins on ins.idProducto = p.idProducto "
                    + "inner join insumos i on i.idInsumo = ins.idInsumo "
                    + "where p.nombreProducto like '" + producto + "'";
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = producto.super.getConecion().query(prepareQuery);
            if (rs.next()) {
                detalle = rs.getString(1) + "-" + rs.getString(2)+"-"+rs.getInt(3);
            }
        } catch (SQLException ex) {
            System.out.println("Error Producto Mauricio: " + ex.toString());
        } finally {
            try {
                this.getConecion().con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return detalle;
    }

    @Override
    public String toString() {
        return nombreProducto;
    }
}
