package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.PostoDocumentDTO;
import br.com.logisticadbc.entity.mongodb.PostoDocument;
import br.com.logisticadbc.repository.PostoDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostoDocumentService {

    private final PostoDocumentRepository postoDocumentRepository;
    private final ObjectMapper objectMapper;

    public List<PostoDocumentDTO> listByCidade (String cidade) {
        List<PostoDocument> listaPostos = postoDocumentRepository.findByCidade(cidade);

        return listaPostos
                .stream()
                .map(postoDocument -> objectMapper.convertValue(postoDocument, PostoDocumentDTO.class))
                .toList();
    }
}
