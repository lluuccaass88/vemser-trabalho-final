package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.out.PostoDocumentDTO;
import br.com.logisticadbc.service.PostoDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Localização")
@RequestMapping("/postos-por-cidade")
public class PostoDocumentController {

    private final PostoDocumentService postoDocumentService;

    @GetMapping
    public ResponseEntity<List<PostoDocumentDTO>> listByCity(@RequestParam String cidade) {
        return new ResponseEntity<>(postoDocumentService.listByCidade(cidade), HttpStatus.OK);
    }
}
