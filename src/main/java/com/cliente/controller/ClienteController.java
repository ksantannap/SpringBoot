package com.cliente.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(ClienteController.class);

	@Autowired
	private ClienteRepository repository;

	/**
	 * Realiza uma requisição buscando todos os clientes na base de dados
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		logger.info("Buscando todos os clientes...");

		try {

			Iterable<ClienteEntity> clientesEncontrados = repository.findAll();

			List<ClienteEntity> listClientes = StreamSupport.stream(clientesEncontrados.spliterator(), false)
					.collect(Collectors.toList());

			if (listClientes.isEmpty()) {

				logger.info("Nenhum cliente foi encontrado");

				return new ResponseEntity<String>("Nenhum cliente foi encontrado", HttpStatus.NOT_FOUND);
			} else {

				logger.info(String.format("Encontrado %s cliente(s)", listClientes.size()));

				return new ResponseEntity<List<ClienteEntity>>(listClientes, HttpStatus.OK);
			}

		} catch (Exception e) {

			String erro = String.format("Erro ao buscar os clientes [%s]", e.getMessage());
			logger.error(erro, e);

			return new ResponseEntity<String>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}

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

		logger.info(String.format("Buscando o cliente com ID [%s]", id));

		try {

			Optional<ClienteEntity> optional = repository.findById(id);

			if (optional.isPresent()) {

				logger.info(String.format("Encontrado o cliente %s", optional.get()));

				return new ResponseEntity<ClienteEntity>(optional.get(), HttpStatus.OK);
			} else {

				logger.info("Não foi encontrado o cliente solicitado...");

				return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			String erro = String.format("Não foi possível encontrar o cliente ID[%s] (%s)", id, e.getMessage());
			logger.error(erro, e);

			return new ResponseEntity<String>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * Utilizado uma classe chamada ClienteForm para não dar poder à requisição de
	 * inserir o ID que é tratado pelo JPA
	 * 
	 * Caso insira o cliente com sucesso retorna o response code 201 e o cliente com
	 * ID gerado pelo JPA
	 * 
	 * @param clienteForm
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insertCliente(@RequestBody ClienteForm clienteForm) {

		logger.info("Iniciando o processo de salvar o cliente");

		try {

			ClienteEntity clienteEntity = ClienteUtil.transformToClienteEntity(clienteForm);

			repository.save(clienteEntity);

			logger.info("Cliente salvo com sucesso!");

			return new ResponseEntity<ClienteEntity>(clienteEntity, HttpStatus.CREATED);

		} catch (Exception e) {

			String erro = String.format("Não foi possível encontrar o cliente (%s)", e.getMessage());
			logger.error(erro, e);

			return new ResponseEntity<String>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

		}

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

		logger.info(String.format("Iniciando processo de deletar o cliente com ID[%s]", id));

		try {

			if (repository.existsById(id)) {

				repository.deleteById(id);

				logger.info("Cliente removido com sucesso!");

				return new ResponseEntity<String>("Cliente removido!", HttpStatus.OK);

			} else {

				logger.info("Cliente não foi encontrado para ser removido");

				return new ResponseEntity<String>("Cliente não foi encontrado para ser removido", HttpStatus.NOT_FOUND);

			}

		} catch (Exception e) {

			String erro = String.format("Não foi possível deletar com cliente ID[%s] (%s)", id, e.getMessage());
			logger.error(erro, e);

			return new ResponseEntity<String>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

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

		logger.info(String.format("Iniciando processo de atualizar o cliente com ID[%s]", id));
		
		try {
			
			if (repository.existsById(id)) {

				ClienteEntity clienteEntity = ClienteUtil.transformToClienteEntity(clienteForm);
				clienteEntity.setIdCliente(id);

				repository.save(clienteEntity);

				logger.info("Cliente atualizado com sucesso!");
				
				return new ResponseEntity<ClienteEntity>(clienteEntity, HttpStatus.OK);

			} else {

				logger.info("Cliente não foi encontrado para ser atualizado");
				
				return new ResponseEntity<String>("Não foi encontrado o cliente para ser atualizado",
						HttpStatus.NOT_FOUND);

			}

		} catch (Exception e) {

			String erro = String.format("Não foi possível deletar com cliente ID[%s] (%s)", id, e.getMessage());
			logger.error(erro, e);

			return new ResponseEntity<String>(erro, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
