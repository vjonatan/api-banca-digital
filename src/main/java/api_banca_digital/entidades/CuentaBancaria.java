package api_banca_digital.entidades;

import api_banca_digital.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Herencia - SINGLE_TABLE
@DiscriminatorColumn(name = "TIPO", length = 4) //TIPO - es una columna que indica el tipo de cta
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuentaBancaria {

    @Id
    private String id;
    private double balance;
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    private List<OperacionCuenta> operacionesCuenta;
}
