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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mkprovedor.model.ENUf;
import com.mkprovedor.model.Empregado;
import com.mkprovedor.model.EmpregadoGrupo;
import com.mkprovedor.model.Grupo;
import com.mkprovedor.model.Municipio;
import com.mkprovedor.repository.EmpregadoGrupos;
import com.mkprovedor.repository.Grupos;
import com.mkprovedor.security.EmpregadoSistema;
import com.mkprovedor.service.EmpregadoGrupoService;
import com.mkprovedor.service.EmpregadoService;
import com.mkprovedor.service.GrupoService;
import com.mkprovedor.service.NegocioException;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmpregadoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EmpregadoSistema empregadoSistema;

	@Inject
	private Grupos grupos;

	@Inject
	private EmpregadoGrupos empregadoGrupos;

	@Inject
	private EmpregadoService cadastroEmpregadoService;

	@Inject
	private GrupoService cadastroGrupoService;

	@Inject
	private EmpregadoGrupoService cadastroEmpregadoGrupoService;

	private Empregado empregado;
	private EmpregadoGrupo empregadoGrupo;

	private Grupo grupoSelecionado;

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
			empregadoSistema = (EmpregadoSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (FacesUtil.isNotPostback())
			carregarPermissoes();
	}

	public void carregarPermissoes() {

		if (empregado.getId() != null) {

			List<EmpregadoGrupo> permissoes = empregadoGrupos.findByEmpregado(empregado);

			for (EmpregadoGrupo permissao : permissoes)
				gruposEmpregado.add(permissao.getGrupo());

			listaGrupos = grupos.findAll();
			listaGrupos.removeAll(gruposEmpregado);

		} else
			listaGrupos = grupos.findAll();
	}

	private void limpar() {
		empregado = new Empregado();
		empregadoGrupo = new EmpregadoGrupo();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/usuario/pesquisa.xhtml");
	}

	private void limparPermissoes() {
		gruposEmpregado.clear();
		empregadoGrupo = new EmpregadoGrupo();
		carregarPermissoes();
	}

	public void adicionarPermissao() {

		if (this.empregado.getId() != null && empregadoGrupo.getGrupo() != null) {

			empregadoGrupo.setEmpregado(empregado);
			cadastroEmpregadoGrupoService.createNew(empregadoGrupo);

			FacesUtil
					.addInfoMessage("Permissão de " + empregadoGrupo.getGrupo().getNome() + " adicionada com sucesso!");
		} else
			throw new NegocioException("Salve o formulário de Empregado antes de adicionar uma permissão!");

		limparPermissoes();
	}

	public void excluirPermissao() {

		try {
			empregadoGrupo.setEmpregado(empregado);
			empregadoGrupo.setGrupo(cadastroGrupoService.findByNome(grupoSelecionado).get(0));
			cadastroEmpregadoGrupoService.delete(empregadoGrupo);

			FacesUtil.addInfoMessage("Permissão de " + grupoSelecionado.getNome() + " removida com sucesso!");

			limparPermissoes();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao excluir o " + grupoSelecionado.getNome() + "!");
		}
	}

	public void salvar() throws IOException {

		if (this.empregado.getId() == null) {
			cadastroEmpregadoService.createNew(this.empregado);
			FacesUtil.addInfoMessage("Empregado salvo com sucesso!");
			listar();

		} else {
			cadastroEmpregadoService.update(this.empregado);
			FacesUtil.addInfoMessage("Empregado atualizado com sucesso!");
			listar();
		}

		atualizarSessaoEmpregado();

		limpar();
	}

	public void excluir() {
		try {
			cadastroEmpregadoService.delete(this.empregado);
			FacesUtil.addInfoMessage("Empregado " + this.empregado.getNome() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + this.empregado.getNome() + ", consulte o suporte!");
		}
	}

	private void atualizarSessaoEmpregado() {
		// Atualiza o objeto Empregado na Session
		FacesContext context = FacesContext.getCurrentInstance();
		ValueExpression ve = context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{empregado}", EmpregadoCadastroBean.class);

		// Atribui o novo valor 'Empregado' para a sessão
		ve.setValue(context.getELContext(), this.empregado);

		empregadoSistema.setEmpregado(this.empregado);

		// Recupera a sessao do usuário logado do Spring Security e depois atualiza
		Authentication authentication = new UsernamePasswordAuthenticationToken(empregadoSistema,
				empregadoSistema.getPassword(), empregadoSistema.getAuthorities());

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

	public EmpregadoGrupo getEmpregadoGrupo() {
		return empregadoGrupo;
	}

	public void setEmpregadoGrupo(EmpregadoGrupo empregadoGrupo) {
		this.empregadoGrupo = empregadoGrupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
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
