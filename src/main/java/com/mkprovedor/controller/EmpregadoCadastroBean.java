package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mkprovedor.model.ENUf;
import com.mkprovedor.model.Empregado;
import com.mkprovedor.model.Grupo;
import com.mkprovedor.model.Municipio;
import com.mkprovedor.model.Usuario;
import com.mkprovedor.model.UsuarioGrupo;
import com.mkprovedor.security.UsuarioSistema;
import com.mkprovedor.service.EmpregadoService;
import com.mkprovedor.service.GrupoService;
import com.mkprovedor.service.MunicipioService;
import com.mkprovedor.service.UsuarioGrupoService;
import com.mkprovedor.service.UsuarioService;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmpregadoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioSistema usuarioSistema;

	@Inject
	private EmpregadoService empregadoService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private GrupoService grupoService;

	@Inject
	private MunicipioService municipioService;

	@Inject
	private UsuarioGrupoService usuarioGrupoService;

	private Empregado empregado;

	@Inject
	private UsuarioGrupo usuarioGrupo;

	private Grupo grupoSelecionado;

	private Municipio municipio;

	private List<Grupo> listaGrupos = new ArrayList<>();
	private List<Grupo> gruposEmpregado = new ArrayList<>();
	private List<Municipio> municipios;

	public EmpregadoCadastroBean() {
		limpar();
	}

	public void inicializar() throws IOException {
		if (this.empregado == null)
			limpar();
		else
			usuarioSistema = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (FacesUtil.isNotPostback()) {
			this.carregarMunicipio();

			carregarPermissoes();
		}
	}

	private void limpar() {
		empregado = new Empregado();
		Usuario usuario = new Usuario();

		empregado.setMunicipio(new Municipio());

		empregado.setUsuario(usuario);
		usuarioGrupo = new UsuarioGrupo();

		municipio = new Municipio();
		municipios = new ArrayList<>();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/usuario/pesquisa.xhtml");
	}

	private void limparPermissoes() {
		gruposEmpregado.clear();
		usuarioGrupo = new UsuarioGrupo();
		carregarPermissoes();
	}

	public void adicionarPermissao() {

		if (this.empregado.getId() != null && usuarioGrupo.getGrupo() != null) {

			usuarioGrupo.setUsuario(empregado.getUsuario());
			usuarioGrupoService.createNew(usuarioGrupo);

			FacesUtil.addInfoMessage("Permissão de " + usuarioGrupo.getGrupo().getNome() + " adicionada com sucesso!");
		} else
			FacesUtil.addErrorMessage("Salve o formulário de Usuário antes de adicionar uma permissão!");

		limparPermissoes();
	}

	public void excluirPermissao() {

		try {
			usuarioGrupo.setUsuario(empregado.getUsuario());
			usuarioGrupo.setGrupo(grupoService.findByNome(grupoSelecionado).get(0));
			usuarioGrupoService.delete(usuarioGrupo);

			FacesUtil.addInfoMessage("Permissão de " + grupoSelecionado.getNome() + " removida com sucesso!");

			limparPermissoes();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao excluir o " + grupoSelecionado.getNome() + "!");
		}
	}

	public void salvar() throws IOException {

		try {
			if (this.empregado.getId() == null) {

				this.empregado.setMunicipio(this.municipio);

				empregado.getUsuario().setNome(empregado.getNome());

				usuarioService.createNew(empregado.getUsuario());

				empregadoService.createNew(this.empregado);
				FacesUtil.addInfoMessage("Empregado salvo com sucesso!");
				listar();

			} else {

				this.empregado.setMunicipio(this.municipio);

				usuarioService.update(empregado.getUsuario());

				empregadoService.update(this.empregado);
				FacesUtil.addInfoMessage("Empregado atualizado com sucesso!");
				listar();
			}
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Este login já existe no sistema!");
		}

		atualizarSessaoEmpregado();

		limpar();
	}

	public void excluir() {
		try {
			empregadoService.delete(this.empregado);
			FacesUtil.addInfoMessage("Empregado " + this.empregado.getNome() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + this.empregado.getNome() + ", consulte o suporte!");
		}
	}

	public void carregarPermissoes() {

		if (empregado.getId() != null) {

			List<UsuarioGrupo> permissoes = usuarioGrupoService.findByEmpregado(empregado.getUsuario());

			for (UsuarioGrupo permissao : permissoes)
				gruposEmpregado.add(permissao.getGrupo());

			listaGrupos = grupoService.findAll();
			listaGrupos.removeAll(gruposEmpregado);

		} else
			listaGrupos = grupoService.findAll();
	}

	public void carregarMunicipio() {
		municipios.clear();
		if (this.empregado.getId() != null) {
			this.municipio = this.empregado.getMunicipio();
			municipios.add(this.municipio);
		}
	}

	public void carregarMunicipios() {
		if (this.municipio.getUf() != null)
			municipios = municipioService.findByUF(ENUf.valueOf(this.municipio.getUf()));
		else
			municipios = new ArrayList<>();
	}

	private void atualizarSessaoEmpregado() {
		// Atualiza o objeto Empregado na Session
		FacesContext context = FacesContext.getCurrentInstance();
		ValueExpression ve = context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{empregado}", EmpregadoCadastroBean.class);

		// Atribui o novo valor 'Empregado' para a sessão
		ve.setValue(context.getELContext(), this.empregado);

		usuarioSistema.setEmpregado(this.empregado);

		// Recupera a sessao do usuário logado do Spring Security e depois atualiza
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioSistema,
				usuarioSistema.getPassword(), usuarioSistema.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public ENUf[] getUfs() {
		return ENUf.values();
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado Empregado) {
		this.empregado = Empregado;
	}

	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}

	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<Grupo> getGruposEmpregado() {
		return gruposEmpregado;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public boolean isEditando() {
		return this.empregado.getId() != null;
	}
}
