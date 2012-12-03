import java.io.IOException;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/*
 * Provides the twitter services to send direct message
 */
public class TwitterServiceImpl {
	private static final String CONSUMER_KEY = "bvLmsZGzID6pSdXOBfRgRA";
	private static final String CONSUMER_SECRET = "O5evkczSPCcDhQv31RiY8rnBZCLzD88PjHI5cyasw";
	private static final String ACCESS_TOKEN = "226825510-bFZsanMDIPH3NJDMdrykDzikmSwEYHop9ZJN63Sm";
	private static final String ACCESS_TOKEN_SECRET = "82XdvxqwEa4AfGQcL8bS9VkRjUVR6YHJPisDhl01Z8";
	
	private static Twitter twitter;
	
	/*
	 * sends direct twitter message
	 */
	public static void sendDirectMessage(String message){

		try{
			if(twitter==null){
				twitter = setup();
			}
			//5aaaaa
			//10aaaaaaaaaa
			//20aaaaaaaaaaaaaaaaaaaa
			//120aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			//120aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			//140aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			sendDirectMessageHelper(message, twitter);
		}catch(TwitterException te){
			System.out.println("TwitterException: " + te.getMessage());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}
	/*
	 * sets up the twitter connection
	 */
	private static Twitter setup() throws TwitterException, IOException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		TwitterFactory factory = new TwitterFactory(cb.build());
		Twitter twitter = factory.getInstance();
		return twitter;
	  }
	
	/*
	 * Helper for updating the twitter status
	 */
	private static void sendDirectMessageHelper( String message, Twitter twitter ) throws TwitterException{
		if(message.length() > 140 ){
			int numberOfMessages = message.length()/140;
			if(message.length() % 140 != 0){
				numberOfMessages++;
			}
			int startIndex = 0;
			int endIndex = 140;
			for(int i=0;i<numberOfMessages;i++){
				twitter.sendDirectMessage("shangsterwu", message.substring(startIndex, endIndex));
				startIndex = endIndex;
				endIndex = endIndex+140;
				if( endIndex>message.length()){
					endIndex = message.length();
				}
			}
		} else{
			twitter.sendDirectMessage("shangsterwu", message);
		}
		
	}
}
