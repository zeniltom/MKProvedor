package com.mkprovedor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mkprovedor.model.Chamado;
import com.mkprovedor.repository.Chamados;

@FacesConverter(forClass = Chamado.class)
public class ChamadoConverter implements Converter {

	@Inject
	private Chamados chamados;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Chamado retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.chamados.findById(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Chamado chamado = (Chamado) value;
			return chamado.getId() == null ? null : chamado.getId().toString();
		}
		return "";
	}

}