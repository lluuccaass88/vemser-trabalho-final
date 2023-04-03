package br.com.logisticadbc.service;


import br.com.logisticadbc.dto.in.LoginDTO;
import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CargoEntity;
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
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
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

    @Mock
    private LogService logService;

    @Mock
    private CargoService cargoService;

    @Mock
    private EmailService emailService;


    @Before
    public void init() {
        // Configurações do ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }


    @Test
    public void deveCriarComSucesso() throws RegraDeNegocioException {
        // SETUP
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setLogin("maicon");
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setEmail("maicon@email.com");
        usuarioCreateDTO.setNome("Maicon");
        usuarioCreateDTO.setDocumento("12345678910");
        usuarioCreateDTO.setNomeCargo("ROLE_ADMIN");

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CargoEntity cargoMock = getCargoAdminMock();

        when(cargoService.buscarPorNome(anyString())).thenReturn(cargoMock);
        when(passwordEncoder.encode(anyString())).thenReturn(usuarioEntityMock.getSenha());
        when(usuarioRepository.save(any())).thenReturn(usuarioEntityMock);

        // ACT
        UsuarioDTO usuarioDTO = usuarioService.criar(usuarioCreateDTO);

        // ASSERT
        Mockito.verify(emailService, times(1)).enviarEmailBoasVindas(any());
        assertNotNull(usuarioDTO);
        assertEquals(usuarioEntityMock.getIdUsuario(), usuarioDTO.getIdUsuario());
        assertEquals(usuarioEntityMock.getNome(), usuarioDTO.getNome());
        assertEquals(usuarioEntityMock.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioEntityMock.getLogin(), usuarioDTO.getLogin());
        assertEquals(usuarioEntityMock.getDocumento(), usuarioDTO.getDocumento());
        assertEquals(usuarioEntityMock.getCargos().contains(cargoMock), usuarioDTO.getCargos().contains(cargoMock));
    }

    @Test
    public void deveEditarSemParametroComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = null;
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        usuarioUpdateDTO.setNome("Joao");
        usuarioUpdateDTO.setDocumento("12345678901");
        usuarioUpdateDTO.setEmail("joao@email.com");
        usuarioUpdateDTO.setSenha("abc");

        UsuarioEntity usuarioLogadoMock = getUsuarioEntityMock();
        CargoEntity cargoMock = getCargoAdminMock();
        usuarioLogadoMock.getCargos().add(cargoMock);

        getSecurityContextMock();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioLogadoMock));
        when(passwordEncoder.encode(anyString())).thenReturn(usuarioUpdateDTO.getSenha());
        when(usuarioRepository.save(any())).thenReturn(usuarioLogadoMock);

        //ACT
        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuarioUpdateDTO);

        //ASSERT
        assertNotNull(usuarioDTO);
        assertEquals(usuarioLogadoMock.getNome(), usuarioDTO.getNome());
        assertEquals(usuarioLogadoMock.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioLogadoMock.getDocumento(), usuarioDTO.getDocumento());
        assertEquals(usuarioUpdateDTO.getSenha(), usuarioLogadoMock.getSenha());
    }

    @Test
    public void deveEditarComParametroComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 2;
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        usuarioUpdateDTO.setNome("Joao");
        usuarioUpdateDTO.setDocumento("12345678901");
        usuarioUpdateDTO.setEmail("joao@email.com");
        usuarioUpdateDTO.setSenha("abc");

        UsuarioEntity usuarioAdmin = getUsuarioEntityMock();
        CargoEntity cargoMock = getCargoAdminMock();
        usuarioAdmin.getCargos().add(cargoMock);

        UsuarioEntity usuarioMotorista = getUsuarioEntityMotoMock();
        CargoEntity cargoMotoMock = getCargoMotoMock();
        usuarioMotorista.getCargos().add(cargoMotoMock);

        getSecurityContextMock();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioAdmin), Optional.of(usuarioMotorista));
        when(passwordEncoder.encode(anyString())).thenReturn(usuarioUpdateDTO.getSenha());
        when(usuarioRepository.save(any())).thenReturn(usuarioMotorista);

        //ACT
        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuarioUpdateDTO);

        //ASSERT
        assertNotNull(usuarioDTO);
        assertEquals(usuarioMotorista.getNome(), usuarioDTO.getNome());
        assertEquals(usuarioMotorista.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioMotorista.getDocumento(), usuarioDTO.getDocumento());
        assertEquals(usuarioUpdateDTO.getSenha(), usuarioMotorista.getSenha());
    }

    @Test
    public void deveDeletarComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntityMotoMock = getUsuarioEntityMotoMock();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMotoMock));

        usuarioService.deletar(2);

        Mockito.verify(usuarioRepository, times(1)).save(any());
        assertEquals(StatusGeral.INATIVO, usuarioEntityMotoMock.getStatus());
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
        String cargo = "ROLE_ADMIN";
        Integer pagina = 0;
        Integer tamanho = 2;

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CargoEntity cargoAdminMock = getCargoAdminMock();
        usuarioEntityMock.getCargos().add(cargoAdminMock);

        List<UsuarioEntity> listaUsuarios = List.of(usuarioEntityMock);

        Page<UsuarioEntity> pageUsuario =
                new PageImpl<>(listaUsuarios, PageRequest.of(pagina, tamanho), listaUsuarios.size());

        when(usuarioRepository.findByCargoUsuario(any(), anyString())).thenReturn(pageUsuario);

        PageDTO<UsuarioDTO> usuarioDTOPaginados = usuarioService.listarPorCargo(cargo, pagina, tamanho);

        assertNotNull(usuarioDTOPaginados);
        Assertions.assertEquals(pagina, usuarioDTOPaginados.getPagina());
        Assertions.assertEquals(tamanho, usuarioDTOPaginados.getTamanho());
        Assertions.assertEquals(1, usuarioDTOPaginados.getElementos().size());
    }

    @Test
    public void deveListarPorCargoEStatus() {
        String cargo = "ROLE_ADMIN";
        Integer pagina = 0;
        Integer tamanho = 2;

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        CargoEntity cargoAdminMock = getCargoAdminMock();
        usuarioEntityMock.getCargos().add(cargoAdminMock);

        UsuarioEntity usuarioEntityMock2 = getUsuarioEntityMock();
        usuarioEntityMock.getCargos().add(cargoAdminMock);

        List<UsuarioEntity> listaUsuarios =
                List.of(usuarioEntityMock, usuarioEntityMock2);

        Page<UsuarioEntity> pageUsuario =
                new PageImpl<>(listaUsuarios, PageRequest.of(pagina, tamanho), listaUsuarios.size());

        when(usuarioRepository.findByCargosAndStatus(any(), anyString(), any())).thenReturn(pageUsuario);

        PageDTO<UsuarioDTO> usuarioDTOPaginados =
                usuarioService.listarPorCargoEStatus(cargo, StatusGeral.ATIVO, pagina, tamanho);

        assertNotNull(usuarioDTOPaginados);
        Assertions.assertEquals(pagina, usuarioDTOPaginados.getPagina());
        Assertions.assertEquals(tamanho, usuarioDTOPaginados.getTamanho());
        Assertions.assertEquals(2, usuarioDTOPaginados.getElementos().size());

    }

    @Test
    public void deveListarMotoristasLivres() {
        Integer pagina = 0;
        Integer tamanho = 2;

        List<UsuarioEntity> listaUsuarios = List.of(getUsuarioEntityMock());
        Page<UsuarioEntity> pageUsuario =
                new PageImpl<>(listaUsuarios, PageRequest.of(pagina, tamanho), listaUsuarios.size());

        when(usuarioRepository.findByMotoristasLivres(any())).thenReturn(pageUsuario);

        PageDTO<UsuarioDTO> usuarioDTOPaginados = usuarioService.listarMotoristasLivres(pagina, tamanho);

        assertNotNull(usuarioDTOPaginados);
        Assertions.assertEquals(pagina, usuarioDTOPaginados.getPagina());
        Assertions.assertEquals(tamanho, usuarioDTOPaginados.getTamanho());
        Assertions.assertEquals(1, usuarioDTOPaginados.getElementos().size());
    }

    @Test
    public void deveBuscarPorIDComSucesso() throws RegraDeNegocioException {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(getUsuarioEntityMock()));

        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(1);

        assertNotNull(usuarioEntity);
        assertEquals(1, usuarioEntity.getIdUsuario());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarIdNaoEncontrado() throws RegraDeNegocioException {
        //SETUP
        Integer idProcurado = 5;

        //ACT
        usuarioService.buscarPorId(idProcurado);
    }

    @Test
    public void deveBuscarPorLogin() {
        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(getUsuarioEntityMock()));

        Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorLogin("maicon");

        assertNotNull(usuarioEntity);
        assertEquals("maicon", usuarioEntity.get().getLogin());
    }

    @Test
    public void deveAutenticarComSucesso() {
    }

    @Test
    public void deveRetornargetIdLoggedUser() {
        UsuarioEntity usuarioLogado = getUsuarioEntityMock();

        getSecurityContextMock();

        Integer idLoggedUser = usuarioService.getIdLoggedUser();

        assertNotNull(idLoggedUser);
        assertEquals(1, idLoggedUser);
    }

    @Test
    public void deveRetornargetLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = getUsuarioEntityMock();

        getSecurityContextMock();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioLogado));

        UsuarioDTO usuarioDTO = usuarioService.getLoggedUser();

        assertNotNull(usuarioDTO);
    }
