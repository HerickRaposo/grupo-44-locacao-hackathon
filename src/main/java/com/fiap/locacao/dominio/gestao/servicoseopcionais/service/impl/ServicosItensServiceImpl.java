package com.fiap.locacao.dominio.gestao.servicoseopcionais.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.locacao.dominio.gestao.endereco.dto.Paginator;
import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.entity.ServicosItens;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.repository.IServicosItensRepository;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.service.ServicosItensService;
import com.fiap.locacao.exception.ControllerNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ServicosItensServiceImpl implements ServicosItensService{
	private @Autowired IServicosItensRepository iServicosItensRepository;

	@Transactional
	@Override
	public ServicosItensDTOout salvar(ServicosItensDTOin servicosItensDTO) {
		ServicosItens servicosItens = this.iServicosItensRepository.save(servicosItensDTO.getEntity());
		return new ServicosItensDTOout().getDTO(servicosItens);
	}

	@Transactional
	@Override
	public ServicosItensDTOout atualizar(ServicosItensDTOin servicosItensDTO,Long id) {
		Optional<ServicosItens> OservicosItens = this.iServicosItensRepository.findById(id);
		
		try {
			ServicosItens servicosItens = OservicosItens.get();
			servicosItens.setDescricao(servicosItensDTO.getDescricao());
			servicosItens.setTipoServicosItens(servicosItensDTO.getTipoServicosItens());
			servicosItens.setValor(servicosItensDTO.getValor());
			
			this.iServicosItensRepository.save(servicosItens);
			
			return new ServicosItensDTOout().getDTO(servicosItens);
		}catch(Exception e) {
			throw new ControllerNotFoundException("Registo não encontrado, id: " + id);
		}	
	}

	@Override
	public String apagar(Long id) {
		Optional<ServicosItens> OservicosItens = this.iServicosItensRepository.findById(id);
		try {
			 ServicosItens servicosItens = OservicosItens.get();
			 this.iServicosItensRepository.delete(servicosItens);
			 return "Removido o Registo de ID: "+id;
		}catch(Exception e) {
			throw new ControllerNotFoundException("Registo não encontrado, id: " + id);			
		}
	}

	@Override
	public ServicosItensDTOout buscarPorId(Long id) {
		try {
			Optional<ServicosItens> OservicosItens = this.iServicosItensRepository.findById(id);
			 ServicosItens servicosItens = OservicosItens.get();
			 this.iServicosItensRepository.delete(servicosItens);
			 return new ServicosItensDTOout().getDTO(servicosItens);
		}catch(Exception e) {
			throw new ControllerNotFoundException("Registo não encontrado, id: " + id);			
		}
	}

	@Override
	public RestDataReturnDTO buscarTodos(PageRequest pageRequest) {
		 Page<ServicosItens> servicosItens = iServicosItensRepository.findAll(pageRequest);
		 return retornoPaginator(servicosItens);
	}

	@Override
	public RestDataReturnDTO buscarPorTipo(PageRequest pageRequest,TipoServicosItens tipoServicosItens) {
		 Page<ServicosItens> servicosItens = iServicosItensRepository.findAll(pageRequest);
		 return retornoPaginator(servicosItens);
	}
	
	public RestDataReturnDTO retornoPaginator(Page<ServicosItens> servicosItens) {
		 List<ServicosItensDTOout> servicosItensDTOsout=new ArrayList<ServicosItensDTOout>();
		 ServicosItensDTOout servicosItensDTOout;
		 
		 if(!servicosItens.isEmpty()) {
			
			 for (ServicosItens u : servicosItens.getContent()) {
				 servicosItensDTOout=new ServicosItensDTOout();
				 BeanUtils.copyProperties(u, servicosItensDTOout);
				 servicosItensDTOsout.add(servicosItensDTOout);
			}
			return new RestDataReturnDTO(servicosItensDTOsout, new Paginator(servicosItens.getNumber(), servicosItens.getTotalElements(), servicosItens.getTotalPages()));
		 }
		 throw new ControllerNotFoundException("Nenhum Registo para listar na pagina especificada.");
	}
	
}
