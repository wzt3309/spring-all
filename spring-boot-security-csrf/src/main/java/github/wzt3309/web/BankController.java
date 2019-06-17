package github.wzt3309.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class BankController {
    private static final Logger log = LoggerFactory.getLogger(BankController.class);

    @RequestMapping(value = "/transfer", method = GET)
    @ResponseBody
    public int transfer(@RequestParam("accountNo") final int accountNo,
                        @RequestParam("amount") final int amount) {
        log.info("Transfer {} to {}", amount, accountNo);
        return amount;
    }

    @RequestMapping(value = "/transfer", method = POST)
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam("accountNo") final int accountNo,
                       @RequestParam("amount") final int amount) {
        log.info("Transfer {} to {}", amount, accountNo);
    }
}
