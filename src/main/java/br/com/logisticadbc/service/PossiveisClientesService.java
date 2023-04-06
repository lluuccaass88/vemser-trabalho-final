package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.kafka.PossiveisClientesDTO;
import br.com.logisticadbc.entity.mongodb.PossiveisClientesEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PossiveisClientesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PossiveisClientesService {

    @Value("${spring.mail.username}")
    private String emailAdmin;

    private final PossiveisClientesRepository possiveisClientesRepository;
    private final UsuarioService usuarioService;
    private final KafkaProdutorService kafkaProdutorService;
    private final ObjectMapper objectMapper;

    public void criar(String email, String nome) throws RegraDeNegocioException {
        boolean emailJaDeUsuario = usuarioService.listar()
                .stream()
                .anyMatch(usuario -> usuario.getEmail().equals(email));

        boolean emailJadePossivelCliente = possiveisClientesRepository.findByEmail(email).isEmpty();

        if (emailJaDeUsuario || !emailJadePossivelCliente) {
            throw new RegraDeNegocioException("Email já cadastrado no sistema.");
        }

        try {
            PossiveisClientesEntity possiveisClientes = new PossiveisClientesEntity();
            possiveisClientes.setEmail(email);
            possiveisClientes.setNome(nome);
            possiveisClientes.setData(LocalDate.now());

            possiveisClientesRepository.save(possiveisClientes);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    @Scheduled(cron = "0 0 * * * *") // todo dia a meia noite
    public void enviarPossiveisClientesParaAdmin() throws JsonProcessingException {
        // verifica se tem dados do dia
        List<PossiveisClientesEntity> listaPossiveisClientesDoDia =
                possiveisClientesRepository.findByData(LocalDate.now());

        if (listaPossiveisClientesDoDia.isEmpty()) {
           // não manda email pro admin
        } else {

            List<PossiveisClientesDTO> listaPossiveisClientesDTOS = listaPossiveisClientesDoDia
                    .stream()
                    .map(cliente -> objectMapper.convertValue(cliente, PossiveisClientesDTO.class))
                    .toList();
            kafkaProdutorService.enviarEmailAdminPossiveisClientes(listaPossiveisClientesDTOS);
        }
    }
}
