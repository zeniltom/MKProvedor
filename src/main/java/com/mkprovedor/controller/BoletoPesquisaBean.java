package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Historico;
import com.mkprovedor.model.Mensalidade;
import com.mkprovedor.model.Parcela;
import com.mkprovedor.security.EmpregadoSistema;
import com.mkprovedor.service.HistoricoService;
import com.mkprovedor.service.MensalidadeService;
import com.mkprovedor.service.ParcelaService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BoletoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EmpregadoSistema empregadoSistema;

	@Inject
	private HistoricoService historicoService;

	@Inject
	private MensalidadeService mensalidadeService;

	@Inject
	private ParcelaService parcelaService;

	private Cliente cliente;

	private Mensalidade mensalidadeSelecionada;
	private Parcela parcelaSelecionada;

	private double valorPago;
	private double troco;
	private boolean valorPagoMaior;
	private boolean vencida;
	private boolean desativarBtPagar;

	private List<Mensalidade> mensalidades;
	private List<Parcela> parcelas;

	private double valorJurosEMulta;
	private double valorJurosEMultaCobrado;

	public BoletoPesquisaBean() {
		if (this.parcelaSelecionada == null)
			desativarBtPagar = true;

		empregadoSistema = (EmpregadoSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		limpar();
	}

	public void limpar() {
		this.troco = 0;
		this.valorPago = 0;
		this.valorPagoMaior = true;
		this.vencida = false;
		this.valorJurosEMulta = 0;
		this.valorJurosEMultaCobrado = 0;
		parcelas = new ArrayList<>();
	}

	public void pesquisar() {
		mensalidades = mensalidadeService.findAll();

		parcelas = parcelaService.findByMensalidade(this.mensalidadeSelecionada);

	}

	public void pagarParcela() {
		try {

			this.parcelaSelecionada.setSituacao(true);
			this.parcelaSelecionada.setDataPagamento(new Date());

			parcelaService.update(this.parcelaSelecionada);

			Historico historico = new Historico();
			historico.setCliente(this.cliente);
			historico.setEmpregado(this.empregadoSistema.getEmpregado());
			historico.setParcela(this.parcelaSelecionada);
			historico.setDataPagamento(this.parcelaSelecionada.getDataPagamento());
			historico.setValor(this.valorPago);

			historicoService.createNew(historico);

			FacesUtil.addInfoMessage(historico.toString());
			System.out.println(historico.toString());
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao realizar o pagamento da Parcela!");
			e.printStackTrace();
		}
	}

	public boolean calcular() {
		this.troco = 0;
		this.valorPagoMaior = true;

		if (this.valorJurosEMulta > 0) {

			double jurosEMultasCobrados = this.valorJurosEMulta + this.parcelaSelecionada.getValor();

			if (this.valorPago >= jurosEMultasCobrados) {
				this.troco = this.valorPago - jurosEMultasCobrados;
				valorPagoMaior = true;
			} else
				valorPagoMaior = false;

		} else {

			valorPagoMaior = false;

			if (this.valorPago >= this.parcelaSelecionada.getValor()) {
				this.troco = this.valorPago - parcelaSelecionada.getValor();
				valorPagoMaior = true;

			} else
				valorPagoMaior = false;
		}

		return valorPagoMaior;
	}

	public void calcularJurosEMulta() {
		valorJurosEMulta = parcelaService.findByParcelaVencidaValor(this.parcelaSelecionada);
		calcularJurosEMultaCobrado();
	}

	public void calcularJurosEMultaCobrado() {
		double valor = this.parcelaSelecionada.getValor();

		if (valorJurosEMulta > 0)
			valorJurosEMultaCobrado = valorJurosEMulta + valor;
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

		this.mensalidadeSelecionada = mensalidade != null ? mensalidade : null;
		this.parcelaSelecionada = null;

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

	public Mensalidade getMensalidadeSelecionada() {
		return mensalidadeSelecionada;
	}

	public void setMensalidadeSelecionada(Mensalidade mensalidadeSelecionada) {
		this.mensalidadeSelecionada = mensalidadeSelecionada;
	}

	public double getValorPago() {
		return valorPago;
	}

	public boolean isValorPagoMaior() {
		return valorPagoMaior;
	}

	public void setValorPagoMaior(boolean valorPagoMaior) {
		this.valorPagoMaior = valorPagoMaior;
	}

	public boolean isVencida() {
		return vencida;
	}

	public void setVencida(boolean vencida) {
		this.vencida = vencida;
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

	public double getValorJurosEMulta() {
		return valorJurosEMulta;
	}

	public double getValorJurosEMultaCobrado() {
		return valorJurosEMultaCobrado;
	}

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

}
