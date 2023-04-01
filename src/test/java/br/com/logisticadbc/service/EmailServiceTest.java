package br.com.logisticadbc.service;


import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Null;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void deveEnviarEmailDeBoasVindasComSucesso() throws MessagingException, IOException, TemplateException, RegraDeNegocioException {
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
    public void deveFalharAoEnviarEmailDeBoasVindas() {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMockado();
        //ACT
        doNothing().when(emailSender).send(mimeMessage);
//        RegraDeNegocioException regraDeNegocioException =
//                assertThrows(RegraDeNegocioException.class,
//                        () -> emailService.enviarEmailBoasVindas(usuarioEntity));
//        assertTrue(regraDeNegocioException.getMessage().contains("Erro ao enviar email para o motorsita: "));
        verify(emailSender, times(0)).send(mimeMessage);
    }

    @Test
    public void deveEnviarEmailDeViagemComSucesso() {
        //SETUP
        //ACT
        //ASSERT
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
