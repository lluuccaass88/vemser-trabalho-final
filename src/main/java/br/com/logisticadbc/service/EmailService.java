
package br.com.logisticadbc.service;

import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.RotaEntity;
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

    public void enviarEmailBoasVindasColabotador(ColaboradorEntity colaborador) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(colaborador.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao DBC Logística");

            String mensagem = "Contamos com a sua ajuda para cada vez mais aproximarmos pessoas por meio dos nossos serviços!";

            mimeMessageHelper.setText(getBoasVindasTemplate(colaborador.getEmail(), colaborador.getNome(), mensagem, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Erro ao enviar email para o motorsita: " + colaborador.getNome() + e.getMessage());
        }
    }

    public void enviarEmailBoasVindasMotorista(MotoristaEntity motorista) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(motorista.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao DBC Logística");

            String mensagem = "E isto não seria possivel sem a contribuição dos nossos motoristas, " +
                    "que são peça chave neste processo!";

            mimeMessageHelper.setText(getBoasVindasTemplate(motorista.getEmail(), motorista.getNome(), mensagem, op),
                    true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Erro ao enviar email para o motorsita: " +
                    motorista.getNome() + e.getMessage());
        }
    }

    private String getBoasVindasTemplate(String emailUsuario, String nomeUsuario, String mensagem, Integer op)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", nomeUsuario);
        dados.put("mensagem", mensagem);
        dados.put("emailUsuario", emailUsuario);
        dados.put("emailContato", "logistica.dbc@dbccompany.com.br");
        dados.put("nome", "Sistema de Logística DBC Company");

        Template template = fmConfiguration.getTemplate("email-template-boas-vindas-usuario.ftl");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void enviarEmailViagemMotorista(RotaEntity rota, MotoristaEntity motorista) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(motorista.getEmail());
            mimeMessageHelper.setSubject("Viagem Criada");

            String mensagem = "Foi atribuido a você uma nova viagem. Seguem os dados da viagem: ";

            mimeMessageHelper.setText(getViagemTemplate(rota, motorista.getNome(), mensagem, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Erro ao enviar email para o motorsita: " +
                    motorista.getNome() + e.getMessage());
        }
    }

    private String getViagemTemplate(RotaEntity rota, String nomeUsuario,String mensagem, Integer op)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", nomeUsuario);
        dados.put("mensagem", mensagem);
        dados.put("rota", rota);
        dados.put("emailContato", "logistica.dbc@dbccompany.com.br");
        dados.put("nome", "Sistema de Logística DBC Company");

        Template template = fmConfiguration.getTemplate("email-template-viagem.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

}



