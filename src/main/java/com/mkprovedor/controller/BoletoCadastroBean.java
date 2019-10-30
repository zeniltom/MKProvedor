package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.model.ENUf;
import com.mkprovedor.model.Municipio;
import com.mkprovedor.model.Servico;
import com.mkprovedor.service.ClienteService;
import com.mkprovedor.service.ContratoService;
import com.mkprovedor.service.MunicipioService;
import com.mkprovedor.service.ServicoService;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BoletoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;

	@Inject
	private ServicoService servicoService;

	@Inject
	private ContratoService contratoService;

	@Inject
	private MunicipioService municipioService;

	private Cliente cliente;

	private Contrato contrato;

	private Municipio municipio;

	private List<Municipio> municipios;
	private List<Servico> planos;

	private PanelGrid financeiro;

	private boolean edicao = true;

	public BoletoCadastroBean() {
		limpar();
	}

	public void inicializar() {

		if (this.cliente == null)
			limpar();

		if (FacesUtil.isNotPostback()) {
			this.carregarMunicipio();
			this.bloquearComponentes();

			planos = servicoService.findAll();
		}

		carregarContrato();
	}

	private void limpar() {
		cliente = new Cliente();
		contrato = new Contrato();
		municipio = new Municipio();

		municipios = new ArrayList<>();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/cliente/pesquisa.xhtml");
	}

	public void salvar() throws IOException {

		if (this.cliente.getId() == null) {

			this.cliente.setMunicipio(this.municipio);
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
			FacesUtil.addErrorMessage("Não foi possível excluir o " + this.cliente.getNome() + ", consulte o suporte!");
		}
	}

	public void salvarContrato() {

		if (!edicao) {
			if (this.contrato.getId() == null) {
				this.contrato.setCliente(this.cliente);
				contratoService.createNew(this.contrato);
				System.out.println("Contrato novo criado");

			} else {
				this.contrato.setCliente(this.cliente);
				contratoService.update(this.contrato);
				System.out.println("Contrato atualizado");
			}
		}
	}

	public void carregarContrato() {
		contrato = contratoService.findByCliente(this.cliente);

		if (contrato == null)
			contrato = new Contrato();
	}

	public void carregarMunicipio() {
		municipios.clear();
		if (this.cliente.getId() != null) {
			this.municipio = this.cliente.getMunicipio();
			municipios.add(this.municipio);
		}
	}

	public void carregarMunicipios() {
		if (this.municipio.getUf() != null)
			municipios = municipioService.findByUF(ENUf.valueOf(this.municipio.getUf()));
	}

	public void bloquearComponentes() {
		for (Object c : financeiro.getChildren()) {

			if (c.toString().startsWith("org.primefaces.component.outputlabel.OutputLabel")) {
				OutputLabel outputLabel = (OutputLabel) c;
				outputLabel.setStyle(edicao ? "color: gray" : "");

			} else if (c.toString().startsWith("org.primefaces.component.inputnumber.InputNumber")) {
				InputNumber inputNumber = (InputNumber) c;
				inputNumber.setDisabled(edicao);

			} else if (c.toString().startsWith("org.primefaces.component.inputtext.InputText")) {
				InputText inputText = (InputText) c;
				inputText.setDisabled(edicao);

			} else if (c.toString().startsWith("org.primefaces.component.selectonemenu.SelectOneMenu")) {
				SelectOneMenu selectOneMenu = (SelectOneMenu) c;
				selectOneMenu.setDisabled(edicao);

			} else if (c.toString().startsWith("org.primefaces.component.calendar.Calendar")) {
				Calendar calendar = (Calendar) c;
				calendar.setDisabled(edicao);
			}
		}
	}

	public ENUf[] getUfs() {
		return ENUf.values();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente Cliente) {
		this.cliente = Cliente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public PanelGrid getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(PanelGrid financeiro) {
		this.financeiro = financeiro;
	}
}
