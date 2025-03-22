package api_banca_digital;

import api_banca_digital.entidades.*;
import api_banca_digital.enums.EstadoCuenta;
import api_banca_digital.enums.TipoOperacion;
import api_banca_digital.excepciones.ClienteNotFoundException;
import api_banca_digital.repositorios.ClienteRepository;
import api_banca_digital.repositorios.CuentaBancariaRepository;
import api_banca_digital.repositorios.OperacionCuentaRepository;
import api_banca_digital.servicios.BancoService;
import api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner consulta(BancoService bancoService){
		return args -> {
			bancoService.consultar();
		};
	}

	@Bean
	CommandLineRunner start (CuentaBancariaService cuentaBancariaService){
		return args -> {

			Stream.of("Jonatan", "Alejandra", "Francisco", "Agustin").forEach(nombre -> {
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre + "@gmail.com");
				cuentaBancariaService.saveCliente(cliente);
			});

			cuentaBancariaService.listClientes().forEach(cliente -> {
                try {
                    cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 9000, 9000, cliente.getId());
					cuentaBancariaService.saveCuentaBancariaAhorro(500000, 5.5, cliente.getId());

					List<CuentaBancaria> cuentaBancariaList = cuentaBancariaService.listCuentasBancarias();

					for (CuentaBancaria cuentaBancaria : cuentaBancariaList){
						for (int i = 0; i < 10; i ++){
							cuentaBancariaService.credit(cuentaBancaria.getId(), 15000, "Credito");
							cuentaBancariaService.debit(cuentaBancaria.getId(), 12000, "Debito");
						}
					}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
		};
	}

	/*@Bean
	CommandLineRunner start (ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository){
		return args -> {

			Stream.of("Jonatan", "Alejandra", "Francisco", "Agustin").forEach(nombre -> {
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre + "@gmail.com");
				clienteRepository.save(cliente);
			});

			//le asignamos cuentas bancarias
			clienteRepository.findAll().forEach(cliente -> {
				CuentaActual cuentaActual = new CuentaActual();
				cuentaActual.setId(UUID.randomUUID().toString());
				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaActual.setBalance(Math.random() * 8000);
				cuentaActual.setCliente(cliente);
				cuentaActual.setFechaCreacion(new Date());
				cuentaActual.setSobregiro(5000);
				cuentaBancariaRepository.save(cuentaActual);
			});

			clienteRepository.findAll().forEach(cliente -> {
				CuentaAhorro cuentaAhorro = new CuentaAhorro();
				cuentaAhorro.setId(UUID.randomUUID().toString());
				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaAhorro.setBalance(Math.random() * 3000);
				cuentaAhorro.setCliente(cliente);
				cuentaAhorro.setFechaCreacion(new Date());
				cuentaAhorro.setTasaDeInteres(1.3);
				cuentaBancariaRepository.save(cuentaAhorro);
			});

			//agregamos las operaciones cuenta
			cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
				for (int i = 0; i < 10;  i++){
					OperacionCuenta operacionCuenta = new OperacionCuenta();
					operacionCuenta.setFechaOperacion(new Date());
					operacionCuenta.setTipoOperacion(Math.random() > 0.5 ? TipoOperacion.CREDITO : TipoOperacion.DEBITO);
					operacionCuenta.setMonto(Math.random() * 6000);
					operacionCuenta.setCuentaBancaria(cuentaBancaria);
					operacionCuentaRepository.save(operacionCuenta);
				}
			});

		};
	}*/

}
