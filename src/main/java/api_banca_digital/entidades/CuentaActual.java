package api_banca_digital.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CA")  //indica el valor que va a tener en la columna TIPO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuentaActual extends CuentaBancaria{

    private double sobregiro; //indica el monto que ha superado al saldo disponible
}
