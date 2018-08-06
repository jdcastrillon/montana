package Pojos;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class mestados extends Persistencia implements Serializable{
    public BigDecimal idEstado;
    public String descripcion;
    public String estado;

   public mestados() {
     super();
   }
   
   public BigDecimal getIdEstado() {
        return idEstado;
    }

   public void setIdEstado(BigDecimal idEstado) {
        this.idEstado = idEstado;
   }

   public String getDescripcion() {
        return descripcion;
    }

   public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
   }

   public String getEstado() {
        return estado;
    }

   public void setEstado(String estado) {
        this.estado = estado;
   }

   @Override
   public int create() {
      int transaccion=-1;
      String prepareInsert="insert into mestados (idEstado,descripcion,estado) values ("+idEstado+","+"'"+descripcion+"'"+","+"'"+estado+"'"+")";
      try {
          this.getConecion().con = this.getConecion().dataSource.getConnection();
          this.getConecion().con.setAutoCommit(false);
//          transaccion=mestados.this.getConecion().transaccion(prepareInsert);
      }  catch (SQLException ex) {
          System.out.println("Error SQL : " + ex.toString());
      }  finally {
           try {
               this.getConecion().getconecion().commit();
               this.getConecion().getconecion().setAutoCommit(true);
               this.getConecion().con.close();
           } catch (SQLException ex) {
               System.out.println(ex);
           }
      }
     return transaccion;       }

   @Override
   public int edit() {
      int transaccion=-1;
      String prepareEdit="update mestados set "+"descripcion="+"'"+descripcion+"'"+","+"estado="+"'"+estado+"'"+" where "+"idEstado="+idEstado;      try {
           this.getConecion().con = this.getConecion().dataSource.getConnection();
           this.getConecion().con.setAutoCommit(false);
//           transaccion=mestados.this.getConecion().transaccion(prepareEdit);
       }  catch (SQLException ex) {
           System.out.println("Error SQL : " + ex.toString());
       } finally {
           try {
                 this.getConecion().getconecion().commit();
                 this.getConecion().getconecion().setAutoCommit(true);
                 this.getConecion().con.close();
            }  catch (SQLException ex) {
                System.out.println(ex);
            }
        }     return transaccion;      }

   @Override
    public int remove() {
       int transaccion=-1;
       String prepareDelete="delete from  mestados where "+"idEstado="+idEstado;       try {
            this.getConecion().con = this.getConecion().dataSource.getConnection();
            this.getConecion().con.setAutoCommit(false);
//            transaccion=mestados.this.getConecion().transaccion(prepareDelete);
       } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex.toString());
       } finally {
          try {
                this.getConecion().getconecion().commit();
                this.getConecion().getconecion().setAutoCommit(true);
                this.getConecion().con.close();
           }  catch (SQLException ex) {
                System.out.println(ex);
            }
        }    return transaccion;      }

   @Override
    public java.util.List<mestados> List() {
        ArrayList<mestados> listmestados=new ArrayList();
       String prepareQuery="select * from mestados";
       try {
             ResultSet rs =mestados.super.getConecion().query(prepareQuery);
             while (rs.next()) { 
            mestados tabla = new mestados();
              tabla.setIdEstado(rs.getBigDecimal(1));
              tabla.setDescripcion(rs.getString(2));
              tabla.setEstado(rs.getString(3));

             listmestados.add(tabla);
             }
        } catch (SQLException ex) {
              System.out.println("Error Consulta : " + ex.toString());
        }
        return listmestados;
    }
}