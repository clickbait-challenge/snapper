package gr.iti.mklab.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClikbaitInstanceItem {
	
	@Expose
    @SerializedName(value = "id")
	private String id;
	
	@Expose
    @SerializedName(value = "postTimestamp")
	private String postTimestamp;
	
	@Expose
    @SerializedName(value = "postText")
	private List<String> postText;
	
	@Expose
    @SerializedName(value = "postMedia")
	private List<String> postMedia;
	
	@Expose
    @SerializedName(value = "targetTitle")
	private String targetTitle;
	
	@Expose
    @SerializedName(value = "targetDescription")
	private String targetDescription;
	
	@Expose
    @SerializedName(value = "targetKeywords")
	private String targetKeywords;
	
	@Expose
    @SerializedName(value = "targetParagraphs")
	private List<String> targetParagraphs;
	
	@Expose
    @SerializedName(value = "targetCaptions")
	private List<String> targetCaptions;
	
	@Expose
    @SerializedName(value = "truthClass")
	private String truthClass;
	
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getiID() {
		return id;
	}
	
	public void setTruthClass(String truthClass) {
		this.truthClass = truthClass;
	}
	
	public String getiTruthClass() {
		return truthClass;
	}
	
	
	public void setPostTimestamp(String postTimestamp) {
		this.postTimestamp = postTimestamp;
	}
	
	public String getPostTimestamp() {
		return postTimestamp;
	}
	
	public void setPostText(List<String> postText) {
		this.postText = postText;
	}
	
	public List<String> getPostText() {
		return postText;
	}
	
	public void setPostMedia(List<String> postMedia) {
		this.postMedia = postMedia;
	}
	
	public List<String> getPostMedia() {
		return postMedia;
	}
	
	public void setTargetTitle(String targetTitle) {
		this.targetTitle = targetTitle;
	}
	
	public String getTargetTitle() {
		return targetTitle;
	}
	
	public void setTargetDescription(String targetDescription) {
		this.targetDescription = targetDescription;
	}
	
	public String getTargetDescription() {
		return targetDescription;
	}
	
	public void setTargetParagraphs(List<String> targetParagraphs) {
		this.targetParagraphs = targetParagraphs;
	}
	
	public List<String> getTargetParagraphs() {
		return targetParagraphs;
	}
	
	public void setTargetKeywords(String targetKeywords) {
		this.targetKeywords = targetKeywords;
	}
	
	public String getTargetKeywords() {
		return targetKeywords;
	}
	
	public void setTargetCaptions(List<String> targetCaptions) {
		this.targetCaptions = targetCaptions;
	}
	
	public List<String> getTargetCaptions() {
		return targetCaptions;
	}
	
	public String toJSONString() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}

}
