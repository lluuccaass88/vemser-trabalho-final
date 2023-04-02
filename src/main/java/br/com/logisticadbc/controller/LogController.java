package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.LogControllerDoc;
import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.LogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Log")
@RequestMapping("/log")
public class LogController implements LogControllerDoc {

    private final LogService logService;
    @GetMapping
    public ResponseEntity<PageDTO<LogDTO>> listByRole(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(
                logService.listAllLogs(pagina, tamanho), HttpStatus.OK);
    }
}
