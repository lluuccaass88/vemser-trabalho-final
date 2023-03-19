package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusMotorista;
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
    private final MotoristaService motoristaService;
    private final CaminhaoService caminhaoService;
    private final EmailService emailService;
    private final RotaService rotaService;
    private final ObjectMapper objectMapper;

    public ViagemDTO criar(Integer idUsuario, ViagemCreateDTO viagemCreateDTO) throws RegraDeNegocioException {
        MotoristaEntity motoristaEncontrado = motoristaService.buscarPorId(idUsuario);
        CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(viagemCreateDTO.getIdCaminhao());

        // Verificações
        if (caminhaoEncontrado.getStatus().equals(StatusGeral.INATIVO)
                || motoristaEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Entidades informadas inativas!");

        } else if (caminhaoEncontrado.getStatusCaminhao().equals(StatusCaminhao.EM_VIAGEM)
                || motoristaEncontrado.getStatusMotorista().equals(StatusMotorista.EM_ESTRADA)) {
            throw new RegraDeNegocioException("Entidades informadas indisponíveis!");

        } else if (viagemCreateDTO.getDataFim().isBefore(viagemCreateDTO.getDataInicio())) {
            throw new RegraDeNegocioException("Data final não pode ser antes da data inicial!");
        }
        try {

            RotaEntity rotaEncontrada = rotaService.buscarPorId(viagemCreateDTO.getIdRota());

            ViagemEntity viagemEntity = objectMapper.convertValue(viagemCreateDTO, ViagemEntity.class);
            viagemEntity.setStatusViagem(StatusViagem.EM_ANDAMENTO);
            viagemEntity.setMotorista(motoristaEncontrado);
            viagemEntity.setCaminhao(caminhaoEncontrado);
            viagemEntity.setRota(rotaEncontrada);

            motoristaService.mudarStatus(motoristaEncontrado, StatusMotorista.EM_ESTRADA);
            motoristaEncontrado.getViagens().add(viagemEntity);
            caminhaoService.mudarStatus(caminhaoEncontrado, StatusCaminhao.EM_VIAGEM);
            caminhaoEncontrado.getViagens().add(viagemEntity);
            rotaEncontrada.getViagens().add(viagemEntity);

            viagemRepository.save(viagemEntity);

            ViagemDTO viagemDTO = objectMapper.convertValue(viagemEntity, ViagemDTO.class);
            viagemDTO.setIdUsuario(idUsuario);
            viagemDTO.setIdCaminhao(caminhaoEncontrado.getIdCaminhao());
            viagemDTO.setIdRota(rotaEncontrada.getIdRota());

//            emailService.enviarEmailViagemMotorista(rotaEncontrada, motoristaEncontrado);

            return viagemDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ViagemDTO editar(Integer idViagem, ViagemUpdateDTO viagemUpdateDTO) throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);

        if (viagemUpdateDTO.getDataFim().isBefore(viagemUpdateDTO.getDataInicio())) {
            throw new RegraDeNegocioException("Data final não pode ser antes da data inicial!");
        }
        try {

            viagemEncontrada.setDescricao(viagemUpdateDTO.getDescricao());
            viagemEncontrada.setDataInicio(viagemUpdateDTO.getDataInicio());
            viagemEncontrada.setDataFim(viagemUpdateDTO.getDataFim());

            MotoristaEntity motoristaEncontrado = motoristaService.buscarPorId(
                    viagemEncontrada.getMotorista().getIdUsuario());
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
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void finalizar(Integer idViagem) throws RegraDeNegocioException {
        ViagemEntity viagemEncontrada = buscarPorId(idViagem);

        try {
            viagemEncontrada.setStatusViagem(StatusViagem.FINALIZADA);

            MotoristaEntity motoristaEncontrado = motoristaService.buscarPorId(
                    viagemEncontrada.getMotorista().getIdUsuario());
            motoristaEncontrado.getViagens().add(viagemEncontrada);
            motoristaService.mudarStatus(motoristaEncontrado, StatusMotorista.DISPONIVEL);


            CaminhaoEntity caminhaoEncontrado = caminhaoService.buscarPorId(
                    viagemEncontrada.getCaminhao().getIdCaminhao());
            caminhaoEncontrado.getViagens().add(viagemEncontrada);
            caminhaoService.mudarStatus(caminhaoEncontrado, StatusCaminhao.ESTACIONADO);

            RotaEntity rotaEncontrada = rotaService.buscarPorId(
                    viagemEncontrada.getRota().getIdRota());
            rotaEncontrada.getViagens().add(viagemEncontrada);

            viagemRepository.save(viagemEncontrada);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a finalização.");
        }
    }

    public List<ViagemDTO> listar() throws RegraDeNegocioException {
        List<ViagemDTO> viagensDTO = viagemRepository.findAll()
                .stream()
                .map(viagem -> {
                    ViagemDTO viagemDTO = objectMapper.convertValue(viagem, ViagemDTO.class);
                    viagemDTO.setIdUsuario(viagem.getMotorista().getIdUsuario());
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
            viagemDTO.setStatusViagem(viagemRecuperada.getStatusViagem());
            viagemDTO.setIdRota(viagemRecuperada.getRota().getIdRota());
            viagemDTO.setIdCaminhao(viagemRecuperada.getCaminhao().getIdCaminhao());
            viagemDTO.setIdUsuario(viagemRecuperada.getMotorista().getIdUsuario());
            viagemDTO.setIdViagem(idViagem);
            return viagemDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<ViagemDTO> listarPorIdMotorista(Integer idMotorista) throws RegraDeNegocioException {
        MotoristaEntity motoristaEncontrado = motoristaService.buscarPorId(idMotorista);

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
                    viagemDTO.setIdUsuario(viagem.getMotorista().getIdUsuario());
                    viagemDTO.setIdCaminhao(viagem.getCaminhao().getIdCaminhao());
                    viagemDTO.setIdRota(idRota);
                    return viagemDTO;
                })
                .toList();
    }

    public ViagemEntity buscarPorId(Integer idViagem) throws RegraDeNegocioException {
        return viagemRepository.findById(idViagem)
                .orElseThrow(() -> new RegraDeNegocioException("Viagem não encontrada"));
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
                    viagemDTO.setIdUsuario(viagem.getMotorista().getIdUsuario());
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
}
