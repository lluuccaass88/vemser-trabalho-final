package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.dto.UsuarioCreateDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
import br.com.logisticadbc.entity.Usuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final RotaService rotaService;

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws Exception {

        Usuario entity = objectMapper.convertValue(usuario, Usuario.class);
        Usuario usuarioCriado = usuarioRepository.adicionar(entity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioCriado, UsuarioDTO.class);

        RotaDTO rota = getRota();

        if (usuarioCriado.getPerfil().equals("COLABORADOR")) {
            emailService.enviarEmailParaColaborador(usuarioDTO);
        } else {
            emailService.enviarEmailParaMotorista(usuarioDTO, rota);
        }

        return usuarioDTO;
    }

    public RotaDTO getRota() {
        List<RotaDTO> rotas = rotaService.listarRotas().stream().toList();
        RotaDTO rota = rotas.get(0);
        return rota;
    }

    public List<UsuarioDTO> listar() throws Exception {
        return usuarioRepository
                .listar()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioAtualizar) throws Exception {

        Usuario usuarioRecuperado = getUsuario(id);
        Integer idUsuario = getUsuario(id).getId();

        usuarioRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioRecuperado.setUsuario(usuarioAtualizar.getUsuario());
        usuarioRecuperado.setSenha(usuarioAtualizar.getSenha());
        usuarioRecuperado.setPerfil(usuarioAtualizar.getPerfil());
        usuarioRecuperado.setCpf(usuarioAtualizar.getCpf());
        usuarioRecuperado.setCnh(usuarioAtualizar.getCnh());
        usuarioRecuperado.setEmail(usuarioAtualizar.getEmail());
        // teste para saber se vai dar certo
        usuarioRepository.editar(idUsuario, usuarioRecuperado);

        UsuarioDTO dto = objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
        return dto;
    }

    public void deletar(Integer id) throws Exception {
        Usuario usuarioRecuperado = getUsuario(id);
        Integer idUsuario = getUsuario(id).getId();
        UsuarioDTO dto = objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);

        usuarioRepository.remover(idUsuario);
    }

    public Usuario getUsuario(Integer id) throws Exception {
        Usuario recuperarUsuario = usuarioRepository.listar().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        return recuperarUsuario;
    }
}