//    @SneakyThrows
    @Test
    public void deveRetornarAtivoComSucesso() throws RegraDeNegocioException {
        // Setup
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("maicon");
        loginDTO.setSenha("abc123");

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuarioEntityMock));

        // Action
        usuarioService.ativo(loginDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveRetornarInativo() throws RegraDeNegocioException {
        // Setup
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("maicon");
        loginDTO.setSenha("abc123");

        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        usuarioEntityMock.setStatus(StatusGeral.INATIVO);

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuarioEntityMock));

        // Action
        usuarioService.ativo(loginDTO);
    }

    //Testar atualzar senha
    @Test
    public void deveRecuperarSenhaComSucesso() throws RegraDeNegocioException {
        // Setup
        String email = "lucas.email@dbccompany.com.br";
        String senhaAleatoria = "KJKsjs231w";

        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

        when(usuarioRepository.findByEmail(anyString())).thenReturn(usuarioMockadoBanco);
        when(passwordEncoder.encode(anyString())).thenReturn(usuarioMockadoBanco.getSenha());

        // Action
        usuarioService.recuperarSenha(email);

        // ASSERT
        Mockito.verify(emailService, times(1)).enviarEmailRecuperarSenha(any(), any());
        Mockito.verify(usuarioRepository, times(1)).save(any());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestatRecuperarSenhaComEmailNaoCadastradoNoSistema() throws RegraDeNegocioException {
        // Setup
        String email = "lucas.email@dbccompany.com.br";

        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();
        usuarioMockadoBanco.setStatus(StatusGeral.INATIVO);

        when(usuarioRepository.findByEmail(anyString())).thenReturn(usuarioMockadoBanco);
//        when(passwordEncoder.encode(anyString())).thenReturn(usuarioMockadoBanco.getSenha());

        // Action
        usuarioService.recuperarSenha(email);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestatRecuperarSenhaComUsuarioInativo() throws RegraDeNegocioException {
        // Setup
        String email = null;

        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMock();

//        when(usuarioRepository.findByEmail(anyString())).thenReturn(usuarioMockadoBanco);
//        when(passwordEncoder.encode(anyString())).thenReturn(usuarioMockadoBanco.getSenha());

        // Action
        usuarioService.recuperarSenha(email);
    }

    @Test
    public void deveEnviarEmailAoClienteComSucesso() throws RegraDeNegocioException {
        // Setup
        String email = "lucas.email@dbccompany.com.br";
        String nome = "Maicon";

        // Action
        usuarioService.enviarEmailInteresseCliente(email, nome);

        // ASSERT
        Mockito.verify(emailService, times(1)).enviarEmailPossivelCliente(anyString(), anyString());
    }


    private static UsuarioEntity getUsuarioEntityMock() {
        UsuarioEntity usuarioMockado = new UsuarioEntity();
        usuarioMockado.setIdUsuario(1);
        usuarioMockado.setLogin("maicon");
        usuarioMockado.setSenha("abc123");
        usuarioMockado.setEmail("maicon@email.com");
        usuarioMockado.setNome("Maicon");
        usuarioMockado.setDocumento("12345678910");
        usuarioMockado.setStatus(StatusGeral.ATIVO);

        Set<CargoEntity> cargoEntitySet = new HashSet<>();
        usuarioMockado.setCargos(cargoEntitySet);

        return usuarioMockado;
    }

    private static UsuarioEntity getUsuarioEntityMotoMock() {
        UsuarioEntity usuarioMockado = new UsuarioEntity();
        usuarioMockado.setIdUsuario(2);
        usuarioMockado.setLogin("rafa");
        usuarioMockado.setSenha("abc123");
        usuarioMockado.setEmail("rafa@email.com");
        usuarioMockado.setNome("Rafa");
        usuarioMockado.setDocumento("12345678911");
        usuarioMockado.setStatus(StatusGeral.ATIVO);

        Set<CargoEntity> cargoEntitySet = new HashSet<>();
        usuarioMockado.setCargos(cargoEntitySet);

        return usuarioMockado;
    }

    private static CargoEntity getCargoAdminMock() {
        CargoEntity cargoMockado = new CargoEntity();
        cargoMockado.setIdCargo(1);
        cargoMockado.setNome("ROLE_ADMIN");

        Set<UsuarioEntity> usuarioEntitySet = new HashSet<>();
        cargoMockado.setUsuarios(usuarioEntitySet);

        return cargoMockado;
    }
    private static CargoEntity getCargoMotoMock() {
        CargoEntity cargoMockado = new CargoEntity();
        cargoMockado.setIdCargo(2);
        cargoMockado.setNome("ROLE_MOTORISTA");

        Set<UsuarioEntity> usuarioEntitySet = new HashSet<>();
        cargoMockado.setUsuarios(usuarioEntitySet);

        return cargoMockado;
    }

    private static void getSecurityContextMock() {
        Integer idUsuarioToken = 1;
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(idUsuarioToken);
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
}