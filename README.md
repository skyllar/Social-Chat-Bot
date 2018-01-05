# Social-Chat-Bot
1. The project extracts conversation from twitter feed and build a social chat bot based on tweets and their comment.
2. The bot is built using Natural Language Processing and various similarity measures.

A Brief Overview:
  
  A. Preprocessing and Data Structures used:
  
      Step1: Extract required fields from 5.5gb corpus.
      Step2: Fetch data from small file and dump tweetId's into adjacency list of their parent TweetId's if available,else 
		       make it as one of the head tweetId's for start of conversation.
      Step3: The adjacency list will store parent child relationship between tweetId' and a Hash Set will store the the head tweetId's for           the start of conversation.

  Usage:-

	    Step1: For creating small file from large file, compile and run class "TrimTweetData.java".
	    Step2: For creating tree from small file, compile and run class "populateTweetInfo.java".
	
 B. Extracting Relevant ChatBot Replies: 
 
	Step1:Fetch tweets information into our memory and construct the tree.
	Step2:Initialize HashSet for every tweet which will contain all the text tokens of pre-processed Tweet's.
	Step3:Traverse tree structure(DFS/BFS),calculate Jaccard's Similarity between tweet Set and query Set using set operations.
	Step4:During traversal, keep track of the tweets with atleast one reply having maxmimum Jaccard's score.
	Step5:Randomly choose a reply tweet for the tweet with maximum similarity.
	
	How To run the code:-
	Step1: Compile and run the class "ChatBot.java".
	(Will take few seconds to load data and contruct tree,and then it waits for the next user query in a loop)
