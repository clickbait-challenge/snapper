package gr.iti.mklab.extractfeatures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.tartarus.snowball.SnowballStemmer;

import weka.core.Instances;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.Morphology;
import gr.iti.mklab.readability.Readability;
import gr.iti.mklab.util.Configuration;
import gr.iti.mklab.util.StanfordLemmatizer;
import gr.iti.mklab.util.TextProcessing;

public class ItemFeaturesExtractorJSON {
	
	
	static String[] tokens;
	static String itemTitle;
	public static Instances isTrainingSet;
	public static List<String> indegreeLines = new ArrayList<String>();
	public static List<String> harmonicLines = new ArrayList<String>();
	public static StanfordLemmatizer stanf;
	
	public ItemFeaturesExtractorJSON(){
 	 	 stanf = new StanfordLemmatizer();
	}

	public static void setItemTitle(String itemTitle) {
		ItemFeaturesExtractorJSON.itemTitle = itemTitle;	
	}
	
	public static List<String> namedEntityList(){
		
		List<String> namedEnt = new LinkedList<String>();
		
		namedEnt.add("LOCATION");
		namedEnt.add("NUMBER");
		namedEnt.add("IDEOLOGY");
		namedEnt.add("MONEY");
		namedEnt.add("PERSON");
		namedEnt.add("SET");
		namedEnt.add("MISC");
		namedEnt.add("TIME");
		namedEnt.add("ORDINAL");
		namedEnt.add("CAUSE_OF_DEATH");
		namedEnt.add("ORGANIZATION");
		namedEnt.add("DATE");
		namedEnt.add("PERCENT");
		namedEnt.add("DURATION");
		namedEnt.add("CRIMINAL_CHARGE");
		
		return namedEnt;
}

	static String text = "";
	
	
	
