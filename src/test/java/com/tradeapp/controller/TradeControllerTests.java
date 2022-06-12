package com.tradeapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeapp.bean.Trade;
import com.tradeapp.constants.TradeConstants;
import com.tradeapp.dao.TradeDao;
import com.tradeapp.dto.TradeDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TradeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeDao tradeDao;

    @Test
    public void testNewTradeShouldInsertIntoDB() throws Exception {
        log.info("testNewTradeShouldInsertIntoDB.");

        TradeDTO tradeDTO = TradeDTO.builder()
                .tradeId("T6")
                .counterPartyId("CP-6")
                .bookId("B6")
                .version(1)
                .expired("N")
                .maturityDate(LocalDate.parse("20/10/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals("Trade processed successfully", response.getContentAsString());

        // Fetching newly inserted trade from db and asserting all fields
        Optional<Trade> optionalTrade = tradeDao.findById(tradeDTO.getTradeId());
        Assertions.assertTrue(optionalTrade.isPresent());
        Assertions.assertEquals("20/10/2022", DateTimeFormatter.ofPattern(TradeConstants.DATE_FORMAT).format(optionalTrade.get().getMaturityDate()));
        Assertions.assertEquals(1, optionalTrade.get().getVersion());
        Assertions.assertEquals("T6", optionalTrade.get().getTradeId());
        Assertions.assertEquals("N", optionalTrade.get().getExpired());
        Assertions.assertEquals("B6", optionalTrade.get().getBookId());
        Assertions.assertEquals("CP-6", optionalTrade.get().getCounterPartyId());
        Assertions.assertEquals(LocalDate.now(), optionalTrade.get().getCreatedDate());
       
    }

    @Test
    public void testHigherVerAndLaterMaturityDateShouldOverrideExistingTrade() throws Exception {
        log.info("testHigherVerAndLaterMaturityDateShouldOverrideExistingTrade.");
        TradeDTO tradeDTO = TradeDTO.builder()
                .tradeId("T1")
                .counterPartyId("CP-1")
                .bookId("B1")
                .version(2)
                .maturityDate(LocalDate.parse("20/09/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals("Trade processed successfully", response.getContentAsString());

        Optional<Trade> optionalTrade = tradeDao.findById(tradeDTO.getTradeId());
        Assertions.assertTrue(optionalTrade.isPresent());
        Assertions.assertEquals("20/09/2022", DateTimeFormatter.ofPattern(TradeConstants.DATE_FORMAT).format(optionalTrade.get().getMaturityDate()));
        Assertions.assertEquals(2, optionalTrade.get().getVersion());

    }

    @Test
    public void testWithVersionLessThanExisting_ResponseWithCONFLICTStatusCode() throws Exception {
        log.info("testSendTrade_WithVersionLessThanExisting_ResponseWithCONFLICTStatusCode..");

        TradeDTO tradeDTO = TradeDTO.builder()
                .tradeId("T6")
                .counterPartyId("CP-6")
                .bookId("B6")
                .version(1)
                .maturityDate(LocalDate.parse("20/09/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals("Trade processed successfully", response.getContentAsString());
        
        
        
        TradeDTO tradeDTO2 = TradeDTO.builder()
                .tradeId("T6")
                .counterPartyId("CP-6")
                .bookId("B6")
                .version(2)
                .maturityDate(LocalDate.parse("20/09/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO2))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult2 = mockMvc.perform(requestBuilder2).andReturn();

        MockHttpServletResponse response2 = mvcResult2.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response2.getStatus());
        Assertions.assertEquals("Trade processed successfully", response2.getContentAsString());
        
        TradeDTO tradeDTO3 = TradeDTO.builder()
                .tradeId("T6")
                .counterPartyId("CP-6")
                .bookId("B6")
                .version(1)
                .maturityDate(LocalDate.parse("20/09/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder3 = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO3))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult3 = mockMvc.perform(requestBuilder3).andReturn();

        MockHttpServletResponse response3 = mvcResult3.getResponse();
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response3.getStatus());
        Assertions.assertEquals("Exception occurred.Version received is lower than the existing version for tradeId: T6", response3.getContentAsString());
    }

  
    @Test
    public void testSendTrade_WithOlderMaturityDate_ShouldIgnoreTheTrade() throws Exception {
        log.info("testSendTrade_WithOlderMaturityDate..");

        TradeDTO tradeDTO = TradeDTO.builder()
                .tradeId("T3")
                .counterPartyId("CP-1")
                .bookId("B1")
                .version(1)
                .maturityDate(LocalDate.parse("20/05/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sendTrade")
                .content(asJsonString(tradeDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        Assertions.assertEquals("Exception occurred.Maturity date should be less than today date. " , response.getContentAsString()); 
        		 
    }

    private String asJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while converting to json.", e);
        }
        return null;
    }
}
