package api_banca_digital.servicios.impl;

import api_banca_digital.dto.ClienteDTO;
import api_banca_digital.entidades.*;
import api_banca_digital.enums.TipoOperacion;
import api_banca_digital.excepciones.BalanceInsuficienteException;
import api_banca_digital.excepciones.ClienteNotFoundException;
import api_banca_digital.excepciones.CuentaBancariaNotFoundException;
import api_banca_digital.mapper.CuentaBancariaMapperImpl;
import api_banca_digital.repositorios.ClienteRepository;
import api_banca_digital.repositorios.CuentaBancariaRepository;
import api_banca_digital.repositorios.OperacionCuentaRepository;
import api_banca_digital.servicios.CuentaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;

    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Guardando nuevo cliente");
        return clienteRepository.save(cliente);
    }

    @Override
    public CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobreGiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado");
        }

        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobreGiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualSave = cuentaBancariaRepository.save(cuentaActual);
        log.info("Cuenta Actual creada");
        return cuentaActualSave;
    }

    @Override
    public CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null){
            throw new ClienteNotFoundException("Cliente no existe");
        }

        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);

        CuentaAhorro cuentaAhorroSave = cuentaBancariaRepository.save(cuentaAhorro);
        log.info("Cuenta Ahorro creada");
        return cuentaAhorroSave;
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());

        return clienteDTOS;
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta Bancaria no existe"));

        return cuentaBancaria;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta Bancaria no existe"));

        if (cuentaBancaria.getBalance() < monto){
            throw new BalanceInsuficienteException("Balance o Saldo insuficiente");
        }

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setMonto(monto);
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);
        log.info("Operación Débito ejecutada con EXITO");
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta no existe"));

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setMonto(monto);
        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
        log.info("Operación Crédito ejecutada con EXITO");
    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        this.debit(cuentaIdPropietario, monto, "Débito por transferencia");
        this.credit(cuentaIdDestinatario, monto, "Crédito por transferencia");
        log.info("Operación Transferencia ejecutada con EXITO");
    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }
}
