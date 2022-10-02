package com.bsf.banktransactionapi.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TransferRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long accountFromId;

    @NotNull
    @ApiModelProperty(required = true)
    private Long accountToId;

    @NotNull
    @ApiModelProperty(required = true)
    @Min(value = 0, message = "Amount should be greater than zero")
    private BigDecimal amount;

    @JsonCreator
    public TransferRequest() {
        super();
    }

    @JsonCreator
    public TransferRequest(@NotNull @JsonProperty("accountFromId") Long accountFromId,
                           @NotNull @JsonProperty("accountToId") Long accountToId,
                           @NotNull @Min(value = 0, message = "Amount should be greater than zero") @JsonProperty("amount") BigDecimal amount) {
        super();
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }

}