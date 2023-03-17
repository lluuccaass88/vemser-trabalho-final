
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.ColaboradorDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ColaboradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ObjectMapper objectMapper;

    // TODO quando criar ou editar alterar o status do usuario

    // TODO CRUD -> CRIAR, EDITAR, LISTAR, DELETAR, BUSCARPORID


    public List<ColaboradorDTO> listar() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) {
        ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);
        colaboradorEntity.setStatusUsuario(StatusUsuario.ATIVO);

        colaboradorRepository.save(colaboradorEntity);

        return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}