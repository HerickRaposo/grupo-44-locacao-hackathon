package com.fiap.locacao.dominio.gestao.servicoseopcionais.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;

@Service
public interface ServicosItensService {
	ServicosItensDTOout salvar(ServicosItensDTOin servicosItensDTO);
	ServicosItensDTOout atualizar(ServicosItensDTOin servicosItensDTO,Long id);
	String apagar(Long id);
	ServicosItensDTOout buscarPorId(Long id);
	List<ServicosItensDTOout> buscarTodos(PageRequest pageRequest);
	List<ServicosItensDTOout> buscarPorTipo(PageRequest pageRequest,TipoServicosItens tipoServicosItens);
}
