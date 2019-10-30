package com.mkprovedor.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mkprovedor.security.EmpregadoSistema;
import com.mkprovedor.util.jsf.FacesUtil;
import com.mkprovedor.util.report.ExecutorRelatorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gerarRelatorioProva(FacesContext facesContext, HttpServletResponse response, EntityManager manager,
			EmpregadoSistema empregadoSistema) throws IOException, JRException {
		empregadoSistema = (EmpregadoSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// CARREGA A LOGO DA PASTA IMAGENS E MANDA COMO PARÂMETRO
		BufferedImage image = ImageIO.read(getClass().getResource("/relatorioss/fasete.png"));
		DateFormat format = new SimpleDateFormat("dd/MM/yyy");

		// CARREGA O SUBRELATÓRIO DE RESPOSTAS E MANDA COMO PARÂMETRO
		File respostas = new File(getClass().getClassLoader().getResource("/relatorios/resposta_prova.jasper").getFile());
		JasperReport subRelatorioDeRespostas = (JasperReport) JRLoader.loadObject(respostas);

		// PARÂMETROS PARA O RELATÓRIO
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("empregado_id", empregadoSistema.getEmpregado().getId());
		parametros.put("logo", image);
		parametros.put("subReport", subRelatorioDeRespostas);

		// NOME DO ARQUIVO PDF
		String arquivo = "prova_de_" + "_" + format.format(new Date()) + ".pdf";

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/prova_gerada.jasper", response, parametros,
				arquivo);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado())
			facesContext.responseComplete();
		else
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
	}
}
