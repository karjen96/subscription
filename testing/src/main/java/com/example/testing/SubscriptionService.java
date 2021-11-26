package com.example.testing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class SubscriptionService {

	public String performSubscription(final CommonDTO requestDTO, final HttpSession session, final HttpServletRequest request)  {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        if(checkIsMaxMonth(LocalDate.parse(requestDTO.getStartDate(), formatter),LocalDate.parse(requestDTO.getEndDate(),formatter))){
        	return "You have exceed the max monthly subscription. Please try again!";
        }
        	
		return constructResponseDTO(requestDTO, formatter);
	}

	private List<String> createInvoiceDateByType(final CommonDTO requestDTO, DateTimeFormatter formatter) {

		LocalDate startDate=LocalDate.parse(requestDTO.getStartDate(), formatter),endDate=LocalDate.parse(requestDTO.getEndDate(), formatter);
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) +1; 
		
		if(SubscriptionType.MONTHLY.equals(requestDTO.getType())) {
			int dayOfMonth=Integer.parseInt(requestDTO.getDayOfWeek());
			return Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDaysBetween).filter( date -> date.getDayOfMonth()== dayOfMonth).map(x->x.toString()).collect(Collectors.toList());
		}else if(SubscriptionType.WEEKLY.equals(requestDTO.getType())) {
			String dayOfWeek=requestDTO.getDayOfWeek().toUpperCase();
			return Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDaysBetween).filter( date -> date.getDayOfWeek()== DayOfWeek.valueOf(dayOfWeek)).map(x->x.toString()).collect(Collectors.toList());
		}else {
			return Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDaysBetween).map(x->x.toString())
					.collect(Collectors.toList()); 
		}
	} 

	private boolean checkIsMaxMonth(LocalDate startDate, LocalDate endDate) {
		long numOfDaysBetween = ChronoUnit.MONTHS.between(startDate, endDate)+1; 
		if(numOfDaysBetween>3) {
			return true;
		}else {
			return false;
		}
	}
	
	private String constructResponseDTO(final CommonDTO requestDTO, DateTimeFormatter formatter) {
		CommonDTO responseDTO = new CommonDTO();
		responseDTO.setAmount(requestDTO.getAmount());
		responseDTO.setType(requestDTO.getType());
		responseDTO.setInvoiceDate(createInvoiceDateByType(requestDTO, formatter));
		
		Gson g = new Gson();
		return g.toJson(responseDTO);
	}

}
