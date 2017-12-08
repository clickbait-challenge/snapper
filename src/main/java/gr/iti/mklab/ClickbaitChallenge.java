package gr.iti.mklab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.configuration.ConfigurationException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger;
import gr.iti.mklab.classification.ItemClassifier2ndLv;
import gr.iti.mklab.classification.ItemClassifierBoWOne;
import gr.iti.mklab.classification.ItemClassifierFSOne;
import gr.iti.mklab.classification.ItemClassifierNgramOne;
import gr.iti.mklab.classification.ItemClassifierOne;
import gr.iti.mklab.classification.ItemClassifierWithTitleOne;
import gr.iti.mklab.extractfeatures.ItemFeatures;
import gr.iti.mklab.extractfeatures.ItemFeaturesExtractorJSON;
import gr.iti.mklab.util.ClikbaitInstanceItem;
import gr.iti.mklab.util.Configuration;
import gr.iti.mklab.util.MongoConfiguration;
import gr.iti.mklab.util.Utils;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;


public class ClickbaitChallenge {
	
		public static String input_path;
		public static String output_path;
		public static String input_dataset_file = "instances.jsonl";
		public static String result_file = "results.jsonl";
		public static List<String> featName;
		public static List<String> featName2ndLevel;
		public static List<String> featNameLexical;
		public static List<String> featNameFeatSel;
				
		static List<JSONObject> jsonFeatures2ndLevel = new ArrayList<JSONObject>();
		
		public static MongoCollection<Document> collectionProbs;
		
		
		public static int bowWords = 1000;
		public static int minFreqWords = 3;
		
