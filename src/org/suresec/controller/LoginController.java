package org.suresec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.suresec.util.Oauth2Util;

@Controller
@RequestMapping("/login")
public class LoginController {
	@ResponseBody
	@RequestMapping("/getAccessToken")
	public String getAccessToken(String grant_type, String client_secret, String client_id, String redirect_uri, String code) {
		System.out.println("code = "+code);
		Oauth2Util o = new Oauth2Util();
		String accessToken = o.getAccessToken(grant_type, client_secret, client_id, redirect_uri, code);
		return accessToken;
	}
	@ResponseBody
	@RequestMapping("/getProfile")
	public String getProfile(String access_token, String token_type, String expires_in) {
		System.out.println("access_token = "+access_token);
		Oauth2Util o = new Oauth2Util();
		String profile = o.getProfile(access_token);
		return profile;
	}
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
