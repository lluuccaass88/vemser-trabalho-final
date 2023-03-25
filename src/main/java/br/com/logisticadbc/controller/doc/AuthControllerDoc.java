package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.LoginDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthControllerDoc {

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna token"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException;

    @Operation(summary = "Retornar usuário logado",
            description = "Retorna usuário logado no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-logado")
    public ResponseEntity<UsuarioDTO> findLoggedUser() throws RegraDeNegocioException;
}
