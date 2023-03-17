
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.ColaboradorDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ColaboradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    // TODO - fazer senha nao retornar no dto
    // TODO - fazer com que id nao incremente quando der erro
    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);

            colaboradorEntity.setStatusUsuario(StatusUsuario.ATIVO);

            colaboradorRepository.save(colaboradorEntity);

            return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorDTO update (Integer idUsuario, ColaboradorCreateDTO colaboradorCreateDTO)
            throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

            colaboradorEncontrado.setNome(colaboradorCreateDTO.getNome());
            colaboradorEncontrado.setSenha(colaboradorCreateDTO.getSenha());
            colaboradorEncontrado.set(colaboradorCreateDTO.getSenha());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}