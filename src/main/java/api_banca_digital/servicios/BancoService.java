package api_banca_digital.servicios;

import api_banca_digital.entidades.CuentaBancaria;
import api_banca_digital.repositorios.CuentaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar() {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById("025077f4-f043-4aee-9c22-03bd8c08e704").orElse(null);
        System.out.println(cuentaBancaria.toString());
        System.out.println(cuentaBancaria.getClass().getSimpleName());
    }
}
