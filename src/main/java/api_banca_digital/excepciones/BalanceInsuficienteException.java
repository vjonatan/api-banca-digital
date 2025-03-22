package api_banca_digital.excepciones;

public class BalanceInsuficienteException extends Exception{

    public BalanceInsuficienteException(String message) {
        super(message);
    }

}
