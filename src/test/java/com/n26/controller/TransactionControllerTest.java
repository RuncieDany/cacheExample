package com.n26.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;



@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@ComponentScan(basePackages = "com.n26.*")
public class TransactionControllerTest {

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private MockMvc mockMVC;
	@Autowired
	private TransactionService transactionService;
	
    @Autowired
	private Statistics statistics;
    @Autowired
	private Transaction transaction;
	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void Setup() {
		this.mockMVC = MockMvcBuilders.webAppContextSetup(this.context).build();
		statistics= new Statistics();	
		transaction = new Transaction ();
		objectMapper=new ObjectMapper();		
	}

	@Test
	public void postTransaction_whileInvalidData_Failure() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		//Instant instant= new Instant ();
		Instant test=Instant.now();
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isBadRequest()).andReturn();	
		
		System.out.println(mvcResult.getResponse().getCharacterEncoding());		
		

	}
	
	
	
	@Test
	public void postTransaction_WhileValidData_Success() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		Instant test=Instant.now();
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();	
		
		System.out.println(mvcResult.getResponse().getContentAsString());		
		

	}
	
	
	
	@Test
	public void getStatistics_WhileValidData_Success() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		Instant test=Instant.now();
		
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();	
		
		transaction.setAmount(new BigDecimal("50.46"));
				transaction.setTimestamp(test);
		 mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();
		 
		 transaction.setAmount(new BigDecimal("13.46"));
			transaction.setTimestamp(test);
	 mvcResult = mockMVC.perform(post("/transaction")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(transaction)))
			.andExpect(status().isCreated()).andReturn();	
	
		
		
		mvcResult = mockMVC.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		

	}
	
	

	@Test
	public void delete_WhileValidData_Success() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		Instant test=Instant.now();
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();	
		
		transaction.setAmount(new BigDecimal("50.46"));
				transaction.setTimestamp(test);
		 mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();
		 
		 transaction.setAmount(new BigDecimal("13.46"));
			transaction.setTimestamp(test);
	 mvcResult = mockMVC.perform(post("/transaction")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(transaction)))
			.andExpect(status().isCreated()).andReturn();	
	
		
		
		mvcResult = mockMVC.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andReturn();
		
		mvcResult = mockMVC.perform(delete("/transactions"))
				.andExpect(status().isNoContent())
				.andReturn();
		
		mvcResult = mockMVC.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		

	}
	
	

	
	
	
}


