package contaAzul.service;

import contaAzul.Domains.Status;
import contaAzul.bean.Boleto;
import contaAzul.bean.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

/**
 * Created by fabiano on 04/11/18.
 */
@Service
public class BoletoService implements IBoletoService{


    @Autowired
    private JdbcTemplate jtm;

    @Override
    public Boleto insert(Boleto boleto) {
        String sql = "INSERT INTO Boletos(Id, Due_Date, Total_in_Cents, Customer, Status) VALUES (?, ?, ?, ?, ?)";
        boleto.setId(UUID.randomUUID());
        boleto.setStatus(Status.PENDING.getValor());
        jtm.update(sql, new Object[]{boleto.getId(), boleto.getDue_date(), boleto.getTotal_in_cents(),
                boleto.getCustomer(), boleto.getStatus().getValor()});
        return boleto;
    }

    @Override
    public List<Boleto> listAll() {
        String sql = "SELECT ID, DUE_DATE, TOTAL_IN_CENTS, CUSTOMER, STATUS FROM Boletos";
        List<Boleto> boleto = jtm.query(sql, new BeanPropertyRowMapper(Boleto.class));
        return boleto;
    }

    @Override
    public Boleto listUnique(String id) {
        String sql = "SELECT * FROM Boletos WHERE ID = ?";
        try{
            Boleto boleto = (Boleto)jtm.queryForObject(sql, new Object[]{id},
                    new BeanPropertyRowMapper(Boleto.class));
            System.out.println(boleto);
            return boleto;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int cancel(String id) {
        String sql = "UPDATE Boletos SET STATUS = ? WHERE ID = ? AND STATUS <> ?";
        return jtm.update(sql, new Object[]{Status.CANCELED.getValor(), id, Status.CANCELED.getValor()});
    }

    @Override
    public int payment(String id, Data payment_Date) throws ParseException {
        System.out.println("ID:" + id + " / PD:" + payment_Date);
        String sql = "UPDATE Boletos SET PAYMENT_DATE = ?, Status = ? WHERE ID = ? AND STATUS = ?";
        return jtm.update(sql,
                new Object[]{payment_Date.getData(), Status.PAID.getValor(), id, Status.PENDING.getValor()});
    }

}
