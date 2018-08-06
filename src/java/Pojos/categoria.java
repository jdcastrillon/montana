package Pojos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class categoria extends Persistencia implements Serializable {

    private BigDecimal idCategoria;
    private String descripcion;
    subcategoria ObjSubCategoria;
    ArrayList<subcategoria> listsubcategoria = new ArrayList();

    public categoria() {
        super();
        ObjSubCategoria = new subcategoria();
//        ListCategorias();
    }

    public BigDecimal getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(BigDecimal idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public subcategoria getObjSubCategoria() {
        if (ObjSubCategoria == null) {
            ObjSubCategoria = new subcategoria();
        }
        return ObjSubCategoria;
    }

    public void setObjSubCategoria(subcategoria ObjSubCategoria) {
        this.ObjSubCategoria = ObjSubCategoria;
    }

    public ArrayList<subcategoria> getListsubcategoria() {
        return listsubcategoria;
    }

    public void setListsubcategoria(ArrayList<subcategoria> listsubcategoria) {
        this.listsubcategoria = listsubcategoria;
    }

    @Override
    public int create() {
        int transaccion = -1;
        String prepareInsert = "insert into categoria (descripcion) values (?)";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareInsert);
            preparedStatement.setString(1, descripcion);
            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
        String prepareEdit = "update categoria set " + "descripcion=? where " + "idCategoria=?";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareEdit);
            preparedStatement.setString(1, descripcion);
            preparedStatement.setBigDecimal(2, idCategoria);
            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
        String prepareDelete = "delete from  categoria where " + "idCategoria=" + idCategoria;
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
            PreparedStatement preparedStatement = this.getConecion().con.prepareStatement(prepareDelete);
            preparedStatement.setBigDecimal(1, idCategoria);
            transaccion = categoria.this.getConecion().transaccion(preparedStatement);
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
    public java.util.List<categoria> List() {
        ArrayList<categoria> listcategoria = new ArrayList();
        String prepareQuery = "select * from categoria";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = categoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                categoria tabla = new categoria();
                tabla.setIdCategoria(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));

                listcategoria.add(tabla);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        for (categoria object : listcategoria) {
            
            for (subcategoria object2 : object.getObjSubCategoria().ListXCategoria(object.getIdCategoria())) {
                object.getListsubcategoria().add(object2);
            }
        }
        return listcategoria;
    }

    public java.util.List<categoria> ListCategorias() {
        ArrayList<categoria> listcategoria = new ArrayList();
        ArrayList<categoria> listcategoriaFinal = new ArrayList();

        String prepareQuery = "SELECT A.idCategoria,A.descripcion,B.idSubCategoria,B.descripcion FROM categoria A , subcategoria B\n"
                + "where A.idCategoria=B.idCategoria order by 1";
        try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            ResultSet rs = categoria.super.getConecion().query(prepareQuery);
            while (rs.next()) {
                categoria tabla = new categoria();
                subcategoria tablasub = new subcategoria();

                tabla.setIdCategoria(rs.getBigDecimal(1));
                tabla.setDescripcion(rs.getString(2));

                tablasub.setIdSubCategoria(rs.getBigDecimal(3));
                tablasub.setIdCategoria(rs.getBigDecimal(1));
                tablasub.setDescripcion(rs.getString(4));

                tabla.setObjSubCategoria(tablasub);

                listcategoria.add(tabla);
            }
            boolean r = false;

            for (categoria object : listcategoria) {
                for (categoria object2 : listcategoriaFinal) {
                    if (object.getIdCategoria() == object2.getIdCategoria()) {
                        r = true;
                    }
                }
                if (r = false) {

                    for (categoria object2 : listcategoriaFinal) {
                        subcategoria tablasub = new subcategoria();
                        tablasub.setIdCategoria(object.getIdCategoria());
                        tablasub.setIdSubCategoria(object.getObjSubCategoria().getIdSubCategoria());
                        tablasub.setDescripcion(object.getObjSubCategoria().getDescripcion());
                        object.getListsubcategoria().add(tablasub);
                    }
                    listcategoriaFinal.add(object);

                }
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        }
        return listcategoriaFinal;
    }
}
