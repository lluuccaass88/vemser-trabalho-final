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
        Integer op = 2;
        try {
            String mensagem = null;
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao " + NOME_LOG);

            mimeMessageHelper.setText(getBoasVindasTemplate(usuario, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException | NullPointerException e) {
            throw new RegraDeNegocioException("Erro ao enviar email para o motorsita: " + usuario.getNome() + e.getMessage());
        }
    }

    private String getBoasVindasTemplate(UsuarioEntity usuario, Integer op)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", usuario.getNome());
        dados.put("emailUsuario", usuario.getEmail());
        dados.put("cargoUsuario", usuario.getCargos());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-boas-vindas-usuario.ftl");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void enviarEmailViagem(RotaEntity rota, ViagemEntity viagem, UsuarioEntity motorista) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(motorista.getEmail());
            mimeMessageHelper.setSubject("Viagem Criada");

            String mensagem = "Foi atribuido a você uma nova viagem. Seguem os dados da viagem: ";

            mimeMessageHelper.setText(getViagemTemplate(rota, viagem, motorista.getNome(), mensagem, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException |  NullPointerException e) {
            throw new RegraDeNegocioException("Erro ao enviar email para o motorista: " +
                    motorista.getNome() + e.getMessage());
        }
    }

    private String getViagemTemplate(RotaEntity rota, ViagemEntity viagem, String nomeUsuario,String mensagem, Integer op)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeUsuario", nomeUsuario);
        dados.put("mensagem", mensagem);
        dados.put("rota", rota);
        dados.put("viagem", viagem);
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-viagem.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void enviarEmailRecuperarSenha(UsuarioEntity usuario, String senhaTemporaria) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Recuperação de senha");

            mimeMessageHelper.setText(geRecuperarSenhaTemplate(usuario, senhaTemporaria), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar email de recuperação de senha");
        }
    }

    private String geRecuperarSenhaTemplate(UsuarioEntity usuario, String senhaTemporaria)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("senhaTemporaria", senhaTemporaria);
        dados.put("usuarioNome", usuario.getNome());
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-recupera-senha.ftl"); //TODO trocar o template
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void enviarEmailPossivelCliente(String email, String nome) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 2;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Contato com o clinte");

            mimeMessageHelper.setText(geEmailPossivelClienteTemplate(email, nome), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar email de recuperação de senha");
        }
    }

    private String geEmailPossivelClienteTemplate(String email, String nome)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();
        dados.put("usuarioNome", nome);
        dados.put("usuarioEmail", email);
        dados.put("emailContato", EMAIL_LOG);
        dados.put("nome", NOME_LOG);

        Template template = fmConfiguration.getTemplate("email-template-email-possivel-cliente.ftl"); //TODO trocar o template
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

}



