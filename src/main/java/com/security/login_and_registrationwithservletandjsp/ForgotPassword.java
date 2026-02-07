package com.security.login_and_registrationwithservletandjsp;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/forgotPassword")
public class ForgotPassword extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		RequestDispatcher dispatcher;
		HttpSession mySession = request.getSession();

		// ✅ Server-side validation
		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("forgotPassword.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// ✅ Generate 6-digit OTP
		int otpvalue = 100000 + new Random().nextInt(900000);

		// ✅ SMTP configuration
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// ✅ Mail session (Jakarta)
		Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						"aboda201986@gmail.com",// 🔴 put your email
						"vhpz bxwv puzq wbnl"          // 🔴 Gmail App Password
				);
			}
		});

		try {
			// ✅ Compose email
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("your_email@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("OTP Verification");
			message.setText("Your OTP is: " + otpvalue);

			// ✅ Send email
			Transport.send(message);

			// ✅ Store OTP in session
			mySession.setAttribute("otp", otpvalue);
			mySession.setAttribute("email", email);

			request.setAttribute("message", "OTP has been sent to your email");

			dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
			dispatcher.forward(request, response);

		} catch (MessagingException e) {
			e.printStackTrace();
			request.setAttribute("status", "emailFailed");
			dispatcher = request.getRequestDispatcher("forgotPassword.jsp");
			dispatcher.forward(request, response);
		}
	}
}
