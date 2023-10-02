package SparkKafka;

import java.util.ArrayList;
import java.util.List;

public class DataProcessingApp {
    
	   static String previousKey = "";
	   static List<String> dataList = new ArrayList<String>();
	   
	   public static void main(String[] args) throws Exception 
	   {      
		   DataProcessorSpark dataProcessor = new DataProcessorSpark();
	       DataResourceManager resourceManager = new DataResourceManager();
	       KafkaDataConsumer dataConsumer = new KafkaDataConsumer(resourceManager);
	       
	       dataConsumer.waitForData(() -> {
	           try {
	               Thread.sleep(3000);
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	           return true;
	       }, (String key, String value, boolean isNewKey) -> {
	           if (previousKey.isEmpty())
	               previousKey = key;
	           if (isNewKey) {
	               try {
	                   if (!dataList.isEmpty())
	                       dataProcessor.processData(previousKey, dataList);
	               } catch (Exception e) { e.printStackTrace(); }
	               
	               previousKey = key;
	               dataList.clear();
	           }
	           dataList.add(value);
	       });
	   }
	}
