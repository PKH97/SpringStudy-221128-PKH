package com.study.servlet.account;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.Domain.User;
import com.study.repository.UserRepository;
import com.study.service.AccountService;
import com.study.util.DTO;


@WebServlet("/auth/register")
public class RegisterApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> registerParams = DTO.getParms(request);
		AccountService accountService = AccountService.getInstance(); //싱글톤
		
		if(AccountService.getInstance().isDuplicateUsername(registerParams.get("username"))) {
			System.out.println("아이디가 중복되었습니다.");
			request.getRequestDispatcher("/WEB-INF/account/error_username.html").forward(request, response);
			return;
		}
		
		System.out.println("가입 가능한 아이디입니다.");
		User user = User.builder()
					.username(registerParams.get("username"))
					.password(registerParams.get("password"))
					.name(registerParams.get("name"))
					.email(registerParams.get("email"))
					.roles("ROLE_USER, ROLE_ADMIN")
					.build();
							
		accountService.register(user);
		
		UserRepository.getInstance().showUserAll();
		
		response.sendRedirect("/login");
	
	}
}
