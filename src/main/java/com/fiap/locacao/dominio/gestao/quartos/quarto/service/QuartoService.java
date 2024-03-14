package com.fiap.locacao.dominio.gestao.quartos.quarto.service;


import com.fiap.locacao.dominio.gestao.quartos.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.quartos.predio.enumertation.TipoQuarto;
import com.fiap.locacao.dominio.gestao.quartos.predio.repository.IPredioRepository;
import com.fiap.locacao.dominio.gestao.quartos.quarto.dto.QuartoDTO;
import com.fiap.locacao.dominio.gestao.quartos.quarto.entity.Quarto;
import com.fiap.locacao.dominio.gestao.quartos.quarto.repository.IQuartoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuartoService {
    @Autowired
    private IQuartoRepository repo;

    @Autowired
    private IPredioRepository predioRepository;


    public List<String> validate(QuartoDTO dto){
        Set<ConstraintViolation<QuartoDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }

    public QuartoDTO retornaFiltroFormatado(String tipoQuarto, Boolean reservado, Long idPredio){
        try {
            QuartoDTO filtro = new QuartoDTO();
            if (tipoQuarto != null && !tipoQuarto.isBlank()){
                Long idTipo = TipoQuarto.buscarCodigoTipoQuarto(tipoQuarto);
                filtro.setTipo(idTipo);
            }

            if (reservado != null){
                filtro.setReservado(reservado);
            }
            if (idPredio != null){
                filtro.setPredio(new PredioDTO());
                filtro.getPredio().setId(idPredio);
            }

            return filtro;
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
    @Transactional(readOnly = true)
    public Page<QuartoDTO> findAll(PageRequest pagina, QuartoDTO filtro) {
        List<QuartoDTO> listaQuartosDTO = new ArrayList<>();
        QuartoDTO quartoDTO;


        Specification<Quarto> specification = Specification.where(null);
        if (filtro.getTipo() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("tipo"), filtro.getTipo()));
        }

        if (filtro.getReservado() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("reservado"), filtro.getReservado()));
        }

        if (filtro.getPredio() != null && filtro.getPredio().getId() != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("id_predio"), filtro.getPredio().getId()));
        }

        var quartos = repo.findAll(specification,pagina);

        for (Quarto quarto : quartos.getContent()) {
            quartoDTO = new QuartoDTO(quarto);
            BeanUtils.copyProperties(quarto, quartoDTO);
            listaQuartosDTO.add(quartoDTO);
        }
        Page<QuartoDTO> pageQuartoDTO = new PageImpl<>(listaQuartosDTO, pagina, quartos.getTotalElements());
        return pageQuartoDTO;
    }

    @Transactional(readOnly = true)
    public QuartoDTO findById(Long id) {
        var quarto = repo.findById(id).orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
        return new QuartoDTO(quarto);
    }

    @Transactional
    public QuartoDTO insert(QuartoDTO dto) {
        var entity = new Quarto();
        BeanUtils.copyProperties(dto, entity);
        var produtoSaved = repo.save(entity);
        return new QuartoDTO(produtoSaved);
    }

    @Transactional
    public QuartoDTO update(Long id, QuartoDTO dto) {
        try {
            Quarto entity = repo.getOne(id);
            BeanUtils.copyProperties(dto, entity);
            return new QuartoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw  new EntityNotFoundException("Quarto não encontrado, id:" + id);
        }
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Violação de integridade da base");
        }

    }
}

