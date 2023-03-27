package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ViagemControllerDoc {
    @Operation(summary = "Listar Viagens", description = "Lista todas as viagens do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de Viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<ViagemDTO>> listAll() throws RegraDeNegocioException;


    @Operation(summary = "Listar Viagem a partir de um id",
            description = "Lista uma Viagem do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma Viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<ViagemDTO> findById(@RequestParam("idViagem") Integer idViagem)
            throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Viagem", description = "Adicionar uma viagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados da viagem adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ViagemDTO> create(@RequestParam("idMotorista") Integer idMotorista,
                                            @Valid @RequestBody ViagemCreateDTO viagemCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar Viagem", description = "Edita uma Viagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a Viagem editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<ViagemDTO> update(@RequestParam("idMotorista") Integer idMotorista,
                                            @RequestParam("idViagem") Integer idViagem,
                                            @Valid @RequestBody ViagemUpdateDTO viagemUpdateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Finalizar Viagem", description = "Realiza um soft delete da viagem")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@RequestParam("idMotorista") Integer idMotorista,
                                @RequestParam("idViagem") Integer idViagem)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar Viagens por motorista",
            description = "Lista todas as viagens de um motorista do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-motorista")
    ResponseEntity<List<ViagemDTO>> listByIdUser(@RequestParam("idMotorista") Integer idMotorista)
    throws RegraDeNegocioException;

    @Operation(summary = "Listar Viagens por rota",
            description = "Lista todas as viagens de uma rota do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-rota")
    ResponseEntity<List<ViagemDTO>> listByIdRote(@RequestParam(value = "idRota") Integer idRota)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar Viagens por caminhao",
            description = "Lista todas as viagens de um caminhao do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-caminhao")
    ResponseEntity<List<ViagemDTO>> listByIdTruck(@RequestParam(value = "idCaminhao") Integer idCaminhao)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar Viagens por status",
            description = "Lista todas as viagens de acordo com o status do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de viagens"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-status/paginacao")
    ResponseEntity<PageDTO<ViagemDTO>> findByStatusOrderByDataBegun(
            @RequestParam(value = "status") StatusViagem statusViagem ,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho);

}
