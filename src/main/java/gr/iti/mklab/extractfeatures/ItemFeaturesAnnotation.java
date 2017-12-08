package gr.iti.mklab.extractfeatures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemFeaturesAnnotation {
	
	@Expose
    @SerializedName(value = "id",  alternate={"_id"})
	protected String id;
	@Expose
    @SerializedName(value = "reliability")
	protected String reliability;
	
	@Expose
    @SerializedName(value = "reliabilityN")
	protected int reliabilityN;
	
	@Expose
    @SerializedName(value = "reliabilityLR")
	protected double reliabilityLR;
	
	
	public String getId() {
		return id;
	}
	
	public String getReliability() {
		return reliability;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setReliability(String reliability){
		this.reliability = reliability;
	}
	
	public double getReliabilityNumLR() {
		return reliabilityLR;
	}
	
	public void setReliabilityNumLR(double reliabilityLR){
		this.reliabilityLR = reliabilityLR;
	}
	
	public int getReliabilityNum() {
		return reliabilityN;
	}
	
	public void setReliabilityNum(int reliabilityN){
		this.reliabilityN = reliabilityN;
	}
}