package com.bsf.banktransactionapi.controller;

import com.bsf.banktransactionapi.model.Account;
import com.bsf.banktransactionapi.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/v1/accounts")
@Api(tags = { "Accounts Controller" })
public class AccountsController {

    @Autowired
    private AccountsService accountService;

    @GetMapping("/{accountId}/details")
    @ApiOperation(value = "Get account details by id", response = Account.class, produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Account ID"),
            @ApiResponse(code = 404, message = "Account not found with ID")})
    public Account getAccountDetails(
            @ApiParam(value = "ID related to the account", required = true) @PathVariable Long accountId) {
        return accountService.getAccountDetails(accountId);
    }
}
