package mconst.rpg.accounts.controllers;

import mconst.rpg.accounts.models.requests.AccountCompensateSpendingRequest;
import mconst.rpg.accounts.models.requests.AccountSpendRequest;
import mconst.rpg.accounts.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{id}/spend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void spend(
            @PathVariable Integer id,
            @RequestBody AccountSpendRequest request
    ) {
        accountService.spend(id, request.getMoney(), request.getToken());
    }

    @PostMapping("/{id}/spend-compensate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void compensateSpending(
            @PathVariable Integer id,
            @RequestBody AccountCompensateSpendingRequest request
    ) {
        accountService.compensateSpending(request.getToken());
    }
}
