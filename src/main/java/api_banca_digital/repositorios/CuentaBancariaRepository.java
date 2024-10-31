package api_banca_digital.repositorios;

import api_banca_digital.entidades.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {
}
