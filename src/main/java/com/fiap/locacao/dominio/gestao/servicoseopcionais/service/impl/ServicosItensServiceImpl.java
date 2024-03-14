package com.fiap.locacao.dominio.gestao.servicoseopcionais.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.service.ServicosItensService;

@Service
public class ServicosItensServiceImpl implements ServicosItensService{

	@Override
	public ServicosItensDTOout salvar(ServicosItensDTOin servicosItensDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServicosItensDTOout atualizar(ServicosItensDTOin servicosItensDTO,Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(ServicosItensDTOin servicosItensDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServicosItensDTOout buscarPorId(ServicosItensDTOout servicosItensDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServicosItensDTOout> buscarTodos(ServicosItensDTOout servicosItensDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServicosItensDTOout> buscarPorTipo(ServicosItensDTOout servicosItensDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
