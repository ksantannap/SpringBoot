package com.cliente.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.entity.ClienteEntity;
import com.cliente.form.ClienteForm;
import com.cliente.repository.ClienteRepository;
import com.cliente.util.ClienteUtil;

/**
 * 
 * Classe armazenando os clientes no banco de dados H2, utilizando JPA, contém
 * os seguintes recursos: GET, POST, PUT, DELETE
 * 
 * @author kaio
 *
 */
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	/**
	 * Realiza uma requisição buscando todos os clientes na base de dados
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<Iterable<ClienteEntity>>(repository.findAll(), HttpStatus.OK);
	}

	/**
	 * Realiza uma requisição buscando o cliente pelo ID
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable Long id) {

		Optional<ClienteEntity> optional = repository.findById(id);

		if (optional.isPresent()) {
			return new ResponseEntity<ClienteEntity>(optional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Utilizado uma classe chamada ClienteForm para não dar poder à requisição de
	 * inserir o ID que é tratado pelo JPA
	 * 
	 * Caso insira o cliente com sucesso retorna o response code 201 e o cliente com ID gerado pelo JPA
	 * 
	 * @param clienteForm
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insertCliente(@RequestBody ClienteForm clienteForm) {

		ClienteEntity clienteEntity = ClienteUtil.transformToClienteEntity(clienteForm);

		repository.save(clienteEntity);

		return new ResponseEntity<ClienteEntity>(clienteEntity, HttpStatus.CREATED);
	}

	/**
	 * Realiza o delete do cliente na base de dados, caso não encontre, retorna
	 * mensagem informando que não encontrou e response code é 404
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCliente(@PathVariable Long id) {

		if (repository.existsById(id)) {

			repository.deleteById(id);
			return new ResponseEntity<String>("Cliente removido!", HttpStatus.OK);

		} else {

			return new ResponseEntity<String>("Cliente não foi encontrado para ser removido", HttpStatus.NOT_FOUND);

		}

	}

	/**
	 * Realiza uma requisição passando o parâmetro na URL do ID que será atualizado
	 * e no corpo da requisição o {@link ClienteForm} para não ser possível alterar
	 * o ID do cliente (Já que é um atualização)
	 * 
	 * Caso não encontre o cliente, é retornado o response code 404 e a mensagem que
	 * não encontrou o cliente para ser editado
	 * 
	 * @param id
	 * @param clienteForm
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody ClienteForm clienteForm) {

		if (repository.existsById(id)) {

			ClienteEntity clienteEntity = ClienteUtil.transformToClienteEntity(clienteForm);
			clienteEntity.setIdCliente(id);

			repository.save(clienteEntity);

			return new ResponseEntity<ClienteEntity>(clienteEntity, HttpStatus.OK);

		} else {

			return new ResponseEntity<String>("Não foi encontrado o cliente para ser atualizado", HttpStatus.NOT_FOUND);

		}
	}

}
