package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CaminhaoServiceTest {

    @InjectMocks
    private CaminhaoService caminhaoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CaminhaoRepository caminhaoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LogService logService;
    // Executa primeiro
    @Before
    public void init() {
        // Configurações do ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(caminhaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveCriarComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer idUsuario = 1;
        CaminhaoCreateDTO caminhaoNovo = new CaminhaoCreateDTO();
        caminhaoNovo.setModelo("VUC");
        caminhaoNovo.setPlaca("ABC1D23");
        caminhaoNovo.setNivelCombustivel(50);

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        Set<CaminhaoEntity> caminhaoEntities = new HashSet<>();
        caminhaoEntities.add(caminhaoEntityMock);
        usuarioEntityMock.setCaminhoes(caminhaoEntities);

        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioEntityMock);
        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
        Mockito.when(caminhaoRepository.save(Mockito.any())).thenReturn(caminhaoEntityMock);

        // ACT
        CaminhaoDTO caminhaoDTORetornado = caminhaoService.criar(idUsuario, caminhaoNovo);

        // ASSERT
        Assertions.assertNotNull(caminhaoDTORetornado);

        Assertions.assertEquals(caminhaoNovo.getModelo(), caminhaoDTORetornado.getModelo());
        Assertions.assertEquals(caminhaoNovo.getPlaca(), caminhaoDTORetornado.getPlaca());
        Assertions.assertEquals(caminhaoNovo.getNivelCombustivel(), caminhaoDTORetornado.getNivelCombustivel());
        Assertions.assertEquals(1, caminhaoDTORetornado.getIdCaminhao());
        Assertions.assertEquals(StatusCaminhao.ESTACIONADO, caminhaoDTORetornado.getStatusCaminhao());
        Assertions.assertEquals(1, caminhaoDTORetornado.getIdUsuario());
    }

    @Test
    public void deveTestarAbastecerComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer combustivel = 20;

        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        CaminhaoEntity caminhaoAbastecido = new CaminhaoEntity();
        caminhaoAbastecido.setIdCaminhao(1);
        caminhaoAbastecido.setModelo("VUC");
        caminhaoAbastecido.setPlaca("ABC1D23");
        caminhaoAbastecido.setNivelCombustivel(50);
        caminhaoAbastecido.setStatusCaminhao(StatusCaminhao.ESTACIONADO);
        caminhaoAbastecido.setStatus(StatusGeral.ATIVO);
        caminhaoAbastecido.setUsuario(getUsuarioEntityMock());
        caminhaoAbastecido.setNivelCombustivel(caminhaoAbastecido.getNivelCombustivel() + combustivel);

        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caminhaoEntityMock));
        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
        Mockito.when(caminhaoRepository.save(Mockito.any())).thenReturn(caminhaoAbastecido);

        // ACT
        CaminhaoDTO caminhaoDTOAbastecido = caminhaoService.abastecer(1, combustivel);

        // ASSERT
        Assertions.assertEquals(caminhaoEntityMock.getNivelCombustivel(),
                caminhaoDTOAbastecido.getNivelCombustivel());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAbastecerComCaminhaoInativo() throws RegraDeNegocioException {
        // SETUP
        Integer combustivel = 20;
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();
        caminhaoEntityMock.setStatus(StatusGeral.INATIVO);

        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caminhaoEntityMock));

        // ACT
        caminhaoService.abastecer(1, combustivel);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAbastecerComGasolinaInformadaMenorOuIgualAZaro() throws RegraDeNegocioException {
        // SETUP
        Integer idCaminhao = 1;
        Integer combustivel = -20;
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caminhaoEntityMock));

        // ACT
        caminhaoService.abastecer(idCaminhao, combustivel);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAbastecerComLimiteDeGasolinaExcedido() throws RegraDeNegocioException {
        // SETUP
        Integer idCaminhao = 1;
        Integer combustivel = 20;
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();
        caminhaoEntityMock.setNivelCombustivel(90);

        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caminhaoEntityMock));

        // ACT
        caminhaoService.abastecer(idCaminhao, combustivel);
    }

    @Test
    public void deveTestarRemoverComSucesso() throws RegraDeNegocioException {
        // SETUP
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caminhaoEntityMock));
        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);

        // ACT
        caminhaoService.deletar(1);

        // ASSERT
        Mockito.verify(caminhaoRepository, times(1)).save(any());
        Assertions.assertEquals(StatusGeral.INATIVO, caminhaoEntityMock.getStatus());
    }

    @Test
    public void deveListarComSucesso() {
        // SETUP
        List<CaminhaoEntity> listaCaminhao = List.of(
                getCaminhaoEntityMock(), getCaminhaoEntityMock(), getCaminhaoEntityMock());

        Mockito.when(caminhaoRepository.findAll()).thenReturn(listaCaminhao);

        // ACT
        List<CaminhaoDTO> listaCaminhaoDTO = caminhaoService.listar();

        // ASSERT
        Assertions.assertNotNull(listaCaminhaoDTO);
        Assertions.assertEquals(3, listaCaminhaoDTO.size());
    }

    @Test
    public void deveListarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCaminhaoEntityMock()));

        // ACT
        CaminhaoDTO caminhaoDTO = caminhaoService.listarPorId(1);

        // ASSERT
        Assertions.assertNotNull(caminhaoDTO);
        Assertions.assertEquals(1, caminhaoDTO.getIdCaminhao());
    }

    @Test
    public void deveBuscarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        Mockito.when(caminhaoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCaminhaoEntityMock()));

        // ACT
        CaminhaoEntity caminhao = caminhaoService.buscarPorId(1);

        // ASSERT
        Assertions.assertNotNull(caminhao);
        Assertions.assertEquals(1, caminhao.getIdCaminhao());
    }

    @Test
    public void deveListarCaminhoesLivresComSucesso() {
        // SETUP
        CaminhaoEntity caminhaoInativo = new CaminhaoEntity();
        caminhaoInativo.setStatus(StatusGeral.INATIVO);
        caminhaoInativo.setStatusCaminhao(StatusCaminhao.ESTACIONADO);

        CaminhaoEntity caminhaoIndisponivel = new CaminhaoEntity();
        caminhaoIndisponivel.setStatusCaminhao(StatusCaminhao.EM_VIAGEM);
        caminhaoIndisponivel.setStatus(StatusGeral.INATIVO);

        List<CaminhaoEntity> listaCaminhao = List.of(
                getCaminhaoEntityMock(), getCaminhaoEntityMock(), caminhaoInativo, caminhaoIndisponivel);

        Mockito.when(caminhaoRepository.findByStatusCaminhaoEquals(StatusCaminhao.ESTACIONADO))
                .thenReturn(listaCaminhao);

        // ACT
        List<CaminhaoDTO> listaCaminhaoDTOS = caminhaoService.listarCaminhoesLivres();

        // ASSERT
        Assertions.assertNotNull(listaCaminhaoDTOS);
        Assertions.assertEquals(2, listaCaminhaoDTOS.size());
    }

    @Test
    public void deveListarPorIdColaboradorComSucesso() throws RegraDeNegocioException {
        // SETUP
        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();

        Set<CaminhaoEntity> caminhaoEntities = new HashSet<>();
        caminhaoEntities.add(getCaminhaoEntityMock());
        caminhaoEntities.add(getCaminhaoEntityMock());

        usuarioEntityMock.setCaminhoes(caminhaoEntities);

        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioEntityMock);

        // ACT
        List<CaminhaoDTO> listaCaminhaoDTOS = caminhaoService.listarPorIdColaborador(1);

        // ASSERT
        Assertions.assertNotNull(listaCaminhaoDTOS);
        Assertions.assertEquals(2, listaCaminhaoDTOS.size());
    }

    @Test
    public void deveMudarStatusComSucesso() {
        // SETUP
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        CaminhaoEntity caminhaoMudado = new CaminhaoEntity();
        caminhaoMudado.setStatusCaminhao(StatusCaminhao.EM_VIAGEM);

        Mockito.when(caminhaoRepository.save(Mockito.any())).thenReturn(caminhaoMudado);

        // ACT
        caminhaoService.mudarStatus(caminhaoEntityMock, StatusCaminhao.EM_VIAGEM);

        // ASSERT
        Mockito.verify(caminhaoRepository, times(1)).save(Mockito.any());
        Assertions.assertEquals(StatusCaminhao.EM_VIAGEM, caminhaoMudado.getStatusCaminhao());
    }

    @Test
    public void deveListarAtivosComSucesso() {
        // SETUP
        CaminhaoEntity caminhaoInativo = new CaminhaoEntity();
        caminhaoInativo.setStatus(StatusGeral.INATIVO);

        List<CaminhaoEntity> listaCaminhao = List.of(
                getCaminhaoEntityMock(), getCaminhaoEntityMock(), caminhaoInativo);

        Mockito.when(caminhaoRepository.findAll()).thenReturn(listaCaminhao);

        // ACT
        List<CaminhaoDTO> listaCaminhaoDTOS = caminhaoService.listarAtivos();

        // ASSERT
        Assertions.assertNotNull(listaCaminhaoDTOS);
        Assertions.assertEquals(2, listaCaminhaoDTOS.size());
    }

    @Test
    public void deveListarInativosComSucesso() {
        // SETUP
        CaminhaoEntity caminhaoInativo = new CaminhaoEntity();
        caminhaoInativo.setUsuario(getUsuarioEntityMock());
        caminhaoInativo.setStatus(StatusGeral.INATIVO);

        List<CaminhaoEntity> listaCaminhao = List.of(
                getCaminhaoEntityMock(), getCaminhaoEntityMock(), caminhaoInativo);

        Mockito.when(caminhaoRepository.findAll()).thenReturn(listaCaminhao);

        // ACT
        List<CaminhaoDTO> listaCaminhaoDTOS = caminhaoService.listarInativos();

        // ASSERT
        Assertions.assertNotNull(listaCaminhaoDTOS);
        Assertions.assertEquals(1, listaCaminhaoDTOS.size());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarIdNaoEncontrado() throws RegraDeNegocioException {
        //SETUP
        Integer idProcurado = 5;

        //ACT
        caminhaoService.buscarPorId(idProcurado);
    }

    @NotNull
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

    @NotNull
    private static UsuarioEntity getUsuarioEntityMock() {
        UsuarioEntity usuarioMockado = new UsuarioEntity();
        usuarioMockado.setIdUsuario(1);
        usuarioMockado.setLogin("maicon");
        usuarioMockado.setSenha("abc123");
        usuarioMockado.setEmail("maicon@email.com");
        usuarioMockado.setNome("Maicon");
        usuarioMockado.setDocumento("12345678910");
        usuarioMockado.setStatus(StatusGeral.ATIVO);

        return usuarioMockado;
    }

    private static UsuarioDTO getUsuarioDTOMock() {
        UsuarioDTO usuarioDTOMockado = new UsuarioDTO();
        usuarioDTOMockado.setIdUsuario(1);
        usuarioDTOMockado.setLogin("maicon");
        usuarioDTOMockado.setEmail("maicon@email.com");
        usuarioDTOMockado.setNome("Maicon");
        usuarioDTOMockado.setDocumento("12345678910");
        usuarioDTOMockado.setStatus(StatusGeral.ATIVO);

        return usuarioDTOMockado;
    }


}
