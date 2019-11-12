package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.mkprovedor.service.ClienteService;
import com.mkprovedor.service.ParcelaService;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;
	
	@Inject
	private ParcelaService parcelaService;

	private DonutChartModel titulos;
	private DonutChartModel clientes;

	@PostConstruct
	public void init() {
		createDonutModelNovo();
	}

	public void createDonutModelNovo() {
		montarTitulos();
		montarClientes();
	}

	public void montarTitulos() {
		int parcelasTotal = parcelaService.findByTotalParcelas();
		int parcelasAVencer = parcelaService.findByTotalParcelasAVencer();
		int parcelasVencidas = parcelaService.findByTotalParcelasVencidas();

		DonutChartOptions options = new DonutChartOptions();

		Legend legend = new Legend();
		legend.setPosition("right");
		options.setLegend(legend);

		Title title = new Title();
		title.setText("Controle de Títulos");
		title.setFontSize(25);
		title.setFontColor("black");
		title.setPadding(10);
		title.setDisplay(true);
		options.setTitle(title);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(0, 43, 225)");
		bgColors.add("rgb(81, 159, 214)");
		bgColors.add("rgb(8, 178, 194)");

		titulos = new DonutChartModel();
		ChartData data = new ChartData();
		DonutChartDataSet dataSet = new DonutChartDataSet();

		List<Number> values = new ArrayList<>();

		values.add(parcelasTotal);
		values.add(parcelasAVencer);
		values.add(parcelasVencidas);
		dataSet.setData(values);
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("Total de títulos");
		labels.add("Títulos à vencer");
		labels.add("Títulos vencidos");
		data.setLabels(labels);

		titulos.setData(data);
		titulos.setOptions(options);
	}

	private void montarClientes() {
		int clientesTotal = clienteService.findByTotalClientes();
		int clientesLivres = clienteService.findByTotalClientesLivres();
		int clientesBloqueados = clienteService.findByTotalClientesBloqueados();

		DonutChartOptions options = new DonutChartOptions();

		Legend legend = new Legend();
		legend.setPosition("right");
		options.setLegend(legend);

		Title title = new Title();
		title.setText("Controle de Clientes");
		title.setFontSize(25);
		title.setFontColor("black");
		title.setPadding(10);
		title.setDisplay(true);
		options.setTitle(title);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(0, 43, 225)");
		bgColors.add("rgb(81, 159, 214)");
		bgColors.add("rgb(8, 178, 194)");

		clientes = new DonutChartModel();
		ChartData data = new ChartData();
		DonutChartDataSet dataSet = new DonutChartDataSet();

		List<Number> values = new ArrayList<>();

		values.add(clientesTotal);
		values.add(clientesLivres);
		values.add(clientesBloqueados);
		dataSet.setData(values);
		dataSet.setBackgroundColor(bgColors);
		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("Total de clintes");
		labels.add("Total livres");
		labels.add("Total bloqueados");
		data.setLabels(labels);

		clientes.setData(data);
		clientes.setOptions(options);
	}

	public DonutChartModel getTitulos() {
		return titulos;
	}

	public DonutChartModel getClientes() {
		return clientes;
	}

}