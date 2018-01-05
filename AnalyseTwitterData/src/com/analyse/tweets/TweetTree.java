package com.analyse.tweets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class TweetTree {

	HashMap<String, LinkedHashSet<String>> root;
	HashMap<String, TweetInfo> tweetInfo;
	LinkedHashSet<String> topElements;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss Z yyyy");

	public Date getDateObject(String dateString) {
		simpleDateFormat.setLenient(true);
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
		}
		return date;
	}

	private long getTimeDiffInMinutes(Date date1, Date date2) {
		long minutes = (date2.getTime() - date1.getTime()) / 60000;
		return minutes;
	}

	public void populateTweetInfo() {
		tweetInfo = new HashMap<String, TweetInfo>();
		root = new HashMap<String, LinkedHashSet<String>>();
		topElements = new LinkedHashSet<String>();

		LinkedHashSet<String> temp;
		TweetInfo tI;
		try {
			BufferedReader bR = new BufferedReader(new FileReader(new File(
					GlobalVariables.compressedTweetsFilePath)));

			String line;
			int i = 0;
			while ((line = bR.readLine()) != null) {
				// System.out.println("TweetId-------->" + line);
				tI = new TweetInfo();
				// System.out.println(line);
				// tI.createdAt = bR.readLine().trim();
				String creationTime = bR.readLine().trim();
				// System.out.println(tI.createdAt);
				tI.tweetTime = getDateObject(creationTime);

				tI.parentTweetId = bR.readLine();
				if (tI.parentTweetId != null && !tI.parentTweetId.equals(""))
					tI.parentTweetId = tI.parentTweetId.trim();
				// System.out.println("ParentTweetId------>" +
				// tI.parentTweetId);
				tI.tweetText = bR.readLine();
				// System.out.println(tI.tweetText);
				tI.userId = bR.readLine();
				if (tI.userId != null && !tI.userId.equals(""))
					tI.userId = tI.userId.trim();
				// System.out.println(tI.userId);

				if (tI.tweetText != null && !tI.tweetText.equals("")) {
					// processTweetTextVector(tI.processedTweetVector,
					// tI.tweetText);
					processTweetTextSet(tI.processedTweetSet, tI.tweetText);
					tweetInfo.put(line.trim(), tI);
					temp = new LinkedHashSet<String>();
					root.put(line.trim(), temp);
				}
				// if (i == 2)
				// break;
				// i++;
			}
			bR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void processTweetTextSet(HashSet<String> processedTweetSet,
			String tweetText) {
		 tweetText = tweetText.replaceAll("[^A-Za-z0-9]", " ");
		//tweetText = preProcessTweetText(tweetText);
		String tmp[] = tweetText.split(" ");
		for (String rootText : tmp) {
			rootText = rootText.trim();
			if (!rootText.equals(""))
				processedTweetSet.add(rootText.toLowerCase());
		}
		// for (String v : processedTweetSet) {
		// System.out.print(v + "~");
		// }
		// System.out.println();
	}

	// private String preProcessTweetText(String tweetText) {
	//
	// StringBuilder stringBuilder = new StringBuilder();
	// int i = 0;
	//
	// if (tweetText.charAt(0) == 'R' && tweetText.charAt(1) == 'T') {
	// i = 2;
	// }
	//
	// for (; i < tweetText.length(); i++) {
	//
	// char ch = tweetText.charAt(i);
	//
	// if (ch == '@' || ch == '#') {
	//
	// i++;
	//
	// if (i == tweetText.length())
	// break;
	//
	// char c = tweetText.charAt(i);
	//
	// while (Character.isLetterOrDigit(c) || c == '_') {
	// i++;
	//
	// if (i == tweetText.length())
	// break;
	//
	// c = tweetText.charAt(i);
	//
	// }
	//
	// } else if (tweetText.substring(i).startsWith("http://")
	// || tweetText.substring(i).startsWith("https://")) {
	//
	// char c = tweetText.charAt(i);
	//
	// while (c != ' ') {
	// i++;
	//
	// if (i == tweetText.length())
	// break;
	//
	// c = tweetText.charAt(i);
	//
	// }
	//
	// } else if (!(((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') ||
	// Character
	// .isDigit(ch))
	// || ch == ':'
	// || ch == ';'
	// || ch == '('
	// || ch == ')' || ch == ',' || ch == ' ')) {
	//
	// } else {
	// stringBuilder.append(ch);
	// }
	//
	// }
	//
	// return new String(stringBuilder).replaceAll("  ", " ").trim()
	// .toLowerCase();
	// }

	private void processTweetTextVector(Vector<String> processedTweetVector,
			String tweetText) {
		tweetText = tweetText.replaceAll("[^A-Za-z0-9]", " ");
		String tmp[] = tweetText.split(" ");
		for (String rootText : tmp) {
			if (!rootText.equals(""))
				processedTweetVector.add(rootText.trim());
		}
		// for (String v : processedTweetVector) {
		// System.out.print(v + "~");
		// }
		// System.out.println();
	}

	private void printChildren(String tweetId) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;
		System.out.print("(");
		int len = children.size();
		while (itr.hasNext()) {
			len--;
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			System.out.print(temp);
			// if (children.size() == 1)
			// break;
			// System.out.print("(");
			printChildren(temp);
			// System.out.print(")");
			if (len > 0)
				System.out.print(",");
		}
		System.out.print(")");
	}

	public void printTweetTree() {
		Iterator<String> itr = topElements.iterator();
		String tweetId;
		while (itr.hasNext()) {
			tweetId = itr.next();
			if (tweetId != null && !tweetId.equals("")) {
				// if (root.get(tweetId).size() > 0) {
				System.out.println("\nNew Conversation.................");
				System.out.print(tweetId);
				printChildren(tweetId);
				System.out.println("\nEnd of Conversation..................");
				// break;
				// }
			}
		}
	}

	long totalActualRoots = 0;

	public void addTweetToTree(String tweetId, String parentTweetId) {
		LinkedHashSet<String> temp;
		if (parentTweetId == null || parentTweetId.equals("")) {
			// totalActualRoots++;
			topElements.add(tweetId);
		} else {
			if (root.containsKey(parentTweetId)) {
				// System.out.println("tweetId:" + tweetId + "::parentId::"
				// + parentTweetId);
				root.get(parentTweetId).add(tweetId);
				// topElements.add(parentTweetId);
				if (topElements.contains(tweetId)) {
					topElements.remove(tweetId);
				}
			}
		}

	}

	public void constructTree() {

		String tweetId, parentTweetId;
		TweetInfo tI;
		Iterator it = tweetInfo.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			tweetId = (String) pairs.getKey();
			tI = (TweetInfo) pairs.getValue();
			parentTweetId = tI.parentTweetId;
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
			// System.out.println(tweetId + "--------->" + parentTweetId);
			addTweetToTree(tweetId, parentTweetId);
		}
		// System.out.println("\nTotal Actual Roots--->" + totalActualRoots);
	}

	void totalConverstionWithAtleastOneReply() {
		Iterator itr = topElements.iterator();
		long totalConversation = 0;
		while (itr.hasNext()) {
			String tweetId = (String) itr.next();
			if (root.get(tweetId).size() >= 1)
				totalConversation++;
		}
		System.out.println("Total Conversation With Atleast 1 Reply--->"
				+ totalConversation);
	}

	long localMax = 0;

	private void countChildrens(String tweetId) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			localMax++;
			countChildrens(temp);
		}
	}

	String countConversationWithMaxReplies() {
		Iterator<String> itr = topElements.iterator();
		String tweetId;
		long currentMax = 0;
		String currentTweetId = "";
		while (itr.hasNext()) {
			tweetId = itr.next();
			if (tweetId != null && !tweetId.equals("")) {
				if (root.get(tweetId).size() > 0) {
					// System.out.println("\nNew Conversation.................");
					// System.out.print(tweetId);
					localMax = 0;
					countChildrens(tweetId);
					if (localMax > currentMax) {
						currentMax = localMax;
						currentTweetId = tweetId;
					}
					// System.out
					// .println("\nEnd of Conversation..................");
					// break;
				}
			}
		}
		System.out.println("\nConversationWithMaxReplies---->" + currentMax);
		return currentTweetId;
	}

	public void printTweetTreeHavingMaxReplies(String tweetId) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;
		System.out.print("(");
		int len = children.size();
		while (itr.hasNext()) {
			len--;
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			System.out.print(temp);
			// if (children.size() == 1)
			// break;
			// System.out.print("(");
			printTweetTreeHavingMaxReplies(temp);
			// System.out.print(")");
			if (len > 0)
				System.out.print(",");
		}
		System.out.print(")");
	}

	Long currentUsers;

	public void getFreqUserPlot(TreeMap<Long, Long> freqUserPlot) {
		Iterator<String> itr = topElements.iterator();
		String tweetId;
		LinkedHashSet<String> userAlreadyTweeted = new LinkedHashSet<String>();
		while (itr.hasNext()) {
			tweetId = itr.next();
			if (tweetId != null && !tweetId.equals("")) {
				currentUsers = (long) 1;
				userAlreadyTweeted.add(tweetInfo.get(tweetId).userId);
				freqUserPlotHelper(tweetId, userAlreadyTweeted);

				if (freqUserPlot.containsKey(currentUsers)) {
					Long temp = freqUserPlot.get(currentUsers) + 1;
					freqUserPlot.put(currentUsers, temp);
				} else {
					freqUserPlot.put(currentUsers, (long) 1);
				}
			}
			userAlreadyTweeted.clear();
		}
	}

	private void freqUserPlotHelper(String tweetId,
			LinkedHashSet<String> userAlreadyTweeted) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			if (!userAlreadyTweeted.contains(tweetInfo.get(temp).userId)) {
				currentUsers++;
				userAlreadyTweeted.add(tweetInfo.get(temp).userId);
			}

			freqUserPlotHelper(temp, userAlreadyTweeted);
		}
	}

	long currentConversations;

	public void getFrequencyConversationLengthPlot(
			TreeMap<Long, Long> freqConversationPlot) {
		Iterator<String> itr = topElements.iterator();
		String tweetId;
		while (itr.hasNext()) {
			tweetId = itr.next();
			if (tweetId != null && !tweetId.equals("")) {
				currentConversations = 1;
				getFrequencyConversationLengthPlotHelper(tweetId);

				if (freqConversationPlot.containsKey(currentConversations)) {
					Long temp = freqConversationPlot.get(currentConversations) + 1;
					freqConversationPlot.put(currentConversations, temp);
				} else {
					freqConversationPlot.put(currentConversations, (long) 1);
				}
			}

		}

	}

	private void getFrequencyConversationLengthPlotHelper(String tweetId) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			currentConversations++;
			getFrequencyConversationLengthPlotHelper(temp);
		}
	}

	Date currentLatestDateObject;

	public void getfreqConversationDurationPlot(
			TreeMap<Long, Long> freqConversationDurationMap) {

		Iterator<String> itr = topElements.iterator();
		String tweetId;
		long currentDuration;
		while (itr.hasNext()) {
			tweetId = itr.next();
			if (tweetId != null && !tweetId.equals("")) {

				// String firstTweetDuration = tweetInfo.get(tweetId).createdAt;

				simpleDateFormat.setLenient(true);
				currentLatestDateObject = tweetInfo.get(tweetId).tweetTime;
				getfreqConversationDurationPlotHelper(tweetId);

				currentDuration = getTimeDiffInMinutes(
						tweetInfo.get(tweetId).tweetTime,
						currentLatestDateObject);

				if (freqConversationDurationMap.containsKey(currentDuration)) {
					Long temp = freqConversationDurationMap
							.get(currentDuration) + 1;
					freqConversationDurationMap.put(currentDuration, temp);
				} else {
					freqConversationDurationMap.put(currentDuration, (long) 1);
				}
			}

		}
	}

	private void getfreqConversationDurationPlotHelper(String tweetId) {
		LinkedHashSet<String> children = root.get(tweetId);
		Iterator<String> itr = children.iterator();
		String temp;
		if (tweetId == null || tweetId.equals("") || children.isEmpty())
			return;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp == null || temp.equals(""))
				continue;
			// currentMaxDuration
			if (currentLatestDateObject.before(tweetInfo.get(temp).tweetTime))
				currentLatestDateObject = tweetInfo.get(temp).tweetTime;

			getfreqConversationDurationPlotHelper(temp);
		}
	}

	public void writeTweeTreeInFile() {
		try {
			BufferedWriter bW = new BufferedWriter(new FileWriter(new File(
					GlobalVariables.treeCoversationInBracketsFilePath)));
			Iterator<String> itr = topElements.iterator();
			String tweetId;
			while (itr.hasNext()) {
				tweetId = itr.next();
				if (tweetId != null && !tweetId.equals("")) {
					if (root.get(tweetId).size() > 0) {
						// System.out.println("\nNew Conversation.................");

						bW.write("(" + tweetId);
						writeChildren(tweetId, bW);
						bW.write(")\n\n");
						// System.out
						// .println("\nEnd of Conversation..................");
						// break;
					}
				}
			}
			bW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeChildren(String tweetId, BufferedWriter bW) {
		try {
			LinkedHashSet<String> children = root.get(tweetId);
			Iterator<String> itr = children.iterator();
			String temp;
			if (tweetId == null || tweetId.equals("") || children.isEmpty())
				return;
			// System.out.print("(");
			bW.write("(");
			int len = children.size();
			while (itr.hasNext()) {
				len--;
				temp = itr.next();
				if (temp == null || temp.equals(""))
					continue;
				// System.out.print(temp);
				bW.write(temp);
				writeChildren(temp, bW);
				// System.out.print(")");
				if (len > 1) {
					// System.out.print(",");
					bW.write(",");
				}
			}
			// System.out.print(")");
			bW.write(")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printOnlyTweetsTextInRange(int lower, int upper, boolean dump) {
		BufferedWriter bW = null;
		try {
			if (dump == true) {
				bW = new BufferedWriter(new FileWriter(new File(
						GlobalVariables.onlyTweetText)));
			}
			int current = 0;
			for (String tweetId : tweetInfo.keySet()) {
				if (current >= lower && current < upper) {
					TweetInfo tI = tweetInfo.get(tweetId);
					if (dump == true) {
						bW.write(tI.tweetText + "\n\n");
						System.out
								.println("#########################################################");
					}
					// else
					// System.out.println("----->" + tI.tweetText);
				} else if (current > upper)
					return;

				current++;
			}
			if (dump == true)
				bW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
