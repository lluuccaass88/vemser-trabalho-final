package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RotaServiceTest {

    @InjectMocks
    private RotaService rotaService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RotaRepository rotaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(rotaService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarCriarComSucesso() throws RegraDeNegocioException {
        Integer idUsiaruio = 1;
        RotaCreateDTO novaRota = new RotaCreateDTO(
                "Rota de Brasilia até São Paulo",
                "Brasilia",
                "São Paulo"
        );

        RotaEntity rotaMockadoDoBanco = getRotaEntityMock();

        RotaDTO rotaDTO = new RotaDTO();

//        UsuarioEntity mock = org.mockito.Mockito.mock(UsuarioEntity.class);
//        mock.getRotas().add(getRotaEntityMock());

        Set<RotaEntity> listaRotasEmUsuario = Set.of(getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());


        UsuarioEntity usuarioRetordadoDaBusca = new UsuarioEntity();
        usuarioRetordadoDaBusca.setIdUsuario(1);

        //rotaDTO.setIdRota(1);
        rotaDTO.setDescricao("Rota de Brasilia até São Paulo");
        rotaDTO.setLocalPartida("Brasilia");
        rotaDTO.setLocalDestino("São Paulo");

        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioRetordadoDaBusca);

        /*when(usuarioRetordadoDaBusca.getRotas()).thenReturn(listaRotasEmUsuario); *///TODO DESCOBRIR COMO TESTA O RELACIONAMENTO DAS CLASSES
//        when(usuarioRetordadoDaBusca.getRotas().add(any())).thenReturn(usuarioRetordadoDaBusca.getRotas().add(getRotaEntityMock()));

        when(rotaRepository.save(any())).thenReturn(rotaMockadoDoBanco);

        RotaDTO rotaRetornada = rotaService.criar(idUsiaruio, novaRota);

        assertNotNull(rotaRetornada);
    }


    @Test
    public void deveTestarListarRotasComSucesso() throws RegraDeNegocioException {
        // SETUP
        List<RotaEntity> listaDeRotas = List.of(getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());

        RotaEntity rota = new RotaEntity();

        when(rotaRepository.findAll()).thenReturn(listaDeRotas);

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        when(rota.getUsuario()).thenReturn(usuarioEntity);

        // ACT
        List<RotaDTO> lista = rotaService.listar();

        // ASSERT
        assertNotNull(lista);
        assertEquals(3, lista.size());
    }



    @NotNull
    private static RotaEntity getRotaEntityMock() {
        RotaEntity rotaMockadaDoBanco = new RotaEntity();
        rotaMockadaDoBanco.setIdRota(1);
        rotaMockadaDoBanco.setStatus(StatusGeral.ATIVO);
        rotaMockadaDoBanco.setDescricao("Rota de Brasilia até São Paulo");
        rotaMockadaDoBanco.setLocalPartida("Brasilia");
        rotaMockadaDoBanco.setLocalDestino("São Paulo");
        return rotaMockadaDoBanco;
    }

}
