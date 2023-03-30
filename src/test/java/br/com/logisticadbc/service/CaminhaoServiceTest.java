package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
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
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class CaminhaoServiceTest {

    @InjectMocks
    private CaminhaoService caminhaoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CaminhaoRepository caminhaoRepository;

    @Mock
    private UsuarioService usuarioService;

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

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CaminhaoEntity caminhaoEntityMock = getCaminhaoEntityMock();

        Set<CaminhaoEntity> caminhaoEntities = new HashSet<>();
        caminhaoEntities.add(caminhaoEntityMock);
        usuarioEntityMock.setCaminhoes(caminhaoEntities);

        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioEntityMock);
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
    public void deveTestarAbastecerComSucesso() {
        // SETUP


        // ACT


        // ASSERT

    }

    @Test
    public void deveTestarRemoverComSucesso() {
        // SETUP


        // ACT


        // ASSERT

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
}
