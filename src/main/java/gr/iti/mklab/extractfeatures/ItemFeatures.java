package gr.iti.mklab.extractfeatures;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ItemFeatures {
	
	@Expose
    @SerializedName(value = "id", alternate={"_id"})
	private String id;
	
	@Expose
    @SerializedName(value = "itemLength",  alternate={"item_length"})
	private Integer itemLength;
	
	@Expose
    @SerializedName(value = "numWords", alternate= {"num_words"})
	private Integer numWords;
	
	@Expose
    @SerializedName(value = "containsQuestionMark", alternate= {"contains_questionmark"})
	private Boolean containsQuestionMark;
	
	@Expose
    @SerializedName(value = "containsExclamationMark", alternate= {"contains_exclamationmark"})
	private Boolean containsExclamationMark;
	
	@Expose
    @SerializedName(value = "numQuestionMark", alternate= {"num_questionmark"})
	private Integer numQuestionMark;
	
	@Expose
    @SerializedName(value = "numExclamationMark", alternate= {"num_exclamationmark"})
	private Integer numExclamationMark;
	
	@Expose
    @SerializedName(value = "containsHappyEmo", alternate= {"contains_happy_emoticon"})
	private Boolean containsHappyEmo;
	
	@Expose
    @SerializedName(value = "containsSadEmo", alternate= {"contains_sad_emoticon"})
	private Boolean containsSadEmo;
	
	@Expose
    @SerializedName(value = "containsFirstOrderPron", alternate= {"contains_first_order_pronoun"})
	private Boolean containsFirstOrderPron;
	
	@Expose
    @SerializedName(value = "containsSecondOrderPronoun", alternate= {"contains_second_order_pronoun"})
	private Boolean containsSecondOrderPron;
	
	@Expose
    @SerializedName(value = "containsThirdOrderPronoun", alternate= {"contains_third_order_pronoun"})
	private Boolean containsThirdOrderPron;
	
	@Expose
    @SerializedName(value = "numUppercaseChars", alternate= {"num_uppercasechars"})
	private Integer numUppercaseChars;
	
	@Expose
    @SerializedName(value = "numNegSentiWords", alternate= {"num_neg_sentiment_words"})
	private Integer numNegSentiWords;
	
	@Expose
    @SerializedName(value = "numPosSentiWords", alternate= {"num_pos_sentiment_words"})
	private Integer numPosSentiWords;
	
	@Expose
    @SerializedName(value = "numMentions", alternate= {"num_mentions"})
	private Integer numMentions;
	
	@Expose
    @SerializedName(value = "numHashtags", alternate= {"num_hashtags"})
	private Integer numHashtags;
	
	@Expose
    @SerializedName(value = "numURLs", alternate= {"num_URLs"})
	private Integer numURLs;
	
	@Expose
    @SerializedName(value = "retweetCount", alternate= {"retweet_count"} )
	private Long retweetCount;
	
	@Expose
    @SerializedName(value = "hasColon", alternate= {"has_colon"})
	private Boolean hasColon;
	
	@Expose
    @SerializedName(value = "hasPlease", alternate= {"has_please"})
	private Boolean hasPlease;
	
	@Expose
    @SerializedName(value = "numNouns", alternate= {"num_nouns"})
	private Integer numNouns;
	
	@Expose
    @SerializedName(value = "num_adjectives")
	private Integer num_adjectives;
	
	@Expose
    @SerializedName(value = "num_verbs")
	private Integer num_verbs;
	
	@Expose
    @SerializedName(value = "num_adverbs")
	private Integer num_adverbs;
	
	@Expose
    @SerializedName(value = "hasExternalLink", alternate= {"has_external_link"})
	private Boolean hasExternalLink;
	
	@Expose
    @SerializedName(value = "wotTrust", alternate= {"wot_Trust"})
	private Integer wotTrust;
	
	@Expose
    @SerializedName(value = "wotSafe")
	private Integer wotSafe;
	
	@Expose
    @SerializedName(value = "numSlangs", alternate= {"num_slangs"})
	private Integer numSlangs;
	
	@Expose
    @SerializedName(value = "readability")
	private Double readability;
	
	@Expose
    @SerializedName(value = "urlIndegree", alternate= {"indegree_centrality"})
	private Float urlIndegree;
	
	@Expose
    @SerializedName(value = "urlHarmonic", alternate= {"harmonic_centrality"})
	private Float urlHarmonic;
	
	@Expose
    @SerializedName(value = "containsWordFake")
	private Boolean containsWordFake;
	
	@Expose
    @SerializedName(value = "numFakeWords")
	private Integer numFakeWords;
	
	@Expose
    @SerializedName(value = "numComments")
	private Integer numComments;
		
	@Expose
    @SerializedName(value = "timeFromStart")
	private Long timeFromStart;
	
	@Expose
    @SerializedName(value = "reliability")
	private String reliability;
	
	@Expose
    @SerializedName(value = "alexaPopularity", alternate= {"alexa_popularity"})
	private Integer alexaPopularity;
	
	@Expose
    @SerializedName(value = "alexaReachRank", alternate= {"alexa_reach_rank"})
	private Integer alexaReachRank;
	
	@Expose
    @SerializedName(value = "alexaDeltaRank", alternate= {"alexa_delta_rank"})
	private Integer alexaDeltaRank;
	
	@Expose
    @SerializedName(value = "alexaCountryRank", alternate= {"alexa_country_rank"})
	private Integer alexaCountryRank;
	
	
	@Expose
    @SerializedName(value = "text", alternate={"postTextT"})
	private String text;
	
	@Expose
    @SerializedName(value = "has_media")
	private boolean hasMedia;
	
	@Expose
    @SerializedName(value = "beginsDigit")
	private Integer beginsDigit;
	
	@Expose
    @SerializedName(value = "pos", alternate={"POS"})
	private List<String> pos;
	
	
	@Expose
    @SerializedName(value = "posPerc", alternate={"posPerc"})
	private List<Double> posPerc;
	
	
	@Expose
    @SerializedName(value = "nedPerc", alternate={"nedPerc"})
	private List<Double> nedPerc;
	
	
	@Expose
    @SerializedName(value = "gidCount", alternate={"gidCount"})
	private List<Double> gidCount;
	
	@Expose
    @SerializedName(value = "posTokens", alternate={"POSTokens"})
	private List<String> posTokens;
	
	@Expose
    @SerializedName(value = "lem", alternate={"LEM"})
	private List<String> lem;
	
	@Expose
    @SerializedName(value = "lempos", alternate={"LEMPOS"})
	private List<String> lempos;
	
	@Expose
    @SerializedName(value = "nea")
	private List<String> nea;
	
	@Expose
    @SerializedName(value = "number_of_tweets")
	private Integer numberOfTweets;
	
	@Expose
    @SerializedName(value = "stopWordsPercentage")
	private Double stopWordsPercentage;
	
	@Expose
    @SerializedName(value = "sentiment")
	private String sentiment;
	
	@Expose
    @SerializedName(value = "sentimentId")
	private Integer sentimentID;
	
	@Expose
    @SerializedName(value = "hasHastag")
	private Boolean hasHastag;
	
	@Expose
    @SerializedName(value = "hasAT")
	private Boolean hasAT;
	
	@Expose
    @SerializedName(value = "voice")
	private String textVoice;
	
	@Expose
    @SerializedName(value = "isPassiveVoice")
	private Boolean isPassiveVoice;
	
	@Expose
    @SerializedName(value = "average_leght_of_each_word") 
	private String average_leght_of_each_word;
	
	@Expose
    @SerializedName(value = "longest_dependency")
	private String longest_dependency;
	
	@Expose
    @SerializedName(value = "presence_of_punctuations")
	private String presence_of_punctuations;
	
	@Expose
    @SerializedName(value = "number_of_hyperbolic_terms")
	private String number_of_hyperbolic_terms;
	
	@Expose
    @SerializedName(value = "normal_ngrams_bayes1")
	private String normal_ngrams_bayes1;
	
	@Expose
    @SerializedName(value = "normal_ngrams_bayes2")
	private String normal_ngrams_bayes2;
	
	@Expose
    @SerializedName(value = "normal_ngrams_bayes3")
	private String normal_ngrams_bayes3;
	
	@Expose
    @SerializedName(value = "subject_of_title")
	private String subject_of_title;
	
	@Expose
    @SerializedName(value = "presence_of_word_contractions")
	private String presence_of_word_contractions;
	
	@Expose
    @SerializedName(value = "presence_of_determiners_and_pronouns")
	private String presence_of_determiners_and_pronouns;	
	
	@Expose
    @SerializedName(value = "number_of_common_words")
	private Integer number_of_common_words;	
	

	
	
	@Expose
    @SerializedName(value = "language")
	private String language;
	
	@Expose
    @SerializedName(value = "embedding")
	private String embedding;
	
	
	@Expose
    @SerializedName(value = "truthClass")
	private String truthClass;
	
	
	@Expose
    @SerializedName(value = "truthMean")
	private double truthMean;
	
	@Expose
    @SerializedName(value = "truthClassAn5")
	private double truthClassPerAn;
	
	@Expose
    @SerializedName(value = "postText_vs_targetTitle_perc")
	private double postText_vs_targetTitle_perc;
	
	@Expose
    @SerializedName(value = "postText_vs_targetDescription_perc")
	private double postText_vs_targetDescription_perc;
	
	@Expose
    @SerializedName(value = "targetKeywords", alternate= {"keywords"})
	private String keywords;
	
	
	
	
	public void setPostText_vs_targetTitle_perc(Double postText_vs_targetTitle_perc){
		this.postText_vs_targetTitle_perc = postText_vs_targetTitle_perc;
	}
	
	public Double getPostText_vs_targetTitle_perc(){
		return postText_vs_targetTitle_perc;
	}
	
	public void setpostText_vs_targetDescription_perc(Double postText_vs_targetDescription_perc){
		this.postText_vs_targetDescription_perc = postText_vs_targetDescription_perc;
	}
	
	public Double postText_vs_targetDescription_perc(){
		return postText_vs_targetDescription_perc;
	}
	
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setEmbedding(String embedding) {
		this.embedding = embedding;
	}
	
	public String getEmbedding() {
		return embedding;
	}
	
	
	
	public void setPresenceOfDeterminersAndPronouns(String presence_of_determiners_and_pronouns) {
		this.presence_of_determiners_and_pronouns = presence_of_determiners_and_pronouns;
	}
	
	public String getPresenceOfDeterminersAndPronouns() {
		return presence_of_determiners_and_pronouns;
	}
	
	public void setPresenceOfWordContractions(String presence_of_word_contractions) {
		this.presence_of_word_contractions = presence_of_word_contractions;
	}
	
	public String getPresenceOfWordContractions() {
		return presence_of_word_contractions;
	}
	
	public void setSubjectOfTitle(String subject_of_title) {
		this.subject_of_title = subject_of_title;
	}
	
	public String getSubjectOfTitle() {
		return subject_of_title;
	}
	
	public void setNormalNgramsBayes3(String normal_ngrams_bayes3) {
		this.normal_ngrams_bayes3 = normal_ngrams_bayes3;
	}
	
	public String getNormalNgramsBayes3() {
		return normal_ngrams_bayes3;
	}
	
	public void setNormalNgramsBayes2(String normal_ngrams_bayes2) {
		this.normal_ngrams_bayes2 = normal_ngrams_bayes2;
	}
	
	public String getNormalNgramsBayes2() {
		return normal_ngrams_bayes2;
	}
	
	public void setNormalNgramsBayes1(String normal_ngrams_bayes1) {
		this.normal_ngrams_bayes1 = normal_ngrams_bayes1;
	}
	
	public String getNormalNgramsBayes1() {
		return normal_ngrams_bayes1;
	}
	
	public void setNumberOfHyperbolicTerms(String number_of_hyperbolic_terms) {
		this.number_of_hyperbolic_terms = number_of_hyperbolic_terms;
	}
	
	public String getNumberOfHyperbolicTerms() {
		return number_of_hyperbolic_terms;
	}
	
	public void setPresenceOfPunctuations(String presence_of_punctuations) {
		this.presence_of_punctuations = presence_of_punctuations;
	}
	
	public String getPresenceOfPunctuations() {
		return presence_of_punctuations;
	}
	
	
	public void setNumberOfCommonWords(Integer number_of_common_words) {
		this.number_of_common_words = number_of_common_words;
	}
	
	public Integer getNumberOfCommonWords() {
		return number_of_common_words;
	}
	
	public void setLongestDependency(String longest_dependency) {
		this.longest_dependency = longest_dependency;
	}
	
	public String getLongestDependency() {
		return longest_dependency;
	}
	
	public void setAverageLeghtOfEachWord(String average_leght_of_each_word) {
		this.average_leght_of_each_word = average_leght_of_each_word;
	}
	
	public String getAverageLeghtOfEachWord() {
		return average_leght_of_each_word;
	}
	
	public void setTextVoice(String textVoice){
		this.textVoice = textVoice;
	}
	
	public String getTextVoice(){
		return textVoice;
	}
	
	public void setIsPassiveVoice(Boolean isPassiveVoice){
		this.isPassiveVoice = isPassiveVoice;
	}
	
	public Boolean getIsPassiveVoice(){
		return isPassiveVoice;
	}
	
	
	
	public void setHasAT(Boolean hasAT){
		this.hasAT = hasAT;
	}
	
	public Boolean getHasAT(){
		return hasAT;
	}
	
	public void setHasHastag(Boolean hasHastag){
		this.hasHastag = hasHastag;
	}
	
	public Boolean getHasHastag(){
		return hasHastag;
	}
	
	public void setSentimentId(Integer sentimentID){
		this.sentimentID = sentimentID;
	}
	
	public Integer getSentimentId(){
		return sentimentID;
	}
	
	public void setSentiment(String sentiment){
		this.sentiment = sentiment;
	}
	
	public String getSentiment(){
		return sentiment;
	}
	
	public void setStopWordsPercentage(Double stopWordsPercentage){
		this.stopWordsPercentage = stopWordsPercentage;
	}
	
	public Double getStopWordsPercentages(){
		return stopWordsPercentage;
	}
	
	public void setNumberOfTweets(Integer numberOfTweets){
		this.numberOfTweets = numberOfTweets;
	}
	
	public Integer getNumberOfTweets(){
		return numberOfTweets;
	}
	
	public void setBeginsDigit(Integer beginsDigit){
		this.beginsDigit = beginsDigit;
	}
	
	public Integer getBeginsDigit(){
		return beginsDigit;
	}
	
	public void setPOS(List<String> pos){
		this.pos = pos;
	}
	
	public List<String> getPOS(){
		return pos;
	}
	
	
	public void setPOSPerc(List<Double> posPerc){
		this.posPerc = posPerc;
	}
	
	public List<Double> getPOSPerc(){
		return posPerc;
	}
	
	public void setnedPerc(List<Double> nedPerc){
		this.nedPerc = nedPerc;
	}
	
	public List<Double> getnedPerc(){
		return nedPerc;
	}
	
	
	public void setgidPerc(List<Double> gidCount){
		this.gidCount = gidCount;
	}
	
	public List<Double> getgidPerc(){
		return gidCount;
	}
	
	
	
	public void setPOSTokens(List<String> posTokens){
		this.posTokens = posTokens;
	}
	
	public List<String> getPOSTokens(){
		return posTokens;
	}
	
	public void setLEM(List<String> lem){
		this.lem = lem;
	}
	
	public List<String> getLEM(){
		return lem;
	}
	
	
	public void setLEMPOS(List<String> lempos){
		this.lempos = lempos;
	}
	
	public List<String> getLEMPOS(){
		return lempos;
	}
	
	public void setNEA(List<String> nea){
		this.nea = nea;
	}
	
	public List<String> getNEA(){
		return nea;
	}
	
	
	public void setHasMedia(boolean hasMedia){
		this.hasMedia = hasMedia;
	}
	
	public boolean getHasMedia(){
		return hasMedia;
	}
	
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	
	public void setTruthMeann(double truthMean){
		this.truthMean = truthMean;
	}
	
	public double getTruthMean(){
		return truthMean;
	}
	
	
	public void setTruthClassAn(double truthClassPerAn){
		this.truthClassPerAn = truthClassPerAn;
	}
	
	public double getTruthClassAn(){
		return truthClassPerAn;
	}
	
	public void setTruthClass(String truthClass){
		this.truthClass = truthClass;
	}
	
	public String getTruthClass(){
		return truthClass;
	}
	
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setItemLength(Integer itemLength){
		this.itemLength = itemLength;
	}
	
	public Integer getItemLength(){
		return itemLength;
	}
	
	public void setNumWords(Integer numWords){
		this.numWords = numWords;
	}
	
	public Integer getNumWords(){
		return numWords;
	}
	
	public void setContainsExclamationMark(Boolean containsExclamationMark){
		this.containsExclamationMark = containsExclamationMark;
	}
	
	public boolean getContainsExclamationMark(){
		return containsExclamationMark;
	}
	
	public void setContainsQuestionMark(Boolean containsQuestionMark){
		this.containsQuestionMark = containsQuestionMark;
	}

	public boolean getContainsQuestionMark(){
		return containsQuestionMark;
	}
	
	public void setnumExclamationMark(Integer numExclamationMark){
		this.numExclamationMark = numExclamationMark;
	}
	
	public Integer getNumExclamationMark(){
		return numExclamationMark;
	}
	
	public void setNumQuestionMark(Integer numQuestionMark){
		this.numQuestionMark = numQuestionMark;
	}
	
	public Integer getNumQuestionMark(){
		return numQuestionMark;
	}
	
	public void setContainsHappyEmo(boolean containsHappyEmo){
		this.containsHappyEmo = containsHappyEmo;
	}
	
	public boolean getContainsHappyEmo(){
		return containsHappyEmo;
	}
	
	public void setContainsSadEmo(boolean containsSadEmo){
		this.containsSadEmo = containsSadEmo;
	}
	
	public boolean getContainsSadEmo(){
		return containsSadEmo;
	}
	
	public void setContainsFirstOrderPron(boolean containsFirstOrderPron){
		this.containsFirstOrderPron = containsFirstOrderPron;
	}
	
	public Boolean getContainsFirstOrderPron(){
		return containsFirstOrderPron;
	}
		
	public void setContainsSecondOrderPron(boolean containsSecondOrderPron){
		this.containsSecondOrderPron = containsSecondOrderPron;
	}
	
	public Boolean getContainsSecondOrderPron(){
		return containsSecondOrderPron;
	}
	
	public void setContainsThirdOrderPron(boolean containsThirdOrderPron){
		this.containsThirdOrderPron = containsThirdOrderPron;
	}
	 
	public Boolean getContainsThirdOrderPron(){
		return containsThirdOrderPron;
	}
	
	public void setNumUppercaseChars(Integer numUppercaseChars){
		this.numUppercaseChars = numUppercaseChars;
	}
	
	public Integer getNumUppercaseChars(){
		return numUppercaseChars;
	}
	
	public void setNumNegSentiWords(Integer numNegSentiWords){
		this.numNegSentiWords = numNegSentiWords;
	}
	
	public Integer getNumNegSentiWords(){
		return numNegSentiWords;
	}
	
	public void setNumPosSentiWords(Integer numPosSentiWords){
		this.numPosSentiWords = numPosSentiWords;
	}
	
	public Integer getNumPosSentiWords(){
		return numPosSentiWords;
	}
	
	public void setNumMentions(Integer numMentions){
		this.numMentions = numMentions;
	}
	
	public Integer getNumMentions(){
		return numMentions;
	}
	
	public void setNumHashtags(Integer numHashtags){
		this.numHashtags = numHashtags;
	}
	
	public Integer getNumHashtags(){
		return numHashtags;
	}
	
	public void setNumURLs(Integer numURLs){
		this.numURLs = numURLs;
	}
	
	public Integer getNumURLs(){
		return numURLs;
	}
	
	public void setRetweetCount(Long retweetCount){
		this.retweetCount = retweetCount;
	}
	
	public Long getRetweetCount(){
		return retweetCount;
	}
	
	public void setReliability(String reliability){
		this.reliability = reliability;
	}
	
	public String getReliability(){
		return reliability;
	}
	
	public void setNumSlangs(Integer numSlangs){
		this.numSlangs = numSlangs;
	}
	
	public Integer getNumSlangs() {
		return numSlangs;
	}
	
	public void setHasColon(boolean hasColon) {
		this.hasColon = hasColon;
	}
	
	public boolean getHasColon() {
		return hasColon;
	}
	
	public void setHasPlease(boolean hasPlease) {
		this.hasPlease = hasPlease;
	}
	
	public boolean getHasPlease() {
		return hasPlease;
	}
	
	public void setHasExternalLink(boolean hasExternalLink) {
		this.hasExternalLink = hasExternalLink;
	}
	
	public boolean getHasExternalLink() {
		return hasExternalLink;
	}
	
	public void setWotTrust(Integer wotTrust){
		this.wotTrust = wotTrust;
	}
	
	public Integer getWotTrust() {
		return wotTrust;
	}
	
	public void setWotSafe(Integer wotSafe){
		this.wotSafe = wotSafe;
	}
	
	public Integer getWotSafe() {
		return wotSafe;
	}
	
	public void setReadability(Double readability){
		this.readability = readability;
	}
	
	public Double getReadability() {
		return readability;
	}
	
	public void setUrlIndegree(Float urlIndegree){
		this.urlIndegree = urlIndegree;
	}
	
	public Float getUrlIndegree() {
		return urlIndegree;
	}
	
	public void setUrlHarmonic(Float urlHarmonic){
		this.urlHarmonic = urlHarmonic;
	}
	
	public Float getUrlHarmonic() {
		return urlHarmonic;
	}
	
	public void setNumNouns(Integer numNouns){
		this.numNouns = numNouns;
	}
	
	public Integer getNumNouns() {
		return numNouns;
	}
	
	public void setNumAdjectives(Integer num_adjectives){
		this.num_adjectives = num_adjectives;
	}
	
	public Integer getNumAdjectives() {
		return num_adjectives;
	}
	
	
	public void setNumVerbs(Integer num_verbs){
		this.num_verbs = num_verbs;
	}
	
	public Integer getNumVerbs() {
		return num_verbs;
	}
	
	
	public void setNumAdverbs(Integer num_adverbs){
		this.num_adverbs = num_adverbs;
	}
	
	public Integer getNumAdverbs() {
		return num_adverbs;
	}
	
	
	
	
	public void setAlexaPopularity(Integer alexaPopularity) {
		this.alexaPopularity = alexaPopularity;
	}
	
	public Integer getAlexaPopularity() {
		return alexaPopularity;
	}
	
	public void setAlexaCountryRank(Integer alexaCountryRank) {
		this.alexaCountryRank = alexaCountryRank;
	}
	
	public Integer getAlexaCountryRank() {
		return alexaCountryRank;
	}
	
	public void setAlexaDeltaRank(Integer alexaDeltaRank) {
		this.alexaDeltaRank = alexaDeltaRank;
	}
	
	public Integer getAlexaDeltaRank() {
		return alexaDeltaRank;
	}
	
	public void setAlexaReachRank(Integer alexaReachRank) {
		this.alexaReachRank = alexaReachRank;
	}
	
	public Integer getAlexaReachRank() {
		return alexaReachRank;
	}
	
	public String toJSONString() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}
	
	 
}
