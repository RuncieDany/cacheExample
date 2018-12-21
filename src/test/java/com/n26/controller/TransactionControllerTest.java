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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
	public void postTransaction_whileunParseableData_Failure() throws Exception {
		
       String testData="{\r\n" + 
          		"  \"amount\": \"345.3343\",\r\n" + 
           		"  \"timestamp\": \"2015678-07-17T09:59:51.312Z\"\r\n" + 
           		"}";      
		
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(testData))
				.andExpect(status().isUnprocessableEntity()).andReturn();	
		
		System.out.println(mvcResult.getResponse().getCharacterEncoding());		
		

	}
	
	
	
	@Test
	public void postTransaction_WhileValidData_Success() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		Instant test=Instant.now();
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(jsonPath("$").doesNotExist())
				.andExpect(status().isCreated()).andReturn();	
		
		System.out.println(mvcResult.getResponse().getContentAsString());		
		

	}
	
	
	
	@Test
	public void getStatistics_WhileValidData_Success() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));
		Instant test=Instant.now();
		
		transaction.setTimestamp(test);
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();	
		
		transaction.setAmount(new BigDecimal("50.46"));
				transaction.setTimestamp(test);
		 mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();
		 
		 transaction.setAmount(new BigDecimal("13.46"));
			transaction.setTimestamp(test);
	 mvcResult = mockMVC.perform(post("/transactions")
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
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();	
		
		transaction.setAmount(new BigDecimal("50.46"));
				transaction.setTimestamp(test);
		 mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isCreated()).andReturn();
		 
		 transaction.setAmount(new BigDecimal("13.46"));
			transaction.setTimestamp(test);
	 mvcResult = mockMVC.perform(post("/transactions")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(transaction)))
			.andExpect(status().isCreated()).andReturn();	
	
		
		
		mvcResult = mockMVC.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andReturn();
		
		mvcResult = mockMVC.perform(delete("/transactionss"))
				.andExpect(status().isNoContent())
				.andReturn();
		
		mvcResult = mockMVC.perform(get("/statistics"))
				.andExpect(status().isOk())
				.andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		

	}
	
	@Test
	public void postTransaction_WhileTimeStampFuture_Failure() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));		
		LocalDate localDate = LocalDate.parse("2019-04-17");
		LocalDateTime localDateTime = localDate.atStartOfDay();
		Instant instant = localDateTime.toInstant(ZoneOffset.UTC);		
		transaction.setTimestamp(instant);
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn();		
				
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		

	}
	
	@Test
	public void postTransaction_WhileTimeStampPast60Sec_Failure() throws Exception {
		

		transaction.setAmount(new BigDecimal("45.46"));		
		LocalDate localDate = LocalDate.parse("2018-12-20");
		LocalDateTime localDateTime = localDate.atStartOfDay();
		Instant instant = localDateTime.toInstant(ZoneOffset.UTC);		
		transaction.setTimestamp(instant);
		MvcResult mvcResult = mockMVC.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn();			
		
			

	}

	
	
	
}


