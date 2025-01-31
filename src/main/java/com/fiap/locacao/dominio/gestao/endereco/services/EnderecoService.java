package com.fiap.locacao.dominio.gestao.endereco.services;



import com.fiap.locacao.adapters.dto.CepDTO;
import com.fiap.locacao.adapters.dto.EnderecoResultViaCepDTO;
import com.fiap.locacao.adapters.out.ServicoViaCepValidatorOut;
import com.fiap.locacao.dominio.gestao.cliente.dto.ClienteDTO;
import com.fiap.locacao.dominio.gestao.cliente.entities.Cliente;
import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.dto.Paginator;
import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.endereco.repositories.IEEnderecoRepository;
import com.fiap.locacao.exception.ControllerNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EnderecoService {
 
	@Autowired
	private IEEnderecoRepository enderecoRepository;
	@Autowired
	private ServicoViaCepValidatorOut servicoViaSepValidator;


	public EnderecoDTO salvar(CepDTO cepDTO, int numeroDeEnereco) {
		EnderecoResultViaCepDTO enderecoResultViaCepDTO = this.servicoViaSepValidator.validarEndereco(cepDTO);
		
		Endereco OEndereco = this.enderecoRepository.BUSCAR_ENDERECO_POR_CEP(cepDTO.getCep());
 
		
		if(OEndereco==null) {
	    	Endereco enderecoEntity = enderecoResultViaCepDTO.getEndereco(cepDTO);
			enderecoEntity.setNumero(numeroDeEnereco);
	    	OEndereco=this.enderecoRepository.save(enderecoEntity);			    	
	    }else {
	    	this.enderecoRepository.SALVAR_ENDERECO(OEndereco.getId(), cepDTO.getCliente().getId());
	    	System.err.println("PASSEI PELO SERVIÇO COM SUCESSO!");
	    }

		return new EnderecoDTO(OEndereco); 
	}
	public EnderecoDTO salvar(CepDTO cepDTO) {
		EnderecoResultViaCepDTO enderecoResultViaCepDTO = this.servicoViaSepValidator.validarEndereco(cepDTO);

		Endereco OEndereco = this.enderecoRepository.BUSCAR_ENDERECO_POR_CEP(cepDTO.getCep());


		if(OEndereco==null) {
			Endereco enderecoEntity = enderecoResultViaCepDTO.getEndereco(cepDTO);
			OEndereco=this.enderecoRepository.save(enderecoEntity);
		}else {
			this.enderecoRepository.SALVAR_ENDERECO(OEndereco.getId(), cepDTO.getCliente().getId());
			System.err.println("PASSEI PELO SERVIÇO COM SUCESSO!");
		}

		return new EnderecoDTO(OEndereco);
	}
	
	public EnderecoDTO findById(Long id) {
		Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Endereço não encontrado"));
		return new EnderecoDTO(endereco);
	}

	public RestDataReturnDTO findAll(PageRequest pageRequest){
		Page<Endereco> enderecos   = enderecoRepository.findAll(pageRequest);
     	List<EnderecoDTO> enderecosDTO= new ArrayList<EnderecoDTO>();
     	EnderecoDTO enderecoDTO;
     	List<ClienteDTO>   clienteDTOSDTO;
		ClienteDTO pessoaDTO;
        if(!enderecos.isEmpty()) {
        	for (Endereco endereco : enderecos) {
        		clienteDTOSDTO   = new ArrayList<ClienteDTO>();
        		enderecoDTO= new EnderecoDTO();
        		BeanUtils.copyProperties(endereco, enderecoDTO);
        		for (Cliente cliente : endereco.getClientes()) {
        			pessoaDTO=new ClienteDTO();
        			BeanUtils.copyProperties(cliente, pessoaDTO);
        			clienteDTOSDTO.add(pessoaDTO);
        		}
        		
        		enderecosDTO.add(enderecoDTO);
        		
			}
        	return new RestDataReturnDTO(enderecosDTO, new Paginator(enderecos.getNumber(), enderecos.getTotalElements(), enderecos.getTotalPages()));
        }
        throw new ControllerNotFoundException("Nenhum Endereço para listar na pagina especificada.");
    }

	public EnderecoDTO atualizar(EnderecoDTO enderecoDTO,Long id) {
        Optional<Endereco> OPendereco = this.enderecoRepository.findById(id);
        try {
        	Endereco endereco = OPendereco.get();
        	endereco.setBairro(enderecoDTO.getBairro());
        	endereco.setCidade(enderecoDTO.getCidade());
        	endereco.setEstado(enderecoDTO.getEstado());
        	endereco.setNumero(enderecoDTO.getNumero());
        	endereco.setRua(enderecoDTO.getRua());
        	this.enderecoRepository.save(endereco);
        	return new EnderecoDTO(endereco);
        }catch (Exception e) {
            throw new ControllerNotFoundException("Endereço não encontrado, id: " + id);
		}
	}

	public String apagar(Long id) {
		Optional<Endereco> OPendereco = this.enderecoRepository.findById(id);
		if(OPendereco.isPresent()){
			Endereco endereco = OPendereco.get();
			this.enderecoRepository.delete(endereco);
			return "Removido o endereço de ID: "+id;
		}
        throw new ControllerNotFoundException("Endereço não encontrado, id: " + id);
	}

	public EnderecoDTO cadastrar(EnderecoDTO enderecoDTO) {
		Endereco endereco = new Endereco();

		BeanUtils.copyProperties(enderecoDTO,endereco);
		var enderecoSaved = enderecoRepository.save(endereco);
		return new EnderecoDTO(endereco);

	}
}
