package gr.iti.mklab.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer {
		    protected StanfordCoreNLP pipeline;
		    public StanfordLemmatizer() {
		        // Create StanfordCoreNLP object properties, with POS tagging
		        // (required for lemmatization), and lemmatization
		        Properties props;
		        props = new Properties();
		        //props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner,parse, sentiment, dcoref");
		        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner,parse, sentiment,  dcoref" );
		      
		        /*
		         * This is a pipeline that takes in a string and returns various analyzed linguistic forms. 
		         * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator), 
		         * and then other sequence model style annotation can be used to add things like lemmas, 
		         * POS tags, and named entities. These are returned as a list of CoreLabels. 
		         * Other analysis components build and store parse trees, dependency graphs, etc. 
		         * 
		         * This class is designed to apply multiple Annotators to an Annotation. 
		         * The idea is that you first build up the pipeline by adding Annotators, 
		         * and then you take the objects you wish to annotate and pass them in and 
		         * get in return a fully annotated object.
		         * 
		         *  StanfordCoreNLP loads a lot of models, so you probably
		         *  only want to do this once per execution
		         */
		        this.pipeline = new StanfordCoreNLP(props);
		    }

		    public List<String> lemmatize(String documentText)
		    {
		        List<String> lemmas = new LinkedList<String>();
		        if (!documentText.isEmpty()){
				        // Create an empty Annotation just with the given text
				        Annotation document = new Annotation(documentText);
				        // run all Annotators on this text
				        this.pipeline.annotate(document);
				        // Iterate over all of the sentences found
				        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				        for(CoreMap sentence: sentences) {
				        	//System.out.println(sentence);
				            // Iterate over all tokens in a sentence
				            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				            //	System.out.println(token);
				                // Retrieve and add the lemma for each word into the
				                // list of lemmas
				                lemmas.add(token.get(LemmaAnnotation.class));
				                //pos.add(token.get(PartOfSpeechAnnotation.class));
				            }
				        }
		        }else{
		        	lemmas.add("");
		        }
		        return lemmas; //lemmas;
		    }
		    
		    //QuotationsAnnotation
		    public List<String> namedentityannotation(String documentText)
		    {
		    	
		        List<String> neann = new LinkedList<String>();
		        
		        if (!documentText.isEmpty()){
		        // Create an empty Annotation just with the given text
		        Annotation document = new Annotation(documentText);
		        // run all Annotators on this text
		        this.pipeline.annotate(document);
		        // Iterate over all of the sentences found
		        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		        for(CoreMap sentence: sentences) {
		        	//System.out.println(sentence);
		            // Iterate over all tokens in a sentence
		            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		            	
		            	//System.out.println(token);
		                // Retrieve and add the lemma for each word into the
		                // list of lemmas
		            	neann.add(token.get(NamedEntityTagAnnotation.class));
		            	//System.out.println(token.get(NamedEntityTagAnnotation.class));
		            }
		        }
		        
		    	 }else{
		    		 neann.add("");
			        }
		        return neann; //lemmas;
		    }
		    
		    public List<String> lemmatizePOS(String documentText)
		    {
		        List<String> lemmaspos = new LinkedList<String>();
		        
		        if (!documentText.isEmpty()){
				        // Create an empty Annotation just with the given text
				        Annotation document = new Annotation(documentText);
				        // run all Annotators on this text
				        this.pipeline.annotate(document);
				        // Iterate over all of the sentences found
				        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				        for(CoreMap sentence: sentences) {
				        	//System.out.println(sentence);
				            // Iterate over all tokens in a sentence
				            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				            //	System.out.println(token);
				                // Retrieve and add the lemma for each word into the
				                // list of lemmas
				                lemmaspos.add(token.get(LemmaAnnotation.class));
				                //pos.add(token.get(PartOfSpeechAnnotation.class));
				            }
				        }
		   	 }else{
		   		lemmaspos.add("");
		        }
		        return lemmaspos; //lemmas;
		    }
		    
		    public List<String> pos(String documentText)
		    {
		        List<String> pos = new LinkedList<String>();
		        
		        if (!documentText.isEmpty()){
			        // Create an empty Annotation just with the given text
			        Annotation document = new Annotation(documentText);
			        // run all Annotators on this text
			        this.pipeline.annotate(document);
			        // Iterate over all of the sentences found
			        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			        for(CoreMap sentence: sentences) {
			        	  	//System.out.println(sentence);
			            // Iterate over all tokens in a sentence
			            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			            	//System.out.println(token);
			                // Retrieve and add the lemma for each word into the
			                // list of lemmas
			                //lemmas.add(token.get(LemmaAnnotation.class));
			                pos.add(token.get(PartOfSpeechAnnotation.class));
			               // System.out.println(token + " -- " + token.get(PartOfSpeechAnnotation.class));
			            }
			        }
		        }else{
		        	pos.add("");
			        }
		        return pos; //lemmas;
		    }
		    
		    
		    public List<String> getTokens(String documentText)
		    {
		        List<String> tokenList = new LinkedList<String>();
		        
		        if (!documentText.isEmpty()){
			        // Create an empty Annotation just with the given text
			        Annotation document = new Annotation(documentText);
			        // run all Annotators on this text
			        this.pipeline.annotate(document);
			        // Iterate over all of the sentences found
			        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			        for(CoreMap sentence: sentences) {
			        	  	//System.out.println(sentence);
			            // Iterate over all tokens in a sentence
			            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			            	//System.out.println(token);
			                // Retrieve and add the lemma for each word into the
			                // list of lemmas
			            	//System.out.println(token.originalText());
			            	tokenList.add(token.originalText());
			                //lemmas.add(token.get(LemmaAnnotation.class));
			               // System.out.println(token + " -- " + token.get(PartOfSpeechAnnotation.class));
			            }
			        }
		        }else{
		        	tokenList.add("");
			        }
		        return tokenList; //lemmas;
		    }
		    
		    public String findActiveOrPassiveVoice(String documentText)
		    {
		    	String voice;
		    	boolean active = false, passive = false;
		    	 if (!documentText.isEmpty()){
		 	            // Create an empty Annotation just with the given text
				        Annotation document = new Annotation(documentText);
				        // run all Annotators on this text
				        this.pipeline.annotate(document);
				        // Iterate over all of the sentences found
				        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				        for(CoreMap sentence: sentences) {
				        	 SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
				           //  System.out.println("dependency graph:\n" + dependencies.typedDependencies());
				             Collection<TypedDependency> typedDep = dependencies.typedDependencies();
				             for (TypedDependency tp : typedDep){
				            	 //System.out.println("Type " + tp.reln());
				            	 if (tp.reln().toString().equals("nsubj")){
				            		 active = true;
				            	 }
				            	 if (tp.reln().toString().equals("nsubjpass")){
				            		 passive = true;
				            	 }
				             }	        
				        }
				        if (active && passive){
				        	voice = "both";
				        }else if (active) {
				        	voice = "active";
				        }else if (passive) {
				        	voice = "passive";
				        }else{
				        	voice = "none";
				        }
		    	 }else{
		    		 voice ="none";
		    	 }
		        return voice; //lemmas;
		    }
		    
		    public String findSentiment(String documentText)
		    {
		    
		    	String sentiment = "none";
			   	 if (!documentText.isEmpty()){
				    	Annotation annotation = pipeline.process(documentText);
				    	List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
				    	for (CoreMap sentence : sentences) {
				    	  sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				    	 // System.out.println(sentiment + "\t" + sentence);
				    	}
			   	 }
		    	return sentiment;
		    	
		    }


		    public static void main(String[] args) {
		       /* System.out.println("Starting Stanford Lemmatizer");
		        String text = " How could you be seeing into. My eyes like open doors? \n"+
		                "You led me down into my core where I've became so numb \n"+
		                "Without a soul my spirit's sleeping somewhere cold \n"+
		                "Until you find it there and led it back home \n"+
		                "You woke me up inside \n"+
		                "Called my name and saved me from the dark \n"+
		                "You have bidden my blood and it ran \n"+
		                "Before I would become undone \n"+
		                "You saved me from the nothing I've almost become \n"+
		                "You were bringing me to life \n"+
		                "Now that I knew what I'm without \n"+
		                "You can've just left me \n"+
		                "You breathed into me and made me real \n"+
		                "Frozen inside without your touch \n"+
		                "Without your love, darling \n"+
		                "Only you are the life among the dead \n"+
		                "I've been living a lie, there's nothing inside \n"+
		                "You were bringing me to life.";
		     StanfordLemmatizer slem = new StanfordLemmatizer();
		        
		   	 StanfordLemmatizer stanf = new StanfordLemmatizer();
	    	 List<String> pos = stanf.pos(text);
	    	 
	    	 
	    	 List<String> posTokens = stanf.getTokens(text);
	    	 
	    	 //  Generates the word lemmas for all tokens in the corpus.
			 List<String> lem = stanf.lemmatize(text);
			 List<String> pos_temp = new ArrayList<String>();
			 List<String> lempos =  new ArrayList<String>();
			 for (int l=0; l <lem.size(); l++){
				 pos_temp = stanf.pos(lem.get(l));
				 lempos.add(pos_temp.get(0));
				//System.out.println(lem.get(l) + " -- " + pos.get(0));
			 }
			 //System.out.println("LEMPOS " + feat.getLEMPOS());
			 
			 // Recognizes named (PERSON, LOCATION, ORGANIZATION, MISC), numerical (MONEY, NUMBER, ORDINAL, PERCENT), and temporal (DATE, TIME, DURATION, SET) entities. 
			 List<String> nea = stanf.namedentityannotation(text);
			 //System.out.println("NEA " + feat.getNEA());
			stanf.findSentiment(text);			
	    	
	   	  	String voice = stanf.findActiveOrPassiveVoice(text);
		        
		        System.out.println(slem.findActiveOrPassiveVoice("At dinner, six shrimp were eaten by Harry"));
		      //  System.out.println(slem.namedentityannotation("Good afternoon Rajat Raina, how are you today?" +
                     //     "I go to school at Stanford University, which is located in California"));
		        //System.out.println(slem.lemmatize(text));
*/		    }

}
