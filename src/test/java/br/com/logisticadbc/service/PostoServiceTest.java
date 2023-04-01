package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.mongodb.PostoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostoServiceTest {

    @InjectMocks
    private PostoService postoService;
    @Mock
    private PostoRepository postoRepository;
    @Mock
    private GeoJsonPoint geoJsonPoint;
    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(postoService, "objectMapper", objectMapper);
//    }

    /*@Test
    public void deveCriarPostoComSucesso() throws RegraDeNegocioException {
        // SETUP
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto Ipiranga");
        postoCreateDTO.setCidade("Fortaleza");
        postoCreateDTO.setLongitude("3.123456");
        postoCreateDTO.setLatitude("4.123456");
        postoCreateDTO.setValorCombustivel(4.50);

        PostoEntity postoEntityMock = getPostoEntityMock();

        when(postoRepository.save(any())).thenReturn(postoEntityMock);

        // ACTION
        PostoDTO postoDTO = postoService.criar(postoCreateDTO);

        // ASSERT
        assertNotNull(postoDTO);
        assertEquals(postoCreateDTO.getNome(), postoDTO.getNome());
        assertEquals(postoCreateDTO.getCidade(), postoDTO.getCidade());
        assertEquals(postoCreateDTO.getValorCombustivel(), postoDTO.getValorCombustivel());
    }*/

    @Test
    public void deveAtualizarComSucesso() {
    }

    @Test
    public void deveListarComSucesso() {
        // SETUP
        List<PostoEntity> listaDePostoMockado = List.of(getPostoEntityMock());

        PostoDTO postoConvertidoDTO = getPostoDTOMock();

        when(postoRepository.findAll()).thenReturn(listaDePostoMockado);
        when(objectMapper.convertValue(any(), PostoDTO.class)).thenReturn(postoConvertidoDTO);


        // ACTION
        List<PostoDTO> listaDePostoRetornado = postoService.listar();

        // ASSERT
        assertNotNull(listaDePostoRetornado);
        assertEquals(1, listaDePostoRetornado.size());
    }

    @Test
    public void deveListarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
//        when(postoRepository.findById(anyString())).thenReturn(Optional.of(getPostoEntityMock()));

        // ACTION
//        PostoDTO postoRetornado = postoService.listarPorId("1");

        // ASSERT
//        assertNotNull(postoRetornado);
//        assertEquals("1", postoRetornado.getId());
    }


    //Testar deletar
    @Test
    public void deveDeletarComSucesso() throws RegraDeNegocioException {
        // SETUP
        String idPosto = "1";
        PostoEntity postoMockadoBanco = getPostoEntityMock();

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoMockadoBanco));
        when(postoRepository.save(any())).thenReturn(postoMockadoBanco);

        // ACTION
        postoService.deletar(idPosto);

        // ASSERT
        assertEquals(StatusGeral.INATIVO, postoMockadoBanco.getStatus());
    }
    @Test(expected = RegraDeNegocioException.class)
    public void deveDeletarComUsuarioInativo() throws RegraDeNegocioException {
        // SETUP
        String idPosto = "1";
        PostoEntity postoMockadoBanco = getPostoEntityMock();
        postoMockadoBanco.setStatus(StatusGeral.INATIVO);

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoMockadoBanco));

        // ACTION
        postoService.deletar(idPosto);

        // ASSERT
        assertEquals(StatusGeral.INATIVO, postoMockadoBanco.getStatus());
    }


    @NotNull
    private PostoEntity getPostoEntityMock() {
        PostoEntity postoMock = new PostoEntity();
        postoMock.setId("1");
        postoMock.setNome("Posto Ipiranga");
        postoMock.setCidade("Fortaleza");
        postoMock.setLocation(getGeoJsonPointMock());
        postoMock.setValorCombustivel(4.50);
        postoMock.setStatus(StatusGeral.ATIVO);
        return postoMock;
    }

    @NotNull
    private PostoDTO getPostoDTOMock() {
        PostoDTO postoTOMock = new PostoDTO();
        postoTOMock.setId("1");
        postoTOMock.setNome("Posto Ipiranga");
        postoTOMock.setCidade("Fortaleza");
        postoTOMock.setLocation(getGeoJsonPointMock());
        postoTOMock.setValorCombustivel(4.50);
        postoTOMock.setStatus(StatusGeral.ATIVO);
        return postoTOMock;
    }

    @NotNull
    private GeoJsonPoint getGeoJsonPointMock() {
        return new GeoJsonPoint(3.123456, 4.123456);
    }
}
