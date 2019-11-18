package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.service.ClienteService;

@Named
@ViewScoped
public class ClienteSelecaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;

	private String nome;

	private List<Cliente> clientesFiltrados;

	public void pesquisar() {
		clientesFiltrados = clienteService.findByNome(nome);
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

		PrimeFaces.current().dialog().openDynamic("SelecaoCliente", config, null);
	}

	public void selecionar(Cliente cliente) {
		PrimeFaces.current().dialog().closeDynamic(cliente);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

}