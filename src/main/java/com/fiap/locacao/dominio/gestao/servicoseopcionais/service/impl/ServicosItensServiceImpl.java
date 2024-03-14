package com.fiap.locacao.dominio.gestao.servicoseopcionais.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
	public List<ServicosItensDTOout> buscarTodos(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServicosItensDTOout> buscarPorTipo(PageRequest pageRequest,TipoServicosItens tipoServicosItens) {
		// TODO Auto-generated method stub
		return null;
	}
}
