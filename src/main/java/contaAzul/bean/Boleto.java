package contaAzul.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import contaAzul.Domains.Status;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by fabiano on 04/11/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Boleto {

    private UUID id;
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date due_date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date payment_date;
    @NotNull
    private BigDecimal total_in_cents;
    @NotNull
    private String customer;
    private BigDecimal fine;
    private Status status;

    public Boleto() {}

    public Boleto(String customer, BigDecimal total_in_cents, Date due_date) {
        this.customer = customer;
        this.total_in_cents = total_in_cents;
        this.due_date = due_date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getTotal_in_cents() {
        return total_in_cents;
    }

    public void setTotal_in_cents(BigDecimal total_in_cents) {
        this.total_in_cents = total_in_cents;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public BigDecimal getFine() {
        if(getPayment_date()!=null){
            BigDecimal calc = BigDecimal.ONE;
            if(getDue_date().before(getPayment_date())){
                long diff = getPayment_date().getTime() - getDue_date().getTime();
                Integer diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                if(diffDays>10){
                    calc = calc
                            .multiply(getTotal_in_cents())
                            .multiply(BigDecimal.ONE)
                            .multiply(new BigDecimal(diffDays));
                }else {
                    calc = calc
                            .multiply(getTotal_in_cents())
                            .multiply(new BigDecimal(0.5))
                            .multiply(new BigDecimal(diffDays));
                }
            }
            return calc
                    .divide(new BigDecimal(100));
        }
        return null;
    }

    @Override
    public String toString() {
        return "Boleto{" +
                "id=" + id +
                ", due_date=" + due_date +
                ", payment_date=" + payment_date +
                ", total_in_cents=" + total_in_cents +
                ", customer='" + customer + '\'' +
                ", fine=" + fine +
                ", status=" + status +
                '}';
    }
}
