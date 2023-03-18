package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
public interface MotoristaControllerDoc {
    @Operation(summary = "Listar motoristas", description = "Lista todos os motoristas do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de motoristas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<MotoristaDTO>> listAll();

    @Operation(summary = "Listar Motorista a partir de um id",
            description = "Lista um Motorista do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um Motorista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<MotoristaDTO> findById(@RequestParam("idMotorista") Integer idMotorista)
            throws RegraDeNegocioException;

    @Operation(summary = "Adicionar motorista", description = "Adicionar um motorista no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do motorista adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<MotoristaDTO> create(@Valid @RequestBody MotoristaCreateDTO motoristaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Edita motorista", description = "Edita um motorista no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o motorista editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    ResponseEntity<MotoristaDTO> update(@RequestParam Integer idUsuario,
                                               @Valid @RequestBody MotoristaUpdateDTO motoristaUpdateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar motorista", description = "Realiza um soft delete do motorista")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar motorista"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@RequestParam Integer idUsuario) throws RegraDeNegocioException;
}
