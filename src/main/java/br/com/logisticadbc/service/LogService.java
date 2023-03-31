package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    // TODO - METODO CRIADO PORÉM NAO SEI SE SERÁ UTILIZADO
    public List<LogDTO> listAllLogs() {
        return logRepository
                .findAll()
                .stream()
                .map(log -> objectMapper.convertValue(log, LogDTO.class))
                .collect(Collectors.toList());
    }

    public void gerarLog(UsuarioEntity usuario, String descricao, TipoOperacao tipoOperacao) throws RegraDeNegocioException {
        Integer idUsuario = usuario.getIdUsuario();
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        LogEntity log = new LogEntity();
        log.setLoginOperador(usuarioEntity.getLogin());
        log.setDescricao(descricao);
        log.setTipoOperacao(tipoOperacao);

        try {
            logRepository.save(log);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação do log.");
        }
    }
}