	public static ItemFeatures extractTextFeatures(String comment, String videoID, String truthClass)
			throws Exception {

 		 ItemFeatures feat = new ItemFeatures();

		 text = comment;
		
		 feat.setText(text);
		 feat.setTruthClass(truthClass);

		String str = text.replaceAll("http+s*+://[^ ]+", "")
				.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
				.replaceAll("RT", "").toLowerCase().trim();

		feat.setId(videoID);
		feat.setItemLength(text.length());
		int numWords = getNumItemWords();
		feat.setNumWords(numWords);
		
		if (numWords > 50){
			String[] splittext = text.split(" ");
			String text_temp ="";
			for (int g =0; g< splittext.length; g++){
				text_temp = text_temp + splittext[g];
			}
			text = text_temp;
			feat.setItemLength(text.length());
			feat.setNumWords(getNumItemWords());
			
		}		
		feat.setContainsQuestionMark(containsSymbol("?"));
		
		feat.setContainsExclamationMark(containsSymbol("!"));

		feat.setnumExclamationMark(getNumSymbol("!"));

		feat.setNumQuestionMark(getNumSymbol("?"));

		feat.setContainsHappyEmo(containsEmo(Configuration.RESOURCES_PATH + "/emoticons/happy-emoticons.txt"));

		feat.setContainsSadEmo(containsEmo(Configuration.RESOURCES_PATH + "/emoticons/sad-emoticons.txt"));

		feat.setNumUppercaseChars(getNumUppercaseChars());		
		feat.setHasColon(containsSymbol(":"));
		feat.setHasPlease(containsSymbol("please"));

		/** Additional features depending on the Post's language **/

		String lang = TextProcessing.getInstance().getLanguage(str);
		feat.setLanguage(lang);
		if (lang.equals("en")) {
			feat.setNumPosSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/positive-words.txt"));
		
			feat.setNumNegSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/negative-words.txt"));
	
			feat.setContainsFirstOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/first-order-prons.txt"));
		
			feat.setContainsSecondOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/second-order-prons.txt"));
	
			feat.setContainsThirdOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/third-order-prons.txt"));
	
			feat.setNumSlangs(getNumSlangs(Configuration.RESOURCES_PATH + "/slang_words/slangwords-english.txt", "en"));
			Double readability = getReadability();
			if (readability != null) 
				feat.setReadability(readability);		
		} else if (lang.equals("es")) {
			feat.setNumPosSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/positive-words-spanish.txt"));
		
			feat.setNumNegSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/negative-words-spanish.txt"));

			feat.setContainsFirstOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/first-order-prons-spanish.txt"));

			feat.setContainsSecondOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/second-order-prons-spanish.txt"));

			feat.setContainsThirdOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/third-order-prons-spanish.txt"));

			feat.setNumSlangs(getNumSlangs(Configuration.RESOURCES_PATH + "/slang_words/slangwords-spanish.txt", "es"));
			// german
		} else if (lang.equals("de")) {
			feat.setNumPosSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/positive-words-german.txt"));

			feat.setNumNegSentiWords(getNumSentiWords(Configuration.RESOURCES_PATH + "/senti_words/negative-words-german.txt"));
		
			feat.setContainsFirstOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/first-order-prons-german.txt"));
		
			feat.setContainsSecondOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/second-order-prons-german.txt"));
		
			feat.setContainsThirdOrderPron(containsPronoun(Configuration.RESOURCES_PATH + "/pronouns/third-order-prons-german.txt"));
		
		}else{
		
		}		
		
		/*
		 * New features
		 */
		
		/*
		 * Stop words percentage
		 */
		int count_stop_words = 0, counter = 0;
    	boolean isStop = false;
    	StringTokenizer st = new StringTokenizer(text);  
	    while (st.hasMoreElements()) {
	    	String word = st.nextElement().toString();
	    	isStop = isStopWord(word.trim());
    		if (isStop){
    			count_stop_words = count_stop_words + 1;
    		}
    		counter = counter + 1; // all  words
	    }
	    double stopPerc = 0.0;
	    if (counter > 0){
	    	 stopPerc = (double) count_stop_words / (double) counter;
	    }
		feat.setStopWordsPercentage(stopPerc);		
		
		// * Begins with digit
		 
		if (!text.isEmpty()){
			boolean isD = Character.isDigit(text.charAt(0));
			if (isD) {
				feat.setBeginsDigit(1);
			}else{
				feat.setBeginsDigit(0);
			}
		}else{
			feat.setBeginsDigit(0);
		}
    	
    	// * Extract part of speech and others    	 
    	
    	 StanfordLemmatizer stanf = new StanfordLemmatizer();
    	 List<String> pos = stanf.pos(text);
    	 feat.setPOS(pos);
    	 
    	 List<String> posTokens = stanf.getTokens(text);
    	 feat.setPOSTokens(posTokens);
    	 
    	 //  Generates the word lemmas for all tokens in the corpus.
		 List<String> lem = stanf.lemmatize(text);
		 feat.setLEM(lem);
		 List<String> pos_temp = new ArrayList<String>();
		 List<String> lempos =  new ArrayList<String>();
		 for (int l=0; l <lem.size(); l++){
			 pos_temp = stanf.pos(lem.get(l));
			 lempos.add(pos_temp.get(0));
		 }
		 feat.setLEMPOS(lempos);
		 
		 // Recognizes named (PERSON, LOCATION, ORGANIZATION, MISC), numerical (MONEY, NUMBER, ORDINAL, PERCENT), and temporal (DATE, TIME, DURATION, SET) entities. 
		 List<String> nea = stanf.namedentityannotation(text);
		 feat.setNEA(nea);
		 feat.setSentiment(stanf.findSentiment(text));    	
		 feat.setHasAT(containsSymbol("@"));
		 feat.setHasHastag(containsSymbol("#"));    	
		 String voice = stanf.findActiveOrPassiveVoice(text);
		   	  feat.setTextVoice(voice);
		   	  if (voice.equals("passive")){
		   		  	feat.setIsPassiveVoice(true); 
		   	  }else{
				 feat.setIsPassiveVoice(false);
		   	  }
		return feat;
	}
	
	
	/**
	 * Sets the file's content to a HashSet<String>.
	 * 
	 * @param file
	 *            String the name of the file.
	 * @return the HashSet<String> which contains the file's data.
	 * @throws IOException
	 */
	public static HashSet<String> loadSetfromFile(String file) throws IOException {
		// load hashmap from file
		HashSet<String> set = new HashSet<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = in.readLine()) != null) {
				set.add(line);
			}
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return set;
	}
	
	public static boolean isStopWord(String str) {
		
		HashSet<String> stop_eng 	= new HashSet<String>();		
		try {
			stop_eng	= loadSetfromFile(Configuration.RESOURCES_PATH + "/stop_words/stop-words-eng.txt");	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (str.length()>2 && !stop_eng.contains(str) ) {
			return false;
		}
		return true;
	}
	
	/**
	 * Calculates the number of words contained in the tweet's text
	 * 
	 * @return Integer the number of words found
	 */
	public static Integer getNumItemWords() {

		// create the string from the tweet's title
		// String itemTitle = item.getTitle().toString();

		// call the tokenizer to get the words of the string

		tokens = TextProcessing.getInstance().tokenizeText(text);
		
		// find the number of words
		Integer numWords = tokens.length;
		//System.out.println("Number of tokens");

		return numWords;

	}

	public static Boolean containsSymbol(String symbol) {

		String str = text.replaceAll("http://[^ ]+", " "); // drop urls

		// check if the text contains the given symbol

		// print info
		// System.out.println("Symbol: " + symbol + " " + str.contains(symbol));

		return str.contains(symbol);
	}

	public static Integer getNumSymbol(String symbol) {
		Integer numSymbols = 0;

		// String itemTitle = item.getTitle().toString();

		// check every single character of text for the given symbol
		for (int i = 0; i < text.length(); i++) {
			Character ch = text.charAt(i);
			if (ch.toString().equals(symbol)) {
				numSymbols++;
			}
		}
		// print info
		// System.out.println("num of " + symbol + ": " + numSymbols);
		return numSymbols;
	}

	public static Boolean containsEmo(String filePath) {
		Boolean containsEmo = false;
		BufferedReader br = null;
		// String itemTitle = item.getTitle().toString();

		// hashset that contains the emoticons from the txt file
		HashSet<String> emoticons = new HashSet<String>();

		try {
			File fileEmoticons = new File(filePath);
			if (!fileEmoticons.exists()) {
				fileEmoticons.createNewFile();
			}
			String currentLine;
			// create the file reader
			br = new BufferedReader(new FileReader(fileEmoticons));
			// read the txt file and add each line to the hash set
			while ((currentLine = br.readLine()) != null) {
				emoticons.add(currentLine);
			}

			// use the iterator to get elements from the hashset
			// check if text contains each of the elements
			Iterator<String> iterator = emoticons.iterator();
			while (iterator.hasNext()) {
				String emo = iterator.next().toString();
				if (text.contains(emo)) {
					containsEmo = true;
				}
			}

			br.close();

			// print info
			//System.out.println("Contains emoticon: " + containsEmo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return containsEmo;

	}

	public static Integer getNumUppercaseChars() {
		Integer numUppercaseChars = 0;
		Character ch = null;

		// drop all URLs, hashtags and mentions ("http://", "#anyhashtag",
		// "@anymention", "@anymentionwithspace")- no need to count the
		// uppercase
		// chars on them

		String str = text.replaceAll("http://[^ ]+", "")
				.replaceAll("@ [^ ]+ ", "").replaceAll("@[^ ]+", "")
				.replaceAll("#[^ ]+", "");

		// count the uppercase chars
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				numUppercaseChars++;
			}
		}
		if (text.contains("RT ") && numUppercaseChars > 1) {
			numUppercaseChars = numUppercaseChars - 2;
		}
		// print info
		// System.out.println("Num of uppercase chars: " + numUppercaseChars);

		return numUppercaseChars;
	}

	public static Boolean containsPronoun(String filePath) {

		Boolean containsPron = false;
		BufferedReader br = null;

		// hash set that contains the words from the txt file
		HashSet<String> pronounWords = new HashSet<String>();

		try {
			File Prons = new File(filePath);
			if (!Prons.exists()) {
				Prons.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(Prons));

			// save to hashset every line of the txt file
			while ((currentLine = br.readLine()) != null) {
				pronounWords.add(currentLine);
			}

			for (int j = 0; j < tokens.length; j++) {
				if (pronounWords.contains(tokens[j].replaceAll("[^A-Za-z0-9 ]",
						"").toLowerCase())) {
					containsPron = true;
				}
			}

			// print info
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return containsPron;
	}

	

	public static Integer getNumSentiWords(String filePath) {
		Integer numSentiWords = 0;
		BufferedReader br = null;
		// String itemTitle = item.getTitle().toString();

		// use hashset to save the words from the txt file
		HashSet<String> sentiwords = new HashSet<String>();
		try {
			File sentiWords = new File(filePath);
			if (!sentiWords.exists()) {
				sentiWords.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(sentiWords));

			while ((currentLine = br.readLine()) != null) {
				sentiwords.add(currentLine);
			}

			for (int i = 0; i < tokens.length; i++) {

				String clearToken = tokens[i].replaceAll("[^A-Za-z0-9 ]", "")
						.toLowerCase();

				if (sentiwords.contains(clearToken)) {
					numSentiWords++;
					// print info
					// System.out.println("Senti word found:" + tokens[i]);
				}
			}

			// print info
			//System.out.println("Number of senti words: " + numSentiWords);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numSentiWords;
	}



	

	public static Integer getNumSlangs(String filePath, String lang)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		// declare the class to use
		Class stemClass = Class
				.forName("org.tartarus.snowball.ext.spanishStemmer");

		// declare the auxiliary variables
		Integer numSlangs = 0, foundCounter = 0, indexHolder = 0, prevIndexHolder = 0, i = 0;

		// array of the tokens of the tweet text
		String[] justTokens = new String[tokens.length];

		// variable to declare the Word attributes (by stanford parser)
		Morphology m = new Morphology();

		// buffered to read the file containing the slangs
		BufferedReader br = null;

		try {
			File slangWords = new File(filePath);
			if (!slangWords.exists()) {
				slangWords.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(slangWords));

			String wrdResult = null;
			// create the hashset that contains the tokens
			for (String token : tokens) {

				token = token.replaceAll("[^A-Za-z0-9 ]", "");
				if (token != null) {
					if (lang == "en") {

						Word wrd = new Word(token);
						wrd = m.stem(wrd);
						wrdResult = wrd.toString();
						// System.out.println(token + " " + wrdResult);

					} else if (lang == "es") {

						SnowballStemmer stemmer = (SnowballStemmer) stemClass
								.newInstance();
						stemmer.setCurrent(token);
						stemmer.stem();
						wrdResult = stemmer.getCurrent();
						// System.out.println(token + " " + wrdResult);

					}
					// titletokens.add(wrdResult);
					justTokens[i] = wrdResult;
					i++;
				}
				// System.out.print(wrdResult+" ");
			}

			// check every line of the file
			while ((currentLine = br.readLine()) != null) {
				String regex = " ";
				if (currentLine.contains("-")) {
					if (currentLine.indexOf("-") != 0
							|| (currentLine.indexOf("-") != (currentLine
									.length() - 1))) {
						regex = "-";
					}
				}
				String[] words = currentLine.split(regex);
				foundCounter = 0;
				indexHolder = 0;
				prevIndexHolder = 0;

				for (String word : words) {

					String prefix = "#";
					if (word.endsWith("-")) {
						prefix = word.replace("-", "");
					}
					String suffix = "!";
					if (word.startsWith("-")) {
						suffix = word.replace("-", "");
					}
					if (lang == "en") {

						Word wrd = new Word(word);
						wrd = m.stem(wrd);
						word = wrd.toString();

					} else if (lang == "es") {
						SnowballStemmer stemmer = (SnowballStemmer) stemClass
								.newInstance();
						stemmer.setCurrent(word);
						stemmer.stem();
						word = stemmer.getCurrent();
						// System.out.println(word+" "+wrdResult);
					}

					for (String token : justTokens) {
						if (token != null) {
							if (token.equals(word) || token.startsWith(prefix)
									|| token.endsWith(suffix)) {
								indexHolder = Arrays.asList(justTokens)
										.indexOf(word);
								// System.out.println(token+" "+word);
								// System.out.println();
								// System.out.println("Found " + word +
								// " with index "+ indexHolder);

								if (indexHolder > prevIndexHolder) {
									prevIndexHolder = indexHolder;
									foundCounter++;
								}
								if (words.length == 1) {
									foundCounter++;
									// System.out.println(foundCounter);
									break;
								}
							}
						}
					}

				}
				// System.out.println(foundCounter);

				if (foundCounter == words.length) {
					numSlangs++;
					// System.out.println("num slangs " + numSlangs+
					// " for phrase ");
					/*for (String p : words) {
						// System.out.println("- " + p);
					}*/
				}

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("NUM TO RETURN " + numSlangs);
		return numSlangs;
	}

	static ArrayList<Integer[]> occurrenceArr = new ArrayList<Integer[]>();

	/**
	 * Function that given a string, a pattern and an initial index, finds the
	 * indexes where the pattern is observed. The method is recursive, given as
	 * initial index for searching, the last index of the previous iteration.
	 * 
	 * @param str
	 *            String that holds the adapted text of the original tweet text
	 * @param pattern
	 *            String that holds the pattern whose existence is checked
	 * @param index
	 *            the start index from where the pattern is searched
	 * @return ArrayList<Integer[]> list of the pairs [startIndex, endIndex] of
	 *         the patern occurrences found
	 */
	public static ArrayList<Integer[]> findPatternOccurrence(String str,
			String pattern, int index) {

		Integer occurStart = str.indexOf("http", index);
		// System.out.println("start " + occurStart);
		// System.out.println("string length "+str.length());
		Integer[] occurs = new Integer[2];
		Integer occurEnd = -1;
		if (occurStart != -1) {
			occurs[0] = occurStart;
			occurEnd = str.indexOf(" ", occurStart);

			if (str.substring(occurStart, occurEnd).contains("\n")) {
				occurEnd = str.indexOf("\n", occurStart);

			}
			// System.out.println("end " + occurEnd);
		}

		if (occurEnd.equals(-1)) {
			occurs[1] = str.length();
			// System.out.println("end again "+occurs[1]);
		} else {
			occurs[1] = occurEnd;
		}
		// System.out.println("string "+itemTitle.substring(occurStart,occurs[1]));

		if (occurs[0] != null && occurs[1] != null
				&& ((occurs[1] - occurs[0]) > 7)) {
			// System.out.println("occurs before added "+occurs[0]+" "+occurs[1]);
			occurrenceArr.add(occurs);
		}
		// System.out.println(occurEnd+" "+occurStart);
		if (occurEnd < str.length() && !occurStart.equals(-1)
				&& !occurEnd.equals(-1)) {
			// System.out.println("hello");
			findPatternOccurrence(str, pattern, occurEnd + 1);

		}

		return occurrenceArr;
	}



	/**
	 * Organizes the procedure of computing the readability of the tweet's text
	 * 
	 * @return Double the calculated value of readability
	 */
	public static Double getReadability() {

		Double readability = null;

		String str = text.replaceAll("http+s*+://[^ ]+", "")
				.replaceAll("#[^ ]+ ", "").replaceAll("@[^ ]+", "");
		str = str.replaceAll("  ", " ");

		if (!str.isEmpty()) {
			Readability r = new Readability(str);
			readability = r.getFleschReadingEase();

		}
		return readability;
	}



	static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
			.create();

	public static synchronized ItemFeatures create(String json) {
		synchronized (gson) {
			ItemFeatures item = gson.fromJson(json, ItemFeatures.class);
			return item;
		}
	}
	
	
	
	

}
