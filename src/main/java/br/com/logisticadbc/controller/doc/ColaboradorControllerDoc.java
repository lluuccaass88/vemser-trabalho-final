package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

// classe responsável por documentar o controlador de usuários e definir os métodos que serão implementados
public interface ColaboradorControllerDoc {

    @Operation(summary = "Adicionar colaborador", description = "Adicionar um colaborador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do colaborador adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ColaboradorDTO> create(@Valid @RequestBody ColaboradorCreateDTO usuario) throws Exception;


    @Operation(summary = "Listar Colaboradores", description = "Lista todos os Usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de Colaboradores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ColaboradorDTO>> listAll() throws Exception;

    @Operation(summary = "Edita colaborador", description = "Edita um colaborador no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o colaborador atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ColaboradorDTO> update(@RequestParam("idUsuario") Integer idUsuario,
                                                 @Valid @RequestBody ColaboradorUpdateDTO colaboradorUpdateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deletar colaborador", description = "Realiza um soft delete do colaborador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception;
}
