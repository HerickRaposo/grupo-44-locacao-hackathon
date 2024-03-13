
package com.fiap.locacao.dominio.cliente.sevices;


import com.fiap.locacao.dominio.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.cliente.dto.ClientePatchDTO;
import com.fiap.locacao.dominio.cliente.entities.Cliente;
import com.fiap.locacao.dominio.cliente.repositories.IClienteRepository;
import com.fiap.locacao.dominio.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.endereco.dto.Paginator;
import com.fiap.locacao.dominio.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.endereco.entities.Endereco;
import com.fiap.locacao.dominio.endereco.repositories.IEEnderecoRepository;
import com.fiap.locacao.dominio.endereco.services.EnderecoService;
import com.fiap.locacao.exception.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ClienteService {

    @Autowired
    private IClienteRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private IEEnderecoRepository enderecoRepository;

    public RestDataReturnDTO findAll(PageRequest pagina){
    	List<ClienteDTO> PessoasDTO=new ArrayList<ClienteDTO>();
    	List<EnderecoDTO> enderecosDTO;
    	EnderecoDTO enderecoDTO;

        ClienteDTO pessoaDTO=null;
        ;
    	var clientes = repo.findAll(pagina);
        final Page<ClienteDTO> map = clientes.map(ClienteDTO::new);
        for (Cliente cliente : clientes.getContent()) {
    		enderecosDTO= new ArrayList<EnderecoDTO>();

    		pessoaDTO   = new ClienteDTO(cliente);

          //PARSEAR DADOS DE PESSOA
			BeanUtils.copyProperties(cliente, pessoaDTO);
			PessoasDTO.add(pessoaDTO);

    		
    		 for (Endereco endereco : cliente.getEnderecos()) {
				//PARSSEAR DADOS DE ENDEREÇO
    			 enderecoDTO=new EnderecoDTO();
    			 BeanUtils.copyProperties(endereco, enderecoDTO);
    			 enderecosDTO.add(enderecoDTO);
			}

            pessoaDTO.setEnderecos(enderecosDTO);

		}

    	return new RestDataReturnDTO(PessoasDTO, new Paginator(clientes.getNumber(), clientes.getTotalElements(), clientes.getTotalPages()));

    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        var pessoa = repo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));
        return new ClienteDTO(pessoa);
    }

    @Transactional
    public ClienteDTO insert(ClienteDTO dto) {
        Cliente entity = new Cliente();
        mapperDtoToEntity(dto,entity);
        var  pessoaSaved = repo.save(entity);
        return new ClienteDTO(pessoaSaved);
    }

    @Transactional
    public ClienteDTO update(Long id,ClienteDTO dto){
        try {
            Cliente entity = repo.getOne(id);
            mapperDtoToEntity(dto,entity);
            entity = repo.save(entity);

            return new ClienteDTO(entity);
        }catch (EntityNotFoundException ene){
            throw new ControllerNotFoundException("Pessoa não encontrada, id: " + id);
        }

    }

    public ClientePatchDTO updatePessoaByFields(Long id, Map<String, Object> fields) {
        Cliente existingPessoa = repo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Cliente.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingPessoa, value);
        });
        var pessoaAtualizada = repo.save(existingPessoa);
        return new ClientePatchDTO(pessoaAtualizada);
    }

    public Long deletePessoa(Long id) {
        Optional<Cliente> existingPessoa = repo.findById(id);
        if (existingPessoa.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new ControllerNotFoundException("Pessoa não encontrada, impossível deletar se não existe pessoa no id: " + id);
        }
        return repo.count();
    }

    @Transactional(readOnly = true)
    public void mapperDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setSexo(dto.getSexo());
        entity.setIdade(dto.getIdade());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setCell(dto.getCell());
        entity.setFotosUrls(dto.getFotosUrls());
        entity.setNat(dto.getNat());

    }

}
