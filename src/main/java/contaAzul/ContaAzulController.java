package contaAzul;

import contaAzul.bean.Boleto;
import contaAzul.bean.Data;
import contaAzul.service.IBoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class ContaAzulController {

    @Autowired
    private IBoletoService boletoService;

    @RequestMapping("/")
    public String index(Model model) {
        return "Home page";
    }

    @RequestMapping(name = "/bankslipsRead", value = "/rest/bankslips", method = RequestMethod.GET)
    public List<Boleto> listAll() {
        List<Boleto> boletos = boletoService.listAll();
        return boletos;
    }

    @RequestMapping(name = "/bankslipsRead", value = "/rest/bankslips/{id}", method = RequestMethod.GET)
    public Boleto listUnique(@PathVariable @NotNull String id) {
        Boleto boleto = boletoService.listUnique(id);
        if(boleto==null){
            throw new ArrayIndexOutOfBoundsException("Bankslip not found with the specified id.");
        } else {
            return boleto;
        }
    }

    @RequestMapping(name = "/bankslipsCancel", value = "/rest/bankslips/{id}", method = RequestMethod.DELETE)
    public ResponseEntity cancel(@PathVariable @NotNull String id) {
        int i = boletoService.cancel(id);
        if(i>0){
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        } else {
            throw new ArrayIndexOutOfBoundsException("Bankslip not found with the specified id.");
        }
    }

    @RequestMapping(name = "/bankslipsPayment", value = "/rest/bankslips/{id}/payments", method = RequestMethod.POST)
    public ResponseEntity payment(@PathVariable @NotNull String id, @RequestBody Data payment_date ) throws ParseException {

        int i = boletoService.payment(id, payment_date);
        if(i>0){
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        } else {
            throw new ArrayIndexOutOfBoundsException("Bankslip not found with the specified id.");
        }
    }

    @RequestMapping(name = "/bankslipsInsert", value = "/rest/bankslips", method = RequestMethod.POST)
    public ResponseEntity<Boleto> insertBoleto(@Valid @RequestBody Boleto boletoJson, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("A field of the provided bankslip was null or with invalid values.");
        } else {
            Boleto boleto = boletoService.insert(boletoJson);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(boleto, headers, HttpStatus.CREATED);
        }
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @ExceptionHandler
    void handleEmptyResultException(ArrayIndexOutOfBoundsException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

}