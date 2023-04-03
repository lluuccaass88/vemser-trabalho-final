package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.LogRepository;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @InjectMocks
    private LogService logService;
    @Mock
    private LogRepository logRepository;
    @Mock
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(logService, "objectMapper", objectMapper);
    }
    @Test
    public void deveListarLogsComSucesso(){
        //SETUP
        Integer pagina = 0;
        Integer tamanho = 2;

        List<LogEntity> listaLog = List.of(
                getLogEntityMockado(), getLogEntityMockado(), getLogEntityMockado());

        Page<LogEntity> pageLog = new PageImpl<>(listaLog, PageRequest.of(pagina, tamanho), listaLog.size());

        when(logRepository.findAll(PageRequest.of(pagina, tamanho))).thenReturn(pageLog);

        //ACT
        PageDTO<LogDTO> logPaginadasDTO =  logService.listAllLogs(pagina, tamanho);

        //ASSERT
        assertNotNull(logPaginadasDTO);
        Assertions.assertEquals(pagina, logPaginadasDTO.getPagina());
        Assertions.assertEquals(tamanho, logPaginadasDTO.getTamanho());
    }

    @Test
    public void deveGerarLogComSucesso() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();

        //ACT
        logService.gerarLog(usuarioEntity.getLogin(), "Operação de Cadastrar Algo", TipoOperacao.CADASTRO);

        //ASSERT
        verify(logRepository, times(1)).save(any());
    }

    private LogEntity getLogEntityMockado() {
        LogEntity logEntity = new LogEntity();
        logEntity.setId("1");
        logEntity.setLoginOperador("fulano");
        logEntity.setDescricao("Operação de Cadastrar Algo");
        logEntity.setTipoOperacao(TipoOperacao.CADASTRO);
        return logEntity;
    }
    private UsuarioEntity getUsuarioEntityMockado() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setLogin("fulano");
        usuarioEntity.setSenha("fulano123");
        usuarioEntity.setNome("Fulano da Silva");
        usuarioEntity.setEmail("fulano@email.com");
        usuarioEntity.setDocumento("12345678910");
        usuarioEntity.setStatus(StatusGeral.ATIVO);
        return usuarioEntity;
    }
}
