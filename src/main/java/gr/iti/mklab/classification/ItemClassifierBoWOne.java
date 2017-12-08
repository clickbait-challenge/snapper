package gr.iti.mklab.classification;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import gr.iti.mklab.extractfeatures.ItemFeatures;
import gr.iti.mklab.util.Configuration;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class ItemClassifierBoWOne {
	
	private Instances isTrainingSet;
	private ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
	public boolean verbose = false;
	
	//constructor
	public ItemClassifierBoWOne() {
		setFvAttributes(declareAttributes());
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
	
	private ArrayList<Attribute> declareAttributes() {
	
		List<String> fvClass = new ArrayList<String>(2);
		fvClass.add("no-clickbait");
		fvClass.add("clickbait");
		Attribute ClassAttribute = new Attribute("theClass", fvClass);
	
		ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();

		Attribute bagOfWords = new Attribute("bagOfWords", (FastVector)null);		   // Declare the class attribute along with its values
		fvAttributes.add(bagOfWords);		
			
		fvAttributes.add(ClassAttribute);
	
		return fvAttributes;
	}


	public Instance createInstance(ItemFeatures listItemFeatures)			
			 {
		Instance iExample = new DenseInstance(fvAttributes.size());
		
		int cnt = 0;
		
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
						if (word.length()>2 && !stop_eng.contains(word) && !pos_remove.contains(pos)){
							newCleanString = newCleanString + " " + word;
						}
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							String.valueOf(newCleanString.trim()));
		
		return iExample;
	}	
	
		public Instances createDataSetOne(ItemFeatures listTest) {
	
				// Create an empty training set
				Instances isTestSet = new Instances("Rel", fvAttributes,
						1);
				// Set class index
				isTestSet.setClassIndex(fvAttributes.size() - 1);
				isTestSet.add(createInstance(listTest));
				
				return isTestSet;
			}
	
	
}

