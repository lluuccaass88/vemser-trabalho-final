package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface LogControllerDoc {
    @Operation(summary = "Listar Logs", description = "Lista todos os logs da aplicação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<PageDTO<LogDTO>> listByRole(
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) throws RegraDeNegocioException;
}
