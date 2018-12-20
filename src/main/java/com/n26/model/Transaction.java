package com.n26.model;

import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

@Component
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=false)
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
	@JsonSerialize(using=InstantSerializer.class)
	
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
