package com.fiap.locacao.dominio.gestao.quartos.localidade.services;

import com.fiap.locacao.dominio.gestao.endereco.dto.EnderecoDTO;
import com.fiap.locacao.dominio.gestao.endereco.entities.Endereco;
import com.fiap.locacao.dominio.gestao.endereco.services.EnderecoService;
import com.fiap.locacao.dominio.gestao.quartos.localidade.dto.LocalidadeDTO;
import com.fiap.locacao.dominio.gestao.quartos.localidade.entities.Localidade;
import com.fiap.locacao.dominio.gestao.quartos.localidade.repositories.ILocalidadeRepository;
import com.fiap.locacao.dominio.gestao.quartos.predio.dto.PredioDTO;
import com.fiap.locacao.dominio.gestao.quartos.predio.entity.Predio;
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
public class LocalidadeService {
    @Autowired
    private ILocalidadeRepository repo;

    @Autowired
    private EnderecoService enderecoService;
    public List<String> validate(LocalidadeDTO dto){
        Set<ConstraintViolation<LocalidadeDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }


    @Transactional(readOnly = true)
    public Page<LocalidadeDTO> findAll(PageRequest pagina, Long filtroIdEndereco) {
        List<LocalidadeDTO> listaLocalidades = new ArrayList<>();
        Specification<Localidade> specification = Specification.where(null);
        if (!StringUtils.isEmpty(filtroIdEndereco)) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("id_endereco"), filtroIdEndereco));
        }

        var localidades = repo.findAll(specification, pagina);

        for (Localidade localidade : localidades.getContent()) {
            LocalidadeDTO localidadeDTO = new LocalidadeDTO(localidade);
            BeanUtils.copyProperties(localidade, localidadeDTO);
            listaLocalidades.add(localidadeDTO);

            List<PredioDTO> predioDTOS = localidade.getListaPredios().stream()
                    .map(PredioDTO::new)
                    .collect(Collectors.toList());

            localidadeDTO.setListaPredios(predioDTOS);
        }

        Page<LocalidadeDTO> pageLocalidadeDTO = new PageImpl<>(listaLocalidades, pagina, localidades.getTotalElements());
        return pageLocalidadeDTO;
    }

    @Transactional(readOnly = true)
    public LocalidadeDTO findById(Long id) {
        var localidade = repo.findById(id).orElseThrow(() -> new RuntimeException("Localidade não encontrada"));
        return new LocalidadeDTO(localidade);
    }

    @Transactional
    public LocalidadeDTO insert(LocalidadeDTO dto) {
        Localidade entity = new Localidade();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getEnderecoDTO() != null){
            EnderecoDTO enderecoDTO = enderecoService.findById(dto.getEnderecoDTO().getId());
            Endereco endereco = new Endereco();
            BeanUtils.copyProperties(enderecoDTO, endereco);
            entity.setEndereco(endereco);
        }
        var localidadeSaved = repo.save(entity);
        return new LocalidadeDTO(localidadeSaved);
    }

    @Transactional
    public LocalidadeDTO update(Long id, LocalidadeDTO dto) {
        try {
            Localidade entity = repo.getOne(id);
            BeanUtils.copyProperties(dto, entity);
            return new LocalidadeDTO(entity);
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
