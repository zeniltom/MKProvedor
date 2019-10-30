package com.mkprovedor.security;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.mkprovedor.util.Util;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;

	public String getNomeEmpregado() {
		String nome = null;

		EmpregadoSistema empregadoLogado = getEmpregadoLogado();

		if (empregadoLogado != null)
			nome = empregadoLogado.getEmpregado().getNome();

		return nome;
	}

	@Produces
	@EmpregadoLogado
	public EmpregadoSistema getEmpregadoLogado() {
		EmpregadoSistema empregado = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null)
			empregado = (EmpregadoSistema) auth.getPrincipal();

		return empregado;
	}

	public void editarEmpregado() throws IOException {
		if (getEmpregadoLogado() != null)
			Util.redirecionarObjeto(
					"/empregado/Empregado.xhtml?empregado=" + getEmpregadoLogado().getEmpregado().getId().toString());
	}

	// INÍCIO PERMISSÃO PARA COMPONENTES MENUITEM DO MENUBAR

	public boolean isComponenteEmpregadoPermitido() {
		return externalContext.isUserInRole("GERENTE");
	}

	// FIM PERMISSÃO PARA COMPONENTES MENUITEM DO MENUBAR

	// INÍCIO PERMISSÃO PARA COMPONENTES COMMANDBUTTON DO MENUBAR

	// FIM PERMISSÃO PARA COMPONENTES COMMANDBUTTON DO MENUBAR
}
