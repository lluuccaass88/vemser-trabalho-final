package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CargoRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Before
    public void init() {
        // Configurações do ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(cargoService, "objectMapper", objectMapper);
    }

//    @Test
//    public void deveListarPorUsuarioComSucesso() throws RegraDeNegocioException {
//        // SETUP
//        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
//        CargoEntity cargoEntityMock = getCargoEntityMock();
//
//        Set<CargoEntity> cargoEntities = new HashSet<>();
//        cargoEntities.add(cargoEntityMock);
//
//        Set<UsuarioEntity> usuarioEntities = new HashSet<>();
//        usuarioEntities.add(usuarioEntityMock);
//
//        usuarioEntityMock.setCargos(cargoEntities);
//        cargoEntityMock.setUsuarios(usuarioEntities);
//
//        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioEntityMock);
//
//        // ACT
//        CargosDeUsuarioDTO cargosDeUsuarioDTO = cargoService.listarPorUsuario(1);
//
//        // ASSERT
//        Assertions.assertNotNull(cargosDeUsuarioDTO);
//        Assertions.assertEquals(usuarioEntityMock.getIdUsuario(), cargosDeUsuarioDTO.getUsuario().getIdUsuario());
//    }

    //Testa criar
    @Test
    public void deveTestarCriar() throws RegraDeNegocioException {
        //Setup
        CargoCreateDTO novoCargo = new CargoCreateDTO(
                "ROLE_BACKEND"
        );

        CargoEntity cargoMockadoDoBanco = getCargoEntityMock();

        when(cargoRepository.save(any())).thenReturn(cargoMockadoDoBanco);

        //Action
        CargoDTO cargoSalvo = cargoService.criar(novoCargo);

        //Assert
        assertNotNull(cargoSalvo);
        Assertions.assertEquals(novoCargo.getNome(), cargoSalvo.getNome());
    }

    //Testar editar
    @Test
    public void deveTestarEditar() throws RegraDeNegocioException {
        //Setup
        Integer idCargo = 1;
        CargoCreateDTO ediatadoCargo = new CargoCreateDTO(
                "ROLE_QA"
        );

        CargoEntity cargoMockadoDoBanco = getCargoEntityMock();

        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(cargoMockadoDoBanco));
        when(cargoRepository.save(any())).thenReturn(cargoMockadoDoBanco);

        //Action
        CargoDTO cargoEditado = cargoService.editar(idCargo, ediatadoCargo);

        //Assert
        assertNotNull(cargoEditado);
        Assertions.assertEquals(ediatadoCargo.getNome(), cargoEditado.getNome());
    }

    //Testa cadastrar Usuario em cargo
    @Test
    public void deveTestarCadatrarusuarioEmRota() throws RegraDeNegocioException {
        //Setup
        Integer idUsuario = 1;
        Integer idCargo = 1;

        Set<CargoEntity> listaCargo = new HashSet<>();
        Set<UsuarioEntity> listaUsuario = new HashSet<>();

        CargoEntity cargoMockadoDoBanco = getCargoEntityMock();
        cargoMockadoDoBanco.setUsuarios(listaUsuario);
        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();
        usuarioMockadoBanco.setCargos(listaCargo);

        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setNome(cargoMockadoDoBanco.getNome());

        Set<CargoDTO> listaCargoDTO = new HashSet<>();
        listaCargoDTO.add(getCargoDTOMock());

        UsuarioDTO usuarioEncontradoDTO = getUsuarioDTOMock();
        usuarioEncontradoDTO.setCargos(listaCargoDTO);


        Set<CargoDTO> listaCargoEsperadoDTO = new HashSet<>();
        listaCargoDTO.add(getCargoDTOMock());

        UsuarioDTO usuarioEsperadoDTO = getUsuarioDTOMock();
        usuarioEsperadoDTO.setCargos(listaCargoEsperadoDTO);

        when(cargoRepository.findById(any())).thenReturn(Optional.of(cargoMockadoDoBanco));
        when(usuarioService.buscarPorId(any())).thenReturn(usuarioMockadoBanco);
        when(usuarioService.listarPorId(anyInt())).thenReturn(usuarioEncontradoDTO);

        //Action
        UsuarioDTO usuarioRelacionadoComCargo = cargoService.cadastrarUsuario(idCargo, idUsuario);

        //Assert
        assertNotNull(usuarioRelacionadoComCargo);
        Assertions.assertEquals(1, usuarioRelacionadoComCargo.getCargos().size());
    }

    //Testat listar
    @Test
    public void deveListarComSucesso() {
        // SETUP
        List<CargoEntity> listaCargo = List.of(
                getCargoEntityMock(), getCargoEntityMock(), getCargoEntityMock());

        Mockito.when(cargoRepository.findAll()).thenReturn(listaCargo);

        // ACT
        List<CargoDTO> cargoDTOS = cargoService.listar();

        // ASSERT
        Assertions.assertNotNull(cargoDTOS);
        Assertions.assertEquals(3, cargoDTOS.size());

    }

    //Testar listar por id
    @Test
    public void deveListarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        Mockito.when(cargoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCargoEntityMock()));

        // ACT
        CargoDTO cargoDTO = cargoService.listarPorId(1);

        // ASSERT
        Assertions.assertNotNull(cargoDTO);
        Assertions.assertEquals(1, cargoDTO.getIdCargo());
    }

    //Testar buscar por id
    @Test
    public void deveBuscarPorIdComSucesso() throws RegraDeNegocioException {
        // SETUP
        Mockito.when(cargoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCargoEntityMock()));

        // ACT
        CargoEntity cargoEntity = cargoService.buscarPorId(1);

        // ASSERT
        Assertions.assertNotNull(cargoEntity);
        Assertions.assertEquals(1, cargoEntity.getIdCargo());
    }

    //Testar buscar por nome
    @Test
    public void deveBuscarPorNomeSucesso() throws RegraDeNegocioException {
        // SETUP
        String nome = "ROLE_ADMIN";
        Mockito.when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(getCargoEntityMock()));

        // ACT
        CargoEntity cargoRetornado = cargoService.buscarPorNome(nome);

        // ASSERT
        Assertions.assertNotNull(cargoRetornado);
        Assertions.assertEquals(nome, cargoRetornado.getNome());
    }
    @NotNull
    private static CargoEntity getCargoEntityMock() {
        CargoEntity cargoMockado = new CargoEntity();
        cargoMockado.setIdCargo(1);
        cargoMockado.setNome("ROLE_ADMIN");

        return cargoMockado;
    }

    @NotNull
    private static CargoDTO getCargoDTOMock() {
        CargoDTO cargoDTOMockado = new CargoDTO();
        cargoDTOMockado.setIdCargo(1);
        cargoDTOMockado.setNome("ROLE_ADMIN");

        return cargoDTOMockado;
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

    @NotNull
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
