package com.mkprovedor.controller;

import com.mkprovedor.model.Cliente;

public class ClienteAlteradoEvent {

	private Cliente cliente;

	public ClienteAlteradoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
