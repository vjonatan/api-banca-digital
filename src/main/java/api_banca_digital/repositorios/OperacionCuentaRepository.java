package api_banca_digital.repositorios;

import api_banca_digital.entidades.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long> {
}
