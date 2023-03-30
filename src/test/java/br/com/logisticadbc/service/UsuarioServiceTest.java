package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.UsuarioRepository;
import br.com.logisticadbc.security.TokenService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Before
    public void init() {
        // Configurações do ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }


    @Test
    public void deveCriarComSucesso() {
    }

    @Test
    public void deveEditarComSucesso() {
    }

    @Test
    public void deveDeletarComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuarioInativo = new UsuarioEntity();
        usuarioInativo.setStatus(StatusGeral.INATIVO);

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(getUsuarioEntityMock()));
        when(usuarioRepository.save(usuarioInativo)).thenReturn(usuarioInativo);

        usuarioService.deletar(1);

        assertEquals(StatusGeral.INATIVO, usuarioInativo.getStatus());
    }

    @Test
    public void deveListarTodosComSucesso() {
        List<UsuarioEntity> listaUsuarios = List.of(getUsuarioEntityMock());
        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        List<UsuarioDTO> listaUsuariosDTO = usuarioService.listar();

        assertNotNull(listaUsuariosDTO);
        assertEquals(1, listaUsuariosDTO.size());
    }

    @Test
    public void deveListarPorIDComSucesso() throws RegraDeNegocioException {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(getUsuarioEntityMock()));

        UsuarioDTO usuarioDTO = usuarioService.listarPorId(1);

        assertNotNull(usuarioDTO);
        assertEquals(1, usuarioDTO.getIdUsuario());
    }

    @Test
    public void deveListarPorCargoComSucesso() {
    }

    @Test
    public void deveListarPorCargoEStatus() {
    }

    @Test
    public void deveGerarRelatorioCompleto() {
    }

    @Test
    public void deveListarMotoristasLivres() {
    }

    @Test
    public void deveBuscarPorIDComSucesso() throws RegraDeNegocioException {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(getUsuarioEntityMock()));

        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(1);

        assertNotNull(usuarioEntity);
        assertEquals(1, usuarioEntity.getIdUsuario());
    }

    @Test
    public void deveBuscarPorLogin() {
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(getUsuarioEntityMock()));

        Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorLogin("maicon");

        assertNotNull(usuarioEntity);
        assertEquals("maicon", usuarioEntity.get().getLogin());
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