package com.bsf.banktransactionapi;

import com.bsf.banktransactionapi.exception.AccountNotExistException;
import com.bsf.banktransactionapi.exception.InsufficientFundException;
import com.bsf.banktransactionapi.exception.OperationalException;
import com.bsf.banktransactionapi.model.Account;
import com.bsf.banktransactionapi.model.TransferRequest;
import com.bsf.banktransactionapi.repository.AccountsRepository;
import com.bsf.banktransactionapi.service.AccountsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountsRepository accRepo;

    @InjectMocks
    AccountsService accService;

    @Test
    public void testRetrieveAccDetails() {
        when(accRepo.findByAccountId(1L)).thenReturn(Optional.of(new Account(1L, "TestName", "TestType", "TestBranch", BigDecimal.ONE)));

        assertEquals(BigDecimal.ONE, accService.getAccountDetails(1L).getBalance());
    }

    @Test(expected = AccountNotExistException.class)
    public void testRetrieveBalanceFromInvalidAccount() {
        when(accRepo.findByAccountId(1L)).thenReturn(Optional.empty());

        accService.getAccountDetails(1L);
    }

    @Test
    public void testTransferBalance() throws Exception {
        Long accountFromId = 1L;
        Long accountFromTo = 2L;
        BigDecimal amount = new BigDecimal(10);

        TransferRequest request = new TransferRequest();
        request.setAccountFromId(accountFromId);
        request.setAccountToId(accountFromTo);
        request.setAmount(amount);


        Account accFrom = new Account(accountFromId,"Ana","Saving","Dubai",BigDecimal.TEN);
        Account accTo = new Account(accountFromTo,"Mina","Saving","Dubai",BigDecimal.TEN);

        when(accRepo.getAccountForUpdate(accountFromId)).thenReturn(Optional.of(accFrom));
        when(accRepo.getAccountForUpdate(accountFromTo)).thenReturn(Optional.of(accTo));

        accService.transferAmount(request);

        assertEquals(BigDecimal.ZERO, accFrom.getBalance());
        assertEquals(BigDecimal.TEN.add(BigDecimal.TEN), accTo.getBalance());
    }

    @Test(expected = InsufficientFundException.class)
    public void testSufficientFund() throws InsufficientFundException, AccountNotExistException, OperationalException {
        Long accountFromId = 1L;
        Long accountFromTo = 2L;
        BigDecimal amount = new BigDecimal(20);

        TransferRequest request = new TransferRequest();
        request.setAccountFromId(accountFromId);
        request.setAccountToId(accountFromTo);
        request.setAmount(amount);

        Account accFrom = new Account(accountFromId, "Kim", "Current", "AbuDhabi", BigDecimal.TEN);
        Account accTo = new Account(accountFromId,"Tim", "Current","RAK", BigDecimal.TEN);

        when(accRepo.getAccountForUpdate(accountFromId)).thenReturn(Optional.of(accFrom));
        when(accRepo.getAccountForUpdate(accountFromTo)).thenReturn(Optional.of(accTo));

        accService.transferAmount(request);
    }



}