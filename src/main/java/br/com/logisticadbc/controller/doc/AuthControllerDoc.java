package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.LoginDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

public interface AuthControllerDoc {

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "202", description = "Retorna token"),
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

    @Operation(summary = "Recuperar senha",
            description = "Envia e-mail para recuperar acesso")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna status"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("recuperar-senha")
    public ResponseEntity<Void> recoverPassword(@Email @RequestParam("emailUsuario") String emailUsuario)
            throws RegraDeNegocioException;
}
