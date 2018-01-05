package com.analyse.tweets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class ChatBot {

	static HashMap<String, LinkedHashSet<String>> root;
	static HashMap<String, TweetInfo> tweetInfo;
	static LinkedHashSet<String> topElements;
	static TweetTree tT;

	public static void main(String[] args) {

		long startTime, endTime;
		startTime = System.currentTimeMillis();
        System.out.println("Initialising TweeTree...");
        initialiseTweetTree();
		endTime = System.currentTimeMillis();
		System.out.println("Total Time For Initialising TweetTree:"
				+ (endTime - startTime) / 1000 + " seconds");

        System.out.println("Start Your Conversation....");
        initialiseChatBot();

		endTime = System.currentTimeMillis();
		System.out.println("Total Time:" + (endTime - startTime) / 1000
				+ " seconds");

	}

	static int lower = 0;
	static int upper = 99;

	static void initialiseTweetTree() {
		tT = new TweetTree();
		tT.populateTweetInfo();
		tT.constructTree();

		// tT.printOnlyTweetsTextInRange(lower, upper, true);
		// tT.printTweetTree();
		// tT.writeTweeTreeInFile();
		initialiseTweetsVaribale();
	}

	private static void initialiseTweetsVaribale() {
		root = tT.root;
		tweetInfo = tT.tweetInfo;
		topElements = tT.topElements;
	}

	static float globalMaxJacardianScore = 0;
	static String closestTweetId = "";

	static long totalEnglishTweet = 1;

	static void initialiseChatBot() {
		Scanner in = new Scanner(System.in);
		String query;
		while (true) {
			// query = "Hello";
			System.out.print("You---> ");
			query = in.nextLine();
			HashSet<String> querySet = new HashSet<String>();
			processQueryTextSet(querySet, query);
			processQuery(query, querySet);
			// System.out.println("-------->>" + totalEnglishTweet);
			totalEnglishTweet = 1;
		}
	}

	static void processQuery(String query, HashSet<String> querySet) {
		Iterator itr = topElements.iterator();
		float normalisedJacardianScore;
		String tweetId;
		while (itr.hasNext()) {
			tweetId = (String) itr.next();
			if (root.get(tweetId).size() > 0) {
				closestTweetId = tweetId;
				globalMaxJacardianScore = calculateSimilarity(query,
						closestTweetId, querySet);
				break;
			}
		}
		while (itr.hasNext()) {
			tweetId = (String) itr.next();
			if (root.get(tweetId).size() > 0) {
				normalisedJacardianScore = calculateSimilarity(query, tweetId,
						querySet);
				// System.out.println("normalisedJacardianScore--->"
				// + normalisedJacardianScore);
				if (normalisedJacardianScore > globalMaxJacardianScore) {
					globalMaxJacardianScore = normalisedJacardianScore;
					closestTweetId = tweetId;
				}
				totalEnglishTweet++;
				calculateSimilarityInReplyTweets(query, tweetId, querySet);
			}
		}

		// System.out.println("Score---->" + globalMaxJacardianScore);
		// System.out.println("\n----->Root Tweet\n"
		// + tweetInfo.get(closestTweetId).tweetText);

        printRandomTweetReply(closestTweetId);
//		for (String replyTweetId : root.get(closestTweetId)) {
//			System.out.println("Bot---> "
//					+ tweetInfo.get(replyTweetId).tweetText);
//			break;
//		}
	}

    static void printRandomTweetReply(String closestTweetId)
    {
        System.out.println("$$$$"+tweetInfo.get(closestTweetId).tweetText);
        int randomNumber = (( int )( Math.random() * 100000 ))%root.get(closestTweetId).size();

        for (String replyTweetId : root.get(closestTweetId)) {
            if(randomNumber == 0) {
                System.out.println("Bot---> "
                        + tweetInfo.get(replyTweetId).tweetText);
                break;
            }
            else
                --randomNumber;
		}
    }
	private static void calculateSimilarityInReplyTweets(String query,
			String tweetId, HashSet<String> querySet) {
		float normalisedJacardianScore;
		for (String childTweetId : root.get(tweetId)) {
			if (root.get(childTweetId).size() > 0) {
				totalEnglishTweet++;
				normalisedJacardianScore = calculateSimilarity(query,
						childTweetId, querySet);
				if (globalMaxJacardianScore < normalisedJacardianScore) {
					globalMaxJacardianScore = normalisedJacardianScore;
					closestTweetId = childTweetId;
				}
				calculateSimilarityInReplyTweets(query, childTweetId, querySet);
			}
		}
	}

	private static float calculateSimilarity(String query, String tweetId,
			HashSet<String> querySet) {
		HashSet<String> tweetSet = tweetInfo.get(tweetId).processedTweetSet;

		HashSet<String> unionSet = new HashSet<String>();
		HashSet<String> intersectionSet = new HashSet<String>();

		unionSet.addAll(tweetSet);
		unionSet.addAll(querySet);

		intersectionSet.addAll(tweetSet);
		intersectionSet.retainAll(querySet);

		float i = intersectionSet.size(), u = unionSet.size();

		if (i == 0.0 || u == 0.0)
			return (float) 0.0;
		else {
			return ((i) / u);
		}
		// if (intersectionSet.size() > 0) {
		// System.out.println("$Size::" + intersectionSet.size() + "::"
		// + unionSet.size() + "::" + "Query-------->" + query);
		// System.out.println("Tweet-------->"
		// + tweetInfo.get(tweetId).tweetText);
		// }
	}

	private static void processQueryTextSet(HashSet<String> processedQuerySet,
			String queryText) {
		// queryText = queryText.replaceAll("[^A-Za-z0-9]", " ");
		String tmp[] = queryText.split(" ");
		for (String rootText : tmp) {
			if (!rootText.equals(""))
				processedQuerySet.add(rootText.trim().toLowerCase());
		}
		// for (String v : processedQuerySet) {
		// System.out.print(v + "~");
		// }
		// System.out.println();
	}
}
