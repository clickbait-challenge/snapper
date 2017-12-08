package gr.iti.mklab.classification;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import gr.iti.mklab.extractfeatures.ItemFeatures;
import gr.iti.mklab.util.Configuration;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class ItemClassifierOne {
	
	private Instances isTrainingSet;
	private ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
	private List<String> gidlist;
	public boolean verbose = false;
	public int embeddingSize = 300;
	public static boolean featSelect = false;

	private static List<String> posList, namedEnt;
	
	//constructor
	public ItemClassifierOne(List<String> features) {
		partOfSpeechList();
		namedEntityList();
	   	try (Stream<String> lines = Files.lines(Paths.get(Configuration.RESOURCES_PATH + "/general_inquirer_dictionary/GID.txt"))) {
	   		gidlist = lines.collect(Collectors.toList());
	   	} catch (IOException e) {
	   	    System.out.println("Failed to load file.");
	   	}
		setFvAttributes(declareAttributes(features));
	}
	
	
	public ArrayList<Attribute> getFvAttributes() {
		return fvAttributes;
	}
	
	public void setFvAttributes(ArrayList<Attribute> list) {
		this.fvAttributes = list;
	}
	
	public Instances getIsTrainingSet() {
		return isTrainingSet;
	}

	public void setIsTrainingSet(Instances isTrainingSet) {
		this.isTrainingSet = isTrainingSet;
	}
	
	public static void partOfSpeechList(){
		
		posList = new LinkedList<String>();		
				posList.add("CC");
				posList.add("CD");
				posList.add("DT");
				posList.add("EX");
				posList.add("FW");
				posList.add("IN");
				posList.add("JJ");
				posList.add("JJR");
				posList.add("JJS");
				posList.add("LS");
				posList.add("MD");
				posList.add("NN");
				posList.add("NNS");
				posList.add("NNP");
				posList.add("NNPS");
				posList.add("PDT");
				posList.add("POS");
				posList.add("PRP");
				posList.add("PRP$");
				posList.add("RB");
				posList.add("RBR");
				posList.add("RBS");
				posList.add("SYM");
				posList.add("TO");
				posList.add("UH");
				posList.add("VB");
				posList.add("VBD");
				posList.add("VBG");
				posList.add("VBN");
				posList.add("VBP");
				posList.add("VBZ");
				posList.add("WDT");
				posList.add("WP");
				posList.add("WP$");
				posList.add("WRB");
				posList.add("#");
				posList.add("$");
				posList.add("''");
				posList.add("(");
				posList.add(")");
				posList.add(",");
				posList.add(":");
				posList.add("``");	
	}
	
	
		public static void namedEntityList(){
		
					namedEnt = new LinkedList<String>();				
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
	
	public int boolToInt(boolean b) {
	    return Boolean.compare(b, false);
	}
	
	public Instances createDataSetOne(ItemFeatures listTest, 
		List<String> featurestemp) {

		// Create an empty training set
		//System.out.println(fvAttributes);
		Instances isTestSet = new Instances("Rel", fvAttributes,
				1);
		// Set class index
		isTestSet.setClassIndex(fvAttributes.size() - 1);
		isTestSet.add(createInstance(listTest, 			
				 featurestemp));
		
		return isTestSet;
	}
	private ArrayList<Attribute> declareAttributes(List<String> features) {
		// declare attributes
		Attribute ItemLength = null, numWords = null, numQuestionMark = null, numExclamationMark = null, 
				numUppercaseChars = null, numPosSentiWords = null,
		numNegSentiWords = null, numSlangs = null, numNouns = null, readability = null,
				containsQuestionMark = null, containsExclamationMark = null, containsHappyEmo = null,
						containsSadEmo = null, containsFirstOrderPron = null, containsSecondOrderPron= null,
								containsThirdOrderPron = null, hasColon = null, hasPlease = null,
	averageLeghtOfEachWord = null, numberOfHyperbolicTerms = null,
				numberOfCommonWords = null, subjectOfTitle = null, presenceOfWordContractions =  null, presenceOfDeterminersAndPronouns = null,
						presenceOfPunctuations = null, longestDependency = null, normalNgramsBayes1 = null, normalNgramsBayes2 = null, normalNgramsBayes3 = null,
								bagOfWords = null, ngram = null;
		Attribute hasMedia = null,
		beginsDigit = null,
		bagOfWordsLem = null, // BoW of LEM
		NgramLem = null,
		bagOfWordsPos = null,
		NgramPos = null,
		stopWordsPercentages = null,
		sentiment = null,
		hasHastag = null,
		hasAT = null,
		textVoice = null,
		isPassiveVoice = null,
				numAdjectives = null, numVerbs= null, numAdverbs= null;
	
	
		List<String> fvClass = new ArrayList<String>(2);
		fvClass.add("no-clickbait");
		fvClass.add("clickbait");
		Attribute ClassAttribute = new Attribute("theClass", fvClass);
	
		ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();	
		
		if (features.contains("item_length")){
			 ItemLength = new Attribute("item_length");
			 fvAttributes.add(ItemLength);
		}
		if (features.contains("num_words")){
			 numWords = new Attribute("num_words");
			 fvAttributes.add(numWords);
		}
		
		if (features.contains("num_questionmark")){
			 numQuestionMark = new Attribute("num_questionmark");			 
			 fvAttributes.add(numQuestionMark);				
		}
		
		if (features.contains("num_exclamationmark")){		
			numExclamationMark = new Attribute("num_exclamationmark");		
			fvAttributes.add(numExclamationMark);
			
		}
		
		if (features.contains("num_uppercasechars")){	
			 numUppercaseChars = new Attribute("num_uppercasechars");
			 fvAttributes.add(numUppercaseChars);		 
		}
		
		// declare nominal attributes
				if (features.contains("contains_question_mark")){
						List<String> fvnominal1 = new ArrayList<String>(2);
						fvnominal1.add("true");
						fvnominal1.add("false");
						containsQuestionMark = new Attribute("contains_question_mark",
								fvnominal1);
						 fvAttributes.add(containsQuestionMark);
				}
				if (features.contains("contains_exclamation_mark")){
						List<String> fvnominal2 = new ArrayList<String>(2);
						fvnominal2.add("true");
						fvnominal2.add("false");
						 containsExclamationMark = new Attribute(
								"contains_exclamation_mark", fvnominal2);
							fvAttributes.add(containsExclamationMark);
				}
				
				if (features.contains("contains_first_order_pron")){
					List<String> fvnominal5 = new ArrayList<String>(2);
					fvnominal5.add("true");
					fvnominal5.add("false");
					 containsFirstOrderPron = new Attribute(
							"contains_first_order_pron", fvnominal5);
					 fvAttributes.add(containsFirstOrderPron);						
				}
	
			if (features.contains("contains_second_order_pron")){
					List<String> fvnominal6 = new ArrayList<String>(2);
					fvnominal6.add("true");
					fvnominal6.add("false");
					 containsSecondOrderPron = new Attribute(
							"contains_second_order_pron", fvnominal6);
					 fvAttributes.add(containsSecondOrderPron);						
			}
			
			if (features.contains("contains_third_order_pron")){
					List<String> fvnominal7 = new ArrayList<String>(2);
					fvnominal7.add("true");
					fvnominal7.add("false");
					 containsThirdOrderPron = new Attribute(
							"contains_third_order_pron", fvnominal7);
					 fvAttributes.add(containsThirdOrderPron);
			}	
			
			if (features.contains("average_leght_of_each_word")){
				 averageLeghtOfEachWord = new Attribute("average_leght_of_each_word");
				 fvAttributes.add(averageLeghtOfEachWord);
			}
			
			if (features.contains("longest_dependency")){
				 longestDependency = new Attribute("longest_dependency");
					fvAttributes.add(longestDependency);	
				}
			
			if (features.contains("presence_of_determiners_and_pronouns")){
				 presenceOfDeterminersAndPronouns = new Attribute("presence_of_determiners_and_pronouns");
					fvAttributes.add(presenceOfDeterminersAndPronouns);				
			}
			
			if (features.contains("number_of_common_words")){
				numberOfCommonWords = new Attribute("number_of_common_words");
				fvAttributes.add(numberOfCommonWords); 			
			}
		
		if (features.contains("num_slangs")){	
			 numSlangs = new Attribute("num_slangs");
			 fvAttributes.add(numSlangs); 
		}
		
		if (features.contains("readability")){	
			 readability = new Attribute("readability");
			 fvAttributes.add(readability); 
		}		
	
		if (features.contains("has_colon")){		
				List<String> fvnominal8 = new ArrayList<String>(2);
				fvnominal8.add("true");
				fvnominal8.add("false");
				 hasColon = new Attribute("has_colon", fvnominal8);
				 fvAttributes.add(hasColon); 
		}
		
		if (features.contains("has_please")){
				List<String> fvnominal9 = new ArrayList<String>(2);
				fvnominal9.add("true");
				fvnominal9.add("false");
				 hasPlease = new Attribute("has_please", fvnominal9);
				 fvAttributes.add(hasPlease); 
		}	
		
		if (features.contains("presence_of_punctuations")){
			 presenceOfPunctuations = new Attribute("presence_of_punctuations");
				fvAttributes.add(presenceOfPunctuations);			
			}
		
		if (features.contains("begins_with_digit")){
			beginsDigit = new Attribute("begins_with_digit");
			 fvAttributes.add(beginsDigit); // new
		}
		
		if (features.contains("hasHastag")){		
			List<String> fvnominalHastag = new ArrayList<String>(2);
			fvnominalHastag.add("true");
			fvnominalHastag.add("false");
			hasHastag = new Attribute("hasHastag", fvnominalHastag);
			 fvAttributes.add(hasHastag); // new
		}
		
		if (features.contains("hasAT")){		
			List<String> fvnominalAT = new ArrayList<String>(2);
			fvnominalAT.add("true");
			fvnominalAT.add("false");
			hasAT = new Attribute("hasAT", fvnominalAT);
			 fvAttributes.add(hasAT); // new
		}
		
		if (features.contains("presence_of_word_contractions")){
			presenceOfWordContractions = new Attribute("presence_of_word_contractions");
			fvAttributes.add(presenceOfWordContractions);		
		}
		
		if (features.contains("num_nouns")){	
			 numNouns = new Attribute("num_nouns");
				fvAttributes.add(numNouns); 				
		}
		
		
		if (features.contains("POSperc")){
			for (int k=0; k < posList.size(); k++){
				fvAttributes.add(new Attribute("attrPOSperc" + posList.get(k)));
			}
		}
		
		if (features.contains("NEAperc")){
			for (int k=0; k < namedEnt.size(); k++){
				fvAttributes.add(new Attribute("attrNEAperc" + namedEnt.get(k)));
			}
		}
	
		if (features.contains("stopWordsPercentage")){
			stopWordsPercentages = new Attribute("stopWordsPercentages");
				fvAttributes.add(stopWordsPercentages); // new
		}
		
		if (features.contains("textVoice")){
			List<String> fvnominalTextVoie = new ArrayList<String>(4);
			fvnominalTextVoie.add("passive");
			fvnominalTextVoie.add("active");
			fvnominalTextVoie.add("both");
			fvnominalTextVoie.add("none");
			textVoice = new Attribute("textVoice",
					fvnominalTextVoie);
			 fvAttributes.add(textVoice);
		}
		
		if (features.contains("isPassiveVoice")){		
			List<String> fvnominalPassive = new ArrayList<String>(2);
			fvnominalPassive.add("true");
			fvnominalPassive.add("false");
			isPassiveVoice = new Attribute("isPassiveVoice", fvnominalPassive);
			 fvAttributes.add(isPassiveVoice); // new
		}
		
		
		if (features.contains("subject_of_title")){
			subjectOfTitle = new Attribute("subject_of_title");
			fvAttributes.add(subjectOfTitle);		
		}
		
		
		if (features.contains("num_adjectives")){	
			numAdjectives = new Attribute("num_adjectives");
				fvAttributes.add(numAdjectives); 			
		}
		
		if (features.contains("num_verbs")){	
			numVerbs = new Attribute("num_verbs");
				fvAttributes.add(numVerbs); 
				
		}
		
		if (features.contains("num_adverbs")){	
			numAdverbs = new Attribute("num_adverbs");
				fvAttributes.add(numAdverbs); // new 
				
		}
		
		if (features.contains("postText_vs_targetTitle_perc")){	
			numAdverbs = new Attribute("postText_vs_targetTitle_perc");
				fvAttributes.add(numAdverbs); // new 				
		}
		
		if (features.contains("postText_vs_targetDescription_perc")){	
			numAdverbs = new Attribute("postText_vs_targetDescription_perc");
				fvAttributes.add(numAdverbs); // new 				
		}
		
		
		if (features.contains("num_pos_sentiment_words")){	
			 numPosSentiWords = new Attribute("num_pos_sentiment_words");
			 fvAttributes.add(numPosSentiWords);				
		}
		
		if (features.contains("num_neg_sentiment_words")){	
			 numNegSentiWords = new Attribute("num_neg_sentiment_words");
			 fvAttributes.add(numNegSentiWords);				
		}
		
		if (features.contains("contains_happy_emo")){
				List<String> fvnominal3 = new ArrayList<String>(2);
				fvnominal3.add("true");
				fvnominal3.add("false");
				 containsHappyEmo = new Attribute("contains_happy_emo",
						fvnominal3);
				 fvAttributes.add(containsHappyEmo);
		}
		if (features.contains("contains_sad_emo")){
				List<String> fvnominal4 = new ArrayList<String>(2);
				fvnominal4.add("true");
				fvnominal4.add("false");
				 containsSadEmo = new Attribute("contains_sad_emo", fvnominal4);
				 fvAttributes.add(containsSadEmo);
		}
		
		if (features.contains("number_of_hyperbolic_terms")){
			numberOfHyperbolicTerms = new Attribute("number_of_hyperbolic_terms");
			fvAttributes.add(numberOfHyperbolicTerms);		
		}
		
		if (features.contains("sentiment")){
			List<String> fvnominalSent = new ArrayList<String>(6);
			fvnominalSent.add("none");
			fvnominalSent.add("Very negative");
			fvnominalSent.add("Negative");
			fvnominalSent.add("Neutral");
			fvnominalSent.add("Positive");
			fvnominalSent.add("Very positive");
			sentiment = new Attribute("sentiment",
					fvnominalSent);
			 fvAttributes.add(sentiment);
		}
		
			if (features.contains("normal_ngrams_bayes1")){
			 normalNgramsBayes1 = new Attribute("normal_ngrams_bayes1");
				fvAttributes.add(normalNgramsBayes1);
			
			}
			if (features.contains("normal_ngrams_bayes2")){
			 normalNgramsBayes2 = new Attribute("normal_ngrams_bayes2");
				fvAttributes.add(normalNgramsBayes2);
			
			}
			if (features.contains("normal_ngrams_bayes3")){
				normalNgramsBayes3 = new Attribute("normal_ngrams_bayes3");
				fvAttributes.add(normalNgramsBayes3); // new
			}
			
			if (features.contains("bagOfWords")){
				 bagOfWords = new Attribute("bagOfWords", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(bagOfWords);
			}
			
			if (features.contains("bagOfWordsClean")){
				 bagOfWords = new Attribute("bagOfWords", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(bagOfWords);
			}
			
			if (features.contains("bagOfWordsClean2")){
				 bagOfWords = new Attribute("bagOfWords", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(bagOfWords);
			}
			
			if (features.contains("ngram")){
				 ngram = new Attribute("ngram", (FastVector)null);
				 fvAttributes.add(ngram);
			}
			
			if (features.contains("ngramClean")){
				 ngram = new Attribute("ngram", (FastVector)null);
				 fvAttributes.add(ngram);
			}
			
			if (features.contains("bagOfWordsLem")){
				 bagOfWordsLem = new Attribute("bagOfWordsLem", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(bagOfWordsLem);
			}
			
			if (features.contains("NgramLem")){
				 NgramLem = new Attribute("NgramLem", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(NgramLem);
			}
			
			if (features.contains("bagOfWordsPos")){
				 bagOfWordsPos = new Attribute("bagOfWordsPos", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(bagOfWordsPos);
			}
			
			if (features.contains("NgramPos")){
				 NgramPos = new Attribute("NgramPos", (FastVector)null);		   // Declare the class attribute along with its values
				 fvAttributes.add(NgramPos);
			}
			
			if (features.contains("GID")){		
				for (int k=0; k < gidlist.size(); k++){
					fvAttributes.add(new Attribute("attrGID" + gidlist.get(k)));
				}
			}
			
			if (features.contains("has_media")){
				List<String> fvnominal4 = new ArrayList<String>(2);
				fvnominal4.add("true");
				fvnominal4.add("false");
				hasMedia = new Attribute("has_media", fvnominal4);
				 fvAttributes.add(hasMedia);
			}
			
			
		fvAttributes.add(ClassAttribute);
	
		return fvAttributes;
	}


	public Instance createInstance19ID(ItemFeatures listItemFeatures, 			
			JSONObject jsonFeature, 
			List<String> features) {
		Instance iExample = new DenseInstance(fvAttributes.size());
		String id = listItemFeatures.getId();
		iExample.setValue((Attribute) fvAttributes.get(0), id);
		int cnt = 1;
		for (int l=0; l < features.size();l++){			
			
				if (features.get(l).equals("item_length")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getItemLength " + listItemFeatures.getItemLength());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getItemLength());					
				}
					
				if (features.get(l).equals("num_words")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumWords " + listItemFeatures.getNumWords());
					}
				iExample.setValue((Attribute) fvAttributes.get(cnt),
						listItemFeatures.getNumWords());
				}
				if (features.get(l).equals("num_questionmark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumQuestionMark " + listItemFeatures.getNumQuestionMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumQuestionMark());
				}
				
				if (features.get(l).equals("num_exclamationmark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- getNumExclamationMark " + listItemFeatures.getNumExclamationMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumExclamationMark());
				}
				
				if (features.get(l).equals("num_uppercasechars")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumUppercaseChars " + listItemFeatures.getNumUppercaseChars());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumUppercaseChars());
				}
				
				if (features.get(l).equals("contains_question_mark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsQuestionMark " + listItemFeatures.getContainsQuestionMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsQuestionMark()));
				}
				if (features.get(l).equals("contains_exclamation_mark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsExclamationMark " + listItemFeatures.getContainsExclamationMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsExclamationMark()));
				}
				
				if (features.get(l).equals("contains_first_order_pron")){	
					if (listItemFeatures.getContainsFirstOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsFirstOrderPron " + listItemFeatures.getContainsFirstOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsFirstOrderPron()));
					}
				}
				
				if (features.get(l).equals("contains_second_order_pron")){	
					if (listItemFeatures.getContainsSecondOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsSecondOrderPron " + listItemFeatures.getContainsSecondOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsSecondOrderPron()));
					}
				}
				
				if (features.get(l).equals("contains_third_order_pron")){	
					if (listItemFeatures.getContainsThirdOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsThirdOrderPron " + listItemFeatures.getContainsThirdOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsThirdOrderPron()));
					}
				}
				
				if (features.get(l).equals("average_leght_of_each_word")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getAverageLeghtOfEachWord " + listItemFeatures.getAverageLeghtOfEachWord());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Double.valueOf(listItemFeatures.getAverageLeghtOfEachWord().trim()));
				}
				
				if (features.get(l).equals("longest_dependency")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getLongestDependency " + listItemFeatures.getLongestDependency());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getLongestDependency().trim()));
				}
				
				if (features.get(l).equals("presence_of_determiners_and_pronouns")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- presence_of_determiners_and_pronouns " + listItemFeatures.getPresenceOfDeterminersAndPronouns());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfDeterminersAndPronouns().trim()));
				}
				
				
				if (features.get(l).equals("number_of_common_words")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- number_of_common_words " + listItemFeatures.getNumberOfCommonWords());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							listItemFeatures.getNumberOfCommonWords());
				}
				
				if (features.get(l).equals("num_slangs")){	
					if (listItemFeatures.getNumSlangs() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumSlangs " + listItemFeatures.getNumSlangs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumSlangs());
					}
				}
				
				if (features.get(l).equals("readability")){	
					if (listItemFeatures.getReadability() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getReadability " + listItemFeatures.getReadability());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getReadability());
					}
				}
				
				if (features.get(l).equals("has_colon")){			
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getHasColon " + listItemFeatures.getHasColon());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getHasColon()));
				}
				
				if (features.get(l).equals("has_please")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getHasPlease " + listItemFeatures.getHasPlease());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
						String.valueOf(listItemFeatures.getHasPlease()));
				}
				
				if (features.get(l).equals("presence_of_punctuations")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getPresenceOfPunctuations " + listItemFeatures.getPresenceOfPunctuations());
					}
				
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfPunctuations().trim()));
				}
				
				
				if (features.get(l).equals("begins_with_digit")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getBeginsDigit " + listItemFeatures.getBeginsDigit());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getBeginsDigit());			
				}
				
				
				if (features.get(l).equals("hasHastag")){
					if (listItemFeatures.getHasHastag() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getHasHastag " + listItemFeatures.getHasHastag());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getHasHastag()));	
					}
				}
				
				if (features.get(l).equals("hasAT")){
					if (listItemFeatures.getHasAT() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getHasAT " + listItemFeatures.getHasAT());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getHasAT()));	
					}
				}
				
				if (features.get(l).equals("presence_of_word_contractions")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getPresenceOfWordContractions " + listItemFeatures.getPresenceOfWordContractions());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfWordContractions().trim()));
				}		
				
				if (features.get(l).equals("num_nouns")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumNouns " + listItemFeatures.getNumNouns());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumNouns());
					}
				}
				
				if (features.get(l).equals("POSperc")){
					// remove one and add then
					cnt = cnt -1 ;
					for (int k=0; k < posList.size(); k++){
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt+1).name());
							System.out.println("	-- POS " + "pos_" + posList.get(k));
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt+1), jsonFeature.getDouble("pos_" + posList.get(k)));
						cnt = cnt +1;
					}
				}
				
				if (features.get(l).equals("NEAperc")){
					// remove one and add then
					cnt = cnt -1 ;
					for (int k=0; k < namedEnt.size(); k++){
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt+1).name());
							System.out.println("	-- NEA " + "nea_" + namedEnt.get(k));
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt+1), jsonFeature.getDouble("nea_" + namedEnt.get(k)));
						cnt = cnt +1;
					}
					//cnt = cnt -1 ;
				}
				
				if (features.get(l).equals("stopWordsPercentage")){
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getStopWordsPercentages());			
				}
				
				if (features.get(l).equals("textVoice")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- getTextVoice " + listItemFeatures.getTextVoice());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getTextVoice());			
				}
				
				if (features.get(l).equals("isPassiveVoice")){
					if (listItemFeatures.getIsPassiveVoice() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getIsPassiveVoice " + listItemFeatures.getIsPassiveVoice());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getIsPassiveVoice()));	
					}
				}
				
				if (features.get(l).equals("subject_of_title")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getSubjectOfTitle().trim()));
				}					
				
				if (features.get(l).equals("num_adjectives")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumAdjectives " + listItemFeatures.getNumAdjectives());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumAdjectives());
					}
				}
				
				if (features.get(l).equals("num_verbs")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumVerbs " + listItemFeatures.getNumVerbs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumVerbs());
					}
				}
				
				if (features.get(l).equals("num_adverbs")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumAdverbs " + listItemFeatures.getNumAdverbs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumAdverbs());
					}
				}
				
				if (features.get(l).equals("postText_vs_targetTitle_perc")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- postText_vs_targetTitle_perc " + listItemFeatures.getPostText_vs_targetTitle_perc());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getPostText_vs_targetTitle_perc());
					}
				}
				
				if (features.get(l).equals("postText_vs_targetDescription_perc")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- postText_vs_targetDescription_perc " + listItemFeatures.postText_vs_targetDescription_perc());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.postText_vs_targetDescription_perc());
					}
				}
				
				
				if (features.get(l).equals("num_pos_sentiment_words")){	
					if (listItemFeatures.getNumPosSentiWords() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumPosSentiWords " + listItemFeatures.getNumPosSentiWords());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumPosSentiWords());
					}
				}
				
				if (features.get(l).equals("num_neg_sentiment_words")){	
					if (listItemFeatures.getNumNegSentiWords() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumNegSentiWords " + listItemFeatures.getNumNegSentiWords());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumNegSentiWords());
					}
				}		
				
				if (features.get(l).equals("contains_happy_emo")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsHappyEmo " + listItemFeatures.getContainsHappyEmo());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsHappyEmo()));						
				}
				
				if (features.get(l).equals("contains_sad_emo")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsSadEmo " + listItemFeatures.getContainsSadEmo());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsSadEmo()));
				}				
				
				if (features.get(l).equals("number_of_hyperbolic_terms")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getNumberOfHyperbolicTerms().trim()));
				}
				
				if (features.get(l).equals("sentiment")){
					if (verbose) {
						System.out.println("attribute " + fvAttributes.get(cnt));
						System.out.println("attribute " + fvAttributes.get(cnt).name());
					}
					if (listItemFeatures.getSentiment() != null && !listItemFeatures.getSentiment().isEmpty()) {
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getSentiment());	
					}
				}					
				
				if (features.get(l).equals("normal_ngrams_bayes1")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes1().trim()));
				}
				if (features.get(l).equals("normal_ngrams_bayes2")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes2().trim()));
				}
				if (features.get(l).equals("normal_ngrams_bayes3")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes3().trim()));
				}
				
				if (features.get(l).equals("bagOfWords")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getText()));
				}
				
				if (features.get(l).equals("bagOfWordsClean")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					String text = listItemFeatures.getText().replaceAll("http+s*+://[^ ]+", "")
							.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
							.replaceAll("RT", "").toLowerCase().trim();
					
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(text));
				}
				
				if (features.get(l).equals("bagOfWordsClean2")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
										
					List<String> tokens = listItemFeatures.getPOSTokens();
					List<String> postokens = listItemFeatures.getPOS();
					
				
					HashSet<String> stop_eng =  new HashSet<String>();	
					HashSet<String> pos_remove =  new HashSet<String>();	
					
					try {
						stop_eng	= loadSetfromFile(Configuration.RESOURCES_PATH + "/stop-words-eng.txt");	
						pos_remove  = loadSetfromFile(Configuration.RESOURCES_PATH + "/pos_remove.txt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (tokens.size() != postokens.size()){
						//System.out.println(" ##### NO equal token - pos " + tokens.size() + " - - " + postokens.size());
					}
					String newCleanString = "";
					for (int  tk = 0; tk < tokens.size(); tk ++){						
						String word = tokens.get(tk);
						String pos = postokens.get(tk);
						//System.out.println(word + " -- " + pos);
						if (word.length()>2 && !stop_eng.contains(word) && !pos_remove.contains(pos)){
							newCleanString = newCleanString + " " + word;
						}
					}
					//System.out.println(newCleanString);
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(newCleanString.trim()));
				}
				
				if (features.get(l).equals("ngram")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- Ngram getText " + listItemFeatures.getText());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getText()));			
				}
				
				if (features.get(l).equals("ngramClean")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- Ngram getText " + listItemFeatures.getText());
					}
					String text = listItemFeatures.getText().replaceAll("http+s*+://[^ ]+", "")
							.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
							.replaceAll("RT", "").toLowerCase().trim();;
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(text));			
				}
				
				// BoW of lem
				if (features.get(l).equals("bagOfWordsLem")){
					String lemToString = String.join(" ", listItemFeatures.getLEM().toString());
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- Bow lemToString " + lemToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							lemToString);
				}
				
				// Ngram of lem
				if (features.get(l).equals("NgramLem")){
					String lemToString = String.join(" ", listItemFeatures.getLEM().toString());
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Ngram lemToString " + lemToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							lemToString);
				}
				
				// BoW of POS
				if (features.get(l).equals("bagOfWordsPos")){
					String posToString = String.join(" ", listItemFeatures.getPOS().toString());
					posToString = posToString.replace("[", "").replace("]", "");
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- BoW of POS " + posToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							posToString);
				}
				
				// Ngram of POS
				if (features.get(l).equals("NgramPos")){
					String posToString = String.join(" ", listItemFeatures.getPOS().toString());
					posToString = posToString.replace("[", "").replace("]", "");
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- Ngram of POS " + posToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							posToString);
				}
				
				if (features.get(l).equals("GID")){
			
					cnt = cnt -1 ;
					for (int k=0; k < gidlist.size(); k++){
							if (!Double.isNaN(jsonFeature.getDouble("gid_" + gidlist.get(k))) ){ 
										if (verbose) {
										System.out.println("* Attribute " + fvAttributes.get(cnt + 1).name());
										System.out.println("	-- gid " + "gid_" + gidlist.get(k));
										}
									iExample.setValue((Attribute) fvAttributes.get(cnt+1), jsonFeature.getDouble("gid_" + gidlist.get(k)));
									cnt = cnt +1;
							}
					}
				}
				
				if (features.get(l).equals("has_media")){
						if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- has_media " + listItemFeatures.getHasMedia());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(listItemFeatures.getHasMedia()));	
				//	}
				}
				
									
				cnt = cnt + 1;
		
		}
	
		return iExample;
	}	
	
	public Instance createInstance(ItemFeatures listItemFeatures, 			
			List<String> features) {
		Instance iExample = new DenseInstance(fvAttributes.size());
		String id = listItemFeatures.getId();
		int cnt = 0;
		
		for (int l=0; l < features.size();l++){
			if (features.get(l).equals("item_length")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- getItemLength " + listItemFeatures.getItemLength());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getItemLength());				
				}
					
				if (features.get(l).equals("num_words")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumWords " + listItemFeatures.getNumWords());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
						listItemFeatures.getNumWords());
						//System.out.println("getNumWords " + listItemFeatures.getNumWords());
				}
				if (features.get(l).equals("num_questionmark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumQuestionMark " + listItemFeatures.getNumQuestionMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumQuestionMark());
				}
				
				if (features.get(l).equals("num_exclamationmark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumExclamationMark " + listItemFeatures.getNumExclamationMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumExclamationMark());
				}
				
				if (features.get(l).equals("num_uppercasechars")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getNumUppercaseChars " + listItemFeatures.getNumUppercaseChars());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getNumUppercaseChars());
				}
				
				if (features.get(l).equals("contains_question_mark")){	
					//System.out.println(listItemFeatures.getContainsQuestionMark());
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsQuestionMark " + listItemFeatures.getContainsQuestionMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsQuestionMark()));
				}
		
				if (features.get(l).equals("contains_exclamation_mark")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsExclamationMark " + listItemFeatures.getContainsExclamationMark());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsExclamationMark()));
				}
				
				if (features.get(l).equals("contains_first_order_pron")){	
					if (listItemFeatures.getContainsFirstOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsFirstOrderPron " + listItemFeatures.getContainsFirstOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsFirstOrderPron()));
					}
				}
				
				if (features.get(l).equals("contains_second_order_pron")){	
					if (listItemFeatures.getContainsSecondOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsSecondOrderPron " + listItemFeatures.getContainsSecondOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsSecondOrderPron()));
					}
				}
				
				if (features.get(l).equals("contains_third_order_pron")){	
					if (listItemFeatures.getContainsThirdOrderPron() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getContainsThirdOrderPron " + listItemFeatures.getContainsThirdOrderPron());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt), String
								.valueOf(listItemFeatures.getContainsThirdOrderPron()));
					}
				}
				
				if (features.get(l).equals("average_leght_of_each_word")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getAverageLeghtOfEachWord " + listItemFeatures.getAverageLeghtOfEachWord());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Double.valueOf(listItemFeatures.getAverageLeghtOfEachWord().trim()));
				}
				
				if (features.get(l).equals("longest_dependency")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getLongestDependency " + listItemFeatures.getLongestDependency());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getLongestDependency().trim()));
				}
				
				if (features.get(l).equals("presence_of_determiners_and_pronouns")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- presence_of_determiners_and_pronouns " + listItemFeatures.getPresenceOfDeterminersAndPronouns());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfDeterminersAndPronouns().trim()));
				}
				
				
				if (features.get(l).equals("number_of_common_words")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- number_of_common_words " + listItemFeatures.getNumberOfCommonWords());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							listItemFeatures.getNumberOfCommonWords());
				}
				
				if (features.get(l).equals("num_slangs")){	
					if (listItemFeatures.getNumSlangs() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumSlangs " + listItemFeatures.getNumSlangs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumSlangs());
					}
				}
				
				if (features.get(l).equals("readability")){	
					if (listItemFeatures.getReadability() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getReadability " + listItemFeatures.getReadability());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getReadability());
					}
				}
				
				if (features.get(l).equals("has_colon")){			
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getHasColon " + listItemFeatures.getHasColon());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getHasColon()));
				}
				
				if (features.get(l).equals("has_please")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getHasPlease " + listItemFeatures.getHasPlease());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
						String.valueOf(listItemFeatures.getHasPlease()));
				}
				
				if (features.get(l).equals("presence_of_punctuations")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getPresenceOfPunctuations " + listItemFeatures.getPresenceOfPunctuations());
					}
				
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfPunctuations().trim()));
				}
				
				
				if (features.get(l).equals("begins_with_digit")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getBeginsDigit " + listItemFeatures.getBeginsDigit());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getBeginsDigit());			
				}
				
				
				if (features.get(l).equals("hasHastag")){
					if (listItemFeatures.getHasHastag() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getHasHastag " + listItemFeatures.getHasHastag());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getHasHastag()));	
					}
				}
				
				if (features.get(l).equals("hasAT")){
					if (listItemFeatures.getHasAT() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getHasAT " + listItemFeatures.getHasAT());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getHasAT()));	
					}
				}
				
				if (features.get(l).equals("presence_of_word_contractions")){	
					if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getPresenceOfWordContractions " + listItemFeatures.getPresenceOfWordContractions());
						}
					
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getPresenceOfWordContractions().trim()));
				}			
				
				if (features.get(l).equals("num_nouns")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumNouns " + listItemFeatures.getNumNouns());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumNouns());
					}
				}
				
				if (features.get(l).equals("POSperc")){
					// remove one and add then
					cnt = cnt -1 ;
					List<Double> posPerc = listItemFeatures.getPOSPerc();
					for (int k=0; k < posList.size(); k++){
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt+1).name());
							System.out.println("	-- POS " + "pos_" + posList.get(k));
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt+1), posPerc.get(k));
						cnt = cnt +1;
					}
				}
				
				if (features.get(l).equals("NEAperc")){
					// remove one and add then
					cnt = cnt -1 ;
					List<Double> nedPerc = listItemFeatures.getnedPerc();
					for (int k=0; k < namedEnt.size(); k++){
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt+1).name());
							System.out.println("	-- NEA " + "nea_" + namedEnt.get(k));
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt+1), nedPerc.get(k));
						cnt = cnt +1;
					}
				}
				
				if (features.get(l).equals("stopWordsPercentage")){
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getStopWordsPercentages());			
				}
				
				if (features.get(l).equals("textVoice")){
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getTextVoice " + listItemFeatures.getTextVoice());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							listItemFeatures.getTextVoice());			
				}
				
				if (features.get(l).equals("isPassiveVoice")){
					if (listItemFeatures.getIsPassiveVoice() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getIsPassiveVoice " + listItemFeatures.getIsPassiveVoice());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(
							listItemFeatures.getIsPassiveVoice()));	
					}
				}				
				if (features.get(l).equals("subject_of_title")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getSubjectOfTitle().trim()));
				}					
				if (features.get(l).equals("num_adjectives")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumAdjectives " + listItemFeatures.getNumAdjectives());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumAdjectives());
					}
				}
				
				if (features.get(l).equals("num_verbs")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumVerbs " + listItemFeatures.getNumVerbs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumVerbs());
					}
				}
				
				if (features.get(l).equals("num_adverbs")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumAdverbs " + listItemFeatures.getNumAdverbs());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumAdverbs());
					}
				}
				
				if (features.get(l).equals("postText_vs_targetTitle_perc")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- postText_vs_targetTitle_perc " + listItemFeatures.getPostText_vs_targetTitle_perc());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getPostText_vs_targetTitle_perc());
					}
				}
				
				if (features.get(l).equals("postText_vs_targetDescription_perc")){	
					if (listItemFeatures.getNumNouns() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- postText_vs_targetDescription_perc " + listItemFeatures.postText_vs_targetDescription_perc());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.postText_vs_targetDescription_perc());
					}
				}
				
				
				if (features.get(l).equals("num_pos_sentiment_words")){	
					if (listItemFeatures.getNumPosSentiWords() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumPosSentiWords " + listItemFeatures.getNumPosSentiWords());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumPosSentiWords());
					}
				}
				
				if (features.get(l).equals("num_neg_sentiment_words")){	
					if (listItemFeatures.getNumNegSentiWords() != null) {
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- getNumNegSentiWords " + listItemFeatures.getNumNegSentiWords());
						}
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getNumNegSentiWords());
					}
				}		
				
				if (features.get(l).equals("contains_happy_emo")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsHappyEmo " + listItemFeatures.getContainsHappyEmo());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsHappyEmo()));
				}
				
				if (features.get(l).equals("contains_sad_emo")){	
					if (verbose) {
						System.out.println("* Attribute " + fvAttributes.get(cnt).name());
						System.out.println("	-- getContainsSadEmo " + listItemFeatures.getContainsSadEmo());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getContainsSadEmo()));
				}				
				
				if (features.get(l).equals("number_of_hyperbolic_terms")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt), 
							Integer.valueOf(listItemFeatures.getNumberOfHyperbolicTerms().trim()));
				}
				
				if (features.get(l).equals("sentiment")){
					//System.out.println(listItemFeatures.getSentiment());
					if (verbose) {
						System.out.println("attribute " + fvAttributes.get(cnt));
						System.out.println("attribute " + fvAttributes.get(cnt).name());
					}
					if (listItemFeatures.getSentiment() != null && !listItemFeatures.getSentiment().isEmpty()) {
						iExample.setValue((Attribute) fvAttributes.get(cnt),
								listItemFeatures.getSentiment());	
					}
				}
								
				if (features.get(l).equals("normal_ngrams_bayes1")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes1().trim()));
				}
				if (features.get(l).equals("normal_ngrams_bayes2")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes2().trim()));
				}
				if (features.get(l).equals("normal_ngrams_bayes3")){	
					iExample.setValue((Attribute) fvAttributes.get(cnt),  
							Double.valueOf(listItemFeatures.getNormalNgramsBayes3().trim()));
				}
				
				if (features.get(l).equals("bagOfWords")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getText()));
				}
				
				if (features.get(l).equals("bagOfWordsClean")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					String text = listItemFeatures.getText().replaceAll("http+s*+://[^ ]+", "")
							.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
							.replaceAll("RT", "").toLowerCase().trim();
					
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(text));
				}
				
				if (features.get(l).equals("bagOfWordsClean2")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					
					List<String> tokens = listItemFeatures.getPOSTokens();
					List<String> postokens = listItemFeatures.getPOS();					
				
					HashSet<String> stop_eng =  new HashSet<String>();	
					HashSet<String> pos_remove =  new HashSet<String>();	
					
					try {
						stop_eng	= loadSetfromFile(Configuration.RESOURCES_PATH + "/stop-words-eng.txt");	
						pos_remove  = loadSetfromFile(Configuration.RESOURCES_PATH + "/pos_remove.txt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (tokens.size() != postokens.size()){
						//System.out.println(" ##### NO equal token - pos " + tokens.size() + " - - " + postokens.size());
					}
					String newCleanString = "";
					for (int  tk = 0; tk < tokens.size(); tk ++){						
						String word = tokens.get(tk);
						String pos = postokens.get(tk);
						//System.out.println(word + " -- " + pos);
						if (word.length()>2 && !stop_eng.contains(word) && !pos_remove.contains(pos)){
							newCleanString = newCleanString + " " + word;
						}
					}
					//System.out.println(newCleanString);
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(newCleanString.trim()));
				}
				
				if (features.get(l).equals("ngram")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Ngram getText " + listItemFeatures.getText());
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(listItemFeatures.getText()));			
				}
				
				if (features.get(l).equals("ngramClean")){
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Ngram getText " + listItemFeatures.getText());
					}
					String text = listItemFeatures.getText().replaceAll("http+s*+://[^ ]+", "")
							.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
							.replaceAll("RT", "").toLowerCase().trim();;
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(text));			
				}
				
				// BoW of lem
				if (features.get(l).equals("bagOfWordsLem")){
					String lemToString = String.join(" ", listItemFeatures.getLEM().toString());
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Bow lemToString " + lemToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							lemToString);
				}
				
				// Ngram of lem
				if (features.get(l).equals("NgramLem")){
					String lemToString = String.join(" ", listItemFeatures.getLEM().toString());
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Ngram lemToString " + lemToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							lemToString);
				}
				
				// BoW of POS
				if (features.get(l).equals("bagOfWordsPos")){
					String posToString = String.join(" ", listItemFeatures.getPOS().toString());
					posToString = posToString.replace("[", "").replace("]", "");
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- BoW of POS " + posToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							posToString);
				}
				
				// Ngram of POS
				if (features.get(l).equals("NgramPos")){
					String posToString = String.join(" ", listItemFeatures.getPOS().toString());
					posToString = posToString.replace("[", "").replace("]", "");
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- Ngram of POS " + posToString);
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							posToString);
				}
				
				if (features.get(l).equals("GID")){
					//System.out.println("gid before " + cnt);
					// remove one and add then
					cnt = cnt -1 ;
					List<Double> gidPerc = listItemFeatures.getgidPerc();
					for (int k=0; k < gidlist.size(); k++){
										if (verbose) {
											System.out.println("* Attribute " + fvAttributes.get(cnt + 1).name());
											System.out.println("	-- gid " + "gid_" + gidlist.get(k));
										}
									iExample.setValue((Attribute) fvAttributes.get(cnt+1), gidPerc.get(k));
									cnt = cnt +1;						
					}
				}
				
				if (features.get(l).equals("has_media")){
						if (verbose) {
							System.out.println("* Attribute " + fvAttributes.get(cnt).name());
							System.out.println("	-- has_media " + listItemFeatures.getHasMedia());
						}
					iExample.setValue((Attribute) fvAttributes.get(cnt), String.valueOf(listItemFeatures.getHasMedia()));	
				//	}
				}
				
				cnt = cnt + 1;
		
		}
	
		return iExample;
	}	
	

	
	
}

