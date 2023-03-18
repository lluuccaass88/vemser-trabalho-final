
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;

import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
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
    private final EmailService emailService;

    public List<ColaboradorDTO> listar() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }


 /*   public List<ColaboradorCompletoDTO> GerarRelatorio(){
        return colaboradorRepository.relatorio();
    }*/


    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);

            colaboradorEntity.setStatusUsuario(StatusGeral.ATIVO);

            colaboradorRepository.save(colaboradorEntity);

            emailService.enviarEmailBoasVindasColabotador(colaboradorEntity);

            return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorDTO editar(Integer idUsuario, ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

            if (colaboradorEncontrado.getStatusUsuario().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Usuário inativo!");
            }

            colaboradorEncontrado.setNome(colaboradorUpdateDTO.getNome());
            colaboradorEncontrado.setSenha(colaboradorUpdateDTO.getSenha());

            colaboradorRepository.save(colaboradorEncontrado);

            return objectMapper.convertValue(colaboradorEncontrado, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);
            colaboradorEncontrado.setStatusUsuario(StatusGeral.INATIVO);

            colaboradorRepository.save(colaboradorEncontrado);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }


    public ColaboradorDTO listarPorId(Integer idColaborador) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorRecuperado = buscarPorId(idColaborador);

            ColaboradorDTO colaboradorDTO = objectMapper.convertValue(colaboradorRecuperado, ColaboradorDTO.class);
            colaboradorDTO.setIdUsuario(idColaborador);
            return colaboradorDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}