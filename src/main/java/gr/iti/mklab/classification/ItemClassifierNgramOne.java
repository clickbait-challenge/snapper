package gr.iti.mklab.classification;


import java.util.ArrayList;
import java.util.List;
import gr.iti.mklab.extractfeatures.ItemFeatures;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class ItemClassifierNgramOne {
	
	private Instances isTrainingSet;
	private ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
	public boolean verbose = false;

	
	//constructor
	public ItemClassifierNgramOne() {
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
		

	private ArrayList<Attribute> declareAttributes() {
	
		List<String> fvClass = new ArrayList<String>(2);
		fvClass.add("no-clickbait");
		fvClass.add("clickbait");
		Attribute ClassAttribute = new Attribute("theClass", fvClass);
	
		ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
		Attribute bagOfWords = new Attribute("ngram", (FastVector)null);		   // Declare the class attribute along with its values
		fvAttributes.add(bagOfWords);
		fvAttributes.add(ClassAttribute);
	
		return fvAttributes;
	}


	public Instance createInstance(ItemFeatures listItemFeatures)			
			 {
		Instance iExample = new DenseInstance(fvAttributes.size());
					if (verbose) {
					//System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- BoW getText " + listItemFeatures.getText());
					}
					String text = listItemFeatures.getText().replaceAll("http+s*+://[^ ]+", "")
							.replaceAll("@[^ ]+", "").replaceAll("#[^ ]+ ", "")
							.replaceAll("RT", "").toLowerCase().trim();
					
					iExample.setValue((Attribute) fvAttributes.get(0),
							String.valueOf(text));
		
		return iExample;
	}	
	
		public Instances createDataSetOne(ItemFeatures listTest) {
	
		// Create an empty training set
		//System.out.println(fvAttributes);
		Instances isTestSet = new Instances("Rel", fvAttributes,
				1);
		// Set class index
		isTestSet.setClassIndex(fvAttributes.size() - 1);
		isTestSet.add(createInstance(listTest));
		
		return isTestSet;
	}


	
	
	
}

