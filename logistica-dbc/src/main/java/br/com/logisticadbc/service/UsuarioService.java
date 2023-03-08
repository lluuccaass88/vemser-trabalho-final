package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.UsuarioCreateDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
import br.com.logisticadbc.entity.Usuario;
import br.com.logisticadbc.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws Exception {

        Usuario entity = objectMapper.convertValue(usuario, Usuario.class);
        Usuario usuarioCriado = usuarioRepository.adicionar(entity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioCriado, UsuarioDTO.class);

        return usuarioDTO;
    }

}
