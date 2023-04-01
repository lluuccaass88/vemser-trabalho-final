package br.com.logisticadbc.service;

import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private final Configuration fmConfiguration;
    private final JavaMailSender emailSender;
    private static String NOME_LOG = "TruckLog";
    private static String EMAIL_LOG = "trucklog@email.com";

    public void enviarEmailBoasVindas(UsuarioEntity usuario) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao " + NOME_LOG);
            mimeMessageHelper.setText(getBoasVindasTemplate(usuario), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | NullPointerException e) {
            throw new RegraDeNegocioException("Erro ao enviar email.");
        }
    }

    private String getBoasVindasTemplate(UsuarioEntity usuario)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", usuario.getNome());
        dados.put("emailUsuario", usuario.getEmail());
        dados.put("cargoUsuario", usuario.getCargos());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-boas-vindas-usuario.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void enviarEmailViagem(RotaEntity rota, ViagemEntity viagem, UsuarioEntity motorista) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(motorista.getEmail());
            mimeMessageHelper.setSubject("Viagem Criada");

            String mensagem = "Foi atribuido a você uma nova viagem. Seguem os dados da viagem: ";

            mimeMessageHelper.setText(getViagemTemplate(rota, viagem, motorista.getNome(), mensagem), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException |  NullPointerException e) {
            throw new RegraDeNegocioException("Erro ao enviar email.");
        }
    }

    private String getViagemTemplate(RotaEntity rota, ViagemEntity viagem, String nomeUsuario,String mensagem)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", nomeUsuario);
        dados.put("mensagem", mensagem);
        dados.put("rota", rota);
        dados.put("viagem", viagem);
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-viagem.ftl");

        //html
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

}



