package com.analyse.tweets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class populatingTweetInfo {

	static TweetTree tT;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		initialiseTweetTree();
		// frequency_UserPlot();
		// frequency_ConversationLength();
		// freq_ConversationDuration();
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time:" + (endTime - startTime) / 1000
				+ " seconds");
	}

	static void initialiseTweetTree() {
		tT = new TweetTree();
		String tweetIdWithMaxReplies = "";
		tT.populateTweetInfo();
		tT.constructTree();
		// initialiseTweetsGlobalVaribale();
		// tT.printTweetTree();
		// tweetIdWithMaxReplies = tT.countConversationWithMaxReplies();
		// tT.printTweetTreeHavingMaxReplies(tweetIdWithMaxReplies);
		// tT.totalConverstionWithAtleastOneReply();
	}

	private static void frequency_ConversationLength() {
		TreeMap<Long, Long> freqConversationPlot = new TreeMap<Long, Long>();
		tT.getFrequencyConversationLengthPlot(freqConversationPlot);
		try {
			// BufferedWriter bW = new BufferedWriter(new FileWriter(new File(
			// GlobalVariables.freqConversationPlotFilePath)));
			BufferedWriter bWx = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqConversationPlotFilePathx)));
			BufferedWriter bWy = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqConversationPlotFilePathy)));

			Iterator itr = freqConversationPlot.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr.next();
				Long conversationCount = (Long) pairs.getKey();
				Long frequencyCount = (Long) pairs.getValue();
				// bW.write(conversationCount + "," + frequencyCount + "\n");
				bWx.write(conversationCount + "\n");
				bWy.write(frequencyCount + "\n");
			}
			// bW.close();
			bWx.close();
			bWy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void frequency_UserPlot() {
		TreeMap<Long, Long> freqUserPlot = new TreeMap<Long, Long>();
		tT.getFreqUserPlot(freqUserPlot);
		try {
			// BufferedWriter bW = new BufferedWriter(new FileWriter(new File(
			// GlobalVariables.freqUserPlotFilePath)));
			BufferedWriter bWx = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqUserPlotFilePathx)));
			BufferedWriter bWy = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqUserPlotFilePathy)));
			Iterator itr = freqUserPlot.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr.next();
				Long userCount = (Long) pairs.getKey();
				Long conversationCount = (Long) pairs.getValue();
				// bW.write(userCount + "," + conversationCount + "\n");
				bWx.write(userCount + "\n");
				bWy.write(conversationCount + "\n");
			}
			// bW.close();
			bWx.close();
			bWy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void freq_ConversationDuration() {
		TreeMap<Long, Long> freqConversationDurationMap = new TreeMap<Long, Long>();
		tT.getfreqConversationDurationPlot(freqConversationDurationMap);
		try {
			// BufferedWriter bW = new BufferedWriter(new FileWriter(new File(
			// GlobalVariables.freqConversationDurationFilePath)));
			BufferedWriter bWx = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqConversationDurationFilePathx)));
			BufferedWriter bWy = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.freqConversationDurationFilePathy)));

			Iterator itr = freqConversationDurationMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr.next();
				Long duration = (Long) pairs.getKey();
				Long frequency = (Long) pairs.getValue();
				// bW.write(duration + "," + frequency + "\n");
				bWx.write(duration + "\n");
				bWy.write(frequency + "\n");
			}
			// bW.close();
			bWx.close();
			bWy.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
