/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conecion;

import Conecion.poolConecciones;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author admin
 */
public class GetConecion {

    public static poolConecciones pool = null;

    public static poolConecciones getControllerpool(int opc) {
        if (pool == null) {
            System.out.println("Conecto : " + opc);

            BasicDataSource DataSource = new BasicDataSource();
            String driver = "com.mysql.jdbc.Driver";
            String User, pass, url;
            switch (opc) {
                case 1:
                    //bd liliana
                    User = "root";
                    pass = "";
                    url = "jdbc:mysql://localhost:3306/montana";
                    break;
                case 2:
                    
                    //bd pruebas
                    User = "root";
                    pass = "123456";
                    url = "jdbc:mysql://192.168.10.200:3306/montana";
                    //jdbc:mysql://dbserver/database?rewriteBatchedStatements=true&relaxAutoCommit=true
                    break;
                default:
                    //bd no programada
                    User = "root";
                    pass = "";
                    url = "jdbc:mysql://localhost:3306/montana";
                    break;
            }
            DataSource.setDriverClassName(driver);
            DataSource.setUsername(User);
            DataSource.setPassword(pass);
            DataSource.setUrl(url);
            DataSource.setMaxActive(50);
            pool = new poolConecciones(DataSource);
        }
        return pool;
    }

    public static poolConecciones getPool() {
        return pool;
    }

    public static void setPool(poolConecciones pool) {
        GetConecion.pool = pool;
    }

}
