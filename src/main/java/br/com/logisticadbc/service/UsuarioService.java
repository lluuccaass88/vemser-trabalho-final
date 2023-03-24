package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public UsuarioDTO criar(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);

        try {
            usuarioEntity.setStatus(StatusGeral.ATIVO);
            // TODO CRIPTOGRAFAR SENHA

            usuarioRepository.save(usuarioEntity);

            // TODO AJEITAR EMAIL SERVICE

//          emailService.enviarEmailBoasVindasColaborador(usuarioEntity); - ESTA COMENTADO POIS VAI BUGAR NO CARGO


            return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public UsuarioDTO editar(Integer idUsuario, UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = buscarPorId(idUsuario);

        try {
            usuarioEncontrado.setNome(usuarioUpdateDTO.getNome());
            usuarioEncontrado.setEmail(usuarioUpdateDTO.getEmail());
            usuarioEncontrado.setSenha(usuarioUpdateDTO.getSenha());
            usuarioEncontrado.setDocumento(usuarioUpdateDTO.getDocumento());

            // TODO CRIPTOGRAFAR SENHA

            usuarioRepository.save(usuarioEncontrado);

            return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = buscarPorId(idUsuario);

        try {
            usuarioEncontrado.setStatus(StatusGeral.INATIVO);
            usuarioRepository.save(usuarioEncontrado);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    public UsuarioDTO listarPorId(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = buscarPorId(idUsuario);

        try {
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
            usuarioDTO.setIdUsuario(idUsuario);
            return usuarioDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    // TODO listarPorCargo - ESTA EM CONTRUÇÃO (LUCAS)
//    public UsuarioDTO listarPorCargo(String cargo) throws RegraDeNegocioException{
//        Set<UsuarioEntity> listaUsuarios = usuarioRepository.findByCargosEquals()
//    }
    // TODO listarPorCargoEStatus

    public List<UsuarioDTO> listarAtivos() {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getStatus().equals(StatusGeral.ATIVO))
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    // TODO gerarRelatorioCompleto

    public UsuarioEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }
}