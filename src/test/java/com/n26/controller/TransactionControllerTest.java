package com.n26.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

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
	

	private Statistics statistics;
	private Transaction transaction;
	
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
		

		String jsonInput= "{ Example :123}"; 	
		MvcResult mvcResult = mockMVC.perform(post("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInput))
				.andExpect(status().isBadRequest()).andReturn();	
		
		System.out.println(mvcResult.getResponse().getContentAsString());		
		

	}
	
	

	
	
	
}


