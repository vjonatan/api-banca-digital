package api_banca_digital.mapper;

import api_banca_digital.dto.ClienteDTO;
import api_banca_digital.entidades.Cliente;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMapperImpl {

    public ClienteDTO mapearDeCliente(Cliente cliente){

        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);

        return clienteDTO;
    }

    public Cliente mapearDeClienteDTO(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);

        return cliente;
    }

}
