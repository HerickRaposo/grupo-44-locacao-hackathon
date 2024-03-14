package com.fiap.locacao.dominio.gestao.servicoseopcionais.service;

import java.util.List;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTO;

public interface ServicosItensService {
	ServicosItensDTO salvar(ServicosItensDTO servicosItensDTO);
	ServicosItensDTO atualizar(ServicosItensDTO servicosItensDTO);
	void deletar(ServicosItensDTO servicosItensDTO);
	ServicosItensDTO buscarPorId(ServicosItensDTO servicosItensDTO);
	List<ServicosItensDTO> buscarTodos(ServicosItensDTO servicosItensDTO);
	List<ServicosItensDTO> buscarPorTipo(ServicosItensDTO servicosItensDTO);
}
