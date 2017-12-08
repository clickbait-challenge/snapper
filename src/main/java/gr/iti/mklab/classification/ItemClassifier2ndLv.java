package gr.iti.mklab.classification;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;

import gr.iti.mklab.extractfeatures.ItemFeatures;
import gr.iti.mklab.extractfeatures.ItemFeaturesAnnotation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class ItemClassifier2ndLv {
	
	private Instances isTrainingSet;
	private ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
	public boolean verbose = false;
		
	//constructor
	public ItemClassifier2ndLv(List<String> features) {
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

	private ArrayList<Attribute> declareAttributes(List<String> features) {
		
		List<String> fvClass = new ArrayList<String>(2);
		fvClass.add("no-clickbait");
		fvClass.add("clickbait");
		Attribute ClassAttribute = new Attribute("theClass", fvClass);

		ArrayList<Attribute> fvAttributes = new ArrayList<Attribute>();
		if (features.contains("PostTextMorphLG")){
			 fvAttributes.add(new Attribute("PostTextMorphLG"));
		}
		if (features.contains("PostTextStylLG")){
			fvAttributes.add(new Attribute("PostTextStylLG"));
		}
		
		if (features.contains("PostTextGramLG")){
			 fvAttributes.add(new Attribute("PostTextGramLG"));			
		}
		
		if (features.contains("PostTextSentLG")){
			 fvAttributes.add(new Attribute("PostTextSentLG"));			
		}
		
		if (features.contains("PostTextGIDLG")){
			 fvAttributes.add( new Attribute("PostTextGIDLG"));			
		}
		
		if (features.contains("PostTextMorph_StylLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_StylLG"));			
		}
		
		if (features.contains("PostTextMorph_GramLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_GramLG"));			
		}
		
		if (features.contains("PostTextMorph_SentLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_SentLG"));			
		}
		
		if (features.contains("PostTextMorph_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_GIDLG"));			
		}
		
		if (features.contains("PostTextStyl_GramLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_GramLG"));			
		}
		
		
		if (features.contains("PostTextStyl_SentLG")){
			 fvAttributes.add( new Attribute("PostTextStyl_SentLG"));			
		}
		
		
		if (features.contains("PostTextStyl_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_GIDLG"));			
		}
		
		
		if (features.contains("PostTextGram_SentLG")){
			 fvAttributes.add(new Attribute("PostTextGram_SentLG"));			
		}
		
		
		if (features.contains("PostTextGram_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextGram_GIDLG"));			
		}
		
		
		if (features.contains("PostTextSent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextSent_GIDLG"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_GramLG")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_GramLG"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_SentLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_SentLG"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_GIDLG"));			
		}
		
		if (features.contains("PostTextMorph_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_SentLG"));			
		}
		
		if (features.contains("PostTextMorph_Gram_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_GIDLG"));			
		}
		
		if (features.contains("PostTextMorph_Sent_GIDLG")){
			 fvAttributes.add( new Attribute("PostTextMorph_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextStyl_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_SentLG"));			
		}
		
		if (features.contains("PostTextStyl_Gram_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_GIDLG"));			
		}
		
		if (features.contains("PostTextStyl_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextGram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextGram_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_Gram_SentLG"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Gram_GIDLG")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_Gram_GIDLG"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Sent_GIDLG")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextMorph_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextStyl_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_Sent_GIDLG"));			
		}
		

		if (features.contains("PostTextMorph_Styl_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_Gram_Sent_GIDLG"));			
		}
		
		if (features.contains("PostTextNgramLG")){
			 fvAttributes.add( new Attribute("PostTextNgramLG"));			
		}
		
		if (features.contains("PostTextBoWLG")){
			 fvAttributes.add(new Attribute("PostTextBoWLG"));			
		}
	
		if (features.contains("PostTextKeywordsLG")){
			 fvAttributes.add(new Attribute("PostTextKeywordsLG"));			
		}
		
		if (features.contains("PostTextFeatSelLG")){
			 fvAttributes.add( new Attribute("PostTextFeatSelLG"));			
		}
		
		
		/*
		 * POST test Random forest
		 */
		
		if (features.contains("PostTextMorphRF")){
			 fvAttributes.add(new Attribute("PostTextMorphRF"));
		}
		if (features.contains("PostTextStylRF")){
			fvAttributes.add(new Attribute("PostTextStylRF"));
		}
		
		if (features.contains("PostTextGramRF")){
			 fvAttributes.add(new Attribute("PostTextGramRF"));			
		}
		
		if (features.contains("PostTextSentRF")){
			 fvAttributes.add(new Attribute("PostTextSentRF"));			
		}
		
		if (features.contains("PostTextGIDRF")){
			 fvAttributes.add( new Attribute("PostTextGIDRF"));			
		}
		
		if (features.contains("PostTextMorph_StylRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_StylRF"));			
		}
		
		if (features.contains("PostTextMorph_GramRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_GramRF"));			
		}
		
		if (features.contains("PostTextMorph_SentRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_SentRF"));			
		}
		
		if (features.contains("PostTextMorph_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_GIDRF"));			
		}
		
		if (features.contains("PostTextStyl_GramRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_GramRF"));			
		}
		
		
		if (features.contains("PostTextStyl_SentRF")){
			 fvAttributes.add( new Attribute("PostTextStyl_SentRF"));			
		}
		
		
		if (features.contains("PostTextStyl_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_GIDRF"));			
		}
		
		
		if (features.contains("PostTextGram_SentRF")){
			 fvAttributes.add(new Attribute("PostTextGram_SentRF"));			
		}
		
		
		if (features.contains("PostTextGram_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextGram_GIDRF"));			
		}
		
		
		if (features.contains("PostTextSent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextSent_GIDRF"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_GramRF")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_GramRF"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_SentRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_SentRF"));			
		}
		
		
		if (features.contains("PostTextMorph_Styl_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_GIDRF"));			
		}
		
		if (features.contains("PostTextMorph_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_SentRF"));			
		}
		
		if (features.contains("PostTextMorph_Gram_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_GIDRF"));			
		}
		
		if (features.contains("PostTextMorph_Sent_GIDRF")){
			 fvAttributes.add( new Attribute("PostTextMorph_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextStyl_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_SentRF"));			
		}
		
		if (features.contains("PostTextStyl_Gram_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_GIDRF"));			
		}
		
		if (features.contains("PostTextStyl_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextGram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextGram_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_Gram_SentRF"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Gram_GIDRF")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_Gram_GIDRF"));			
		}
		
		if (features.contains("PostTextMorph_Styl_Sent_GIDRF")){
			 fvAttributes.add( new Attribute("PostTextMorph_Styl_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextMorph_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Gram_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextStyl_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextStyl_Gram_Sent_GIDRF"));			
		}
		

		if (features.contains("PostTextMorph_Styl_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PostTextMorph_Styl_Gram_Sent_GIDRF"));			
		}
		
		if (features.contains("PostTextNgramRF")){
			 fvAttributes.add( new Attribute("PostTextNgramRF"));			
		}
		
		if (features.contains("PostTextBoWRF")){
			 fvAttributes.add(new Attribute("PostTextBoWRF"));			
		}
	
		if (features.contains("PostTextKeywordsRF")){
			 fvAttributes.add(new Attribute("PostTextKeywordsRF"));			
		}
		
		if (features.contains("PostTextFeatSelRF")){
			 fvAttributes.add( new Attribute("PostTextFeatSelRF"));			
		}
		
		
		if (features.contains("PandTMorphLG")){
			 fvAttributes.add(new Attribute("PandTMorphLG"));
		}
		if (features.contains("PandTStylLG")){
			fvAttributes.add(new Attribute("PandTStylLG"));
		}
		
		if (features.contains("PandTGramLG")){
			 fvAttributes.add(new Attribute("PandTGramLG"));			
		}
		
		if (features.contains("PandTSentLG")){
			 fvAttributes.add(new Attribute("PandTSentLG"));			
		}
		
		if (features.contains("PandTGIDLG")){
			 fvAttributes.add( new Attribute("PandTGIDLG"));			
		}
		
		if (features.contains("PandTMorph_StylLG")){
			 fvAttributes.add(new Attribute("PandTMorph_StylLG"));			
		}
		
		if (features.contains("PandTMorph_GramLG")){
			 fvAttributes.add(new Attribute("PandTMorph_GramLG"));			
		}
		
		if (features.contains("PandTMorph_SentLG")){
			 fvAttributes.add(new Attribute("PandTMorph_SentLG"));			
		}
		
		if (features.contains("PandTMorph_GIDLG")){
			 fvAttributes.add(new Attribute("PandTMorph_GIDLG"));			
		}
		
		if (features.contains("PandTStyl_GramLG")){
			 fvAttributes.add(new Attribute("PandTStyl_GramLG"));			
		}
		
		
		if (features.contains("PandTStyl_SentLG")){
			 fvAttributes.add( new Attribute("PandTStyl_SentLG"));			
		}
		
		
		if (features.contains("PandTStyl_GIDLG")){
			 fvAttributes.add(new Attribute("PandTStyl_GIDLG"));			
		}
		
		
		if (features.contains("PandTGram_SentLG")){
			 fvAttributes.add(new Attribute("PandTGram_SentLG"));			
		}
		
		
		if (features.contains("PandTGram_GIDLG")){
			 fvAttributes.add(new Attribute("PandTGram_GIDLG"));			
		}
		
		
		if (features.contains("PandTSent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTSent_GIDLG"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_GramLG")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_GramLG"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_SentLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_SentLG"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_GIDLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_GIDLG"));			
		}
		
		if (features.contains("PandTMorph_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_SentLG"));			
		}
		
		if (features.contains("PandTMorph_Gram_GIDLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_GIDLG"));			
		}
		
		if (features.contains("PandTMorph_Sent_GIDLG")){
			 fvAttributes.add( new Attribute("PandTMorph_Sent_GIDLG"));			
		}
		
		if (features.contains("PandTStyl_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_SentLG"));			
		}
		
		if (features.contains("PandTStyl_Gram_GIDLG")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_GIDLG"));			
		}
		
		if (features.contains("PandTStyl_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTStyl_Sent_GIDLG"));			
		}
		
		if (features.contains("PandTGram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTGram_Sent_GIDLG"));			
		}
		
		if (features.contains("PandTMorph_Styl_Gram_SentLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_Gram_SentLG"));			
		}
		
		if (features.contains("PandTMorph_Styl_Gram_GIDLG")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_Gram_GIDLG"));			
		}
		
		if (features.contains("PandTMorph_Styl_Sent_GIDLG")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_Sent_GIDLG"));			
		}
		
		if (features.contains("PandTMorph_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_Sent_GIDLG"));			
		}
		
		if (features.contains("PandTStyl_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_Sent_GIDLG"));			
		}
		

		if (features.contains("PandTMorph_Styl_Gram_Sent_GIDLG")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_Gram_Sent_GIDLG"));			
		}
		

		if (features.contains("PandTMorphRF")){
			 fvAttributes.add(new Attribute("PandTMorphRF"));
		}
		if (features.contains("PandTStylRF")){
			fvAttributes.add(new Attribute("PandTStylRF"));
		}
		
		if (features.contains("PandTGramRF")){
			 fvAttributes.add(new Attribute("PandTGramRF"));			
		}
		
		if (features.contains("PandTSentRF")){
			 fvAttributes.add(new Attribute("PandTSentRF"));			
		}
		
		if (features.contains("PandTGIDRF")){
			 fvAttributes.add( new Attribute("PandTGIDRF"));			
		}
		
		if (features.contains("PandTMorph_StylRF")){
			 fvAttributes.add(new Attribute("PandTMorph_StylRF"));			
		}
		
		if (features.contains("PandTMorph_GramRF")){
			 fvAttributes.add(new Attribute("PandTMorph_GramRF"));			
		}
		
		if (features.contains("PandTMorph_SentRF")){
			 fvAttributes.add(new Attribute("PandTMorph_SentRF"));			
		}
		
		if (features.contains("PandTMorph_GIDRF")){
			 fvAttributes.add(new Attribute("PandTMorph_GIDRF"));			
		}
		
		if (features.contains("PandTStyl_GramRF")){
			 fvAttributes.add(new Attribute("PandTStyl_GramRF"));			
		}
		
		
		if (features.contains("PandTStyl_SentRF")){
			 fvAttributes.add( new Attribute("PandTStyl_SentRF"));			
		}
		
		
		if (features.contains("PandTStyl_GIDRF")){
			 fvAttributes.add(new Attribute("PandTStyl_GIDRF"));			
		}
		
		
		if (features.contains("PandTGram_SentRF")){
			 fvAttributes.add(new Attribute("PandTGram_SentRF"));			
		}
		
		
		if (features.contains("PandTGram_GIDRF")){
			 fvAttributes.add(new Attribute("PandTGram_GIDRF"));			
		}
		
		
		if (features.contains("PandTSent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTSent_GIDRF"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_GramRF")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_GramRF"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_SentRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_SentRF"));			
		}
		
		
		if (features.contains("PandTMorph_Styl_GIDRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_GIDRF"));			
		}
		
		if (features.contains("PandTMorph_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_SentRF"));			
		}
		
		if (features.contains("PandTMorph_Gram_GIDRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_GIDRF"));			
		}
		
		if (features.contains("PandTMorph_Sent_GIDRF")){
			 fvAttributes.add( new Attribute("PandTMorph_Sent_GIDRF"));			
		}
		
		if (features.contains("PandTStyl_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_SentRF"));			
		}
		
		if (features.contains("PandTStyl_Gram_GIDRF")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_GIDRF"));			
		}
		
		if (features.contains("PandTStyl_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTStyl_Sent_GIDRF"));			
		}
		
		if (features.contains("PandTGram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTGram_Sent_GIDRF"));			
		}
		
		if (features.contains("PandTMorph_Styl_Gram_SentRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_Gram_SentRF"));			
		}
		
		if (features.contains("PandTMorph_Styl_Gram_GIDRF")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_Gram_GIDRF"));			
		}
		
		if (features.contains("PandTMorph_Styl_Sent_GIDRF")){
			 fvAttributes.add( new Attribute("PandTMorph_Styl_Sent_GIDRF"));			
		}
		
		if (features.contains("PandTMorph_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Gram_Sent_GIDRF"));			
		}
		
		if (features.contains("PandTStyl_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTStyl_Gram_Sent_GIDRF"));			
		}
		

		if (features.contains("PandTMorph_Styl_Gram_Sent_GIDRF")){
			 fvAttributes.add(new Attribute("PandTMorph_Styl_Gram_Sent_GIDRF"));			
		}
		

		
		
		
		
		
		fvAttributes.add(ClassAttribute);

		return fvAttributes;
	}


	
	public Instance createInstance19ID(ItemFeatures listItemFeatures, 			
			JSONObject jsonFeature, 
			List<String> features) {
		Instance iExample = new DenseInstance(fvAttributes.size());
		//Instance iExample = new SparseInstance(fvAttributes.size());
		//String id = listItemFeatures.getId().replaceAll("[^\\d.]", "");
		String id = listItemFeatures.getId();
		System.out.println(" -- Post id " + id);
		iExample.setValue((Attribute) fvAttributes.get(0), id);
		int cnt = 1;
		for (int l=0; l < features.size();l++){
			//System.out.println("Cnt " + cnt + " -- Feature " + features.get(l));
				//System.out.println(features.get(l));
				//System.out.println(cnt);			
			
				if (features.get(l).equals("PostTextMorphLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorphLG " + jsonFeature.getDouble("PostTextMorphLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorphLG"));
				}
				
				if (features.get(l).equals("PostTextStylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStylLG " + jsonFeature.getDouble("PostTextStylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStylLG"));
				}
				
				if (features.get(l).equals("PostTextGramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGramLG " + jsonFeature.getDouble("PostTextGramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGramLG"));
				}	
				
				if (features.get(l).equals("PostTextSentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSentLG " + jsonFeature.getDouble("PostTextSentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSentLG"));
				}	
				
				if (features.get(l).equals("PostTextGIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGIDLG " + jsonFeature.getDouble("PostTextGIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGIDLG"));
				}	
				
				if (features.get(l).equals("PostTextMorph_StylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_StylLG " + jsonFeature.getDouble("PostTextMorph_StylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_StylLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GramLG " + jsonFeature.getDouble("PostTextMorph_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GramLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_SentLG " + jsonFeature.getDouble("PostTextMorph_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_SentLG"));
				}
				
				
				if (features.get(l).equals("PostTextMorph_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GIDLG " + jsonFeature.getDouble("PostTextMorph_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GramLG " + jsonFeature.getDouble("PostTextStyl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GramLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_SentLG " + jsonFeature.getDouble("PostTextStyl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_SentLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GIDLG " + jsonFeature.getDouble("PostTextStyl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextGram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_SentLG " + jsonFeature.getDouble("PostTextGram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_SentLG"));
				}
				
				
				if (features.get(l).equals("PostTextGram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_GIDLG " + jsonFeature.getDouble("PostTextGram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextSent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSent_GIDLG " + jsonFeature.getDouble("PostTextSent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GramLG " + jsonFeature.getDouble("PostTextMorph_Styl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GramLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_SentLG " + jsonFeature.getDouble("PostTextMorph_Styl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_SentLG " + jsonFeature.getDouble("PostTextMorph_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_GIDLG " + jsonFeature.getDouble("PostTextMorph_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_SentLG " + jsonFeature.getDouble("PostTextStyl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_GIDLG " + jsonFeature.getDouble("PostTextStyl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Sent_GIDLG " + jsonFeature.getDouble("PostTextStyl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextGram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_Sent_GIDLG " + jsonFeature.getDouble("PostTextGram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_SentLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextNgramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextNgramLG " + jsonFeature.getDouble("PostTextNgramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextNgramLG"));
				}
				
				if (features.get(l).equals("PostTextBoWLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextBoWLG " + jsonFeature.getDouble("PostTextBoWLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextBoWLG"));
				}
				
				if (features.get(l).equals("PostTextKeywordsLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextKeywordsLG " + jsonFeature.getDouble("PostTextKeywordsLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextKeywordsLG"));
				}
				
				if (features.get(l).equals("PostTextFeatSelLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextFeatSelLG " + jsonFeature.getDouble("PostTextFeatSelLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextFeatSelLG"));
				}			
				
				
				/*
				 * Post Text Random Forest
				 */
				
				if (features.get(l).equals("PostTextMorphRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorphRF " + jsonFeature.getDouble("PostTextMorphRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorphRF"));
				}
				
				if (features.get(l).equals("PostTextStylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStylRF " + jsonFeature.getDouble("PostTextStylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStylRF"));
				}
				
				if (features.get(l).equals("PostTextGramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGramRF " + jsonFeature.getDouble("PostTextGramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGramRF"));
				}	
				
				if (features.get(l).equals("PostTextSentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSentRF " + jsonFeature.getDouble("PostTextSentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSentRF"));
				}	
				
				if (features.get(l).equals("PostTextGIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGIDRF " + jsonFeature.getDouble("PostTextGIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGIDRF"));
				}	
				
				if (features.get(l).equals("PostTextMorph_StylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_StylRF " + jsonFeature.getDouble("PostTextMorph_StylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_StylRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GramRF " + jsonFeature.getDouble("PostTextMorph_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GramRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_SentRF " + jsonFeature.getDouble("PostTextMorph_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_SentRF"));
				}
				
				
				if (features.get(l).equals("PostTextMorph_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GIDRF " + jsonFeature.getDouble("PostTextMorph_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GramRF " + jsonFeature.getDouble("PostTextStyl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GramRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_SentRF " + jsonFeature.getDouble("PostTextStyl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_SentRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GIDRF " + jsonFeature.getDouble("PostTextStyl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextGram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_SentRF " + jsonFeature.getDouble("PostTextGram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_SentRF"));
				}
				
				
				if (features.get(l).equals("PostTextGram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_GIDRF " + jsonFeature.getDouble("PostTextGram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextSent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSent_GIDRF " + jsonFeature.getDouble("PostTextSent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GramRF " + jsonFeature.getDouble("PostTextMorph_Styl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GramRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_SentRF " + jsonFeature.getDouble("PostTextMorph_Styl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_SentRF " + jsonFeature.getDouble("PostTextMorph_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_GIDRF " + jsonFeature.getDouble("PostTextMorph_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_SentRF " + jsonFeature.getDouble("PostTextStyl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_GIDRF " + jsonFeature.getDouble("PostTextStyl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Sent_GIDRF " + jsonFeature.getDouble("PostTextStyl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextGram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_Sent_GIDRF " + jsonFeature.getDouble("PostTextGram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_SentRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextNgramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextNgramRF " + jsonFeature.getDouble("PostTextNgramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextNgramRF"));
				}
				
				if (features.get(l).equals("PostTextBoWRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextBoWRF " + jsonFeature.getDouble("PostTextBoWRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextBoWRF"));
				}
				
				if (features.get(l).equals("PostTextKeywordsRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextKeywordsRF " + jsonFeature.getDouble("PostTextKeywordsRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextKeywordsRF"));
				}
				
				if (features.get(l).equals("PostTextFeatSelRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextFeatSelRF " + jsonFeature.getDouble("PostTextFeatSelRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextFeatSelRF"));
				}			
				
				cnt = cnt + 1;
		
		}

		return iExample;
	}
	
	public Instances createDataSetOne(JSONObject listTest, 
			List<String> featurestemp) {

			// Create an empty training set
			//System.out.println(fvAttributes);
			Instances isTestSet = new Instances("Rel", fvAttributes,
					1);
			// Set class index
			isTestSet.setClassIndex(fvAttributes.size() - 1);
			isTestSet.add(createInstance19ID(listTest, 			
					 featurestemp));
			
			return isTestSet;
		}
	
	public Instance createInstance19ID(JSONObject jsonFeature, 
			List<String> features) {
		Instance iExample = new DenseInstance(fvAttributes.size());
		//Instance iExample = new SparseInstance(fvAttributes.size());
		//String id = listItemFeatures.getId().replaceAll("[^\\d.]", "");
		String id = jsonFeature.getString("_id");
		System.out.println(" -- Post id " + id);
		//iExample.setValue((Attribute) fvAttributes.get(0), id);
		int cnt = 1;
		for (int l=0; l < features.size();l++){
			//System.out.println("Cnt " + cnt + " -- Feature " + features.get(l));
				//System.out.println(features.get(l));
				//System.out.println(cnt);			
			
				if (features.get(l).equals("PostTextMorphLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorphLG " + jsonFeature.getDouble("PostTextMorphLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorphLG"));
				}
				
				if (features.get(l).equals("PostTextStylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStylLG " + jsonFeature.getDouble("PostTextStylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStylLG"));
				}
				
				if (features.get(l).equals("PostTextGramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGramLG " + jsonFeature.getDouble("PostTextGramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGramLG"));
				}	
				
				if (features.get(l).equals("PostTextSentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSentLG " + jsonFeature.getDouble("PostTextSentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSentLG"));
				}	
				
				if (features.get(l).equals("PostTextGIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGIDLG " + jsonFeature.getDouble("PostTextGIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGIDLG"));
				}	
				
				if (features.get(l).equals("PostTextMorph_StylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_StylLG " + jsonFeature.getDouble("PostTextMorph_StylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_StylLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GramLG " + jsonFeature.getDouble("PostTextMorph_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GramLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_SentLG " + jsonFeature.getDouble("PostTextMorph_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_SentLG"));
				}
				
				
				if (features.get(l).equals("PostTextMorph_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GIDLG " + jsonFeature.getDouble("PostTextMorph_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GramLG " + jsonFeature.getDouble("PostTextStyl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GramLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_SentLG " + jsonFeature.getDouble("PostTextStyl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_SentLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GIDLG " + jsonFeature.getDouble("PostTextStyl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextGram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_SentLG " + jsonFeature.getDouble("PostTextGram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_SentLG"));
				}
				
				
				if (features.get(l).equals("PostTextGram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_GIDLG " + jsonFeature.getDouble("PostTextGram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextSent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSent_GIDLG " + jsonFeature.getDouble("PostTextSent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GramLG " + jsonFeature.getDouble("PostTextMorph_Styl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GramLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_SentLG " + jsonFeature.getDouble("PostTextMorph_Styl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_SentLG " + jsonFeature.getDouble("PostTextMorph_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_GIDLG " + jsonFeature.getDouble("PostTextMorph_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_SentLG " + jsonFeature.getDouble("PostTextStyl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_GIDLG " + jsonFeature.getDouble("PostTextStyl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Sent_GIDLG " + jsonFeature.getDouble("PostTextStyl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextGram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_Sent_GIDLG " + jsonFeature.getDouble("PostTextGram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_SentLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PostTextNgramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextNgramLG " + jsonFeature.getDouble("PostTextNgramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextNgramLG"));
				}
				
				if (features.get(l).equals("PostTextBoWLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextBoWLG " + jsonFeature.getDouble("PostTextBoWLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextBoWLG"));
				}
				
				if (features.get(l).equals("PostTextKeywordsLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextKeywordsLG " + jsonFeature.getDouble("PostTextKeywordsLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextKeywordsLG"));
				}
				
				if (features.get(l).equals("PostTextFeatSelLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextFeatSelLG " + jsonFeature.getDouble("PostTextFeatSelLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextFeatSelLG"));
				}			
				
				
				/*
				 * Post Text Random Forest
				 */
				
				if (features.get(l).equals("PostTextMorphRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorphRF " + jsonFeature.getDouble("PostTextMorphRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorphRF"));
				}
				
				if (features.get(l).equals("PostTextStylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStylRF " + jsonFeature.getDouble("PostTextStylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStylRF"));
				}
				
				if (features.get(l).equals("PostTextGramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGramRF " + jsonFeature.getDouble("PostTextGramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGramRF"));
				}	
				
				if (features.get(l).equals("PostTextSentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSentRF " + jsonFeature.getDouble("PostTextSentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSentRF"));
				}	
				
				if (features.get(l).equals("PostTextGIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGIDRF " + jsonFeature.getDouble("PostTextGIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGIDRF"));
				}	
				
				if (features.get(l).equals("PostTextMorph_StylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_StylRF " + jsonFeature.getDouble("PostTextMorph_StylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_StylRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GramRF " + jsonFeature.getDouble("PostTextMorph_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GramRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_SentRF " + jsonFeature.getDouble("PostTextMorph_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_SentRF"));
				}
				
				
				if (features.get(l).equals("PostTextMorph_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_GIDRF " + jsonFeature.getDouble("PostTextMorph_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GramRF " + jsonFeature.getDouble("PostTextStyl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GramRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_SentRF " + jsonFeature.getDouble("PostTextStyl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_SentRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_GIDRF " + jsonFeature.getDouble("PostTextStyl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextGram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_SentRF " + jsonFeature.getDouble("PostTextGram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_SentRF"));
				}
				
				
				if (features.get(l).equals("PostTextGram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_GIDRF " + jsonFeature.getDouble("PostTextGram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextSent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextSent_GIDRF " + jsonFeature.getDouble("PostTextSent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextSent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GramRF " + jsonFeature.getDouble("PostTextMorph_Styl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GramRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_SentRF " + jsonFeature.getDouble("PostTextMorph_Styl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_SentRF " + jsonFeature.getDouble("PostTextMorph_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_GIDRF " + jsonFeature.getDouble("PostTextMorph_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_SentRF " + jsonFeature.getDouble("PostTextStyl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_GIDRF " + jsonFeature.getDouble("PostTextStyl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Sent_GIDRF " + jsonFeature.getDouble("PostTextStyl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextGram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextGram_Sent_GIDRF " + jsonFeature.getDouble("PostTextGram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextGram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_SentRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextStyl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextStyl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextStyl_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextMorph_Styl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextMorph_Styl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextMorph_Styl_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PostTextNgramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextNgramRF " + jsonFeature.getDouble("PostTextNgramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextNgramRF"));
				}
				
				if (features.get(l).equals("PostTextBoWRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextBoWRF " + jsonFeature.getDouble("PostTextBoWRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextBoWRF"));
				}
				
				if (features.get(l).equals("PostTextKeywordsRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextKeywordsRF " + jsonFeature.getDouble("PostTextKeywordsRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextKeywordsRF"));
				}
				
				if (features.get(l).equals("PostTextFeatSelRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PostTextFeatSelRF " + jsonFeature.getDouble("PostTextFeatSelRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PostTextFeatSelRF"));
				}		
				
				
				if (features.get(l).equals("PandTMorphLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorphLG " + jsonFeature.getDouble("PandTMorphLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorphLG"));
				}
				
				if (features.get(l).equals("PandTStylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStylLG " + jsonFeature.getDouble("PandTStylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStylLG"));
				}
				
				if (features.get(l).equals("PandTGramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGramLG " + jsonFeature.getDouble("PandTGramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGramLG"));
				}	
				
				if (features.get(l).equals("PandTSentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTSentLG " + jsonFeature.getDouble("PandTSentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTSentLG"));
				}	
				
				if (features.get(l).equals("PandTGIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGIDLG " + jsonFeature.getDouble("PandTGIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGIDLG"));
				}	
				
				if (features.get(l).equals("PandTMorph_StylLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_StylLG " + jsonFeature.getDouble("PandTMorph_StylLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_StylLG"));
				}
				
				if (features.get(l).equals("PandTMorph_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_GramLG " + jsonFeature.getDouble("PandTMorph_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_GramLG"));
				}
				
				if (features.get(l).equals("PandTMorph_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_SentLG " + jsonFeature.getDouble("PandTMorph_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_SentLG"));
				}
				
				
				if (features.get(l).equals("PandTMorph_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_GIDLG " + jsonFeature.getDouble("PandTMorph_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_GIDLG"));
				}
				
				if (features.get(l).equals("PandTStyl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_GramLG " + jsonFeature.getDouble("PandTStyl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_GramLG"));
				}
				
				if (features.get(l).equals("PandTStyl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_SentLG " + jsonFeature.getDouble("PandTStyl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_SentLG"));
				}
				
				if (features.get(l).equals("PandTStyl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_GIDLG " + jsonFeature.getDouble("PandTStyl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_GIDLG"));
				}
				
				if (features.get(l).equals("PandTGram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_SentLG " + jsonFeature.getDouble("PandTGram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_SentLG"));
				}
				
				
				if (features.get(l).equals("PandTGram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_GIDLG " + jsonFeature.getDouble("PandTGram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_GIDLG"));
				}
				
				if (features.get(l).equals("PandTSent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTSent_GIDLG " + jsonFeature.getDouble("PandTSent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTSent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_GramLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_GramLG " + jsonFeature.getDouble("PandTMorph_Styl_GramLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_GramLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_SentLG " + jsonFeature.getDouble("PandTMorph_Styl_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_SentLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_GIDLG " + jsonFeature.getDouble("PandTMorph_Styl_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_SentLG " + jsonFeature.getDouble("PandTMorph_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_GIDLG " + jsonFeature.getDouble("PandTMorph_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Sent_GIDLG " + jsonFeature.getDouble("PandTMorph_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_SentLG " + jsonFeature.getDouble("PandTStyl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_GIDLG " + jsonFeature.getDouble("PandTStyl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PandTStyl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Sent_GIDLG " + jsonFeature.getDouble("PandTStyl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTGram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_Sent_GIDLG " + jsonFeature.getDouble("PandTGram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_SentLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_SentLG " + jsonFeature.getDouble("PandTMorph_Styl_Gram_SentLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_SentLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_GIDLG " + jsonFeature.getDouble("PandTMorph_Styl_Gram_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Sent_GIDLG " + jsonFeature.getDouble("PandTMorph_Styl_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_Sent_GIDLG " + jsonFeature.getDouble("PandTMorph_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PandTStyl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_Sent_GIDLG"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_Sent_GIDLG")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_Sent_GIDLG " + jsonFeature.getDouble("PandTMorph_Styl_Gram_Sent_GIDLG"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_Sent_GIDLG"));
				}
				
		
				
				if (features.get(l).equals("PandTMorphRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorphRF " + jsonFeature.getDouble("PandTMorphRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorphRF"));
				}
				
				if (features.get(l).equals("PandTStylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStylRF " + jsonFeature.getDouble("PandTStylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStylRF"));
				}
				
				if (features.get(l).equals("PandTGramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGramRF " + jsonFeature.getDouble("PandTGramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGramRF"));
				}	
				
				if (features.get(l).equals("PandTSentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTSentRF " + jsonFeature.getDouble("PandTSentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTSentRF"));
				}	
				
				if (features.get(l).equals("PandTGIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGIDRF " + jsonFeature.getDouble("PandTGIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGIDRF"));
				}	
				
				if (features.get(l).equals("PandTMorph_StylRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_StylRF " + jsonFeature.getDouble("PandTMorph_StylRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_StylRF"));
				}
				
				if (features.get(l).equals("PandTMorph_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_GramRF " + jsonFeature.getDouble("PandTMorph_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_GramRF"));
				}
				
				if (features.get(l).equals("PandTMorph_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_SentRF " + jsonFeature.getDouble("PandTMorph_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_SentRF"));
				}
				
				
				if (features.get(l).equals("PandTMorph_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_GIDRF " + jsonFeature.getDouble("PandTMorph_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_GIDRF"));
				}
				
				if (features.get(l).equals("PandTStyl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_GramRF " + jsonFeature.getDouble("PandTStyl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_GramRF"));
				}
				
				if (features.get(l).equals("PandTStyl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_SentRF " + jsonFeature.getDouble("PandTStyl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_SentRF"));
				}
				
				if (features.get(l).equals("PandTStyl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_GIDRF " + jsonFeature.getDouble("PandTStyl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_GIDRF"));
				}
				
				if (features.get(l).equals("PandTGram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_SentRF " + jsonFeature.getDouble("PandTGram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_SentRF"));
				}
				
				
				if (features.get(l).equals("PandTGram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_GIDRF " + jsonFeature.getDouble("PandTGram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_GIDRF"));
				}
				
				if (features.get(l).equals("PandTSent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTSent_GIDRF " + jsonFeature.getDouble("PandTSent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTSent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_GramRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_GramRF " + jsonFeature.getDouble("PandTMorph_Styl_GramRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_GramRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_SentRF " + jsonFeature.getDouble("PandTMorph_Styl_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_SentRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_GIDRF " + jsonFeature.getDouble("PandTMorph_Styl_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_SentRF " + jsonFeature.getDouble("PandTMorph_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_GIDRF " + jsonFeature.getDouble("PandTMorph_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Sent_GIDRF " + jsonFeature.getDouble("PandTMorph_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_SentRF " + jsonFeature.getDouble("PandTStyl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_GIDRF " + jsonFeature.getDouble("PandTStyl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PandTStyl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Sent_GIDRF " + jsonFeature.getDouble("PandTStyl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTGram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTGram_Sent_GIDRF " + jsonFeature.getDouble("PandTGram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTGram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_SentRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_SentRF " + jsonFeature.getDouble("PandTMorph_Styl_Gram_SentRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_SentRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_GIDRF " + jsonFeature.getDouble("PandTMorph_Styl_Gram_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Sent_GIDRF " + jsonFeature.getDouble("PandTMorph_Styl_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Gram_Sent_GIDRF " + jsonFeature.getDouble("PandTMorph_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTStyl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTStyl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PandTStyl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTStyl_Gram_Sent_GIDRF"));
				}
				
				if (features.get(l).equals("PandTMorph_Styl_Gram_Sent_GIDRF")){	
					if (verbose) {
					System.out.println("* Attribute " + fvAttributes.get(cnt).name());
					System.out.println("	-- PandTMorph_Styl_Gram_Sent_GIDRF " + jsonFeature.getDouble("PandTMorph_Styl_Gram_Sent_GIDRF"));
					}
					iExample.setValue((Attribute) fvAttributes.get(cnt),
							jsonFeature.getDouble("PandTMorph_Styl_Gram_Sent_GIDRF"));
				}
				
		
				cnt = cnt + 1;
		
		}

		return iExample;
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
	
	
	public Instances createDataSet(List<JSONObject> jsonFeatures, List<String> features) {

		//System.out.println("Size of dataset " + listTest.size());
		//System.out.println("Size of annotation " + itemFeaturesAnnot.size());
		// Create an empty training set
		System.out.println(fvAttributes);
		Instances isTestSet = new Instances("Rel", fvAttributes,
				jsonFeatures.size());
		// Set class index
		isTestSet.setClassIndex(fvAttributes.size() - 1);

				
		//iterate through list of ItemFeatures
		for (int i = 0; i < jsonFeatures.size(); i++) {
			//create an Instance
			//System.out.println("listTest " + listTest.get(i).getContainsExclamationMark());
			// Instance iExample = createInstance19(listTest.get(i));
			//Instance iExample = createInstance19NOID(listTest.get(i));
			Instance iExample = createInstance19ID(jsonFeatures.get(i), 
					features);
			//System.out.println("iExample " + iExample);
			//Instance iExample = createInstance(listTest.get(i));
			//find the reliability value of this feature from the map and put it to the Instance object just created
			//System.out.println("value " + (fvAttributes.size() - 1));
			//System.out.println(map.get(listTest.get(i).getId()));
			iExample.setValue((Attribute) fvAttributes.get(fvAttributes.size() - 1), "no-clickbait");
			//add the complete Instance to the Instances object
			//System.out.println("iExample after " + iExample);
			isTestSet.add(iExample);
		}	
		return isTestSet;

	}	
	
	public Instances createDataSet(List<ItemFeatures> listTest, 
			List<JSONObject> jsonFeatures,
			List<ItemFeaturesAnnotation> itemFeaturesAnnot, List<String> features) {

		//System.out.println("Size of dataset " + listTest.size());
		//System.out.println("Size of annotation " + itemFeaturesAnnot.size());
		// Create an empty training set
		//System.out.println(fvAttributes);
		Instances isTestSet = new Instances("Rel", fvAttributes,
				listTest.size());
		// Set class index
		isTestSet.setClassIndex(fvAttributes.size() - 1);

		//save the <id,label> pair to a map
		HashMap<String,String> map = new HashMap<String, String>();
		
		for (int j = 0; j < itemFeaturesAnnot.size(); j++) {
			//System.out.println("itemFeaturesAnnot " + itemFeaturesAnnot.get(j).getId() + " -- " + itemFeaturesAnnot.get(j).getReliability());
			map.put(itemFeaturesAnnot.get(j).getId(), itemFeaturesAnnot.get(j).getReliability());
		}
		
		//iterate through list of ItemFeatures
		for (int i = 0; i < listTest.size(); i++) {
			//create an Instance
			//System.out.println("listTest " + listTest.get(i).getContainsExclamationMark());
			// Instance iExample = createInstance19(listTest.get(i));
			//Instance iExample = createInstance19NOID(listTest.get(i));
			Instance iExample = createInstance19ID(listTest.get(i), 
					jsonFeatures.get(i), 
					features);
			//System.out.println("iExample " + iExample);
			//Instance iExample = createInstance(listTest.get(i));
			//find the reliability value of this feature from the map and put it to the Instance object just created
			//System.out.println("value " + (fvAttributes.size() - 1));
			//System.out.println(map.get(listTest.get(i).getId()));
			iExample.setValue((Attribute) fvAttributes.get(fvAttributes.size() - 1), map.get(listTest.get(i).getId()) );
			//add the complete Instance to the Instances object
			//System.out.println("iExample after " + iExample);
			isTestSet.add(iExample);
		}	
		return isTestSet;

	}	
	
}

