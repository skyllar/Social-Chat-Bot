Explanation of Q1:
	Step1: Extracted required fields from 5.5gb corpus and dump it into small file "compressedTweets.txt" of 137mb.
	Step2: Now, again fetch data from small file and dump tweetId's into adjacency list of their parent TweetId's if available,else 
		   make it as one of the head tweetId's for start of conversation.The adjacency list will store parent child relationship between tweetId's
		   and a Hash Set will store the the head tweetId's for the start of conversation.
	
	How To run the code:-
	Step1: For creating small file from large file, compile and run class "TrimTweetData.java".
	Step2: For creating tree from small file, compile and run class "populateTweetInfo.java".
	
--------------------------------------------------------------------------------------------------------------------------

Description of Q2:
	1.For plotting graph,we need to do Depth Order Traversal or Breadth Order Traversal to take count of various parameters 
	required for constructing graph.
	2.Results obtained are there in .pdf and .xlsx files attached with the code.

--------------------------------------------------------------------------------------------------------------------------

Approach for Q3:
	Step1:Fetch tweets information into our memory and construct the tree.
	Step2:Initialize HashSet for every tweet which will contain all the text tokens of pre-processed Tweet's.
	Step3:Traverse tree structure(DFS/BFS),calculate Jaccard's Similarity between tweet Set and query Set using set operations.
	Step4:During traversal,keep track of the tweets with atleast one reply having maxmimum Jaccard's score.
	Step5:Randomly choose a reply tweet for the tweet with maximum similarity.
	
	How To run the code:-
	Step1: Compile and run the class "ChatBot.java".
	(Will take few seconds to load data and contruct tree,and then it waits for the next user query in a loop)

---------------------------------------------------------------------------------------------------------------------------

Answer to Q4:

