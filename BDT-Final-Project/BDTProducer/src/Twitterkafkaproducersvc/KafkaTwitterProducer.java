package Twitterkafkaproducersvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class KafkaTwitterProducer {

	//static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAALn5cQEAAAAAewEQumcXf1Qp3lbiI6HVSsQ%2B4zo%3DinCPJcQSDvNdH8ldoYTyg23Tjf4XRQQAhqgzUH9Tyqnr76YKIm";
//	static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAAf6cQEAAAAATBtKO4SOyNfpS4krp1sHvD6l%2BpU%3DbBDClbN916jirUFtKlQJijGpD28WXltGUNMWjXbwEG3VZ7uA2Q";
	
	static final String BEARER_TOKEN ="AAAAAAAAAAAAAAAAAAAAABWyqAEAAAAAQKDr0ftuzCPMLWskfnhQ0EVzjkg%3Dm7P7EBjJTiCDz1mFiJpYr9YH5ls1mCkYXOhjitvxIOQYVayhiw";
	public static Log logger = LogFactory.getLog(KafkaTwitterProducer.class);
	
		public static void main(String[] args) {
		    try {
		        // Create an HttpClient
		        HttpClient httpClient = HttpClients.createDefault();

		        // Define the URL to fetch JSON data
		        String url = "https://raw.githubusercontent.com/SarozDev/BDTProjectData/main/BDTTwitter.json";

		        // Create an HTTP GET request
		        HttpGet httpGet = new HttpGet(url);

		        // Execute the request and get the response
		        String jsonResponse = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
		        logger.info("response ; " + jsonResponse);

		        // Parse the JSON using Gson's JsonParser
		        JsonElement jsonElement = JsonParser.parseString(jsonResponse);
		        if (jsonElement.isJsonObject()) {
		            JsonObject jsonObject = jsonElement.getAsJsonObject();
		            if (jsonObject.has("data") && jsonObject.get("data").isJsonArray()) {
		                JsonArray dataArray = jsonObject.getAsJsonArray("data");
		                for (JsonElement tweetElement : dataArray) {
		                    if (tweetElement.isJsonObject()) {
		                        JsonObject tweetObject = tweetElement.getAsJsonObject();
		                        String id = tweetObject.get("id").getAsString();
		                        String text = tweetObject.get("text").getAsString();
		                        System.out.println("ID: " + id);
		                        System.out.println("Text: " + text);
		                        KafkaMessageSender.sendTweetsToKafka(id, text);
		                        //KafkaMessageSender.sendTweetsToKafka(id, text);
		                    }
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}

	
