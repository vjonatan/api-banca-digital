package api_banca_digital.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SA") //indica el valor que va a tener en la columna TIPO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuentaAhorro extends CuentaBancaria {

    private double tasaDeInteres;

}
