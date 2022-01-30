package com.mkprovedor.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;

public class Util {
	
	private Util() {}

	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static String contexto() {
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getExternalContext().getRequestContextPath();
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return url;
	}

	public static void redirecionarObjeto(String url) throws IOException {
		Faces.redirect(Util.contexto() + url);
	}

	public static boolean isValidCNPJ(String cnpj) { // validacao do CNPJ
		cnpj = cnpj.replace("/", "").replace(".", "").replace("-", "").replace("_", "");
		if ((cnpj == null) || (cnpj.length() != 14) || (cnpj.equalsIgnoreCase("00000000000000")))
			return false;
		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCPF(String cpf) {
		cpf = cpf.replace("-", "").replace(".", "").replace("_", "");

		if ((cpf == null) || (cpf.length() != 11) || (cpf.equalsIgnoreCase("00000000000"))
				|| (cpf.equalsIgnoreCase("11111111111")) || (cpf.equalsIgnoreCase("22222222222"))
				|| (cpf.equalsIgnoreCase("33333333333")) || (cpf.equalsIgnoreCase("44444444444"))
				|| (cpf.equalsIgnoreCase("55555555555")) || (cpf.equalsIgnoreCase("66666666666"))
				|| (cpf.equalsIgnoreCase("77777777777")) || (cpf.equalsIgnoreCase("88888888888"))
				|| (cpf.equalsIgnoreCase("99999999999")))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	private static int calcularDigito(String str, int[] peso) { // usado na validacao do CNPJ e CPF
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}
}
