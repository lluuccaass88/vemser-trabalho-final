package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioCompletoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public PageDTO<UsuarioDTO> listarPorCargo(String cargo, Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository.findByCargoUsuario(solicitacaoPagina, cargo);

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    // TODO listarPorCargoEStatus - ESTA EM CONTRUÇÃO (MARCKLEN)
    public PageDTO<UsuarioDTO> listarPorCargoEStatus(String cargo, StatusGeral status, Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository.findByCargosAndStatus(solicitacaoPagina, cargo, status);

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public List<UsuarioDTO> listarAtivos() {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getStatus().equals(StatusGeral.ATIVO))
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    // TODO gerarRelatorioCompleto
        public PageDTO<UsuarioCompletoDTO> gerarRelatorioCompleto(Integer pagina, Integer tamanho) { //ORDENAR POR CARGO

            Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

            Page<UsuarioCompletoDTO> paginacaoMotorista = usuarioRepository.relatorio(solicitacaoPagina);

            List<UsuarioCompletoDTO> usuarioDTOList = paginacaoMotorista
                    .getContent()
                    .stream()
                    .map(colaborador -> objectMapper.convertValue(colaborador, UsuarioCompletoDTO.class))
                    .toList();

            return new PageDTO<>(
                    paginacaoMotorista.getTotalElements(),
                    paginacaoMotorista.getTotalPages(),
                    pagina,
                    tamanho,
                    usuarioDTOList
            );
        }

    public PageDTO<UsuarioDTO> listarMotoristasLivres(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository.findByUsuarioLivre(solicitacaoPagina, StatusViagem.FINALIZADA, "ROLE_MOTORISTA");

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public UsuarioEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }
}