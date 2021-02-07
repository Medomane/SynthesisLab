package com.sid.kafka.Core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.kafka.Model.Order;
import com.sid.kafka.Model.Token;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MyGlobals {
    public static final String TOPIC = "FACTURATION";
    public static Token getToken() throws Exception {
        String urlParameters  = "client_id=angular-app&grant_type=password&username=med&password=1234";
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        String request = "http://localhost:8080/auth/realms/MySynthesisLab-realm/protocol/openid-connect/token";
        URL url = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
        conn.setUseCaches(false);
        try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        InputStream inputStream;
        if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) inputStream = conn.getInputStream();
        else inputStream = conn.getErrorStream();
        var in = new BufferedReader(new InputStreamReader(inputStream));
        var response = new StringBuilder();
        String currentLine;
        while ((currentLine = in.readLine()) != null) response.append(currentLine);
        in.close();
        return new ObjectMapper().readValue(response.toString().replace("not-before-policy","not_before_policy"),Token.class);
    }

    public static void buy(Order[] orders,Long customerId) throws Exception {
        var token = getToken();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8084/orders/buy/"+customerId);

        String json = new ObjectMapper().writeValueAsString(orders);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Authorization","Bearer "+token.getAccess_token());
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
    }
}
