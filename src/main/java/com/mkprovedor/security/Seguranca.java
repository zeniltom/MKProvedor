package com.mkprovedor.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;

	public String getNomeUsuario() {
		String nome = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado();

		if (usuarioLogado != null)
			nome = usuarioLogado.getUsuario().getNome();

		return nome;
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null)
			usuario = (UsuarioSistema) auth.getPrincipal();

		return usuario;
	}

	// INÍCIO PERMISSÃO PARA COMPONENTES MENUITEM DO MENUBAR
	public boolean isComponenteEmpregadoPermitido() {
		return externalContext.isUserInRole("GERENTE");
	}
}
