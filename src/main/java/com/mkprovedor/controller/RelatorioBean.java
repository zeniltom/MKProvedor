package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.SelectEvent;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.model.Historico;
import com.mkprovedor.service.ContratoService;
import com.mkprovedor.service.HistoricoService;
import com.mkprovedor.service.RelatorioService;
import com.mkprovedor.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@PersistenceContext(unitName = "PedidoPU")
	private EntityManager manager;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private HistoricoService historicoService;

	@Inject
	private ContratoService contratoService;

	@Inject
	private RelatorioService relatorioService;

	private Date dataFiltro;

	private Historico historicoSelecionado;

	private Contrato contrato;

	private List<Historico> historicos;

	public RelatorioBean() {
		limpar();
	}

	public void limpar() {
		this.historicos = new ArrayList<>();
	}

	public void pesquisar() {
		historicos = historicoService.filter(dataFiltro);
	}

	public double total() {
		double total = 0;

		for (Historico historico : historicos)
			total += historico.getValor();

		return total;
	}

	public String contratoCliente(Cliente cliente) {
		contrato = contratoService.findByCliente(cliente);

		if (contrato == null)
			return "Sem plano";
		else
			return contrato.getServico().toString();
	}

	public void onRowHistoricoSelect(SelectEvent event) {
		Historico historico = (Historico) event.getObject();
		System.out.println(historico);
		this.historicoSelecionado = historico;
	}

	public void emitir() throws IOException, JRException {
		if (this.historicos.size() > 0) {

			if (this.dataFiltro != null)
				relatorioService.gerarRelatorioHistorico(this.facesContext, this.response, this.manager,
						this.dataFiltro);
			else
				FacesUtil.addErrorMessage("Preencha a Data de Pagamento antes de gerar o relatório!");

		} else
			FacesUtil.addErrorMessage("Não histórico disponível para gerar o relatório!");
	}

	public Date getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public Historico getHistoricoSelecionado() {
		return historicoSelecionado;
	}

	public void setHistoricoSelecionado(Historico historicoSelecionado) {
		this.historicoSelecionado = historicoSelecionado;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

}