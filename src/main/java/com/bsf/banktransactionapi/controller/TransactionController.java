package com.bsf.banktransactionapi.controller;

import javax.validation.Valid;

import com.bsf.banktransactionapi.exception.InsufficientFundException;
import com.bsf.banktransactionapi.response.TransferResponse;
import com.bsf.banktransactionapi.exception.AccountNotExistException;
import com.bsf.banktransactionapi.exception.BalanceCheckException;
import com.bsf.banktransactionapi.model.TransferRequest;
import com.bsf.banktransactionapi.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Slf4j
@RestController
@RequestMapping("/v1/transaction")
@Api(tags = {"Transaction Controller"}, description = "Provide APIs for transaction related operation")
public class TransactionController {

    @Autowired
    private AccountsService accountService;

    @PostMapping(consumes = { "application/json" })
    @ApiOperation(value = "API to create transaction", response = TransferResponse.class, produces = "application/json")
    public ResponseEntity moneyTransfer(@RequestBody @Valid TransferRequest request) throws Exception {

        try {
            accountService.transferAmount(request);

            TransferResponse result = new TransferResponse();
            result.setAccountFromId(request.getAccountFromId());
            result.setBalanceAfterTransfer(accountService.checkBalance(request.getAccountFromId()));

            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (AccountNotExistException | InsufficientFundException e) {
            log.error("Money transfer failed");
            throw e;
        } catch (BalanceCheckException cbEx) {
            log.error("Balance check operation failed.");
            throw cbEx;
        }
    }
}
