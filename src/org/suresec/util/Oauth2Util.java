package org.suresec.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.suresec.config.CasConfig;

/**
 * 
 * @author wcc
 * @time 2019-09-18 11:12
 * @description oauth2协议--获取token、profile
 */
public class  Oauth2Util {
	/**
	  *  获取getAccessToken
	 * @param grant_type
	 * @param client_secret
	 * @param client_id
	 * @param redirect_uri
	 * @param code
	 * @return
	 */
    public static String getAccessToken(String grant_type,String client_secret, String client_id,String redirect_uri,String code) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(CasConfig.GET_OAUTH_ACCESSTOKEN_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("grant_type", grant_type));
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("client_secret", client_secret));
			params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
			params.add(new BasicNameValuePair("code", code));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status=" + status);
			String accessToken = readResponse(response);
			System.out.println("accessToken---111--------"+accessToken);
//			return accessToken;
			String[] strs = accessToken.split(",");
			String[] tokens = strs[0].split(":");
			System.out.println("accessToken=" + tokens[1].substring(1,tokens[1].length()-1));
			return tokens[1].substring(1,tokens[1].length()-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
         *  获取getProfile
     * @param accessToken
     * @return
     */
    public static String getProfile(String accessToken) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			HttpGet httpPost = new HttpGet(CasConfig.GET_OAUTH_PROFILE_URL+"?access_token="+accessToken);
			
			HttpResponse response = client.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status=" + status);
			String profile = readResponse(response);
			System.out.println("profile=" + profile);
			return profile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
         *  获取tgt
     * @param resp 
     * @param username
     * @param password
     * @return
     */
    public static String getTGT(HttpServletResponse resp, String username, String password) {
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(CasConfig.GET_TOKEN_URL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            Header headerLocation = response.getFirstHeader("Location");
            String location = headerLocation == null ? null : headerLocation.getValue();
            System.out.println("Location：" + location);

            System.out.println("set-cookie:"+response.getFirstHeader("Set-Cookie").getValue());
            //resp.setHeader("Set-Cookie", response.getFirstHeader("Set-Cookie").getValue());
            resp.addHeader("Set-Cookie", response.getFirstHeader("Set-Cookie").getValue());
    		
    		//Cookie cookie = new Cookie(CasConfig.COOKIE_NAME, response.getFirstHeader("Set-Cookie").getValue());
    		//cookie.setMaxAge(CasConfig.COOKIE_VALID_TIME); // Cookie有效时间
    		//cookie.setPath("/"); // Cookie有效路径 
    		//cookie.setHttpOnly(true); //
            //resp.addCookie(cookie);
            System.out.println("cookie_name:"+resp.getHeaderNames());
            if (location != null) {
                return location.substring(location.lastIndexOf("/") + 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
         *  获取ST
     * @param TGT
     * @param service
     * @return
     */
    public static String getST(String TGT, String service){
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(CasConfig.GET_TOKEN_URL + "/" + TGT);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("service", service));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String st = readResponse(response);
            return st == null ? null : (st == "" ? null : st);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
         *  读取 response body 内容为字符串
     * @param response
     * @return
     * @throws IOException
     */
    private static String readResponse(HttpResponse response) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = new String();
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

}
