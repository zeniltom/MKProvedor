package com.mkprovedor.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mkprovedor.model.Servico;
import com.mkprovedor.repository.Servicos;

@FacesConverter(forClass = Servico.class)
public class ServicoConverter implements Converter {

	@Inject
	private Servicos servicos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Servico retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.servicos.findById(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Servico servico = (Servico) value;
			return servico.getId() == null ? null : servico.getId().toString();
		}
		return "";
	}

}