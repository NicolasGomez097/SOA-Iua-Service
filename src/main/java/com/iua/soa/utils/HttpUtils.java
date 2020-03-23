package com.iua.soa.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static String getMethod(String url_request) throws Exception{
		 URL url = new URL(url_request);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Accept", "application/json");
         if (conn.getResponseCode() != 200) {
             throw new RuntimeException("Failed : HTTP Error code : "
                     + conn.getResponseCode());
         }
         InputStreamReader in = new InputStreamReader(conn.getInputStream());
         BufferedReader br = new BufferedReader(in);
         String aux;
         String output = "";
         while ((aux = br.readLine()) != null) {
             output.concat(aux);
         }
         conn.disconnect();
         return output;
	}
	
	public static String postMethod(String url_request, String body) throws Exception{
		URL url = new URL(url_request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);           
        }
        
        if (conn.getResponseCode() != 201) {
            return null;
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String aux;
        String output = "";
        while ((aux = br.readLine()) != null) {
            output += aux;
        }
        conn.disconnect();
        return output;
	}
}
