package com.cliente.form;

public class ClienteForm {

	private String nmCliente;
	private String sobrenomeCliente;
	private Integer idade;
	private String dsEmail;

	public String getNmCliente() {
		return nmCliente;
	}

	public void setNmCliente(String nmCliente) {
		this.nmCliente = nmCliente;
	}

	public String getSobrenomeCliente() {
		return sobrenomeCliente;
	}

	public void setSobrenomeCliente(String sobrenomeCliente) {
		this.sobrenomeCliente = sobrenomeCliente;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	@Override
	public String toString() {
		return "ClienteForm [nmCliente=" + nmCliente + ", sobrenomeCliente=" + sobrenomeCliente + ", idade=" + idade
				+ ", dsEmail=" + dsEmail + "]";
	}

}
