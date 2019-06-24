package org.suresec.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.suresec.config.CasConfig;


public class  Oauth2Util {
	public static void main(String[] args) {
		
	}
	 /**
     * 获取getAccessToken
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
     * 获取getProfile
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
     * 读取 response body 内容为字符串
     *
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
