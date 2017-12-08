package gr.iti.mklab.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClikbaitTruthItem {
	
	@Expose
    @SerializedName(value = "id")
	private String id;
	
	@Expose
    @SerializedName(value = "truthJudgments")
	private  List<Double> truthJudgments;
	
	@Expose
    @SerializedName(value = "truthMean")
	private Double truthMean;
	
	@Expose
    @SerializedName(value = "truthMedian")
	private Double truthMedian;
	
	@Expose
    @SerializedName(value = "truthMode")
	private Double truthMode;
	
	@Expose
    @SerializedName(value = "truthClass")
	private String truthClass;
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getiID() {
		return id;
	}
	
	public void setTruthJudgments( List<Double> truthJudgments) {
		this.truthJudgments = truthJudgments;
	}
	
	public List<Double> getTruthJudgments() {
		return truthJudgments;
	}

	public void setTruthMean( Double truthMean) {
		this.truthMean = truthMean;
	}
	
	public Double getTruthMean() {
		return truthMean;
	}
	
	public void setTruthMedian( Double truthMedian) {
		this.truthMedian = truthMedian;
	}
	
	public  Double getTruthMedian() {
		return truthMedian;
	}
	
	public void setTruthMode( Double truthMode) {
		this.truthMode = truthMode;
	}
	
	public Double getTruthMode() {
		return truthMode;
	}
	
	public void setTruthClass(String truthClass) {
		this.truthClass = truthClass;
	}
	
	public String getTruthClass() {
		return truthClass;
	}
	
	public String toJSONString() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}
}
