package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Historico;
import com.mkprovedor.model.Mensalidade;
import com.mkprovedor.model.Parcela;
import com.mkprovedor.service.HistoricoService;
import com.mkprovedor.service.MensalidadeService;
import com.mkprovedor.service.ParcelaService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BoletoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HistoricoService historicoService;

	@Inject
	private MensalidadeService mensalidadeService;

	@Inject
	private ParcelaService parcelaService;

	private Cliente cliente;

	private Parcela parcelaSelecionada;

	private double valorPago;
	private double troco;
	private boolean retorno;
	private boolean desativarBtPagar;

	private List<Mensalidade> mensalidades;
	private List<Parcela> parcelas;

	public BoletoPesquisaBean() {
		if (this.parcelaSelecionada == null)
			desativarBtPagar = true;

		limpar();
	}

	public void limpar() {
		this.troco = 0;
		this.valorPago = 0;
		this.retorno = true;
		parcelas = new ArrayList<>();
	}

	public void pesquisar() {
		mensalidades = mensalidadeService.findAll();
	}

	public void pagarParcela() {
		try {

			this.parcelaSelecionada.setSituacao(true);
			this.parcelaSelecionada.setDataPagamento(new Date());

			parcelaService.update(this.parcelaSelecionada);

			Historico historico = new Historico();
			historico.setCliente(this.cliente);
			historico.setParcela(this.parcelaSelecionada);
			historico.setDataPagamento(this.parcelaSelecionada.getDataPagamento());
			historico.setValor(this.parcelaSelecionada.getValor());

			historicoService.createNew(historico);

			FacesUtil.addInfoMessage(historico.toString());
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao realizar o pagamento da Parcela!");
			e.printStackTrace();
		}
	}

	public boolean calcular() {
		this.troco = 0;
		this.retorno = true;

		if (this.valorPago >= this.parcelaSelecionada.getValor()) {
			this.troco = this.valorPago - parcelaSelecionada.getValor();
			retorno = true;
		} else
			retorno = false;

		return retorno;
	}

	public boolean validacao() {
		return (this.valorPago == 0 || this.valorPago < this.parcelaSelecionada.getValor()) ? false : true;
	}

	public boolean mensalidadeEmDia(Mensalidade mensalidade) {
		List<Parcela> parcela = null;
		parcela = parcelaService.findByParcelaVencida(mensalidade);

		return parcela.size() > 0 ? false : true;
	}

	public boolean parcelaEmDia(Parcela parcela) {
		return parcela.getDataVencimento().before(new Date()) && parcela.getDataPagamento() == null ? false : true;
	}

	public void onRowMensalidadeSelect(SelectEvent event) {
		Mensalidade mensalidade = (Mensalidade) event.getObject();
		parcelas = parcelaService.findByMensalidade(mensalidade);
		this.cliente = mensalidade != null ? mensalidade.getCliente() : null;

		desativarBtPagar = true;
	}

	public void onRowParcelaSelect(SelectEvent event) {
		Parcela parcela = (Parcela) event.getObject();
		this.parcelaSelecionada = parcela != null ? parcela : null;

		desativarBtPagar = this.parcelaSelecionada != null && !this.parcelaSelecionada.isSituacao() ? false : true;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Parcela getParcelaSelecionada() {
		return parcelaSelecionada;
	}

	public double getValorPago() {
		return valorPago;
	}

	public boolean getRetorno() {
		return retorno;
	}

	public void setRetorno(boolean retorno) {
		this.retorno = retorno;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public double getTroco() {
		return troco;
	}

	public void setTroco(double troco) {
		this.troco = troco;
	}

	public boolean isDesativarBtPagar() {
		return desativarBtPagar;
	}

	public void setDesativarBtPagar(boolean desativarBtPagar) {
		this.desativarBtPagar = desativarBtPagar;
	}

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

}
