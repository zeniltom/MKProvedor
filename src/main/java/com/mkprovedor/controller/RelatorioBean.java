package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.primefaces.event.SelectEvent;

import com.mkprovedor.model.Historico;
import com.mkprovedor.service.HistoricoService;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@PersistenceContext(unitName = "PedidoPU")
	private EntityManager manager;

	/*
	 * @Inject private FacesContext facesContext;
	 * 
	 * @Inject private HttpServletResponse response;
	 */

	@Inject
	private HistoricoService historicoService;

	private Date dataInicio;
	private Date dataFim;

	private Historico historico;

	private List<Historico> historicos;

	public void pesquisar() {
		historicos = historicoService.findByHistoricoDia(new Date());
	}

	public void onRowHistoricoSelect(SelectEvent event) {
		Historico historico = (Historico) event.getObject();
		System.out.println(historico);
		this.historico = historico;
	}

	public void emitir() {
		/*
		 * Map<String, Object> parametros = new HashMap<>();
		 * parametros.put("data_inicio", this.dataInicio); parametros.put("data_fim",
		 * this.dataFim); DateFormat format = new SimpleDateFormat("dd/MM/yyy");
		 * 
		 * ExecutorRelatorio executor = new
		 * ExecutorRelatorio("/relatorios/relatorio_pedidos_emitidos.jasper",
		 * this.response, parametros, "Pedidos emitidos " + format.format(new Date()) +
		 * ".pdf");
		 * 
		 * Session session = manager.unwrap(Session.class); session.doWork(executor);
		 * 
		 * if (executor.isRelatorioGerado()) { facesContext.responseComplete(); } else {
		 * FacesUtil.addErrorMessage("A execução do relatório não retornou dados."); }
		 */
		System.out.println("Relatório para o " + historico.getCliente() + " emitido com sucesso!");
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}
}