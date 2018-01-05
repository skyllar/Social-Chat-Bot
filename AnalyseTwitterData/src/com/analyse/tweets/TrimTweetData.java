package com.analyse.tweets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TrimTweetData {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		compressTweetsData(GlobalVariables.tweetsFilePath,
				GlobalVariables.compressedTweetsFilePath);
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time:" + (endTime - startTime) / 1000
				+ " seconds");
	}

	private static void compressTweetsData(String tweetsFilePath,
			String compressedTweetsFilePath) {

		String line, tweetText, userId;
		JSONParser parser = new JSONParser();
		JSONObject obj1, obj2, obj3;
		Object tweetTextObj;

		try {
			// System.out.println("tweetsFilePath----------->" +
			// tweetsFilePath);
			BufferedReader bR = new BufferedReader(new FileReader(new File(
					tweetsFilePath)));
			BufferedWriter bW = new BufferedWriter(new FileWriter(new File(
					compressedTweetsFilePath)));

			while ((line = bR.readLine()) != null) {
				if (line.length() != 0) {
					obj1 = (JSONObject) parser.parse(line);
					if (obj1 != null) {
						obj2 = (JSONObject) parser.parse((String) obj1
								.get("Data"));

						if (obj2 != null) {

							// System.out.println("TweetId--->"
							// + obj2.get("IdStr"));
							if (obj2.get("IdStr") != null)
								bW.write(obj2.get("IdStr") + "");
							bW.write("\n");

							// System.out.println("CreatedAt----->"
							// + obj2.get("CreatedAt"));
							if (obj2.get("CreatedAt") != null)
								bW.write(obj2.get("CreatedAt") + "");
							bW.write("\n");

							// System.out.println("InReplyToTweetId---->"
							// + obj2.get("InReplyToStatusIdStr"));
							if (obj2.get("InReplyToStatusIdStr") != null)
								bW.write(obj2.get("InReplyToStatusIdStr") + "");
							bW.write("\n");

							// System.out.println(obj2.get("RetweetedStatus"));

							// System.out.println("Tweet Text---->"
							// + obj2.get("Text"));

							tweetTextObj = obj2.get("Text");
							if (tweetTextObj != null) {
								tweetText = tweetTextObj.toString().replaceAll(
										"\n", " ");
								tweetText = tweetText.toString().replaceAll(
										"\r", " ");
								bW.write(tweetText);
							}
							bW.write("\n");

							obj3 = (JSONObject) obj2.get("User");
							if (obj3 != null) {
								// System.out.println("UserId------>"
								// + obj3.get("Id"));
								if (obj3.get("Id") != null)
									bW.write(obj3.get("Id") + "");
							}
							bW.write("\n");
						}
					}
				}
			}

			bW.close();
			bR.close();
		} catch (Exception e ) {
			e.printStackTrace();
		}

	}
}
