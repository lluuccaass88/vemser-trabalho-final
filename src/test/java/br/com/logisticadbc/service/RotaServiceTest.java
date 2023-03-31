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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @Mock
    private LogService logService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(rotaService, "objectMapper", objectMapper);
    }

    @Test
    public void deveCriarComSucesso() throws RegraDeNegocioException {
        //Setup
        Integer idUsuario = 1;
        RotaCreateDTO novaRota = new RotaCreateDTO(
                "Rota de Brasilia até São Paulo",
                "Brasilia",
                "São Paulo"
        );

        RotaEntity rotaMockadoDoBanco = getRotaEntityMock();
        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

        Set<RotaEntity> rotaEntities = new HashSet<>();
        rotaEntities.add(getRotaEntityMock());
        rotaEntities.add(getRotaEntityMock());
        usuarioMockadoBanco.setRotas(rotaEntities);

        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioMockadoBanco);

        when(rotaRepository.save(any())).thenReturn(rotaMockadoDoBanco);

        //Action
        RotaDTO rotaRetornada = rotaService.criar(idUsuario, novaRota);

        //Assert
        assertNotNull(rotaRetornada);
        Assertions.assertEquals(novaRota.getDescricao(), rotaRetornada.getDescricao());
        Assertions.assertEquals(novaRota.getLocalPartida(), rotaRetornada.getLocalPartida());
        Assertions.assertEquals(novaRota.getLocalDestino(), rotaRetornada.getLocalDestino());
        Assertions.assertEquals(StatusGeral.ATIVO, rotaRetornada.getStatus());
    }

    @Test
    public void deveListarRotasComSucesso(){
        // SETUP

        List<RotaEntity> listaRota = List.of(
                getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());

        when(rotaRepository.findAll()).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaDTO = rotaService.listar();

        // ASSERT
        Assertions.assertNotNull(listaRotaDTO);
        Assertions.assertEquals(3, listaRotaDTO.size());
    }

    @Test
    public void deveListarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer idRota = 1;

        RotaEntity rotaMockadoDoBanco = getRotaEntityMock();

        when(rotaRepository.findById(anyInt())).thenReturn(Optional.of(rotaMockadoDoBanco));

        // ACT
        RotaDTO rotaRetornadaDTO = rotaService.listarPorId(idRota);

        // ASSERT
        Assertions.assertNotNull(rotaRetornadaDTO);
        Assertions.assertEquals(idRota, rotaRetornadaDTO.getIdRota());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarListarPorIdSemEncontrarId() throws RegraDeNegocioException {
        // SETUP
        Integer idRota = 1;

        RotaEntity rotaMockadoDoBanco = new RotaEntity();

        when(rotaRepository.findById(anyInt())).thenReturn(Optional.of(rotaMockadoDoBanco));

        // ACT
        RotaDTO rotaRetornadaDTO = rotaService.listarPorId(idRota);

        // ASSERT
        Assertions.assertNotNull(rotaRetornadaDTO);
        Assertions.assertEquals(idRota, rotaRetornadaDTO.getIdRota());
    }

    @Test
    public void deveListarPorLocalDePartidaComSucesso() throws RegraDeNegocioException {
        // SETUP
        String localPartida = "Brasilia";

        List<RotaEntity> listaRota = List.of(
                getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());

        when(rotaRepository.findBylocalPartidaIgnoreCase(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarPorLocalPartida(localPartida);

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(localPartida, listaRotaRetornadaDTO.get(0).getLocalPartida()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(3, listaRotaRetornadaDTO.size());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarLocalDePartidaNaoEncontrado() throws RegraDeNegocioException {
        // SETUP
        String localPartida = "Brasilia";

        List<RotaEntity> listaRota = List.of();

        when(rotaRepository.findBylocalPartidaIgnoreCase(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarPorLocalPartida(localPartida);

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(localPartida, listaRotaRetornadaDTO.get(0).getLocalPartida()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(3, listaRotaRetornadaDTO.size());
    }

    @Test
    public void deveListarPorLocalDeDestinoComSucesso() throws RegraDeNegocioException {
        // SETUP
        String localDestino = "São Paulo";

        List<RotaEntity> listaRota = List.of(
                getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());
        when(rotaRepository.findBylocalDestinoIgnoreCase(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarPorLocalDestino(localDestino);

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(localDestino, listaRotaRetornadaDTO.get(0).getLocalDestino()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(3, listaRotaRetornadaDTO.size());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarLocalDeDestinoNaoEncontrado() throws RegraDeNegocioException {
        // SETUP
        String localDestino = "São Paulo";

        List<RotaEntity> listaRota = List.of();

        when(rotaRepository.findBylocalPartidaIgnoreCase(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarPorLocalDestino(localDestino);

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(localDestino, listaRotaRetornadaDTO.get(0).getLocalPartida()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(3, listaRotaRetornadaDTO.size());
    }
    @Test
    public void deveListarRotasAtivasComSucesso() throws RegraDeNegocioException {
        // SETUP
        List<RotaEntity> listaRota = List.of(
                getRotaEntityMock(), getRotaEntityMock(), getRotaEntityMock());

        when(rotaRepository.findByStatusEquals(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarRotasAtivas();

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(StatusGeral.ATIVO, listaRotaRetornadaDTO.get(0).getStatus()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(3, listaRotaRetornadaDTO.size());
    }

    @Test
    public void deveListarRotasInativasComSucesso() throws RegraDeNegocioException {
        // SETUP
        RotaEntity rotaInativaMockadoDoBanco = getRotaEntityMock();
        rotaInativaMockadoDoBanco.setStatus(StatusGeral.INATIVO);

        List<RotaEntity> listaRota = List.of(
                rotaInativaMockadoDoBanco);

        when(rotaRepository.findByStatusEquals(any())).thenReturn(listaRota);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarRotasInativas();

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(StatusGeral.INATIVO, listaRotaRetornadaDTO.get(0).getStatus()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(1, listaRotaRetornadaDTO.size());
    }

    @Test
    public void deveListarPorIdDoColaboradorComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer idColaborador = 1;
        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

        Set<RotaEntity> rotaEntities = new HashSet<>();
        rotaEntities.add(getRotaEntityMock());
        rotaEntities.add(getRotaEntityMock());

        usuarioMockadoBanco.setRotas(rotaEntities);

        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioMockadoBanco);

        // ACT
        List<RotaDTO> listaRotaRetornadaDTO = rotaService.listarPorIdColaborador(idColaborador);

        // ASSERT
        Assertions.assertNotNull(listaRotaRetornadaDTO);
        Assertions.assertEquals(idColaborador, listaRotaRetornadaDTO.get(0).getIdUsuario()); //TODO DESCOBRIR COMO TESTAR QUANDO VOLTA UMA LISTA
        Assertions.assertEquals(2, listaRotaRetornadaDTO.size());
//        Assertions.assertIterableEquals();
    }

    //Buscar por id testes
    @Test
    public void deveBuscarPorIdComSucesso() throws RegraDeNegocioException {
        Integer idRota = 1;
        RotaEntity rotaMockadoDoBanco = getRotaEntityMock();
        when(rotaRepository.findById(anyInt())).thenReturn(Optional.of(rotaMockadoDoBanco));

        // ACT
        RotaEntity rotaRetornada = rotaService.buscarPorId(idRota);

        // ASSERT
        Assertions.assertNotNull(rotaRetornada);
        Assertions.assertEquals(idRota, rotaRetornada.getIdRota());
    }


    //Testes deletar
    @Test
    public void deveTestarDeletar() throws RegraDeNegocioException {
        // SETUP
        int idRota = 1;

        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

        Set<RotaEntity> rotaEntities = new HashSet<>();
        rotaEntities.add(getRotaEntityMock());
        rotaEntities.add(getRotaEntityMock());
        usuarioMockadoBanco.setRotas(rotaEntities);

        RotaEntity rotaInativa = new RotaEntity();
        rotaInativa.setStatus(StatusGeral.INATIVO);

        Mockito.when(rotaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getRotaEntityMock()));
        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioMockadoBanco);

        Mockito.when(rotaRepository.save(Mockito.any())).thenReturn(rotaInativa);

        // ACT
        rotaService.deletar(idRota);

        // ASSERT
        Assertions.assertEquals(StatusGeral.INATIVO, rotaInativa.getStatus());
//        Assertions.assertEquals(idRota, rotaInativa.getIdRota());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeletarComRotaInativa() throws RegraDeNegocioException{
        // SETUP
        int idRota = 6;

        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

        Set<RotaEntity> rotaEntities = new HashSet<>();
        rotaEntities.add(getRotaEntityMock());
        rotaEntities.add(getRotaEntityMock());
        usuarioMockadoBanco.setRotas(rotaEntities);

        RotaEntity rotaInativa = new RotaEntity();
        rotaInativa.setStatus(StatusGeral.INATIVO);

        Mockito.when(rotaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(rotaInativa));
        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioMockadoBanco);
        Mockito.when(rotaRepository.save(Mockito.any())).thenReturn(rotaInativa);

        // ACT
        rotaService.deletar(idRota);

        // ASSERT
        Assertions.assertEquals(StatusGeral.INATIVO, rotaInativa.getStatus());
//        Assertions.assertEquals(idRota, rotaInativa.getIdRota());
    }

    //Testes editar
    @Test
    public void deveTestarEditar() throws RegraDeNegocioException {
        // SETUP
        int idRota = 1;
        RotaCreateDTO rotaEditada = new RotaCreateDTO(
                "Rota de Salvador até São Paulo",
                "Salvador",
                "São Paulo"
        );


        UsuarioEntity usuarioMockadoBanco = new UsuarioEntity();

        Set<RotaEntity> rotaEntities = new HashSet<>();
        rotaEntities.add(getRotaEntityMock());
        rotaEntities.add(getRotaEntityMock());
        usuarioMockadoBanco.setRotas(rotaEntities);

        when(rotaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getRotaEntityMock()));
        when(usuarioService.buscarPorId(anyInt())).thenReturn(usuarioMockadoBanco);

        when(rotaRepository.save(any())).thenReturn(getRotaEntityMock());

        // ACT
        RotaDTO rotaEditadaDTO = rotaService.editar(idRota, rotaEditada);

        // ASSERT
        assertNotNull(rotaEditadaDTO);
        Assertions.assertEquals("Salvador", rotaEditadaDTO.getLocalPartida());
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

}
