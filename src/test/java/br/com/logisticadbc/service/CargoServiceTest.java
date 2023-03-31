package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.CargosDeUsuarioDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
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

    @Test
    public void deveListarPorUsuarioComSucesso() throws RegraDeNegocioException {
        // SETUP
        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CargoEntity cargoEntityMock = getCargoEntityMock();

        Set<CargoEntity> cargoEntities = new HashSet<>();
        cargoEntities.add(cargoEntityMock);

        Set<UsuarioEntity> usuarioEntities = new HashSet<>();
        usuarioEntities.add(usuarioEntityMock);

        usuarioEntityMock.setCargos(cargoEntities);
        cargoEntityMock.setUsuarios(usuarioEntities);

        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt())).thenReturn(usuarioEntityMock);

        // ACT
        CargosDeUsuarioDTO cargosDeUsuarioDTO = cargoService.listarPorUsuario(1);

        // ASSERT
        Assertions.assertNotNull(cargosDeUsuarioDTO);
        Assertions.assertEquals(usuarioEntityMock.getIdUsuario(), cargosDeUsuarioDTO.getUsuario().getIdUsuario());
    }

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

    @NotNull
    private static CargoEntity getCargoEntityMock() {
        CargoEntity cargoMockado = new CargoEntity();
        cargoMockado.setIdCargo(1);
        cargoMockado.setNome("ROLE_ADMIN");

        return cargoMockado;
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
