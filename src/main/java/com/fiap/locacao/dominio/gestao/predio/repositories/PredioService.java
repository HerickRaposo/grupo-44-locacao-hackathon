package com.fiap.locacao.dominio.gestao.predio.repositories;

import com.fiap.locacao.dominio.gestao.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.localidade.services.LocalidadeService;
import com.fiap.locacao.dominio.gestao.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.predio.entity.Predio;
import com.fiap.locacao.dominio.gestao.predio.repository.IPredioRepository;
import com.fiap.locacao.dominio.gestao.quarto.dto.QuartoDTO;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PredioService {
    @Autowired
    private IPredioRepository repo;

    @Autowired
    private LocalidadeService localidadeService;

    public List<String> validate(PredioDTO dto){
        Set<ConstraintViolation<PredioDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }

    @Transactional(readOnly = true)
    public Page<PredioDTO> findAll(PageRequest pagina, Long filtroIdEndereco) {
        List<PredioDTO> prediosDTO = new ArrayList<>();
        Specification<Predio> specification = Specification.where(null);
        if (!StringUtils.isEmpty(filtroIdEndereco)) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("id_endereco"), filtroIdEndereco));
        }

        var predios = repo.findAll(specification, pagina);

        for (Predio predio : predios.getContent()) {
            PredioDTO predioDTO = new PredioDTO(predio);
            BeanUtils.copyProperties(predio, predioDTO);
            prediosDTO.add(predioDTO);

            List<QuartoDTO> quartosDTO = predio.getListaquartos().stream()
                    .map(QuartoDTO::new)
                    .collect(Collectors.toList());

            predioDTO.setListaquartos(quartosDTO);
        }

        Page<PredioDTO> pagePredioDTO = new PageImpl<>(prediosDTO, pagina, predios.getTotalElements());
        return pagePredioDTO;
    }

    @Transactional(readOnly = true)
    public PredioDTO findById(Long id) {
        var predio = repo.findById(id).orElseThrow(() -> new RuntimeException("Predio não encontrado"));
        return new PredioDTO(predio);
    }

    @Transactional
    public PredioDTO insert(PredioDTO dto) {
        Predio entity = new Predio();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getLocalidade() != null){
            LocalidadeDTO localidadeDTO = localidadeService.findById(dto.getLocalidade().getId());
            Localidade localidade = new Localidade();
            BeanUtils.copyProperties(localidadeDTO, localidade);
            entity.setLocalidade(localidade);
        }
        var predioSaved = repo.save(entity);
        return new PredioDTO(predioSaved);
    }

    @Transactional
    public PredioDTO update(Long id, PredioDTO dto) {
        try {
            Predio entity = repo.getReferenceById(id);
            BeanUtils.copyProperties(dto, entity);
            entity = repo.save(entity);
            return new PredioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw  new EntityNotFoundException("Produto não encontrado, id:" + id);
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

