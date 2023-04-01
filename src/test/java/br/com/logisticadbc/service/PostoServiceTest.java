package br.com.logisticadbc.service;

<<<<<<< HEAD
=======
import br.com.logisticadbc.dto.in.PostoCreateDTO;
>>>>>>> 5a20e6cb3a348856f79c4f38f5ba526f5ed53d1b
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.mongodb.PostoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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
<<<<<<< HEAD
    private ObjectMapper objectMapper = new ObjectMapper();

//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(postoService, "objectMapper", objectMapper);
//    }

    /*@Test
=======
    private ObjectMapper objectMapper;

    @Test
>>>>>>> 5a20e6cb3a348856f79c4f38f5ba526f5ed53d1b
    public void deveCriarPostoComSucesso() throws RegraDeNegocioException {
        // SETUP
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto Ipiranga");
        postoCreateDTO.setCidade("Fortaleza");
        postoCreateDTO.setLongitude("3.123456");
        postoCreateDTO.setLatitude("4.123456");
        postoCreateDTO.setValorCombustivel(4.50);

        PostoEntity postoEntityMock = getPostoEntityMock();

        when(objectMapper.convertValue(postoCreateDTO, PostoEntity.class)).thenReturn(postoEntityMock);
        when(postoRepository.save(any())).thenReturn(postoEntityMock);
        when(objectMapper.convertValue(postoEntityMock, PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACTION
        PostoDTO postoDTO = postoService.criar(postoCreateDTO);

        // ASSERT
        assertNotNull(postoDTO);
        assertEquals(postoCreateDTO.getNome(), postoDTO.getNome());
        assertEquals(postoCreateDTO.getCidade(), postoDTO.getCidade());
        assertEquals(postoCreateDTO.getValorCombustivel(), postoDTO.getValorCombustivel());
    }

   /* @Test
    public void deveEditarComSucesso() throws RegraDeNegocioException {
        //SETUP
        String id = "1";
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto");
        postoCreateDTO.setCidade("Caucaia");
        postoCreateDTO.setLongitude("3.12345");
        postoCreateDTO.setLatitude("4.123455");
        postoCreateDTO.setValorCombustivel(4.00);

        PostoEntity postoEntityMock = getPostoEntityMock();

        PostoDTO postoDTOEditado = new PostoDTO(postoEntityMock.getId(), postoEntityMock.getNome(), postoEntityMock.getLocation(),
                postoEntityMock.getCidade(), postoEntityMock.getValorCombustivel(),
                postoEntityMock.getStatus());

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));
        when(postoRepository.save(any())).thenReturn(postoEntityMock);
        when(objectMapper.convertValue(postoEntityMock, PostoDTO.class)).thenReturn(postoDTOEditado);

        // ACT
        PostoDTO postoDTO = postoService.editar("id", postoCreateDTO);

        //ASSERT
        assertEquals(postoCreateDTO.getNome(), postoDTO.getNome());
        assertEquals(postoCreateDTO.getValorCombustivel(), postoDTO.getValorCombustivel());
        assertEquals(postoCreateDTO.getCidade(), postoDTO.getCidade());
        assertEquals(postoCreateDTO.getLongitude(), postoDTO.getLocation().getX());
        assertEquals(postoCreateDTO.getLatitude(), postoDTO.getLocation().getY());
    }*/

    @Test
    public void deveListarComSucesso() {
        // SETUP
<<<<<<< HEAD
        List<PostoEntity> listaDePostoMockado = List.of(getPostoEntityMock());

        PostoDTO postoConvertidoDTO = getPostoDTOMock();

        when(postoRepository.findAll()).thenReturn(listaDePostoMockado);
        when(objectMapper.convertValue(any(), PostoDTO.class)).thenReturn(postoConvertidoDTO);
=======
        List<PostoEntity> listaDePostoMockado = List.of(getPostoEntityMock(), getPostoEntityMock());

        when(postoRepository.findAll()).thenReturn(listaDePostoMockado);
        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());
>>>>>>> 5a20e6cb3a348856f79c4f38f5ba526f5ed53d1b


        // ACTION
        List<PostoDTO> listaDePostoRetornado = postoService.listar();

        // ASSERT
        assertNotNull(listaDePostoRetornado);
<<<<<<< HEAD
        assertEquals(1, listaDePostoRetornado.size());
=======
        assertEquals(2, listaDePostoRetornado.size());
>>>>>>> 5a20e6cb3a348856f79c4f38f5ba526f5ed53d1b
    }

    @Test
    public void deveListarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        String id = "1";
        PostoEntity postoEntityMock = getPostoEntityMock();

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));
        when(objectMapper.convertValue(postoEntityMock, PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACTION
        PostoDTO postoDTO = postoService.listarPorId(id);

        // ASSERT
        assertNotNull(postoDTO);
        assertEquals(id, postoDTO.getId());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharListarPorId() throws RegraDeNegocioException {
        // SETUP
        String id = "2";

        // ACTION
        postoService.listarPorId(id);
    }

    @Test
    public void deveBuscarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        String id = "1";
        PostoEntity postoEntityMock = getPostoEntityMock();

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));

        // ACTION
        PostoEntity postoEntity = postoService.buscarPorId(id);

        // ASSERT
        assertNotNull(postoEntity);
        assertEquals(id, postoEntity.getId());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharBuscarPorId() throws RegraDeNegocioException {
        // SETUP
        String id = "2";

        // ACTION
        postoService.buscarPorId(id);
    }


    //Testar deletar
    @Test
    public void deveDeletarComSucesso() throws RegraDeNegocioException {
<<<<<<< HEAD
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
=======
        PostoEntity postoEntityMock = getPostoEntityMock();

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));

        postoService.deletar("1");

        Mockito.verify(postoRepository, times(1)).save(any());
        assertEquals(StatusGeral.INATIVO, postoEntityMock.getStatus());
>>>>>>> 5a20e6cb3a348856f79c4f38f5ba526f5ed53d1b
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeletarInativo() throws RegraDeNegocioException {
        PostoEntity postoEntityMock = getPostoEntityMock();
        postoEntityMock.setStatus(StatusGeral.INATIVO);

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));

        postoService.deletar("1");
    }

    @Test
    public void deveListarAtivosComSucesso() {
        // SETUP

        List<PostoEntity> postoEntityList = List.of(
                getPostoEntityMock() , getPostoEntityMock());

        when(postoRepository.findByStatusEquals(any())).thenReturn(postoEntityList);
        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listarPostosAtivos();

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(2, postoDTOS.size());
    }

    @Test
    public void deveListarInativosComSucesso() {
        // SETUP
        PostoEntity postoEntityMock = getPostoEntityMock();
        postoEntityMock.setStatus(StatusGeral.INATIVO);

        List<PostoEntity> postoEntityList = List.of(postoEntityMock, postoEntityMock);

        when(postoRepository.findByStatusEquals(any())).thenReturn(postoEntityList);
        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listarPostosInativos();

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(2, postoDTOS.size());
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

    private PostoDTO getPostoDTOMock() {
        PostoDTO postoDTO = new PostoDTO();
        postoDTO.setId("1");
        postoDTO.setNome("Posto Ipiranga");
        postoDTO.setCidade("Fortaleza");
        postoDTO.setLocation(getGeoJsonPointMock());
        postoDTO.setValorCombustivel(4.50);
        postoDTO.setStatus(StatusGeral.ATIVO);
        return postoDTO;
    }
}
