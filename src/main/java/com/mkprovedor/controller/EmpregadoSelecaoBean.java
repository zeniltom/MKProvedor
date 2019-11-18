package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.service.EmpregadoService;

@Named
@ViewScoped
public class EmpregadoSelecaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpregadoService empregadoService;

	private String nome;

	private List<Empregado> empregadosFiltrados;

	public void pesquisar() {
		empregadosFiltrados = empregadoService.findByNome(nome);
	}

	public void abrirDialogo() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("modal", true);
		config.put("width", 840);
		config.put("height", 420);
		config.put("draggable", false);
		config.put("resizable", false);
		config.put("contentWidth", "100%");
		config.put("contentHeight", "100%");
		config.put("headerElement", "customheader");

		PrimeFaces.current().dialog().openDynamic("SelecaoEmpregado", config, null);
	}

	public void selecionar(Empregado empregado) {
		PrimeFaces.current().dialog().closeDynamic(empregado);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Empregado> getEmpregadosFiltrados() {
		return empregadosFiltrados;
	}

}