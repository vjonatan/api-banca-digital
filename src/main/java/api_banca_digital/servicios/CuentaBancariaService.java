package api_banca_digital.servicios;

import api_banca_digital.dto.ClienteDTO;
import api_banca_digital.entidades.Cliente;
import api_banca_digital.entidades.CuentaActual;
import api_banca_digital.entidades.CuentaAhorro;
import api_banca_digital.entidades.CuentaBancaria;
import api_banca_digital.excepciones.BalanceInsuficienteException;
import api_banca_digital.excepciones.ClienteNotFoundException;
import api_banca_digital.excepciones.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    Cliente saveCliente(Cliente cliente);

    CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobreGiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException;

    List<ClienteDTO> listClientes();

    CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;

    void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    List<CuentaBancaria> listCuentasBancarias();

}
