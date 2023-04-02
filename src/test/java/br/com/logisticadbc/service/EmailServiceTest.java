package br.com.logisticadbc.service;


import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private Configuration fmConfiguration;
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private Template template;

    @Before
    public void setUp() throws IOException {
        ReflectionTestUtils.setField(emailService, "from", "fulano@email.com");
        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
    }

    @Test
    public void deveEnviarEmailDeBoasVindasComSucesso() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        //ACT
        emailService.enviarEmailBoasVindas(usuarioEntity);
        //ASSERT
        verify(emailSender).send(mimeMessage);
        verify(emailSender).createMimeMessage();
        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharAoEnviarEmailDeBoasVindas() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();

        //ACT
        emailService.enviarEmailBoasVindas(usuarioEntity);

        verify(emailSender, times(0)).send((MimeMessage) Mockito.any());
    }

    @Test
    public void deveEnviarEmailDeViagemComSucesso() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();
        RotaEntity rotaEntity = getRotaEntityMockado();
        ViagemEntity viagemEntity = getViagemEntityMockado();

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        //ACT
        emailService.enviarEmailViagem(rotaEntity, viagemEntity, usuarioEntity);
        //ASSERT
        verify(emailSender).send(mimeMessage);
        verify(emailSender).createMimeMessage();
        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharAoEnviarEmailDeViagem() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();
        RotaEntity rotaEntity = getRotaEntityMockado();
        ViagemEntity viagemEntity = getViagemEntityMockado();

        //ACT
        emailService.enviarEmailViagem(rotaEntity, viagemEntity, usuarioEntity);

        verify(emailSender, times(0)).send((MimeMessage) Mockito.any());
    }

    @Test
    public void deveEnviarEmailDePossivelClienteComSucesso() throws RegraDeNegocioException {
        //SETUP
        String email = "email@gmail.com";
        String nome = "Maicon";

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        //ACT
        emailService.enviarEmailPossivelCliente(email, nome);

        //ASSERT
        verify(emailSender).send(mimeMessage);
        verify(emailSender).createMimeMessage();
        verify(emailSender, times(1)).send(mimeMessage);
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharAoEnviarEmailDePossivelCliente() throws RegraDeNegocioException {
        //SETUP
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();
        RotaEntity rotaEntity = getRotaEntityMockado();
        ViagemEntity viagemEntity = getViagemEntityMockado();

        String email = "email@gmail.com";
        String nome = "Maicon";

        emailService.enviarEmailPossivelCliente(email, nome);
    }

    @Test
    public void deveEnviarEmailRecuperarSenhaComSucesso() throws RegraDeNegocioException {
        //SETUP
        String senhaTemporaria = "Sjhkjs232";
        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMockado();

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        //ACT
        emailService.enviarEmailRecuperarSenha(usuarioMockadoBanco, senhaTemporaria);

        //ASSERT
        verify(emailSender).send(mimeMessage);
        verify(emailSender).createMimeMessage();
        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveFalharAoEmailRecuperarSenha() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioMockadoBanco = getUsuarioEntityMockado();
        String senhaTemporaria = "lkksjlsLKJ1";
        //ACT
        emailService.enviarEmailRecuperarSenha(usuarioMockadoBanco, senhaTemporaria);

        verify(emailSender, times(0)).send((MimeMessage) Mockito.any());
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

    private RotaEntity getRotaEntityMockado() {
        RotaEntity rotaEntity = new RotaEntity();
        rotaEntity.setIdRota(1);
        rotaEntity.setDescricao("Rota de São Paulo até Brasília");
        rotaEntity.setLocalPartida("São Paulo");
        rotaEntity.setLocalDestino("Brasília");
        rotaEntity.setStatus(StatusGeral.ATIVO);
        return rotaEntity;
    }

    private ViagemEntity getViagemEntityMockado() {
        ViagemEntity viagemEntity = new ViagemEntity();
        viagemEntity.setIdViagem(1);
        viagemEntity.setDescricao("viagem longa com 2 paradas");
        viagemEntity.setDataInicio(LocalDate.parse("2020-03-03"));
        viagemEntity.setDataFim(LocalDate.parse("2020-03-04"));
        viagemEntity.setStatusViagem(StatusViagem.EM_ANDAMENTO);
        return viagemEntity;
    }
}
