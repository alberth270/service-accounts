package com.everis.proyect.controller;

import com.everis.proyect.models.Account;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/core")
public class AccountController {
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  
  /***
   * @author abancesa
   * @
   * @param cardNumber
   * @return AccountResponse
   * @throws Exception
   */
  
  @GetMapping("/accounts")
  public Single<AccountResponse> getAccount(
      @RequestParam(value = "cardNumber") String cardNumber) throws Exception {
    if (cardNumber.isEmpty()) {
      logger.info("Numero de tarjeta vacio");
      throw new RuntimeException();
    }
    try {
      return this.getResponse(cardNumber);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }

  }

  private Single<AccountResponse> getResponse(String cardNumber) throws InterruptedException {
    List<Account> listAccount = new ArrayList<>();
    char ultimoDigito = cardNumber.charAt(cardNumber.length() - 1);
    String constIncrement = "XXX";
    Account account = new Account();
    switch (ultimoDigito) {
      case '1':
        account.setAccountNumber(cardNumber + constIncrement);
        account.setAmount(1000.00);
        listAccount.add(account);
        listAccount.add(new Account("12456333", 500.00));
        break;
      case '2':
        account.setAccountNumber(cardNumber + constIncrement);
        account.setAmount(500.00);
        listAccount.add(account);
        break;
      case '3':
        account.setAccountNumber(cardNumber + constIncrement);
        account.setAmount(1500.00);
        listAccount.add(account);
        break;
      default:
        break;
    }
    Thread.sleep(5000L);
    return Single.just(new AccountResponse(listAccount)).subscribeOn(Schedulers.io());
  }
}
