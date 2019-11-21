package com.mkprovedor.service;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.util.jsf.FacesUtil;
import com.mkprovedor.util.report.ExecutorRelatorio;

import net.sf.jasperreports.engine.JRException;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gerarContrato(FacesContext facesContext, HttpServletResponse response, EntityManager manager,
			Cliente clienteSelecionado) throws IOException, JRException {

		// PARÂMETROS PARA O RELATÓRIO
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("cliente_id", clienteSelecionado.getId());

		// NOME DO ARQUIVO PDF
		String arquivo = "contrato_cliente_" + clienteSelecionado.getCpfCnpj() + ".pdf";

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/contrato.jasper", response, parametros,
				arquivo);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado())
			facesContext.responseComplete();
		else
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
	}

	public void gerarRelatorioHistorico(FacesContext facesContext, HttpServletResponse response, EntityManager manager,
			Date dataInicio, Date dataFim) throws IOException, JRException {

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		// PARÂMETROS PARA O RELATÓRIO
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DATA_INICIO", dataInicio);
		parametros.put("DATA_FIM", dataFim);

		// NOME DO ARQUIVO PDF
		String arquivo = "historico_de_" + format.format(new Date()) + ".pdf";

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/historico_gerado.jasper", response, parametros,
				arquivo);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado())
			facesContext.responseComplete();
		else
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
	}
}
