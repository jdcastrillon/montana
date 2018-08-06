/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Pojos.insumos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author JuanDavid
 */
@FacesConverter(value = "productosConvert")
public class productosConvert implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DualListModel<insumos> model = (DualListModel<insumos>) ((PickList) component).getValue();
        for (insumos employee : model.getSource()) {
            if (employee.getIdInsumo().equals(value)) {
                return employee;
            }
        }
        for (insumos employee : model.getTarget()) {
            if (employee.getIdInsumo().equals(value)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "";//((insumos) value).getNombreInsumo();
    }

}
