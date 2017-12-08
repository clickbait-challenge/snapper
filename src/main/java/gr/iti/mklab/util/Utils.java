package gr.iti.mklab.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;


public class Utils {
	
	public static List<String> partOfSpeechList(){
		
		List<String> posList= new LinkedList<String>();
		
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
		posList.add("VBP");
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
		
		return posList;
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
 
 public static double percentageOfNamedEnt(String pos, List<String> posListOfNamedEnt){
		
		int size = posListOfNamedEnt.size();
		//System.out.println(posListOfNamedEnt.toString());
		long n = IntStream.range(0, posListOfNamedEnt.size())
	            .filter(i -> pos.equals(posListOfNamedEnt.get(i)))
	            .count();
	//	System.out.println("Named Entity " + pos + " -- Frequency " + n + " -- percentage " + ((double) n /(double)size));
		return ((double) n /(double)size);
	}

	public static double percentageOfPos(String pos, List<String> posListOfPost){
		
		int size = posListOfPost.size();
		//System.out.println(posListOfPost.toString());
		long n = IntStream.range(0, posListOfPost.size())
	            .filter(i -> pos.equals(posListOfPost.get(i)))
	            .count();
	//	System.out.println("Pos " + pos + " -- Frequency " + n + " -- percentage " + ((double) n /(double)size));
		return ((double) n /(double)size);
	}
	
	public static double getNumGID(String postText, String gidFile) {
		Integer numGIDWords = 0;
		BufferedReader br = null;
		// String itemTitle = item.getTitle().toString();
		String[] tokens = TextProcessing.getInstance().tokenizeText(postText);
		// use hashset to save the words from the txt file
		HashSet<String> gidwords = new HashSet<String>();
		try {
			File gidWords = new File(gidFile);
			if (!gidWords.exists()) {
				gidWords.createNewFile();
			}
			String currentLine;
			br = new BufferedReader(new FileReader(gidWords));

			while ((currentLine = br.readLine()) != null) {
				String[] splitCurr = currentLine.split("#");
				//System.out.println(splitCurr[0]);
				gidwords.add(splitCurr[0].toLowerCase());
			}

			for (int i = 0; i < tokens.length; i++) {

				String clearToken = tokens[i].replaceAll("[^A-Za-z0-9 ]", "")
						.toLowerCase();

				if (gidwords.contains(clearToken.toLowerCase())) {
					numGIDWords++;
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
		double percGIDWord = (double) numGIDWords / (double) tokens.length;
		return percGIDWord;
	}
	
	
	public static String removeNewlines(String text){
		String textValue = text;
		 textValue= textValue.replaceAll("\n", "");
		 textValue= textValue.replaceAll("\t", "");
		 textValue= textValue.replaceAll("\\n", "");
		 textValue= textValue.replaceAll("\\t", "");
		 textValue= textValue.replaceAll("\r", "");
		 textValue= textValue.replaceAll("\\r", "");
		 textValue= textValue.replaceAll("\r\n", "");
		 textValue= textValue.replaceAll("\\r\\n", "");
		return textValue;
	}
	
	

}
