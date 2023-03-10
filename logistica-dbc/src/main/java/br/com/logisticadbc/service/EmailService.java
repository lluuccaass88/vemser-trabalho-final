package br.com.logisticadbc.service;


import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
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

    private final Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;

    public void enviarEmailParaColaborador(UsuarioDTO usuario) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 1;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao DBC Logística - Painel de Colaborador");

//            mimeMessageHelper.setText("Olá " + usuario.getNome() + ", seja bem vindo ao sistema de logística da DBC Company. \n" +
//                    "Seu usuário é: " + usuario.getEmail() + " e sua senha é: " + usuario.getSenha() + "\n" +
//                    "Acesse o sistema através do link: http://localhost:4200/login", true);

            mimeMessageHelper.setText(getUsuarioTemplate(usuario, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar email para o colaborador: " + usuario.getNome());
        }
    }

    public void enviarEmailParaMotorista(UsuarioDTO usuario) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Integer op = 1;
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setSubject("Bem vindo ao DBC Logística - Painel de Motorista");

            mimeMessageHelper.setText(getUsuarioTemplate(usuario, op), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao enviar email para o motorsita: " + usuario.getNome());
        }
    }

    public String getUsuarioTemplate(UsuarioDTO usuario, Integer opc) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", "Sistema de Logística DBC Company");
        dados.put("usuario", usuario);

        switch (opc) {
            case 1:
                Template template1 = fmConfiguration.getTemplate("email-template-colaborador.ftl");
                String html1 = FreeMarkerTemplateUtils.processTemplateIntoString(template1, dados);
                return html1;
            case 2:
                Template template2 = fmConfiguration.getTemplate("email-template-motorista.ftl");
                String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(template2, dados);
                return html2;
            default:
                return "Opção inválida!";
        }
    }
}
