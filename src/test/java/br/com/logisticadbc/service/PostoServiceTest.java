package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
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
    @Mock
    private LogService logService;
    @Mock
    private UsuarioService usuarioService;

    @Test
    public void deveCriarPostoComSucesso() throws RegraDeNegocioException {
        // SETUP
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto Ipiranga");
        postoCreateDTO.setCidade("Fortaleza");
        postoCreateDTO.setLongitude("3.123456");
        postoCreateDTO.setLatitude("4.123456");
        postoCreateDTO.setValorCombustivel(4.50);

        PostoEntity postoEntityMock = getPostoEntityMock();
        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        when(objectMapper.convertValue(postoCreateDTO, PostoEntity.class)).thenReturn(postoEntityMock);
        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
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

    @Test
    public void deveEditarComSucesso() throws RegraDeNegocioException {
        //SETUP
        String id = "1";
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto");
        postoCreateDTO.setCidade("Caucaia");
        postoCreateDTO.setLongitude("3.12345");
        postoCreateDTO.setLatitude("4.12345");
        postoCreateDTO.setValorCombustivel(4.00);

        PostoEntity postoEntityMock = getPostoEntityMock();
        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        PostoDTO postoDTOEditado = new PostoDTO(
                postoEntityMock.getId(), postoCreateDTO.getNome(), postoEntityMock.getLocation(),
                postoCreateDTO.getCidade(), postoCreateDTO.getValorCombustivel(),
                postoEntityMock.getStatus());

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));
        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
        when(postoRepository.save(any())).thenReturn(postoEntityMock);
        when(objectMapper.convertValue(postoEntityMock, PostoDTO.class)).thenReturn(postoDTOEditado);

        // ACT
        PostoDTO postoDTO = postoService.editar("id", postoCreateDTO);

        //ASSERT
        assertEquals(postoCreateDTO.getNome(), postoDTO.getNome());
        assertEquals(postoCreateDTO.getValorCombustivel(), postoDTO.getValorCombustivel());
        assertEquals(postoCreateDTO.getCidade(), postoDTO.getCidade());
        assertEquals(postoCreateDTO.getLongitude(), String.valueOf(postoDTO.getLocation().getX()));
        assertEquals(postoCreateDTO.getLatitude(), String.valueOf(postoDTO.getLocation().getY()));
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarEditarInativo() throws RegraDeNegocioException {
        //SETUP
        String id = "1";
        PostoCreateDTO postoCreateDTO = new PostoCreateDTO();
        postoCreateDTO.setNome("Posto");
        postoCreateDTO.setCidade("Caucaia");
        postoCreateDTO.setLongitude("3.12345");
        postoCreateDTO.setLatitude("4.12345");
        postoCreateDTO.setValorCombustivel(4.00);

        PostoEntity postoEntityMock = getPostoEntityMock();
        postoEntityMock.setStatus(StatusGeral.INATIVO);

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));
        when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);

        // ACT
        PostoDTO postoDTO = postoService.editar("id", postoCreateDTO);
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
        // SETUP
        String idPosto = "1";
        PostoEntity postoMockadoBanco = getPostoEntityMock();

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
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

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        Mockito.when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoMockadoBanco));

        // ACTION
        postoService.deletar(idPosto);

        // ASSERT
        assertEquals(StatusGeral.INATIVO, postoMockadoBanco.getStatus());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeletarInativo() throws RegraDeNegocioException {
        PostoEntity postoEntityMock = getPostoEntityMock();
        postoEntityMock.setStatus(StatusGeral.INATIVO);

        UsuarioDTO usuarioDTOMockadoBanco = getUsuarioDTOMock();

        when(usuarioService.getLoggedUser()).thenReturn(usuarioDTOMockadoBanco);
        when(postoRepository.findById(anyString())).thenReturn(Optional.of(postoEntityMock));

        postoService.deletar("1");
    }

    @Test
    public void deveListarAtivosComSucesso() {
        // SETUP

        List<PostoEntity> postoEntityList = List.of(
                getPostoEntityMock() , getPostoEntityMock());

        when(postoRepository.findByStatusEquals(any())).thenReturn(postoEntityList);
//        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

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
//        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listarPostosInativos();

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(2, postoDTOS.size());
    }

    //Testar Listar tudo
    @Test
    public void deveListarComSucesso() {
        // SETUP

        List<PostoEntity> postoEntityList = List.of(
                getPostoEntityMock() , getPostoEntityMock());

        when(postoRepository.findAll()).thenReturn(postoEntityList);
//        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listar();

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(2, postoDTOS.size());
    }

    //Testar Listar por cidade
    @Test
    public void deveListarPorCidadeComSucesso() {
        // SETUP
        String cidade = "Brasilia";

        PostoEntity postoMockadoBanco = getPostoEntityMock();
        postoMockadoBanco.setCidade(cidade);

        List<PostoEntity> postoEntityList = List.of(
                postoMockadoBanco , postoMockadoBanco);

        when(postoRepository.findByCidadeIgnoreCase(anyString())).thenReturn(postoEntityList);
//        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listByCidade(cidade);

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(2, postoDTOS.size());
    }

    //Testar listar por localização
    @Test
    public void deveListarPorLocalizacao() {
        // SETUP
        String longitude = "923902";
        String latitude = "923902";
        Double distancia = 1.33;

        List<PostoEntity> postoEntityList = List.of(
                getPostoEntityMock() , getPostoEntityMock(), getPostoEntityMock());


        when(postoRepository.findByLocationNear(any(), any())).thenReturn(postoEntityList);
//        when(objectMapper.convertValue(getPostoEntityMock(), PostoDTO.class)).thenReturn(getPostoDTOMock());

        // ACT
        List<PostoDTO> postoDTOS = postoService.listarPorLocalizacao(longitude, latitude, distancia);

        // ASSERT
        Assertions.assertNotNull(postoDTOS);
        Assertions.assertEquals(3, postoDTOS.size());
    }

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

    private GeoJsonPoint getGeoJsonPointMock() {
        return new GeoJsonPoint(3.12345, 4.12345);
    }
}
