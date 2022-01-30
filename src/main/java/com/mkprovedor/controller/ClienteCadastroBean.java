package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.model.ENUf;
import com.mkprovedor.model.Mensalidade;
import com.mkprovedor.model.Municipio;
import com.mkprovedor.model.Servico;
import com.mkprovedor.service.ClienteService;
import com.mkprovedor.service.ContratoService;
import com.mkprovedor.service.MensalidadeService;
import com.mkprovedor.service.MunicipioService;
import com.mkprovedor.service.ParcelaService;
import com.mkprovedor.service.RelatorioService;
import com.mkprovedor.service.ServicoService;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ClienteCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@PersistenceContext(unitName = "PedidoPU")
	private EntityManager manager;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private RelatorioService relatorioService;

	@Inject
	private ClienteService clienteService;

	@Inject
	private ServicoService servicoService;

	@Inject
	private MensalidadeService mensalidadeService;

	@Inject
	private ParcelaService parcelaService;

	@Inject
	private ContratoService contratoService;

	@Inject
	private MunicipioService municipioService;

	private Cliente cliente;

	private Contrato contrato;

	private Mensalidade mensalidade;

	private Municipio municipio;

	private List<Municipio> municipios;
	private List<Servico> planos;

	private PanelGrid financeiro;
	private CommandButton btSalvarContrato;

	private boolean edicao;
	private Date dataPrimeiraParcela;

	public ClienteCadastroBean() {
		limpar();
	}

	public void inicializar() {

		if (this.cliente == null)
			limpar();

		if (FacesUtil.isNotPostback()) {
			this.carregarMunicipio();

			planos = servicoService.findAll();
		}

		carregarContrato();
	}

	private void limpar() {
		cliente = new Cliente();
		contrato = new Contrato();
		mensalidade = new Mensalidade();
		municipio = new Municipio();
		municipios = new ArrayList<>();
		edicao = true;
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/cliente/pesquisa.xhtml");
	}

	public void salvar() throws IOException {

		if (this.cliente.isJuridica()) {
			if (!Util.isValidCNPJ(this.cliente.getCpfCnpj())) {
				FacesUtil.addErrorMessage("CNPJ inválido!");
				return;
			}
		} else if (!Util.isValidCPF(this.cliente.getCpfCnpj())) {
			FacesUtil.addErrorMessage("CPF inválido!");
			return;
		}

		if (this.cliente.getId() == null) {

			this.cliente.setMunicipio(this.municipio);
			this.cliente.setStatus("ATIVO");
			clienteService.createNew(this.cliente);

			salvarContrato();

			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
			listar();

		} else {

			this.cliente.setMunicipio(this.municipio);
			clienteService.update(this.cliente);

			salvarContrato();

			FacesUtil.addInfoMessage("Cliente atualizado com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() {
		try {
			clienteService.delete(this.cliente);
			FacesUtil.addInfoMessage("Cliente " + this.cliente.getNome() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + this.cliente.getNome() + ", consulte o suporte!");
		}
	}

	public void salvarContrato() {
		if (!edicao) {
			try {

				if (this.contrato.getId() != null)
					parcelaService.deleteMensalidadeAndParcela(this.mensalidade);

				this.contrato.setCliente(this.cliente);
				contratoService.update(this.contrato);

				this.mensalidade = new Mensalidade();

				this.mensalidade.setDataPrimeiraParcela(dataPrimeiraParcela);
				this.mensalidade.setCliente(this.contrato.getCliente());
				this.mensalidade.setQtdParcela(this.contrato.getQtdMeses());
				this.mensalidade.setValor(this.contrato.getServico().getValorPlano());
				mensalidadeService.createNew(this.mensalidade);

			} catch (Exception e) {
				e.printStackTrace();
				FacesUtil.addErrorMessage("Erro ao salvar o contrato");
			}
		}
	}

	public void permitirAlteracaoFinanceiro(boolean permitir) {
		this.edicao = permitir;
	}

	public void carregarContrato() {
		contrato = contratoService.findByCliente(this.cliente);

		if (contrato == null)
			contrato = new Contrato();
		else
			carregarMensalidade();
	}

	public void carregarMensalidade() {
		mensalidade = mensalidadeService.findByCliente(this.cliente);

		if (mensalidade == null)
			mensalidade = new Mensalidade();
	}

	public void carregarMunicipio() {
		municipios.clear();
		if (this.cliente.getId() != null) {
			this.municipio = this.cliente.getMunicipio() != null ? this.cliente.getMunicipio() : new Municipio();
			municipios.add(this.municipio);
		}
	}

	public void carregarMunicipios() {
		if (this.municipio.getUf() != null)
			municipios = municipioService.findByUF(ENUf.valueOf(this.municipio.getUf()));
	}

	public void imprimirContrato() {
		relatorioService.gerarContrato(this.facesContext, this.response, this.manager, this.cliente);
	}

	public ENUf[] getUfs() {
		return ENUf.values();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public List<Servico> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Servico> planos) {
		this.planos = planos;
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}

	public Date getDataPrimeiraParcela() {
		return dataPrimeiraParcela;
	}

	public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
		this.dataPrimeiraParcela = dataPrimeiraParcela;
	}

	public PanelGrid getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(PanelGrid financeiro) {
		this.financeiro = financeiro;
	}

	public CommandButton getBtSalvarContrato() {
		return btSalvarContrato;
	}

	public void setBtSalvarContrato(CommandButton btSalvarContrato) {
		this.btSalvarContrato = btSalvarContrato;
	}
}
