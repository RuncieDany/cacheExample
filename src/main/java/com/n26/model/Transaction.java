package com.n26.model;

import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Transaction {
	@Valid
	@NotNull
	private BigDecimal amount;
	@NotNull
	@PastOrPresent
	private Instant timestamp;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
