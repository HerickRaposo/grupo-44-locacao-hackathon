package com.fiap.locacao.dominio.gestao.servicoseopcionais.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.locacao.dominio.gestao.endereco.dto.RestDataReturnDTO;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOin;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.dto.ServicosItensDTOout;
import com.fiap.locacao.dominio.gestao.servicoseopcionais.enumerations.TipoServicosItens;

@Service
public interface ServicosItensService {
	ServicosItensDTOout salvar(ServicosItensDTOin servicosItensDTO);
	ServicosItensDTOout atualizar(ServicosItensDTOin servicosItensDTO,Long id);
	String apagar(Long id);
	ServicosItensDTOout buscarPorId(Long id);
	RestDataReturnDTO buscarTodos(PageRequest pageRequest);
	RestDataReturnDTO buscarPorTipo(PageRequest pageRequest,TipoServicosItens tipoServicosItens);
}
