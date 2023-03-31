package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ViagemServiceTest {

    @InjectMocks
    private ViagemService viagemService;
    @Mock
    private ViagemRepository viagemRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private CaminhaoService caminhaoService;
    @Mock
    private RotaService rotaService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(viagemService, "objectMapper", objectMapper);
    }
    //Testar criar
//    @Test
//    public void deveCriarComSucesso() throws RegraDeNegocioException {
//        //Setup
//        Integer idMotorista = 1;
//        ViagemCreateDTO novaViagem = new ViagemCreateDTO(
//                "Viagem longa com duas paradas",
//                LocalDate.of(2023, 04, 22),
//                LocalDate.of(2023, 05, 22),
//                1,
//                1
//        );
//
//        List<ViagemEntity> listaDeViagens
//
//
//        ViagemEntity viagemMockadoDoBanco = getViagemEntityMock();
//
//        UsuarioEntity usuarioMockadoDoBanco = getUsuarioEntityMock();
//        usuarioMockadoDoBanco
//
//
//        CaminhaoEntity caminhaoEntityMockadoDoBanco = getCaminhaoEntityMock();
//        RotaEntity rotaEntityMockadoDoBanco = getRotaEntityMock();
//
//        when(usuarioService.buscarPorId(anyInt())).thenReturn(getUsuarioEntityMock());
//        when(caminhaoService.buscarPorId(anyInt())).thenReturn(getCaminhaoEntityMock());
//        when(rotaService.buscarPorId(anyInt())).thenReturn(getRotaEntityMock());
//        when(viagemRepository.save(any())).thenReturn(viagemMockadoDoBanco);
//
//
////        RotaEntity rotaMockadoDoBanco = getRotaEntityMock();
////        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();
////
////        Set<RotaEntity> rotaEntities = new HashSet<>();
////        rotaEntities.add(getRotaEntityMock());
////        rotaEntities.add(getRotaEntityMock());
////        usuarioMockadoBanco.setRotas(rotaEntities);
////
////        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioMockadoBanco);
////
////        when(rotaRepository.save(any())).thenReturn(rotaMockadoDoBanco);
//
//        //Action
//        ViagemDTO viagemRetornada = viagemService.criar(idMotorista, novaViagem);
//
//        //Assert
//        assertNotNull(viagemRetornada);
////        Assertions.assertEquals(novaRota.getDescricao(), rotaRetornada.getDescricao());
////        Assertions.assertEquals(novaRota.getLocalPartida(), rotaRetornada.getLocalPartida());
////        Assertions.assertEquals(novaRota.getLocalDestino(), rotaRetornada.getLocalDestino());
////        Assertions.assertEquals(StatusGeral.ATIVO, rotaRetornada.getStatus());
//    }

    //Teste Listar
    @Test
    public void deveListarViagemComSucesso() throws RegraDeNegocioException {
        // SETUP

        List<ViagemEntity> listaViagem = List.of(
                getViagemEntityMock(), getViagemEntityMock(), getViagemEntityMock());

        when(viagemRepository.findAll()).thenReturn(listaViagem);

        // ACT
        List<ViagemDTO> listaViagemDTO = viagemService.listar();

        // ASSERT
        Assertions.assertNotNull(listaViagemDTO);
        Assertions.assertEquals(3, listaViagemDTO.size());
    }

    //Teste listar por id
    @Test
    public void deveListaPorID() throws RegraDeNegocioException {
        // SETUP
        Integer idViagem = 1;
        ViagemEntity viagemEntity = getViagemEntityMock();

        when(viagemRepository.findById(anyInt())).thenReturn(Optional.of(viagemEntity));

        // ACT
        ViagemDTO viagemDTO = viagemService.listarPorId(idViagem);

        // ASSERT
        Assertions.assertNotNull(viagemDTO);
        Assertions.assertEquals(idViagem, viagemDTO.getIdViagem());
    }

    //Teste listar Por Id do motorista
    @Test
    public void deveListarPorIdMotorista() throws RegraDeNegocioException {
        // SETUP
        Integer idUsuario = 1;

        Set<ViagemEntity> viagemEntities = new HashSet<>();
        viagemEntities.add(getViagemEntityMock());
        viagemEntities.add(getViagemEntityMock());

        UsuarioEntity usuarioMockadoBanco = new UsuarioEntity();
        usuarioMockadoBanco.setViagens(viagemEntities);

        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioMockadoBanco);

        // ACT
        List<ViagemDTO> listviagensDTO = viagemService.listarPorIdMotorista(idUsuario);

        // ASSERT
        Assertions.assertNotNull(listviagensDTO);

        for (int i = 0; i < listviagensDTO.size(); i++) {
            ViagemDTO viagem = listviagensDTO.get(i);
            Assertions.assertEquals(idUsuario, viagem.getIdUsuario());
        }
    }

    //Teste listar Por Id da rota
    @Test
    public void deveListarPorIdRota() throws RegraDeNegocioException {
        // SETUP
        Integer idRota = 1;

        Set<ViagemEntity> viagemEntities = new HashSet<>();
        viagemEntities.add(getViagemEntityMock());
        viagemEntities.add(getViagemEntityMock());

        RotaEntity rotaMockadoBanco = getRotaEntityMock();
        rotaMockadoBanco.setViagens(viagemEntities);

        when(rotaService.buscarPorId(anyInt())).thenReturn(rotaMockadoBanco);

        // ACT
        List<ViagemDTO> listviagensDTO = viagemService.listarPorIdRota(idRota);

        // ASSERT
        Assertions.assertNotNull(listviagensDTO);

        for (int i = 0; i < listviagensDTO.size(); i++) {
            ViagemDTO viagem = listviagensDTO.get(i);
            Assertions.assertEquals(idRota, viagem.getIdRota());
        }
    }

    //Teste listar Por Id do caminhão
    @Test
    public void deveListarPorIdCaminhão() throws RegraDeNegocioException {
        // SETUP
        Integer idCaminhao = 1;

        Set<ViagemEntity> viagemEntities = new HashSet<>();
        viagemEntities.add(getViagemEntityMock());
        viagemEntities.add(getViagemEntityMock());

        CaminhaoEntity caminhaoMockadoBanco = getCaminhaoEntityMock();
        caminhaoMockadoBanco.setViagens(viagemEntities);

        when(caminhaoService.buscarPorId(anyInt())).thenReturn(caminhaoMockadoBanco);

        // ACT
        List<ViagemDTO> listviagensDTO = viagemService.listarPorIdCaminhao(idCaminhao);

        // ASSERT
        Assertions.assertNotNull(listviagensDTO);

        //Verificando de todos os id de caminhão são o esperado
        for (int i = 0; i < listviagensDTO.size(); i++) {
            ViagemDTO viagem = listviagensDTO.get(i);
            Assertions.assertEquals(idCaminhao, viagem.getIdCaminhao());
        }
    }

    //Teste Listar Por Status ordenado por data de inicio paginado e ordenado
    @Test
    public void develistarPorStatusOrdenadoPorDataInicioAsc() throws RegraDeNegocioException {
      // SETUP
        StatusViagem statusViagem = StatusViagem.EM_ANDAMENTO;
        Integer pagina = 0;
        Integer tamanho = 2;

        List<ViagemEntity> listaViagem = List.of(
                getViagemEntityMock(), getViagemEntityMock(), getViagemEntityMock());

        Page<ViagemEntity> pageViagem = new PageImpl<>(listaViagem, PageRequest.of(pagina, tamanho), listaViagem.size());

        when(viagemRepository.findByStatusViagemEqualsOrderByDataInicioAsc(PageRequest.of(pagina, tamanho), statusViagem))
                .thenReturn(pageViagem);

        // ACT
        PageDTO<ViagemDTO> viagensPaginadasDTO = viagemService.listarPorStatusOrdenadoPorDataInicioAsc(statusViagem, pagina, tamanho);

        // ASSERT
        Assertions.assertNotNull(viagensPaginadasDTO);
        Assertions.assertEquals(pagina, viagensPaginadasDTO.getPagina());
        Assertions.assertEquals(tamanho, viagensPaginadasDTO.getTamanho());

    }





    private static UsuarioEntity getUsuarioEntityMock() {
        UsuarioEntity usuarioMockado = new UsuarioEntity();
        usuarioMockado.setIdUsuario(1);
        usuarioMockado.setLogin("Joaquim");
        usuarioMockado.setSenha("abc123");
        usuarioMockado.setEmail("lallala@email.com");
        usuarioMockado.setNome("Joaquim");
        usuarioMockado.setDocumento("12345678910");
        usuarioMockado.setStatus(StatusGeral.ATIVO);

        return usuarioMockado;
    }


    private static CaminhaoEntity getCaminhaoEntityMock() {
        CaminhaoEntity caminhaoMockado = new CaminhaoEntity();
        caminhaoMockado.setIdCaminhao(1);
        caminhaoMockado.setModelo("VUC");
        caminhaoMockado.setPlaca("ABC1D23");
        caminhaoMockado.setNivelCombustivel(50);
        caminhaoMockado.setStatusCaminhao(StatusCaminhao.ESTACIONADO);
        caminhaoMockado.setStatus(StatusGeral.ATIVO);

        UsuarioEntity usuarioMockado = getUsuarioEntityMock();
        caminhaoMockado.setUsuario(usuarioMockado);

        return caminhaoMockado;
    }


    private static RotaEntity getRotaEntityMock() {
        RotaEntity rotaMockadaDoBanco = new RotaEntity();
        rotaMockadaDoBanco.setIdRota(1);
        rotaMockadaDoBanco.setStatus(StatusGeral.ATIVO);
        rotaMockadaDoBanco.setDescricao("Rota de Brasilia até São Paulo");
        rotaMockadaDoBanco.setLocalPartida("Brasilia");
        rotaMockadaDoBanco.setLocalDestino("São Paulo");
        rotaMockadaDoBanco.setUsuario(getUsuarioEntityMock());
        return rotaMockadaDoBanco;
    }


    private static ViagemEntity getViagemEntityMock() {
        ViagemEntity viagemMockadaDoBanco = new ViagemEntity();
        viagemMockadaDoBanco.setIdViagem(1);
        viagemMockadaDoBanco.setStatusViagem(StatusViagem.EM_ANDAMENTO);
        viagemMockadaDoBanco.setUsuario(getUsuarioEntityMock());
        viagemMockadaDoBanco.setRota(getRotaEntityMock());
        viagemMockadaDoBanco.setCaminhao(getCaminhaoEntityMock());
        viagemMockadaDoBanco.setDataInicio(LocalDate.now());
        viagemMockadaDoBanco.setDataFim(LocalDate.of(2023, 04, 22));
        return viagemMockadaDoBanco;
    }
}
