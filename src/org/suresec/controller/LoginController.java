package org.suresec.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.suresec.bean.UserCheckResponse;
import org.suresec.config.CasConfig;
import org.suresec.util.Oauth2Util;

@Controller
@RequestMapping("/login")
public class LoginController {
	/**
	 * client_url 重定向oauth2登录（未完成--需要解决跨域问题）
	 * @param redirectURL
	 * @return
	 */
	@RequestMapping("/redirectOauth2Login")
	public String redirectOauth2Login(String redirectURL) {
		System.out.println("redirectURL = "+redirectURL);
		Oauth2Util o = new Oauth2Util();
		//String accessToken = o.getAccessToken(grant_type, client_secret, client_id, redirect_uri, code);
		String redirect = "https://cas.example.org:8443/cas5.3.5/oauth2.0/authorize?response_type=code&client_id=100001&redirect_uri=http://127.0.0.1:8080/client1";
		return "redirect:"+redirect;
	}
	/**
	  *  根据code获取accesstoken--by wcc 20190624
	 * @param grant_type
	 * @param client_secret
	 * @param client_id
	 * @param redirect_uri
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAccessToken")
	public String getAccessToken(String grant_type, String client_secret, String client_id, String redirect_uri, String code) {
		System.out.println("code = "+code);
		Oauth2Util o = new Oauth2Util();
		String accessToken = o.getAccessToken(grant_type, client_secret, client_id, redirect_uri, code);
		return accessToken;
	}
	/**
	 * accesstoken获取profile--by wcc 20190624
	 * @param access_token
	 * @param token_type
	 * @param expires_in
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProfile")
	public String getProfile(String access_token, String token_type, String expires_in) {
		System.out.println("access_token = "+access_token);
		Oauth2Util o = new Oauth2Util();
		String profile = o.getProfile(access_token);
		return profile;
	}
	/**
	 * client login--by wcc 20190624
	 * @param access_token
	 * @param token_type
	 * @param expires_in
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");
        System.out.println("username：" + username + "，password：" + password + "，service：" + service);

		Oauth2Util o = new Oauth2Util();
        // 1、获取 TGT
        String tgt = o.getTGT(response,username, password);
        System.out.println("TGT：" + tgt);

        // 2、获取 ST
        String st = o.getST(tgt, service);
        System.out.println("ST：" + st);

        if (tgt == null || st==null){
            return new ResponseEntity("用户名或密码错误。", HttpStatus.BAD_REQUEST).toString();
        }

        // 3、设置cookie（1小时）
        //Cookie cookie = new Cookie(CasConfig.COOKIE_NAME, username + "@" + tgt);
		/*
		 * Cookie cookie = new Cookie(CasConfig.COOKIE_NAME, tgt+"@127.0.0.1");
		 * cookie.setMaxAge(CasConfig.COOKIE_VALID_TIME); // Cookie有效时间
		 * cookie.setPath("/"); // Cookie有效路径 cookie.setHttpOnly(true); //
		 * 只允许服务器获取cookie response.addCookie(cookie);
		 * System.out.println("cookie="+cookie.toString());
		 * response.addHeader("Set-Cookie", cookie.toString());
		 */
        //response.setHeader("SET-COOKIE", CasConfig.COOKIE_NAME+"="+ username + "@" + tgt  + ";Path=/cas5.3.5/; HttpOnly");


        // 4、将当前用户的TGT信息存储在Redis上
        //tgtServer.setTGT(username, tgt, CasConfig.COOKIE_VALID_TIME);

        // 5、302重定向最后授权
        String redirectUrl = service + "?ticket=" + st;
        UserCheckResponse result = new UserCheckResponse();
        result.setStatus(1);
        result.setData(redirectUrl); 
        //response.setHeader("Access-Control-Allow-Origin","*");
        //response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma,Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
       
        System.out.println("cookie_name:"+response.getHeaderNames());
        System.out.println("cookie_value:"+response.getHeader("Set-Cookie"));
        return result;
	}
	/**
	  *  测试方法
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
