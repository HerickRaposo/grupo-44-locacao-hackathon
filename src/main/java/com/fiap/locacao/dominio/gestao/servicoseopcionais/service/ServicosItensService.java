package com.fiap.locacao.dominio.gestao.servicoseopcionais.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;

@Service
public interface ServicosItensService {
	ServicosItensDTOout salvar(ServicosItensDTOin servicosItensDTO);
	ServicosItensDTOout atualizar(ServicosItensDTOin servicosItensDTO,Long id);
	void deletar(ServicosItensDTOin servicosItensDTO);
	ServicosItensDTOout buscarPorId(ServicosItensDTOout servicosItensDTO);
	List<ServicosItensDTOout> buscarTodos(ServicosItensDTOout servicosItensDTO);
	List<ServicosItensDTOout> buscarPorTipo(ServicosItensDTOout servicosItensDTO);
}
