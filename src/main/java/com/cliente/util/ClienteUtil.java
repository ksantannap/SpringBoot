package com.cliente.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cliente.entity.ClienteEntity;
import com.cliente.form.ClienteForm;

public class ClienteUtil {

	private static final Logger logger = LogManager.getLogger(ClienteUtil.class);
	
	public static ClienteEntity transformToClienteEntity(ClienteForm clienteForm) {
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setNmCliente(clienteForm.getNmCliente());
		clienteEntity.setSobrenomeCliente(clienteForm.getSobrenomeCliente());
		clienteEntity.setDsEmail(clienteForm.getDsEmail());
		clienteEntity.setIdade(clienteForm.getIdade());

		logger.info("Formulário do cliente convertido para entity com sucesso!");
		
		return clienteEntity;
	}
	
}
