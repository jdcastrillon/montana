/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author admin
 */
public class ValidarNumeroEntero implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        System.out.println("Objecto : " + arg2);
        try {
            Integer.parseInt(arg2.toString());
        } catch (ValidatorException ex) {
            throw new ValidatorException(new FacesMessage("Debe ser un entero"));
        }

    }

}
