package com.devparadigam.agrade.model.response;

import com.google.gson.annotations.SerializedName;

public class ResultDetailsResponse{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("response")
	private String response;

	@SerializedName("id")
	private String id;

	@SerializedName("paper_id")
	private String paperId;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPaperId(String paperId){
		this.paperId = paperId;
	}

	public String getPaperId(){
		return paperId;
	}

	@Override
 	public String toString(){
		return 
			"ResultDetailsResponse{" + 
			"user_id = '" + userId + '\'' + 
			",response = '" + response + '\'' + 
			",id = '" + id + '\'' + 
			",paper_id = '" + paperId + '\'' + 
			"}";
		}
}