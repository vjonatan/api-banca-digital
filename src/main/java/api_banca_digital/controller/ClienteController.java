package api_banca_digital.controller;

import api_banca_digital.dto.ClienteDTO;
import api_banca_digital.entidades.Cliente;
import api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    CuentaBancariaService cuentaBancariaService;

    @GetMapping("/clientes")
    public List<ClienteDTO> getClientes() {
        return cuentaBancariaService.listClientes();
    }


}