		public static int ngramWords = 2000;
		public static int minNgram = 1;
		public static int maxNgram = 4;
		public static ItemFeatures feat = new ItemFeatures();
		public static ItemFeatures featTargetTitile = new ItemFeatures();
		
		
		public static  MongoClient mongoClient;
		public static String mongoDocId = "_id";
		public static MongoDatabase database;
		public static String classifier;
		public static String ProbCollection1stLevel = "Probs1stLevelTest";
		public static String mainDB = "ClickbaitChallenge";
		public static boolean auth = true;
		
		
		public static void initialize(){
	         try {
				Configuration.load(ClickbaitChallenge.class.getResource("/local.properties").getFile() );
			} catch (ConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	         // Define if mongo db will be used with authentication or not. If auth = true the mongo.properties file should contain the credentials
	         auth = true;
	         // Define db names
	         mainDB = "ClickbaitChallenge";
	         ProbCollection1stLevel = "Probs1stLevelTest";
	   
		}
		
		
		public static void closeMongoClient(){
				mongoClient.close();
		 }
		 
		 
		 public static MongoClient openMongoClientsAuth(){
				try {	
				       MongoConfiguration.load(ClickbaitChallenge.class.getResource("/mongo.properties").getFile());
					} catch (ConfigurationException e) {
						e.printStackTrace();
					}
					/*
					 *  * open mongo db with auth
					 */
			    MongoCredential credential = MongoCredential.createCredential(MongoConfiguration.USER, MongoConfiguration.ADMIN_DB, MongoConfiguration.PWD.toCharArray());
			  
			 		try { 
							  mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));
						} catch (Exception e) {
							e.printStackTrace();
						}
			 	return mongoClient;
			}
		 
				
			public static MongoClient openMongoClients(){
		 		try { 
						  mongoClient = new MongoClient("localhost",27017);
					} catch (Exception e) {
						e.printStackTrace();
					}
		 	return mongoClient;
			}
			
		 
		  /**
		  * Checks if an activity exists with a given id. if no such activity exists
		  * returns false. Returns true for one or more activities with a matching
		  * id.
		  * 
		  * @param db
		  * @param id
		  * @return boolean - true if one or more functions with matching names exit.
		  */
		  public static boolean documentsExists(MongoDatabase db, String collection, String id) {
		      FindIterable<Document> iterable = db.getCollection(collection).find(new Document("_id", id));
		      return iterable.first() != null;
		  }
		  
		  
		 
		  
		  public static void featuresNames2ndLevelList(String category) throws Exception{
				
			  featName2ndLevel = new LinkedList<String>();
				
				if (category.contains("postText")){
					
					featName2ndLevel.add("PostTextMorphLG");
					featName2ndLevel.add("PostTextStylLG");
					featName2ndLevel.add("PostTextGramLG");
					featName2ndLevel.add("PostTextSentLG");
					featName2ndLevel.add("PostTextGIDLG");
					featName2ndLevel.add("PostTextMorph_StylLG");
					featName2ndLevel.add("PostTextMorph_GramLG");
					featName2ndLevel.add("PostTextMorph_SentLG");
					featName2ndLevel.add("PostTextMorph_GIDLG");
					featName2ndLevel.add("PostTextStyl_GramLG");
					featName2ndLevel.add("PostTextStyl_SentLG");
					featName2ndLevel.add("PostTextStyl_GIDLG");
					featName2ndLevel.add("PostTextGram_SentLG");
					featName2ndLevel.add("PostTextGram_GIDLG");
					featName2ndLevel.add("PostTextSent_GIDLG");
					featName2ndLevel.add("PostTextMorph_Styl_GramLG");
					featName2ndLevel.add("PostTextMorph_Styl_SentLG");
					featName2ndLevel.add("PostTextMorph_Styl_GIDLG");
					featName2ndLevel.add("PostTextMorph_Gram_SentLG");
					featName2ndLevel.add("PostTextMorph_Gram_GIDLG");
					featName2ndLevel.add("PostTextMorph_Sent_GIDLG");
					featName2ndLevel.add("PostTextStyl_Gram_SentLG");
					featName2ndLevel.add("PostTextStyl_Gram_GIDLG");
					featName2ndLevel.add("PostTextStyl_Sent_GIDLG");
					featName2ndLevel.add("PostTextGram_Sent_GIDLG");
					featName2ndLevel.add("PostTextMorph_Styl_Gram_SentLG");
					featName2ndLevel.add("PostTextMorph_Styl_Gram_GIDLG");
					featName2ndLevel.add("PostTextMorph_Styl_Sent_GIDLG");
					featName2ndLevel.add("PostTextMorph_Gram_Sent_GIDLG");
					featName2ndLevel.add("PostTextStyl_Gram_Sent_GIDLG");
					featName2ndLevel.add("PostTextMorph_Styl_Gram_Sent_GIDLG");		
					
					featName2ndLevel.add("PostTextNgramLG");
					featName2ndLevel.add("PostTextBoWLG");
					featName2ndLevel.add("PostTextFeatSelLG");					
					featName2ndLevel.add("PandTMorphLG");
					featName2ndLevel.add("PandTStylLG");
					featName2ndLevel.add("PandTGramLG");
					featName2ndLevel.add("PandTSentLG");
					featName2ndLevel.add("PandTGIDLG");
					featName2ndLevel.add("PandTMorph_StylLG");
					featName2ndLevel.add("PandTMorph_GramLG");
					featName2ndLevel.add("PandTMorph_SentLG");
					featName2ndLevel.add("PandTMorph_GIDLG");
					featName2ndLevel.add("PandTStyl_GramLG");
					featName2ndLevel.add("PandTStyl_SentLG");
					featName2ndLevel.add("PandTStyl_GIDLG");
					featName2ndLevel.add("PandTGram_SentLG");
					featName2ndLevel.add("PandTGram_GIDLG");
					featName2ndLevel.add("PandTSent_GIDLG");
					featName2ndLevel.add("PandTMorph_Styl_GramLG");
					featName2ndLevel.add("PandTMorph_Styl_SentLG");
					featName2ndLevel.add("PandTMorph_Styl_GIDLG");
					featName2ndLevel.add("PandTMorph_Gram_SentLG");
					featName2ndLevel.add("PandTMorph_Gram_GIDLG");
					featName2ndLevel.add("PandTMorph_Sent_GIDLG");
					featName2ndLevel.add("PandTStyl_Gram_SentLG");
					featName2ndLevel.add("PandTStyl_Gram_GIDLG");
					featName2ndLevel.add("PandTStyl_Sent_GIDLG");
					featName2ndLevel.add("PandTGram_Sent_GIDLG");
					featName2ndLevel.add("PandTMorph_Styl_Gram_SentLG");
					featName2ndLevel.add("PandTMorph_Styl_Gram_GIDLG");
					featName2ndLevel.add("PandTMorph_Styl_Sent_GIDLG");
					featName2ndLevel.add("PandTMorph_Gram_Sent_GIDLG");
					featName2ndLevel.add("PandTStyl_Gram_Sent_GIDLG");
					featName2ndLevel.add("PandTMorph_Styl_Gram_Sent_GIDLG");

				}
		  }
		  
		  
		  public static void featuresNamesLexicalList(String category){
			  
			  featNameLexical = new LinkedList<String>();
			  
			  if (category.equals("BoW")){
				  featNameLexical.add("bagOfWordsClean2");				  
			  }
			  
			  if (category.equals("Ngram")){
				  featNameLexical.add("ngramClean");				  
			  }	  
			
		  }
		  
		  public static void featuresNamesList(String category){
				
				featName = new LinkedList<String>();
				
				if (category.contains("Morph")){					
					featName.add("item_length");
					featName.add("num_words");
					featName.add("num_questionmark");
					featName.add("num_exclamationmark");
					featName.add("num_uppercasechars");
					featName.add("contains_question_mark");
					featName.add("contains_exclamation_mark");
					featName.add("contains_first_order_pron");
					featName.add("contains_second_order_pron");
					featName.add("contains_third_order_pron");
					featName.add("average_leght_of_each_word");
					featName.add("longest_dependency");
					featName.add("presence_of_determiners_and_pronouns");
					featName.add("number_of_common_words");
				}
			
				if (category.contains("Styl")){
					// stylistic
					featName.add("num_slangs");
					featName.add("readability");
					featName.add("has_colon");
					featName.add("has_please");
					featName.add("presence_of_punctuations");
					featName.add("begins_with_digit");
					featName.add("hasHastag");
					featName.add("hasAT");
					featName.add("presence_of_word_contractions");				
				}			
			
				if (category.contains("Gram")){
					// Grammatical
					featName.add("num_nouns");
					featName.add("POSperc");
					featName.add("NEAperc");
					featName.add("stopWordsPercentage");
					featName.add("textVoice");
					featName.add("isPassiveVoice");
					featName.add("subject_of_title");
					featName.add("num_adjectives");
					featName.add("num_verbs");
					featName.add("num_adverbs");
					
					featName.add("postText_vs_targetTitle_perc");
					featName.add("postText_vs_targetDescription_perc");
				}
				
				if (category.contains("Sent")){
					// Sentiment
					featName.add("num_pos_sentiment_words");
					featName.add("num_neg_sentiment_words");
					featName.add("contains_happy_emo");
					featName.add("contains_sad_emo");
					featName.add("number_of_hyperbolic_terms");
					featName.add("sentiment");
				}			
			
					if (category.contains("GID")){
						// GID
						featName.add("GID");
					}				
					
					featName.add("has_media");
				
		
			}
		  
		  public static void featuresNamesFSList(String category){
				
				featNameFeatSel = new LinkedList<String>();
				
			
				
				if (category.contains("FeatSel")){
					
					featNameFeatSel.add("num_questionmark");
					featNameFeatSel.add("contains_second_order_pron");
					featNameFeatSel.add("presence_of_determiners_and_pronouns");
					featNameFeatSel.add("begins_with_digit");
					featNameFeatSel.add("num_nouns");
					featNameFeatSel.add("POSperc");
					featNameFeatSel.add("subject_of_title");
					featNameFeatSel.add("postText_vs_targetTitle_perc");
					featNameFeatSel.add("GID");
			

				}		
			}	  
		  
		  public static Instances createFeatureInstances2ndLevel(String collectionNameProbs, String id_file, List<String> features){
				// System.out.println(collectionNameProbs);
				 
				 MongoCollection<Document> collectionProbs = database.getCollection(collectionNameProbs);
				 
			 	 BufferedReader reader = null;
			 	 Instances dataSet = null;
			 	 
				 List<JSONObject> jsonFeatures = new ArrayList<JSONObject>();				 
				 
			 	 try {
					    File file = new File(id_file);
					    reader = new BufferedReader(new FileReader(file));
					    String line;
					    while ((line = reader.readLine()) != null) {
					      //  System.out.println(line);
					    	if (documentsExists( database, collectionNameProbs, line)){
						        JSONObject json;
						        Document query = new Document(mongoDocId, line);						
						        FindIterable<Document> findIterable2 = collectionProbs.find(query);				
						        Document queryResult2 = findIterable2.first();
						        String s2 = JSON.serialize(queryResult2);
						        // System.out.println(s);
						        json = new JSONObject(s2);
						        jsonFeatures.add(json);
							
					    	}else{
					    		System.out.println("This video " + line + " has no description");
					    	}				
						}
					// System.out.println("Number of ItemFeatures " + jsonFeatures.size());
					 
									// System.out.println("Feature test before " + itemFeaturesTraining.get(itemFeaturesTraining.size()-1));
							// System.out.println("Create training set");
							 ItemClassifier2ndLv ic = new ItemClassifier2ndLv(features);
							 dataSet = ic.createDataSet(jsonFeatures, features);
							// System.out.println("Feature test " + dataSet.get(dataSet.size()-1));			 
				
				// System.out.println("Dataset Size " + dataSet.size());		
			 	}catch (IOException e) {
				    e.printStackTrace();
				} finally {
				    try {
				        reader.close();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				}
			 	return dataSet;
			 }
		  
		  static Logger root = (Logger) LoggerFactory
			        .getLogger(Logger.ROOT_LOGGER_NAME);

			static {
			    root.setLevel(Level.INFO);
			}  
	
	public static void main(String[] args) throws Exception{
		 System.out.println("there are "+args.length+" command-line arguments.\n");
		 long start_time  = System.currentTimeMillis();
         for(int i=0;i<args.length;i++)
        	 System.out.println("args["+i+"]:"+args[i]);	
         
         if (args.length == 4){
        	 if (args[0].equals("-i")){
        		 input_path = args[1];
        	 }else{
        		 System.out.println("-i : input parameter missing");
        	 }
        	 
        	 if (args[2].equals("-o")){
        		 output_path = args[3];
        	 }else{
        		 System.out.println("-o : output parameter missing");
        	 }
        	 
         }else{
        	  	 System.out.println("Wrong number of input parameters");
        	  	 input_path = "D:/Java_workspace/clikbait-challenge-CERTH-deploy/input_folder_test/";
        	  	 output_path = "D:/Java_workspace/clikbait-challenge-CERTH-deploy/result_folder_test/";
        	// return;
         }
         
        initialize();        
    
        if (auth){
        	openMongoClientsAuth();
        }else{
            openMongoClients();

        }
		 database = mongoClient.getDatabase("ClikbaitChallenge2017");
		 collectionProbs = database.getCollection(ProbCollection1stLevel);
		 

         File fileOut = new File(Configuration.RESOURCES_PATH + "/stop_clickbait_paper/Posts.txt");
	   	 FileWriter fw = new FileWriter( fileOut.getAbsoluteFile( ) );
	   	 BufferedWriter bw = new BufferedWriter( fw );		
         
         String input_file = input_path + "/" + input_dataset_file;
    
         
         // Writing to a file
         File file=new File(output_path + "/results.jsonl");
         file.createNewFile();
         FileWriter fileWriter = new FileWriter(file);
         System.out.println("Writing JSON object to file");
         System.out.println("-----------------------");
     
         
         List<ClikbaitInstanceItem> listItems = new ArrayList<ClikbaitInstanceItem>();
        
         
		/**
		 * 
		 *          LOAD CLASSIFIERS
		 *          
		 *          
		 */
     	long log_models_time = System.currentTimeMillis();
         		 featuresNamesList("Morph");
		         ItemClassifierOne icMorth = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTMorth = new ItemClassifierWithTitleOne(featName);
		         Classifier clasMoprh = (Classifier) weka.core.SerializationHelper.read( Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorphLG" + ".model");
		         Classifier clasMoprhT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorphLG" + ".model");
				 		
		         featuresNamesList("Sent");
		         ItemClassifierOne icSent = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTSent = new ItemClassifierWithTitleOne(featName);
		         Classifier clasSent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextSentLG" + ".model");
		         Classifier clasSentT = (Classifier) weka.core.SerializationHelper.read( Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTSentLG" + ".model");
	         		         
		         featuresNamesList("Styl");
		         ItemClassifierOne icStyl = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTStyl = new ItemClassifierWithTitleOne(featName);
		         Classifier clasStyl = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStylLG" + ".model");
		         Classifier clasStylT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStylLG" + ".model");
		     
		         featuresNamesList("Gram");
		         ItemClassifierOne icGram = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTGram = new ItemClassifierWithTitleOne(featName);
		         Classifier clasGram = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextGramLG" + ".model");
		         Classifier clasGramT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTGramLG" + ".model");
		          
		         featuresNamesList("GID");
		         ItemClassifierOne icGID = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTGID = new ItemClassifierWithTitleOne(featName);
		         Classifier clasGID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextGIDLG" + ".model");
		         Classifier clasGIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTGIDLG" + ".model");
		          
		         featuresNamesList("Morph_Styl");
		         ItemClassifierOne icMorph_Styl = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTMorph_Styl = new ItemClassifierWithTitleOne(featName);
		         Classifier clasMorph_Styl = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_StylLG" + ".model");
		         Classifier clasMorph_StylT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_StylLG" + ".model");
		     
		         featuresNamesList("Morph_Gram");
		         ItemClassifierOne icMorph_Gram = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTMorph_Gram = new ItemClassifierWithTitleOne(featName);
		         Classifier clasMorph_Gram = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_GramLG" + ".model");
		         Classifier clasMorph_GramT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_GramLG" + ".model");
		     
		         featuresNamesList("Morph_Sent");
		         ItemClassifierOne icMorph_Sent = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTMorph_Sent = new ItemClassifierWithTitleOne(featName);
		         Classifier clasMorph_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_SentLG" + ".model");
		         Classifier clasMorph_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_SentLG" + ".model");
		     
		         featuresNamesList("Morph_GID");
		         ItemClassifierOne icMorph_GID = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTMorph_GID = new ItemClassifierWithTitleOne(featName);
		         Classifier clasMorph_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_GIDLG" + ".model");
		         Classifier clasMorph_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_GIDLG" + ".model");
		         
		         featuresNamesList("Styl_Gram");
		         ItemClassifierOne icStyl_Gram = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTStyl_Gram = new ItemClassifierWithTitleOne(featName);
		         Classifier clasStyl_Gram = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_GramLG" + ".model");
		         Classifier clasStyl_GramT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_GramLG" + ".model");
				 
		         featuresNamesList("Styl_Sent");
		         ItemClassifierOne icStyl_Sent = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTStyl_Sent = new ItemClassifierWithTitleOne(featName);
		         Classifier clasStyl_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_SentLG" + ".model");
		         Classifier clasStyl_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_SentLG" + ".model");
		  	 
		         featuresNamesList("Styl_GID");
		         ItemClassifierOne icStyl_GID = new ItemClassifierOne(featName);
		         ItemClassifierWithTitleOne icTStyl_GID = new ItemClassifierWithTitleOne(featName);
		         Classifier clasStyl_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_GIDLG" + ".model");
		         Classifier clasStyl_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_GIDLG" + ".model");
	
		         featuresNamesList("Gram_Sent");
        		 ItemClassifierOne icGram_Sent = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTGram_Sent = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasGram_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextGram_SentLG" + ".model");
        		 Classifier clasGram_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTGram_SentLG" + ".model");
       		 
        		 featuresNamesList("Gram_GID");
        		 ItemClassifierOne icGram_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTGram_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasGram_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextGram_GIDLG" + ".model");
        		 Classifier clasGram_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTGram_GIDLG" + ".model");
        		 
        		 featuresNamesList("Sent_GID");
        		 ItemClassifierOne icSent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTSent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasSent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextSent_GIDLG" + ".model");
        		 Classifier clasSent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTSent_GIDLG" + ".model");
          		 
        		 featuresNamesList("Morph_Styl_Gram");
        		 ItemClassifierOne icMorph_Styl_Gram = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Gram = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_Gram = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_GramLG" + ".model");
        		 Classifier clasMorph_Styl_GramT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_GramLG" + ".model");
       		 
        		 featuresNamesList("Morph_Styl_Sent");
        		 ItemClassifierOne icMorph_Styl_Sent = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Sent = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_SentLG" + ".model");
        		 Classifier clasMorph_Styl_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_SentLG" + ".model");
        		 
        		 featuresNamesList("Morph_Styl_GID");
        		 ItemClassifierOne icMorph_Styl_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_GIDLG" + ".model");
        		 Classifier clasMorph_Styl_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_GIDLG" + ".model");
         		 
        		 featuresNamesList("Morph_Gram_Sent");
        		 ItemClassifierOne icMorph_Gram_Sent = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Gram_Sent = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Gram_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Gram_SentLG" + ".model");
        		 Classifier clasMorph_Gram_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Gram_SentLG" + ".model");
      		 
        		 featuresNamesList("Morph_Gram_GID");
        		 ItemClassifierOne icMorph_Gram_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Gram_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Gram_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Gram_GIDLG" + ".model");
        		 Classifier clasMorph_Gram_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Gram_GIDLG" + ".model");
       		 
        		 featuresNamesList("Morph_Sent_GID");
        		 ItemClassifierOne icMorph_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Sent_GIDLG" + ".model");
        		 Classifier clasMorph_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Sent_GIDLG" + ".model");
        		 
        		 featuresNamesList("Styl_Gram_Sent");
        		 ItemClassifierOne icStyl_Gram_Sent = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTStyl_Gram_Sent = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasStyl_Gram_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_Gram_SentLG" + ".model");
        		 Classifier clasStyl_Gram_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_Gram_SentLG" + ".model");
        		 
        		 featuresNamesList("Styl_Gram_GID");
        		 ItemClassifierOne icStyl_Gram_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTStyl_Gram_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasStyl_Gram_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_Gram_GIDLG" + ".model");
        		 Classifier clasStyl_Gram_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_Gram_GIDLG" + ".model");
        		 
        		 featuresNamesList("Styl_Sent_GID");
        		 ItemClassifierOne icStyl_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTStyl_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		Classifier clasStyl_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_Sent_GIDLG" + ".model");
        		Classifier clasStyl_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_Sent_GIDLG" + ".model");
       		 
        		 featuresNamesList("Gram_Sent_GID");
        		 ItemClassifierOne icGram_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTGram_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasGram_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextGram_Sent_GIDLG" + ".model");
        		 Classifier clasGram_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTGram_Sent_GIDLG" + ".model");
        		 
        		 featuresNamesList("Morph_Styl_Gram_Sent");
        		 ItemClassifierOne icMorph_Styl_Gram_Sent = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Gram_Sent = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_Gram_Sent = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_Gram_SentLG" + ".model");
        		 Classifier clasMorph_Styl_Gram_SentT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_Gram_SentLG" + ".model");
        
        		 featuresNamesList("Morph_Styl_Gram_GID");
        		 ItemClassifierOne icMorph_Styl_Gram_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Gram_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_Gram_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_Gram_GIDLG" + ".model");
        		 Classifier clasMorph_Styl_Gram_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_Gram_GIDLG" + ".model");
        		 			
        		 featuresNamesList("Morph_Styl_Sent_GID");
        		 ItemClassifierOne icMorph_Styl_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Styl_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_Sent_GIDLG" + ".model");
        		 Classifier clasMorph_Styl_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_Sent_GIDLG" + ".model");
        		 
        		 featuresNamesList("Morph_Gram_Sent_GID");
        		 ItemClassifierOne icMorph_Gram_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Gram_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasMorph_Gram_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Gram_Sent_GIDLG" + ".model");
        		 Classifier clasMorph_Gram_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Gram_Sent_GIDLG" + ".model");
        	 
        		 featuresNamesList("Styl_Gram_Sent_GID");
        		 ItemClassifierOne icStyl_Gram_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTStyl_Gram_Sent_GID = new ItemClassifierWithTitleOne(featName);
        		 Classifier clasStyl_Gram_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextStyl_Gram_Sent_GIDLG" + ".model");
        		 Classifier clasStyl_Gram_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTStyl_Gram_Sent_GIDLG" + ".model");
        		 
        		 featuresNamesList("Morph_Styl_Gram_Sent_GID");
        		 ItemClassifierOne icMorph_Styl_Gram_Sent_GID = new ItemClassifierOne(featName);
        		 ItemClassifierWithTitleOne icTMorph_Styl_Gram_Sent_GID = new ItemClassifierWithTitleOne(featName);	
        		 Classifier clasMorph_Styl_Gram_Sent_GID = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextMorph_Styl_Gram_Sent_GIDLG" + ".model");
        		 Classifier clasMorph_Styl_Gram_Sent_GIDT = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PandTMorph_Styl_Gram_Sent_GIDLG" + ".model");
        	 
        		 featuresNamesLexicalList("Ngram");
        		 ItemClassifierNgramOne icNgram = new ItemClassifierNgramOne();
        		 Classifier clasNgram = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextNgramLG" + ".model");
        	       		 
        		 featuresNamesLexicalList("BoW");
        		 ItemClassifierBoWOne icBoW = new ItemClassifierBoWOne();
        		 Classifier clasBoW = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextBoWLG" + ".model");
        		 			
        		 featuresNamesFSList("FeatSel");
        		 ItemClassifierFSOne icFeatSel = new ItemClassifierFSOne(featNameFeatSel);
        		 Classifier clasFeatSel = (Classifier) weka.core.SerializationHelper.read(  Configuration.RESOURCES_PATH + "/models/Logisticmodel_PostTextFeatSelLG" + ".model");
        		 
        		
        		 System.out.println(" -- Models loaded in " + (System.currentTimeMillis() - log_models_time));
       		 
        		 featuresNames2ndLevelList("postText");
        		 ItemClassifier2ndLv ic = new ItemClassifier2ndLv(featName2ndLevel);
         
         
         String cvsSplitBy = ",";
         List<String> allPostsForBoW = new ArrayList<String>();
         try ( Stream<String> lines =  Files.lines(Paths.get(input_file))){
			   lines.forEach(line -> {
				   
				   try {  
					   String postID = "";
					   Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
								.create();				   
					   ClikbaitInstanceItem clickbaitItem = gson.fromJson(line, ClikbaitInstanceItem.class);
					   System.out.println("clickbait Item ID " + clickbaitItem.getiID());
					   postID = clickbaitItem.getiID();
					   listItems.add(clickbaitItem);
					   List<String> postTextList = null;
					   postTextList = clickbaitItem.getPostText();
					   String postText = postTextList.get(0);
					   String targetTitle = clickbaitItem.getTargetTitle();
					   List<String> postMedia = clickbaitItem.getPostMedia();
					   allPostsForBoW.add(postText.trim());
					   feat = ItemFeaturesExtractorJSON.extractTextFeatures( postText, clickbaitItem.getiID(), "no-clickbait");				
				   
					   if (postMedia.size() > 0){
						   feat.setHasMedia(true);
					   }else{
						   feat.setHasMedia(false);
					   }
					   double percNed = 0, percPos = 0;
					   List<String> ned = Utils.namedEntityList();
					   List<Double> nedPers = new ArrayList<Double>();
					    for (int k =0; k < ned.size();k++){				    
					    	percNed = 	Utils.percentageOfNamedEnt(ned.get(k), feat.getNEA());
					    	nedPers.add(percNed);
					    	
					    }
				    	
					    feat.setnedPerc(nedPers);
					    
					    List<String> pos = Utils.partOfSpeechList();
					    List<Double> posPers = new ArrayList<Double>();
					    for (int k =0; k < pos.size();k++){				    
					    	percPos = 	Utils.percentageOfPos(pos.get(k), feat.getPOS());
					    	posPers.add(percPos);
					    }
					    feat.setPOSPerc(posPers);
					    List<Double> gidCount = new ArrayList<Double>();
					    try (Stream<String> linesGID = Files.lines(Paths.get(Configuration.RESOURCES_PATH + "/general_inquirer_dictionary/GID.txt"))) {	
						    	linesGID.forEach(linegid ->{
							    	gidCount.add(Utils.getNumGID(postText, Configuration.RESOURCES_PATH + "/general_inquirer_dictionary/GID/" + linegid.trim()));
						    	});
					    } catch (IOException e) {
					   	    System.out.println("Failed to load file.");
					   	}	
					    feat.setgidPerc(gidCount);
			
					    
					    String statusPostText = JavaRunCommandPython.execute(Configuration.RESOURCES_PATH + Configuration.STOP_CLICKBAIT_PYTHON, postText);
					  //  statusPostText = "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";
					       	String statusRemove =  statusPostText.replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("\t", "").trim();
			   					String[] splitfeat= statusRemove.split(cvsSplitBy);
			   					
				            	feat.setAverageLeghtOfEachWord(splitfeat[2]);
				            	feat.setNumberOfHyperbolicTerms(splitfeat[3]);
				            	feat.setNumberOfCommonWords(Integer.valueOf(splitfeat[4]));
				            	feat.setSubjectOfTitle(splitfeat[5]);
				            	feat.setPresenceOfWordContractions(splitfeat[6]);
				            	feat.setPresenceOfDeterminersAndPronouns(splitfeat[7]);
				            	feat.setPresenceOfPunctuations(splitfeat[8]);
				            	feat.setLongestDependency(splitfeat[10]);
				            	feat.setNumNouns(Integer.valueOf(splitfeat[11]));
				            	feat.setNumAdjectives(Integer.valueOf(splitfeat[12]));
				            	feat.setNumVerbs(Integer.valueOf(splitfeat[13]));
				            	feat.setNumAdverbs(Integer.valueOf(splitfeat[14]));
				  
					    	
					    	   if (postText.equals(targetTitle)){
					    		   System.out.println("Post text equals target title ");
								   featTargetTitile = feat;						   
							   }else{
								 //  System.out.println("Target Title extraction");
								   featTargetTitile = ItemFeaturesExtractorJSON.extractTextFeatures( targetTitle, clickbaitItem.getiID(), "no-clickbait");
								   double percNedT = 0, percPosT = 0;
								   List<String> nedT = Utils.namedEntityList();
								   List<Double> nedPersT = new ArrayList<Double>();
								    for (int k =0; k < nedT.size();k++){				    
								    	percNedT = 	Utils.percentageOfNamedEnt(nedT.get(k), featTargetTitile.getNEA());
								    	nedPersT.add(percNedT);
								    	
								    }
								    featTargetTitile.setnedPerc(nedPersT);
								    
								    
								    List<String> posT = Utils.partOfSpeechList();
								    List<Double> posPersT = new ArrayList<Double>();
								    for (int k =0; k < posT.size();k++){				    
								    	percPosT = 	Utils.percentageOfPos(posT.get(k), featTargetTitile.getPOS());
								    	posPersT.add(percPosT);
								    }
								    featTargetTitile.setPOSPerc(posPersT);
								    
								    List<Double> gidCountT = new ArrayList<Double>();
								    try (Stream<String> linesGIDT = Files.lines(Paths.get(Configuration.RESOURCES_PATH + "/general_inquirer_dictionary/GID.txt"))) {	
									    	linesGIDT.forEach(linegid ->{
										    	gidCountT.add(Utils.getNumGID(postText, Configuration.RESOURCES_PATH + "/general_inquirer_dictionary/GID/" + linegid.trim()));
									    	});
								    } catch (IOException e) {
								   	    System.out.println("Failed to load file.");
								   	}	
								    featTargetTitile.setgidPerc(gidCountT);
								    
								    String status = JavaRunCommandPython.execute(Configuration.RESOURCES_PATH + Configuration.STOP_CLICKBAIT_PYTHON, targetTitle);
								  //  status = "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]";		
								    String statusRemove1 =  status.replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("\t", "").trim();
							            	String[] splitfeat1 = statusRemove1.split(cvsSplitBy);

							            	featTargetTitile.setAverageLeghtOfEachWord(splitfeat1[2].trim());
							            	featTargetTitile.setNumberOfHyperbolicTerms(splitfeat1[3].trim());
							            	featTargetTitile.setNumberOfCommonWords(Integer.valueOf(splitfeat1[4].trim()));
							            	featTargetTitile.setSubjectOfTitle(splitfeat1[5].trim());
							            	featTargetTitile.setPresenceOfWordContractions(splitfeat1[6].trim());
							            	featTargetTitile.setPresenceOfDeterminersAndPronouns(splitfeat1[7].trim());
							            	featTargetTitile.setPresenceOfPunctuations(splitfeat1[8].trim());
							            	featTargetTitile.setLongestDependency(splitfeat1[10].trim());
							            	featTargetTitile.setNumNouns(Integer.valueOf(splitfeat1[11].trim()));
							            	featTargetTitile.setNumAdjectives(Integer.valueOf(splitfeat1[12].trim()));
							            	featTargetTitile.setNumVerbs(Integer.valueOf(splitfeat1[13].trim()));
							            	featTargetTitile.setNumAdverbs(Integer.valueOf(splitfeat1[14].trim()));
							    
							   }	
					    	   
					    	   System.out.println(feat.toString());
				 
					    	   	   String categories;
								   categories = "Morph";
								   featuresNamesList("Morph");
								   Instances instMorh = null, instTitleMorph = null;
								   instMorh = icMorth.createDataSetOne(feat, 
										   featName);
								   instTitleMorph = icTMorth.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
									classification2(instMorh.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG",clasMoprh);
									classification2(instTitleMorph.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG",clasMoprhT);
								   } catch (Exception e1) {									
									   e1.printStackTrace();
								   }

								   categories = "Sent";
								   featuresNamesList("Sent");
								   Instances instSent = null, instTitleSent = null;
								   instSent = icSent.createDataSetOne(feat, 
										   featName);
								   instTitleSent = icTSent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instSent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasSent);
										classification2(instTitleSent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasSentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Styl";
							       featuresNamesList("Styl");

								   Instances instStyl = null, instTitleStyl = null;
								   instStyl = icStyl.createDataSetOne(feat, 
										   featName);
								   instTitleStyl = icTStyl.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl);
										classification2(instTitleStyl.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStylT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Gram";
							         featuresNamesList("Gram");

								   Instances instGram = null, instTitleGram = null;
								   instGram = icGram.createDataSetOne(feat, 
										   featName);
								   instTitleGram = icTGram.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instGram.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasGram);
										classification2(instTitleGram.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasGramT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "GID";
							         featuresNamesList("GID");

								   Instances instGID = null, instTitleGID = null;
								   instGID = icGID.createDataSetOne(feat, 
										   featName);
								   instTitleGID = icTGID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instGID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasGID);
										classification2(instTitleGID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasGIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Styl";
							         featuresNamesList("Morph_Styl");

								   Instances instMorph_Styl = null, instTitleMorph_Styl = null;
								   instMorph_Styl = icMorph_Styl.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl = icTMorph_Styl.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl);
										classification2(instTitleMorph_Styl.instance(0),"PandT", categories,  postID , "PandT" + categories + "LG", clasMorph_StylT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Gram";
							         featuresNamesList("Morph_Gram");

								   Instances instMorph_Gram = null, instTitleMorph_Gram = null;
								   instMorph_Gram = icMorph_Gram.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Gram = icTMorph_Gram.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Gram.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Gram);
										classification2(instTitleMorph_Gram.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_GramT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Morph_Sent";
							         featuresNamesList("Morph_Sent");

								   Instances instMorph_Sent = null, instTitleMorph_Sent = null;
								   instMorph_Sent = icMorph_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Sent = icTMorph_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Sent);
										classification2(instTitleMorph_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Morph_GID";
							         featuresNamesList("Morph_GID");

								   Instances instMorph_GID = null, instTitleMorph_GID = null;
								   instMorph_GID = icMorph_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_GID = icTMorph_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_GID);
										classification2(instTitleMorph_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Styl_Gram";
							         featuresNamesList("Styl_Gram");

								   Instances instStyl_Gram = null, instTitleStyl_Gram = null;
								   instStyl_Gram = icStyl_Gram.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Gram = icTStyl_Gram.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Gram.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_Gram);
										classification2(instTitleStyl_Gram.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_GramT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Styl_Sent";
							         featuresNamesList("Styl_Sent");

								   Instances instStyl_Sent = null, instTitleStyl_Sent = null;
								   instStyl_Sent = icStyl_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Sent = icTStyl_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_Sent);
										classification2(instTitleStyl_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Styl_GID";
							         featuresNamesList("Styl_GID");

								   Instances instStyl_GID = null, instTitleStyl_GID = null;
								   instStyl_GID = icStyl_GID.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_GID = icTStyl_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_GID);
										classification2(instTitleStyl_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Gram_Sent";
							         featuresNamesList("Gram_Sent");

								   Instances instGram_Sent = null, instTitleGram_Sent = null;
								   instGram_Sent = icGram_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleGram_Sent = icTGram_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instGram_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasGram_Sent);
										classification2(instTitleGram_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasGram_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Gram_GID";
					        		 featuresNamesList("Gram_GID");

								   Instances instGram_GID = null, instTitleGram_GID = null;
								   instGram_GID = icGram_GID.createDataSetOne(feat, 
										   featName);
								   instTitleGram_GID = icTGram_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instGram_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasGram_GID);
										classification2(instTitleGram_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasGram_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   
								   categories = "Sent_GID";
					        		 featuresNamesList("Sent_GID");

								   Instances instSent_GID = null, instTitleSent_GID = null;
								   instSent_GID = icSent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleSent_GID = icTSent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instSent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasSent_GID);
										classification2(instTitleSent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasSent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Styl_Gram";
					        		 featuresNamesList("Morph_Styl_Gram");

								   Instances instMorph_Styl_Gram = null, instTitleMorph_Styl_Gram = null;
								   instMorph_Styl_Gram = icMorph_Styl_Gram.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Gram = icTMorph_Styl_Gram.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Gram.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Gram);
										classification2(instTitleMorph_Styl_Gram.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_GramT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Morph_Styl_Sent";
					        		 featuresNamesList("Morph_Styl_Sent");

								   Instances instMorph_Styl_Sent = null, instTitleMorph_Styl_Sent = null;
								   instMorph_Styl_Sent = icMorph_Styl_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Sent = icTMorph_Styl_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Sent);
										classification2(instTitleMorph_Styl_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Styl_GID";
					        		 featuresNamesList("Morph_Styl_GID");

								   Instances instMorph_Styl_GID = null, instTitleMorph_Styl_GID = null;
								   instMorph_Styl_GID = icMorph_Styl_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_GID = icTMorph_Styl_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_GID);
										classification2(instTitleMorph_Styl_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Gram_Sent";
					        		 featuresNamesList("Morph_Gram_Sent");

								   Instances instMorph_Gram_Sent = null, instTitleMorph_Gram_Sent = null;
								   instMorph_Gram_Sent = icMorph_Gram_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Gram_Sent = icTMorph_Gram_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Gram_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Gram_Sent);
										classification2(instTitleMorph_Gram_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Gram_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Gram_GID";
					        		 featuresNamesList("Morph_Gram_GID");

								   Instances instMorph_Gram_GID = null, instTitleMorph_Gram_GID = null;
								   instMorph_Gram_GID = icMorph_Gram_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Gram_GID = icTMorph_Gram_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Gram_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Gram_GID);
										classification2(instTitleMorph_Gram_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Gram_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Sent_GID";
					        		 featuresNamesList("Morph_Sent_GID");

								   Instances instMorph_Sent_GID = null, instTitleMorph_Sent_GID = null;
								   instMorph_Sent_GID = icMorph_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Sent_GID = icTMorph_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Sent_GID);
										classification2(instTitleMorph_Sent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Styl_Gram_Sent";
					        		 featuresNamesList("Styl_Gram_Sent");

								   Instances instStyl_Gram_Sent = null, instTitleStyl_Gram_Sent = null;
								   instStyl_Gram_Sent = icStyl_Gram_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Gram_Sent = icTStyl_Gram_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Gram_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_Gram_Sent);
										classification2(instTitleStyl_Gram_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_Gram_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Styl_Gram_GID";
					        		 featuresNamesList("Styl_Gram_GID");

								   Instances instStyl_Gram_GID = null, instTitleStyl_Gram_GID = null;
								   instStyl_Gram_GID = icStyl_Gram_GID.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Gram_GID = icTStyl_Gram_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Gram_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_Gram_GID);
										classification2(instTitleStyl_Gram_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_Gram_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Styl_Sent_GID";
					        		 featuresNamesList("Styl_Sent_GID");

								   Instances instStyl_Sent_GID = null, instTitleStyl_Sent_GID = null;
								   instStyl_Sent_GID = icStyl_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Sent_GID = icTStyl_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasStyl_Sent_GID);
										classification2(instTitleStyl_Sent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasStyl_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Gram_Sent_GID";
					        		 featuresNamesList("Gram_Sent_GID");

								   Instances instGram_Sent_GID = null, instTitleGram_Sent_GID = null;
								   instGram_Sent_GID = icGram_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleGram_Sent_GID = icTGram_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instGram_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasGram_Sent_GID);
										classification2(instTitleGram_Sent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasGram_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }				   
								   
								   categories = "Morph_Styl_Gram_Sent";
					        		 featuresNamesList("Morph_Styl_Gram_Sent");

								   Instances instMorph_Styl_Gram_Sent = null, instTitleMorph_Styl_Gram_Sent = null;
								   instMorph_Styl_Gram_Sent = icMorph_Styl_Gram_Sent.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Gram_Sent = icTMorph_Styl_Gram_Sent.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Gram_Sent.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Gram_Sent);
										classification2(instTitleMorph_Styl_Gram_Sent.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_Gram_SentT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Morph_Styl_Gram_GID";
					        		 featuresNamesList("Morph_Styl_Gram_GID");

								   Instances instMorph_Styl_Gram_GID = null, instTitleMorph_Styl_Gram_GID = null;
								   instMorph_Styl_Gram_GID = icMorph_Styl_Gram_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Gram_GID = icTMorph_Styl_Gram_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Gram_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Gram_GID);
										classification2(instTitleMorph_Styl_Gram_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_Gram_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Morph_Styl_Sent_GID";
					        		 featuresNamesList("Morph_Styl_Sent_GID");

								   Instances instMorph_Styl_Sent_GID = null, instTitleMorph_Styl_Sent_GID = null;
								   instMorph_Styl_Sent_GID = icMorph_Styl_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Sent_GID = icTMorph_Styl_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Sent_GID);
										classification2(instTitleMorph_Styl_Sent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   categories = "Morph_Gram_Sent_GID";
					        		 featuresNamesList("Morph_Gram_Sent_GID");

								   Instances instMorph_Gram_Sent_GID = null, instTitleMorph_Gram_Sent_GID = null;
								   instMorph_Gram_Sent_GID = icMorph_Gram_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Gram_Sent_GID = icTMorph_Gram_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Gram_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Gram_Sent_GID);
										classification2(instTitleMorph_Gram_Sent_GID.instance(0), "PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Gram_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   categories = "Styl_Gram_Sent_GID";
					        		 featuresNamesList("Styl_Gram_Sent_GID");

								   Instances instStyl_Gram_Sent_GID = null, instTitleStyl_Gram_Sent_GID = null;
								   instStyl_Gram_Sent_GID = icStyl_Gram_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleStyl_Gram_Sent_GID = icTStyl_Gram_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instStyl_Gram_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG",clasStyl_Gram_Sent_GID);
										classification2(instTitleStyl_Gram_Sent_GID.instance(0), "PandT", categories,  postID,  "PandT" + categories + "LG",clasStyl_Gram_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   
								   
								   
								   categories = "Morph_Styl_Gram_Sent_GID";
					        		 featuresNamesList("Morph_Styl_Gram_Sent_GID");

								   Instances instMorph_Styl_Gram_Sent_GID = null, instTitleMorph_Styl_Gram_Sent_GID = null;
								   instMorph_Styl_Gram_Sent_GID = icMorph_Styl_Gram_Sent_GID.createDataSetOne(feat, 
										   featName);
								   instTitleMorph_Styl_Gram_Sent_GID = icTMorph_Styl_Gram_Sent_GID.createDataSetOne(feat, featTargetTitile, 
										   featName);
								   
								   try {
										classification2(instMorph_Styl_Gram_Sent_GID.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasMorph_Styl_Gram_Sent_GID);
										classification2(instTitleMorph_Styl_Gram_Sent_GID.instance(0),"PandT", categories,  postID,  "PandT" + categories + "LG", clasMorph_Styl_Gram_Sent_GIDT);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   							   
								   System.out.println(" -- Feature Vector " + instMorph_Styl_Gram_Sent_GID.instance(0));
								   categories = "FeatSel";
								   featuresNamesFSList("FeatSel");
								   Instances instFeatSel = null;
								   instFeatSel = icFeatSel.createDataSetOne(feat, 
										   featNameFeatSel);
								
								   try {
										classification2(instFeatSel.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasFeatSel);
									   } catch (Exception e1) {
										   e1.printStackTrace();
									   }
								   

								   categories = "BoW";
								   Instances instBoW = null, testBoW;
								   instBoW = icBoW.createDataSetOne(feat);
								
								   FixedDictionaryStringToWordVector filterF = new FixedDictionaryStringToWordVector();						
							         
							         String dictionary_path = Configuration.RESOURCES_PATH + "/trainingBoWdictionary.txt"; 
							         filterF.setDictionaryFile(new File(dictionary_path));
							         LovinsStemmer stemmer = new LovinsStemmer();
							         filterF.setStemmer(stemmer);			 
							         filterF.setLowerCaseTokens(true);
							         filterF.setTFTransform(true);
							         filterF.setOutputWordCounts(true);	
							         filterF.setInputFormat(instBoW);
							         testBoW = Filter.useFilter(instBoW, filterF);
								   
								   try {
										classification2(testBoW.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasBoW);
									} catch (Exception e1) {
										   e1.printStackTrace();
									}
								   
								   categories = "Ngram";
								   Instances insNgram = null, testNgram = null;
								   insNgram = icNgram.createDataSetOne(feat);
								
								   
								   NGramTokenizer tokenizer = new NGramTokenizer();
					                //Set Ngram tokenizer to Tri-gram tokenizer
					                tokenizer.setNGramMinSize(minNgram);
					                tokenizer.setNGramMaxSize(maxNgram);
					                FixedDictionaryStringToWordVector filterNgram = new FixedDictionaryStringToWordVector();

								   	String dictionary_path_ngram = Configuration.RESOURCES_PATH + "/trainingNgramdictionary.txt";
								   	filterNgram.setDictionaryFile(new File(dictionary_path_ngram));
								   	filterNgram.setStemmer(stemmer);			 
								   	filterNgram.setLowerCaseTokens(true);
								   	filterNgram.setTFTransform(true);
								   	filterNgram.setOutputWordCounts(true);	
								   	filterNgram.setInputFormat(insNgram);
							         testNgram = Filter.useFilter(insNgram, filterNgram);
								   
								   try {
										classification2(testNgram.instance(0), "PostText", categories,  postID,  "PostText" + categories + "LG", clasNgram);
									} catch (Exception e1) {
										   // TODO Auto-generated catch block
										   e1.printStackTrace();
									}
				   
				   
				   
				  /* 
				    * Second level classification
				    */		   				      
				      	Document query = new Document(mongoDocId, postID);
					    FindIterable<Document> findIterable = collectionProbs.find(query);				
				        Document queryResult = findIterable.first();
				        String s = JSON.serialize(queryResult);
				        JSONObject json = new JSONObject(s);	 
								   
					 Instances dataSet2ndLevel;
				   		dataSet2ndLevel = ic.createDataSetOne(json, featName2ndLevel);	
				   		
				   		classifier = "LG";
						String modelNameLG =   Configuration.RESOURCES_PATH + "/models/Logisticmodel_2ndLevel6" +  classifier + ".model";
					    Classifier logistic = (Classifier) weka.core.SerializationHelper.read(modelNameLG);
					 try {			 
						   	for (int i = 0; i < dataSet2ndLevel.numInstances(); i++) {
						    	   double[] probDistrLG = null;
									try {
										probDistrLG = logistic.distributionForInstance(dataSet2ndLevel.instance(i));
									} catch (Exception e) {
										e.printStackTrace();
									}									
									String jsonString = new JSONObject()
							                  .put("id", postID)
							                  .put("clickbaitScore", probDistrLG[1]).toString();
								    fileWriter.write(jsonString);
								    fileWriter.write("\n");							
						    }	  			
				    } catch (IOException e) {
				        e.printStackTrace();
				    }		 
				   				 
				   } catch (Exception e1) {
						e1.printStackTrace();
					}
				   
			   });
		   };
		   fileWriter.flush();
	       fileWriter.close();
		   bw.close();
		   
		
	
		   closeMongoClient();
		   long end_time = System.currentTimeMillis();
		   System.out.println(" -- END in  " + (end_time - start_time));
	}
	
	public static void classification2(Instance inst, String dataType, String categories, String id, String probId, Classifier clas) throws Exception{
	    	   double[] probDistr = null;
				try {
					probDistr = clas.distributionForInstance(inst);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				Document query = new Document(mongoDocId, id);	
				if (documentsExists(database, ProbCollection1stLevel, id)){							
					Bson upd = new Document("$set", new Document(probId,  probDistr[1]));
					collectionProbs.updateOne(query, upd);
				}else{
					query.append(probId,  probDistr[1]);
					collectionProbs.insertOne(query);
				}			
	}	

}
