package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.impl.IUsuarioControllerDoc;
import br.com.logisticadbc.dto.ColaboradorDTO;
import br.com.logisticadbc.service.ColaboradorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colaborador") // http://localhost:8080/usuario
@Validated
@Slf4j
public class ColaboradorController { // implements IUsuarioControllerDoc {

    private final ColaboradorService colaboradorService;

/*    @GetMapping
    public List<ColaboradorDTO> list() {
        return colaboradorService.listarColaboradores();
    }*/
}