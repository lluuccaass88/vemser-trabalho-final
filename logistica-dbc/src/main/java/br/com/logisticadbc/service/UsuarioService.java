package br.com.logisticadbc.service;

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

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws Exception {

        Usuario entity = objectMapper.convertValue(usuario, Usuario.class);
        Usuario usuarioCriado = usuarioRepository.adicionar(entity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioCriado, UsuarioDTO.class);

        return usuarioDTO;
    }

    public List<UsuarioDTO> listar() throws Exception {
        return usuarioRepository
                .listar()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO editar (Integer id, UsuarioCreateDTO usuarioAtualizar) throws Exception {

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

//    public Usuario loginUsuario(String usurario, String senha){
//        Usuario usuarioLogado = new Usuario();
//        try {
//            usuarioLogado = usuarioRepository.login(usurario, senha);
//            if(usuarioLogado.getId() == null){
//                throw new Exception("Erro ao fazer o login, verifique seu usuario ou senha");
//            }else{
//                System.out.println("Entrando...");
//                return usuarioLogado;
//            }
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("ERRO LOGIN -> " + e.getMessage());
//        }
//        return usuarioLogado;
//    }

    public Usuario getUsuario(Integer id) throws Exception {
        Usuario recuperarUsuario = usuarioRepository.listar().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        return recuperarUsuario;
    }
}
