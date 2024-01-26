package com.tunehub.app.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.tunehub.app.entity.Users;
import com.tunehub.app.service.UserService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@Controller
public class PaymentController {
	@Autowired
	UserService service;

	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}

	@GetMapping("/payment")
	public String payment(HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		return "redirect:/pay";
	}

	public boolean Validator(HttpSession session) {

		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");

		return (email != null && role != null && role.equals("user"));
	}

	@GetMapping("/payment-success")
	public String paymentSuccess(HttpSession session) {
		String mail = (String) session.getAttribute("email");
		Users u = service.getUser(mail);
		u.setPremium(true);
		service.updateUser(u);
		return "userhome";
	}

	@GetMapping("/payment-failure")
	public String paymentFailure() {
		return "userhome";
	}

	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {

		int amount = 5000;
		Order order = null;
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_kTu9geCj6cJJZ8", "O6c5XcLHFkBiku1JrgTqzF9I");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount * 100); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");

			order = razorpay.orders.create(orderRequest);

		} catch (RazorpayException e) {
			e.printStackTrace();
		} finally {
			return order.toString();
		}
	}

	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
			@RequestParam String signature) {
		try {
			// Initialize Razorpay client with your API key and secret
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_kTu9geCj6cJJZ8", "O6c5XcLHFkBiku1JrgTqzF9I");
			// Create a signature verification data string
			System.out.println("Client: " + razorpayClient + " Successfully added to Premium Member ('.') ");// Successfully
																												// added
																												// to
																												// premium
																												// member
			String verificationData = orderId + "|" + paymentId;

			// Use Razorpay's utility function to verify the signature
			boolean isValidSignature = Utils.verifySignature(verificationData, signature, "O6c5XcLHFkBiku1JrgTqzF9I");

			return isValidSignature;
		} catch (RazorpayException e) {
			e.printStackTrace();
			return false;
		}
	}
}
