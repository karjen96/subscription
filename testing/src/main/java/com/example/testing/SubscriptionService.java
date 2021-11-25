package com.example.testing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        
		List<String> invoiceDate= new ArrayList<String>();
		if(SubscriptionType.MONTHLY.equals(requestDTO.getType())) {
			invoiceDate=convertStringList(getDatesBetweenUsingJava8Monthly(LocalDate.parse(requestDTO.getStartDate(), formatter),LocalDate.parse(requestDTO.getEndDate(), formatter),Integer.parseInt(requestDTO.getDayOfWeek())));
		}else if(SubscriptionType.WEEKLY.equals(requestDTO.getType())) {
			invoiceDate=convertStringList(getDatesBetweenUsingJava8Weekly(LocalDate.parse(requestDTO.getStartDate(), formatter),LocalDate.parse(requestDTO.getEndDate(), formatter),requestDTO.getDayOfWeek()));
		}else {
			invoiceDate=convertStringList(getDatesBetweenUsingJava8Daily(LocalDate.parse(requestDTO.getStartDate(), formatter),LocalDate.parse(requestDTO.getEndDate(), formatter)));
		}
		
		CommonDTO responseDTO = new CommonDTO();
		responseDTO.setAmount(requestDTO.getAmount());
		responseDTO.setType(requestDTO.getType());
		responseDTO.setInvoiceDate(invoiceDate);
		
		Gson g = new Gson();
		return g.toJson(responseDTO);
	} 
	
	public  List<LocalDate> getDatesBetweenUsingJava8Daily(LocalDate startDate, LocalDate endDate) { 
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) +1; 
		return IntStream.iterate(0, i -> i + 1)
			      .limit(numOfDaysBetween)
			      .mapToObj(i -> startDate.plusDays(i))
			      .collect(Collectors.toList()); 
	}
	
	public  List<LocalDate> getDatesBetweenUsingJava8Weekly(LocalDate startDate, LocalDate endDate, String dayOfWeek) { 
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) +1; 
		return Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDaysBetween).filter( date -> date.getDayOfWeek()== DayOfWeek.valueOf(dayOfWeek.toUpperCase())).collect(Collectors.toList());

	}
	
	public  List<LocalDate> getDatesBetweenUsingJava8Monthly(LocalDate startDate, LocalDate endDate, int dayOfMonth) { 
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1; 
		return Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDaysBetween).filter( date -> date.getDayOfMonth()== dayOfMonth).collect(Collectors.toList());
	}
	
	private List<String> convertStringList(List<LocalDate> dateList){
		
		return dateList.stream().map(x-> String.valueOf(x)).collect(Collectors.toList());
	}
	
	private boolean checkIsMaxMonth(LocalDate startDate, LocalDate endDate) {
		long numOfDaysBetween = ChronoUnit.MONTHS.between(startDate, endDate)+1; 
		if(numOfDaysBetween>3) {
			return true;
		}else {
			return false;
		}

	}
}
