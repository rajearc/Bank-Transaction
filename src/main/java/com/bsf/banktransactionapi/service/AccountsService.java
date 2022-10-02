package com.bsf.banktransactionapi.service;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;

import javax.transaction.Transactional;

import com.bsf.banktransactionapi.exception.AccountNotExistException;
import com.bsf.banktransactionapi.exception.BalanceCheckException;
import com.bsf.banktransactionapi.exception.InsufficientFundException;
import com.bsf.banktransactionapi.exception.OperationalException;
import com.bsf.banktransactionapi.model.Account;
import com.bsf.banktransactionapi.model.TransferRequest;
import com.bsf.banktransactionapi.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${endpoint.accountBalance}")
    private String getAccountBalanceUrl;

    public Account getAccountDetails(Long accountId) {
        Account account = accountsRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AccountNotExistException("Account " + accountId + " does not exist.", "NOT_FOUND"));

        return account;
    }

    @Transactional
    public void transferAmount(TransferRequest transfer) throws InsufficientFundException, AccountNotExistException, OperationalException {
        Account accountFrom = accountsRepository.getAccountForUpdate(transfer.getAccountFromId())
                .orElseThrow(() -> new AccountNotExistException("Account " + transfer.getAccountFromId() + " does not exist.", "Bad_Request"));

        Account accountTo = accountsRepository.getAccountForUpdate(transfer.getAccountToId())
                .orElseThrow(() -> new AccountNotExistException("Account " + transfer.getAccountToId() + " does not exist.", "Bad_Request"));

        if(accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new InsufficientFundException("Account :" + accountFrom.getAccountId() + " does not have enough balance to make transaction.", "Bad Request");
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
    }

    public BigDecimal checkBalance(@NotNull Long accountId) throws OperationalException {

        try {
            String url = getAccountBalanceUrl.replace("{id}", accountId.toString());

            log.info("checking balance: {} ",url);

            ResponseEntity<Account> balanceCheckResult = restTemplate.getForEntity(url, Account.class);

            if(balanceCheckResult.getStatusCode().is2xxSuccessful()) {
                if(balanceCheckResult.hasBody()) {
                    return balanceCheckResult.getBody().getBalance();
                }
            }
        } catch (ResourceAccessException ex) {
            final String errorMessage = "Timeout Error";

            if(ex.getCause() instanceof SocketTimeoutException) {
                throw new BalanceCheckException(errorMessage, "Time_Out");
            }
        }
        // for any other fail cases
        throw new OperationalException("System Error Occured.", "INTERNAL_SERVER_ERROR");
    }
}