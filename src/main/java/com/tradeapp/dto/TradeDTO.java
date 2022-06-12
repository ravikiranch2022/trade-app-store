package com.tradeapp.dto;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {
	private String tradeId;
	private Integer version;
	private String counterPartyId;
	private String bookId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate maturityDate;
	private String expired;
}
