package Pojos;

import Conecion.*;
import Conecion.poolConecciones;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

abstract class Persistencia {

    BasicDataSource DataSource = new BasicDataSource();
    poolConecciones pool;

    public Persistencia() {
        pool = GetConecion.getControllerpool(2);
    }

    public abstract int create();

    public abstract int edit();

    public abstract int remove();

    public abstract List List();

    public poolConecciones getConecion() {
        return pool;
    }

    public void setPool(poolConecciones pool) {
        this.pool = pool;
    }



}
