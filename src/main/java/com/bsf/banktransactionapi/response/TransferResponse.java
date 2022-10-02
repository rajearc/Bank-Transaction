package com.bsf.banktransactionapi.response;


import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransferResponse {

    private Long accountFromId;

    private BigDecimal balanceAfterTransfer;

}
