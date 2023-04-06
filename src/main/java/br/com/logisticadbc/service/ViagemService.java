package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ViagemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ViagemService {
    private final ViagemRepository viagemRepository;
    private final UsuarioService usuarioService;
    private final CaminhaoService caminhaoService;
    private final RotaService rotaService;
    private final ObjectMapper objectMapper;
    private final KafkaProdutorService kafkaProdutorService;
    private final LogService logService;

    public ViagemDTO criar(Integer idMotorista, ViagemCreateDTO viagemCreateDTO) throws RegraDeNegocioException {
        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioLogado = usuarioService.buscarPorId(idLoggedUser);

        UsuarioEntity motorista = null;

        // se tiver parâmetro verifica se usuario logado é admin
        if (idMotorista != null) {
            if (!usuarioService.isAdmin(usuarioLogado)) {
                throw new RegraDeNegocioException("Só admin pode criar viagem para outro motorista.");
            }
            motorista = usuarioService.buscarPorId(idMotorista);

        // se nao tiver parâmetro, usa o proprio usuario logado para ser motorista
        } else {
            motorista = usuarioLogado;
        }
        CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(viagemCreateDTO.getIdCaminhao());
        RotaEntity rotaEncontrada = rotaService.buscarPorId(viagemCreateDTO.getIdRota());

        boolean isMotorista = motorista.getCargos()
                .stream()
                .anyMatch(cargo -> cargo.getNome().equals("ROLE_MOTORISTA"));

        boolean motoristaJaEmViagem = motorista.getViagens()
                .stream()
                .anyMatch(viagem -> viagem.getStatusViagem().equals(StatusViagem.EM_ANDAMENTO));

        if (!isMotorista) {
            throw new RegraDeNegocioException("Usuário não é motorista!");

        } else if (motoristaJaEmViagem) {
            throw new RegraDeNegocioException("Motorista já em viagem!");

        } else if (caminhaoEncontrado.getStatus().equals(StatusGeral.INATIVO)
                || rotaEncontrada.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Entidades informadas inativas!");

        } else if (caminhaoEncontrado.getStatusCaminhao().equals(StatusCaminhao.EM_VIAGEM)) {
            throw new RegraDeNegocioException("Caminhão informado indisponível!");

        } else if (viagemCreateDTO.getDataFim().isBefore(viagemCreateDTO.getDataInicio())) {
            throw new RegraDeNegocioException("Data final não pode ser antes da data inicial!");
        }
        try {
            ViagemEntity viagemEntity = objectMapper.convertValue(viagemCreateDTO, ViagemEntity.class);
            viagemEntity.setStatusViagem(StatusViagem.EM_ANDAMENTO);
            viagemEntity.setUsuario(motorista);
            viagemEntity.setCaminhao(caminhaoEncontrado);
            viagemEntity.setRota(rotaEncontrada);

            motorista.getViagens().add(viagemEntity);
            caminhaoService.mudarStatus(caminhaoEncontrado, StatusCaminhao.EM_VIAGEM);
            caminhaoEncontrado.getViagens().add(viagemEntity);
            rotaEncontrada.getViagens().add(viagemEntity);

            viagemRepository.save(viagemEntity);

            String descricao = "Operação em Viagem: " + viagemEntity.getDescricao();
            logService.gerarLog(motorista.getLogin(), descricao, TipoOperacao.CADASTRO);

            kafkaProdutorService.enviarEmailViagem(
                    motorista.getEmail(),
                    motorista.getNome(),
                    rotaEncontrada.getLocalPartida(),
                    rotaEncontrada.getLocalDestino(),
                    viagemEntity.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    viagemEntity.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            ViagemDTO viagemDTO = objectMapper.convertValue(viagemEntity, ViagemDTO.class);
            viagemDTO.setIdUsuario(idMotorista);
            viagemDTO.setIdCaminhao(caminhaoEncontrado.getIdCaminhao());
            viagemDTO.setIdRota(rotaEncontrada.getIdRota());
            return viagemDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        } catch (JsonProcessingException e) {
            throw new RegraDeNegocioException("Erro no produtor enviarEmailViagem.");
        }
    }

    public ViagemDTO editar(Integer idMotorista, Integer idViagem, ViagemUpdateDTO viagemUpdateDTO)
            throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (viagemEncontrada.getStatusViagem().equals(StatusViagem.FINALIZADA)) {
            throw new RegraDeNegocioException("Permissão negada, não pode editar viagem já finalizada!");

        } else if (!viagemEncontrada.getUsuario().getIdUsuario().equals(idMotorista)) {
            throw new RegraDeNegocioException("Permissão negada, motorista não criou a viagem!");

        } else if (viagemUpdateDTO.getDataFim().isBefore(viagemUpdateDTO.getDataInicio())) {
            throw new RegraDeNegocioException("Data final não pode ser antes da data inicial!");
        }
        try {
            viagemEncontrada.setDescricao(viagemUpdateDTO.getDescricao());
            viagemEncontrada.setDataInicio(viagemUpdateDTO.getDataInicio());
            viagemEncontrada.setDataFim(viagemUpdateDTO.getDataFim());

            UsuarioEntity motoristaEncontrado = usuarioService.buscarPorId(
                    viagemEncontrada.getUsuario().getIdUsuario());
            motoristaEncontrado.getViagens().add(viagemEncontrada);

            CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(
                    viagemEncontrada.getCaminhao().getIdCaminhao());
            caminhaoEncontrado.getViagens().add(viagemEncontrada);

            RotaEntity rotaEncontrada = rotaService.buscarPorId(
                    viagemEncontrada.getRota().getIdRota());
            rotaEncontrada.getViagens().add(viagemEncontrada);

            viagemRepository.save(viagemEncontrada);

            String descricao = "Operação em Viagem: " + viagemEncontrada.getDescricao();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.ALTERACAO);

            ViagemDTO viagemDTO = objectMapper.convertValue(viagemEncontrada, ViagemDTO.class);
            viagemDTO.setIdUsuario(motoristaEncontrado.getIdUsuario());
            viagemDTO.setIdCaminhao(caminhaoEncontrado.getIdCaminhao());
            viagemDTO.setIdRota(rotaEncontrada.getIdRota());
            return viagemDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public void finalizar(Integer idMotorista, Integer idViagem) throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (!viagemEncontrada.getUsuario().getIdUsuario().equals(idMotorista)) {
            throw new RegraDeNegocioException("Permissão negada, motorista não criou a viagem!");
        }
        try {
            viagemEncontrada.setStatusViagem(StatusViagem.FINALIZADA);

            UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(
                    viagemEncontrada.getUsuario().getIdUsuario());
            usuarioEncontrado.getViagens().add(viagemEncontrada);

            CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(
                    viagemEncontrada.getCaminhao().getIdCaminhao());
            caminhaoEncontrado.getViagens().add(viagemEncontrada);
            caminhaoService.mudarStatus(caminhaoEncontrado, StatusCaminhao.ESTACIONADO);

            RotaEntity rotaEncontrada = rotaService.buscarPorId(
                    viagemEncontrada.getRota().getIdRota());
            rotaEncontrada.getViagens().add(viagemEncontrada);

            viagemRepository.save(viagemEncontrada);

            String descricao = "Operação em Viagem: " + viagemEncontrada.getDescricao();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.FINALIZACAO);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public List<ViagemDTO> listar() throws RegraDeNegocioException {
        return viagemRepository.findAll()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getUsuario().getIdUsuario());
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(viagem.getRota().getIdRota());
                    return viagemDTO;
                })
                .toList();
    }

    public ViagemDTO listarPorId(Integer idViagem) throws RegraDeNegocioException {
        ViagemEntity viagemRecuperada = buscarPorId(idViagem);

        try {
            ViagemDTO viagemDTO = objectMapper.convertValue(viagemRecuperada, ViagemDTO.class);
            viagemDTO.setIdRota(viagemRecuperada.getRota().getIdRota());
            viagemDTO.setIdCaminhao(viagemRecuperada.getCaminhao().getIdCaminhao());
            viagemDTO.setIdUsuario(viagemRecuperada.getUsuario().getIdUsuario());
            return viagemDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro na conversão.");
        }
    }

    public List<ViagemDTO> listarPorIdMotorista(Integer idMotorista) throws RegraDeNegocioException {
        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioLogado = usuarioService.buscarPorId(idLoggedUser);
        UsuarioEntity motoristaEncontrado = null;

        // se tiver parâmetro verifica se é admin
        if (idMotorista != null) {

            if (!usuarioService.isAdmin(usuarioLogado)) {
                throw new RegraDeNegocioException("Só admin pode listar de outro usuário.");
            }
            motoristaEncontrado = usuarioService.buscarPorId(idMotorista);

            // se nao tiver parâmetro, usa o proprio usuario logado
        } else {
            motoristaEncontrado = usuarioLogado;
        }
        return motoristaEncontrado.getViagens()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(idMotorista);
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(viagem.getRota().getIdRota());
                    return viagemDTO;
                })
                .toList();
    }

    public List<ViagemDTO> listarPorIdRota(Integer idRota) throws RegraDeNegocioException {
        RotaEntity rotaEncontrada = rotaService.buscarPorId(idRota);

        return rotaEncontrada.getViagens()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getUsuario().getIdUsuario());
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(idRota);
                    return viagemDTO;
                })
                .toList();
    }

    public List<ViagemDTO> listarPorIdCaminhao(Integer idCaminhao) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(idCaminhao);

        return caminhaoEncontrado.getViagens()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getUsuario().getIdUsuario());
                    viagemDTO.setIdRota(viagem.getRota().getIdRota());
                    viagemDTO.setIdCaminhao(idCaminhao);
                    return viagemDTO;
                })
                .toList();
    }

    public PageDTO<ViagemDTO> listarPorStatusOrdenadoPorDataInicioAsc(
            StatusViagem statusViagem, Integer pagina, Integer tamanho) {

        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<ViagemEntity> paginacaoViagens = viagemRepository.findByStatusViagemEqualsOrderByDataInicioAsc(
                solicitacaoPagina, statusViagem);

        List<ViagemDTO> viagensDTOList = paginacaoViagens
                .getContent()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getUsuario().getIdUsuario());
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(viagem.getRota().getIdRota());
                    return viagemDTO;
                }).toList();

        return new PageDTO<>(
                paginacaoViagens.getTotalElements(),
                paginacaoViagens.getTotalPages(),
                pagina,
                tamanho,
                viagensDTOList
        );
    }

    public ViagemEntity buscarPorId(Integer idViagem) throws RegraDeNegocioException {
        return viagemRepository.findById(idViagem)
                .orElseThrow(() -> new RegraDeNegocioException("Viagem não encontrada"));
    }
}
