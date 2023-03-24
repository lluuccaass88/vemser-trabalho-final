package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ViagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ViagemService {
    private final ViagemRepository viagemRepository;
    private final UsuarioService usuarioService;
    private final CaminhaoService caminhaoService;
    private final EmailService emailService;
    private final RotaService rotaService;
    private final ObjectMapper objectMapper;

    public ViagemDTO criar(Integer idUsuario, ViagemCreateDTO viagemCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idUsuario);
        CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(viagemCreateDTO.getIdCaminhao());
        RotaEntity rotaEncontrada = rotaService.buscarPorId(viagemCreateDTO.getIdRota());

        // Verificações
        if (caminhaoEncontrado.getStatus().equals(StatusGeral.INATIVO)
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
            viagemEntity.setUsuario(usuarioEncontrado);
            viagemEntity.setCaminhao(caminhaoEncontrado);
            viagemEntity.setRota(rotaEncontrada);

            usuarioEncontrado.getViagens().add(viagemEntity);
            caminhaoService.mudarStatus(caminhaoEncontrado, StatusCaminhao.EM_VIAGEM);
            caminhaoEncontrado.getViagens().add(viagemEntity);
            rotaEncontrada.getViagens().add(viagemEntity);

            viagemRepository.save(viagemEntity);

            ViagemDTO viagemDTO = objectMapper.convertValue(viagemEntity, ViagemDTO.class);
            viagemDTO.setIdUsuario(idUsuario);
            viagemDTO.setIdCaminhao(caminhaoEncontrado.getIdCaminhao());
            viagemDTO.setIdRota(rotaEncontrada.getIdRota());

            emailService.enviarEmailViagemMotorista(rotaEncontrada, usuarioEncontrado);

            return viagemDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ViagemDTO editar(Integer idMotorista, Integer idViagem, ViagemUpdateDTO viagemUpdateDTO)
            throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);

        if (!viagemEncontrada.getUsuario().getIdUsuario().equals(idMotorista)){
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

            ViagemDTO viagemDTO = objectMapper.convertValue(viagemEncontrada, ViagemDTO.class);
            viagemDTO.setIdUsuario(motoristaEncontrado.getIdUsuario());
            viagemDTO.setIdCaminhao(caminhaoEncontrado.getIdCaminhao());
            viagemDTO.setIdRota(rotaEncontrada.getIdRota());
            return viagemDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void finalizar(Integer idMotorista, Integer idViagem) throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);

        if (!viagemEncontrada.getUsuario().getIdUsuario().equals(idMotorista)){
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

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a finalização.");
        }
    }

    public List<ViagemDTO> listar() throws RegraDeNegocioException {
        List<ViagemDTO> viagensDTO = viagemRepository.findAll()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getUsuario().getIdUsuario());
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(viagem.getRota().getIdRota());
                    return viagemDTO;
                })
                .toList();

        return viagensDTO;
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
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<ViagemDTO> listarPorIdMotorista(Integer idMotorista) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idMotorista);

        return usuarioEncontrado.getViagens()
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
        RotaEntity rotaEncontrada = rotaService.buscarPorId(idCaminhao);

        return rotaEncontrada.getViagens()
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
            StatusViagem statusViagem,Integer pagina, Integer tamanho) {

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
