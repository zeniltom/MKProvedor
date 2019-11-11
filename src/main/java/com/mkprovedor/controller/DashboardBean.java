package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DonutChartModel titulos;
	private DonutChartModel clientes;

	@PostConstruct
	public void init() {
		createDonutModelNovo();
	}

	public void createDonutModelNovo() {
		DonutChartOptions options = new DonutChartOptions();
		DonutChartOptions options2 = new DonutChartOptions();

		Legend legend = new Legend();
		legend.setPosition("right");
		options.setLegend(legend);
		options2.setLegend(legend);

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
		values.add(8886);
		values.add(8015);
		values.add(871);
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

		clientes = new DonutChartModel();
		ChartData data1 = new ChartData();

		DonutChartDataSet dataSet1 = new DonutChartDataSet();
		List<Number> values1 = new ArrayList<>();
		values1.add(963);
		values1.add(937);
		values1.add(50);
		dataSet1.setData(values1);

		dataSet1.setBackgroundColor(bgColors);

		data1.addChartDataSet(dataSet1);
		List<String> labels1 = new ArrayList<>();
		labels1.add("Total de clientes");
		labels1.add("Total livres");
		labels1.add("Total bloqueados");
		data1.setLabels(labels1);

		clientes.setData(data1);

		title = new Title();
		title.setText("Controle de Clientes");
		title.setFontSize(25);
		title.setPadding(20);
		title.setDisplay(true);
		options2.setTitle(title);
		clientes.setOptions(options2);
	}

	public DonutChartModel getTitulos() {
		return titulos;
	}

	public DonutChartModel getClientes() {
		return clientes;
	}

}