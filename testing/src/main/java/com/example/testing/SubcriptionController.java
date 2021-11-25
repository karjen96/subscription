package com.example.testing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = { "/test" })
public class SubcriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;

	
	@PostMapping(path = { "subscription", "/subscription" })
	public String subscription(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session,
			@RequestBody final String input) throws Exception {

		return subscriptionService.performSubscription(new Gson().fromJson(input, CommonDTO.class), session, request);
	}
	
	//Mock data testing 
	@PostMapping(path = { "subscription/daily"})
	public String subscriptionDaily(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session,
			@RequestBody final String input) throws Exception {
		
		
		CommonDTO commonDTO=new CommonDTO(10.00,SubscriptionType.DAILY,"01/09/2021","01/11/2021");
		return subscriptionService.performSubscription(commonDTO, session, request);
	}
	
	@PostMapping(path = { "subscription/weekly"})
	public String subscriptionWeekly(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session,
			@RequestBody final String input) throws Exception {
		CommonDTO commonDTO=new CommonDTO(10.00,SubscriptionType.WEEKLY,"TUESDAY","01/09/2021","01/11/2021");

		return subscriptionService.performSubscription(commonDTO, session, request);
	}
	
	@PostMapping(path = { "subscription/monthly"})
	public String subscriptionMonthly(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session,
			@RequestBody final String input) throws Exception {
		CommonDTO commonDTO=new CommonDTO(10.00,SubscriptionType.MONTHLY,"23","01/09/2021","01/11/2021");

		return subscriptionService.performSubscription(commonDTO, session, request);
	}
	
	@PostMapping(path = { "subscription/monthly/exceed"})
	public String subscriptionExceedMonthly(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session,
			@RequestBody final String input) throws Exception {
		CommonDTO commonDTO=new CommonDTO(10.00,SubscriptionType.MONTHLY,"23","01/08/2021","01/11/2021");

		return subscriptionService.performSubscription(commonDTO, session, request);
	}
	
	
}
