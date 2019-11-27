package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.model.Historico;
import com.mkprovedor.service.ContratoService;
import com.mkprovedor.service.HistoricoService;

@Named
@ViewScoped
public class CaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HistoricoService historicoService;

	@Inject
	private ContratoService contratoService;

	private Contrato contrato;

	private List<Historico> historicos;

	private Historico historico;

	public CaixaBean() {
		limpar();
	}

	public void limpar() {

	}

	public void pesquisar() {
		historicos = historicoService.findByHistoricoDia(new Date());
	}

	public boolean calcular() {

		return false;
	}

	public double total() {
		double total = 0;

		for (Historico historico : historicos)
			total += historico.getValor();

		return total;
	}

	public double jurosEMulta() {
		double jurosEMulta = 0;

		if (historico != null && historico.getCliente() != null)
			jurosEMulta = this.historico.getValor() - this.historico.getParcela().getValor();

		return jurosEMulta;
	}

	public String planoCliente() {
		if (historico != null && historico.getCliente() != null)
			contrato = contratoService.findByCliente(historico.getCliente());

		if (contrato == null)
			return "Sem plano";
		else
			return contrato.getServico().toString();
	}

	public String contratoCliente(Cliente cliente) {
		contrato = contratoService.findByCliente(cliente);

		if (contrato == null)
			return "Sem plano";
		else
			return contrato.getServico().toString();
	}

	public void onRowHistoricoSelect(SelectEvent event) {
		historico = (Historico) event.getObject();
		System.out.println(historico);
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

}
