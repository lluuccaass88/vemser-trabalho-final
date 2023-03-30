package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostoServiceTest {

    @InjectMocks
    private PostoService postoService;
    @Mock
    private PostoRepository postoRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(postoService, "objectMapper", objectMapper);
    }

    // TODO - Implementar testes unitários (CRIAR, EDITAR, DELETAR, LISTAR TODOS, LISTAR POR ID, LISTAR POSTOS ATIVOS
    //  LISTAR POSTOS INATIVOS, LISTAR POSTOS POR CIDADE, BUSCAR POR ID)
    // FIXME - SABER A DIFERENÇA ENTRE BUSCAR POR ID E LISTAR POR ID , POIS LISTAR IMPLEMENTA BUSCAR POR ID ?

    @Test
    public void deveCriarPostoComSucesso() throws RegraDeNegocioException {
        // SETUP
//        PostoCreateDTO novoPosto = new PostoCreateDTO();
//        novoPosto.setNome("Posto Ipiranga");
//        novoPosto.setCidade("Fortaleza");
//        novoPosto.setLatitude("3.123456");
//        novoPosto.setLongitude("4.123456");
//        novoPosto.setValorCombustivel(4.50);

//        PostoEntity postoMockado = getPostoEntityMock();

//        when(postoRepository.save(any())).thenReturn(postoMockado);
        // ACTION
//        PostoDTO postoRetornado = postoService.criar(novoPosto);

        // ASSERT
//        assertNotNull(postoRetornado);
//        assertEquals(novoPosto.getNome(), postoRetornado.getNome());
//        assertEquals(novoPosto.getCidade(), postoRetornado.getCidade());
//        assertEquals(novoPosto.getLatitude(), postoRetornado.getLocation().getCoordinates().toString());
//        assertEquals(novoPosto.getLongitude(), postoRetornado.getLocation().getCoordinates().toString());
//        assertEquals(novoPosto.getValorCombustivel(), postoRetornado.getValorCombustivel());
//        assertEquals("1", postoRetornado.getId());
    }

    @Test
    public void deveAtualizarComSucesso(){}

    @Test
    public void deveListarComSucesso() {
        // SETUP
//        List<PostoEntity> listaDePostoMockado = List.of(getPostoEntityMock());
//        when(postoRepository.findAll()).thenReturn(listaDePostoMockado);

        // ACTION
//        List<PostoDTO> listaDePostoRetornado = postoService.listar();

        // ASSERT
//        assertNotNull(listaDePostoRetornado);
//        assertEquals(1, listaDePostoRetornado.size());
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

    @Test
    public void deveDeletarComSucesso() throws RegraDeNegocioException {
        // SETUP
        PostoEntity postoInativo = new PostoEntity();
        postoInativo.setStatus(StatusGeral.INATIVO);

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(getPostoEntityMock()));
        when(postoRepository.save(any())).thenReturn(postoInativo);
        // ACTION
        postoService.deletar("1");
        // ASSERT
        assertEquals(StatusGeral.INATIVO, postoInativo.getStatus());
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
    private GeoJsonPoint getGeoJsonPointMock() {
        PostoCreateDTO postoComCordenadas = new PostoCreateDTO();
        postoComCordenadas.setLatitude("3.123456");
        postoComCordenadas.setLongitude("4.123456");
        GeoJsonPoint locationPoint = new GeoJsonPoint(
                Double.parseDouble(postoComCordenadas.getLongitude()),
                Double.parseDouble(postoComCordenadas.getLatitude()));
        return locationPoint;
    }
}
