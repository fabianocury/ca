package contaAzul.Domains;

/**
 * Created by fabiano on 04/11/18.
 */
public enum Status {

    PENDING("PENDING"),
    CANCELED("CANCELED"),
    PAID("PAID");

    private final String valor;

    Status(String status){
        valor = status;
    }
    public String getValor(){
        return valor;
    }

}
