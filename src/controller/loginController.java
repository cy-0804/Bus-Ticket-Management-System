package controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dto.LoginRequest;

public class loginController {
	private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(json.getString("username"));
        loginRequest.setPassword(json.getString("password"));

        String result = loginService.loginUser(loginRequest);
        response.getWriter().write(result);
    }
}
