package com.mkprovedor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mkprovedor.model.Municipio;
import com.mkprovedor.repository.Municipios;

@FacesConverter(forClass = Municipio.class)
public class MunicipioConverter implements Converter {

	@Inject
	private Municipios municipios;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Municipio retorno = null;

		if (StringUtils.isNotEmpty(value))
			retorno = this.municipios.findById(new Long(value));

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Municipio municipio = (Municipio) value;
			return municipio.getId() == null ? null : municipio.getId().toString();
		}

		return "";
	}

}