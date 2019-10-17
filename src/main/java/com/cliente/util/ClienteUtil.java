package com.cliente.util;

import com.cliente.entity.ClienteEntity;
import com.cliente.form.ClienteForm;

public class ClienteUtil {

	public static ClienteEntity transformToClienteEntity(ClienteForm clienteForm) {
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setNmCliente(clienteForm.getNmCliente());
		clienteEntity.setSobrenomeCliente(clienteForm.getSobrenomeCliente());
		clienteEntity.setDsEmail(clienteForm.getDsEmail());
		clienteEntity.setIdade(clienteForm.getIdade());
		
		return clienteEntity;
	}
	
}
