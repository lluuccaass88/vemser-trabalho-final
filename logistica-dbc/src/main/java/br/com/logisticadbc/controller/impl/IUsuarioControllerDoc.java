package br.com.logisticadbc.controller.impl;

import br.com.logisticadbc.dto.UsuarioCreateDTO;
import br.com.logisticadbc.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

// classe responsável por documentar o controlador de usuários e definir os métodos que serão implementados
public interface IUsuarioControllerDoc {

    // EXEMPLO
    @Operation(summary = "Adicionar Usuários", description = "Adicionar um usuário do tipo Colaborador ou Motorista")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do Usuário adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<UsuarioDTO> adicionar(@Valid @RequestBody UsuarioCreateDTO usuario) throws Exception;


    @Operation(summary = "Listar Usuários", description = "Lista todos os Usuários do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<UsuarioDTO>> listar() throws Exception;

    @Operation(summary = "Atualizar Usuário", description = "Atualiza uma Usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o Usuário atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<UsuarioDTO> editar(@PathVariable Integer id, @Valid @RequestBody UsuarioCreateDTO usuario) throws Exception;


    @Operation(summary = "Deletar Usuário", description = "Deleta um Usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Informa que deu certo deletar um Usuário no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws Exception;
}
