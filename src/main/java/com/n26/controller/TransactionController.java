package com.n26.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping(value="/statistics")
	@ResponseStatus(value=HttpStatus.OK)
	public Statistics getStatistics() throws Exception {
		return transactionService.getAllStatistics();
	}
	@PostMapping(value="/transaction")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void postTransaction(@RequestBody @Valid Transaction transaction) {
		transactionService.postTransaction(transaction);
		
	}

}
