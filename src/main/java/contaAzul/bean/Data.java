package contaAzul.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import contaAzul.util.DateDeserializer;

import java.util.Date;

/**
 * Created by fabiano on 04/11/18.
 */
public class Data {

    @JsonDeserialize(using = DateDeserializer.class)
    private Date payment_date;

    public Data() {}

    public Data(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Date getData() {
        return payment_date;
    }

    public void setData(Date payment_date) {
        this.payment_date = payment_date;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + payment_date +
                '}';
    }
}
