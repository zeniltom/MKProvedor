package com.mkprovedor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.repository.Empregados;

@FacesConverter(forClass = Empregado.class)
public class EmpregadoConverter implements Converter {

	@Inject
	private Empregados empregados;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Empregado retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.empregados.findById(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Empregado empregado = (Empregado) value;
			return empregado.getId() == null ? null : empregado.getId().toString();
		}

		return "";
	}

}