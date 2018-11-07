package contaAzul.service;

import contaAzul.bean.Boleto;
import contaAzul.bean.Data;

import java.text.ParseException;
import java.util.List;

/**
 * Created by fabiano on 04/11/18.
 */
public interface IBoletoService {
    public Boleto insert(Boleto boleto);
    public List<Boleto> listAll();
    public Boleto listUnique(String id);
    public int cancel(String id);
    public int payment(String id, Data payment_data) throws ParseException;

}
