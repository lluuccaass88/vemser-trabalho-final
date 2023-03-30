package br.com.logisticadbc.service;

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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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


    @NotNull
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

    @NotNull
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
