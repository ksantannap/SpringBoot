package com.cliente.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TB_CLIENTE")
public class ClienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idCliente;

	private String nmCliente;
	private String sobrenomeCliente;
	private Integer idade;
	private String dsEmail;

	public ClienteEntity() {
	}

	public ClienteEntity(Long idCliente, String nmCliente, String sobrenomeCliente, Integer idade, String dsEmail) {
		this.idCliente = idCliente;
		this.nmCliente = nmCliente;
		this.sobrenomeCliente = sobrenomeCliente;
		this.idade = idade;
		this.dsEmail = dsEmail;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

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
		return "ClienteEntity [idCliente=" + idCliente + ", nmCliente=" + nmCliente + ", sobrenomeCliente="
				+ sobrenomeCliente + ", idade=" + idade + ", dsEmail=" + dsEmail + "]";
	}

}
