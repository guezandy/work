package com.zipper.zipcloset;


import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;


public class Entity extends GenericJson {

	@Key("_id")
    private String id; 
    @Key
    private String brand;
    @Key
    private String imageUrl;
    @Key
    private String purchaseUrl;
    @Key
    private String price;
    @Key
    private String date;
    @Key
    private String similarImg1;
    @Key
    private String similarUrl1;
    @Key
    private String similarImg2;
    @Key
    private String similarUrl2;
    @Key
    private String type;
    @Key
    private int hits;    
    
    
	public Entity(){}
	
	public Entity(String id) {
		this.id = id;
	}
	
	public Entity(String id, 
			String brand, 
			String imageUrl,
			String purchaseUrl,
			String price,
			String date,
			String similarImg1,
			String similarUrl1,
			String similarImg2,
			String similarUrl2,
			String type,
			int hits){
		this.id = id;
		this.brand= brand;
		this.imageUrl = imageUrl;
		this.purchaseUrl= purchaseUrl;
		this.price = price;
		this.date = date;
		this.similarImg1 = similarImg1;
		this.similarUrl1 = similarUrl1;
		this.similarImg2 = similarImg2;
		this.similarUrl2 = similarUrl2;
		this.type = type;
		this.hits = hits;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String url) {
		this.imageUrl = url;
	}
	public void setPurchaseUrl(String purchase) {
		this.purchaseUrl = purchase;
	}
	public String getPurchaseUrl() {
		return purchaseUrl;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice() {
		return price;
	}
	public void setSimilarImg1(String similarImg1) {
		this.similarImg1 = similarImg1;
	}
	public String similarImg1() {
		return similarImg1;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
	public void setSimilarUrl1(String similarUrl1) {
		this.similarUrl1 = similarUrl1;
	}
	public String getSimilarUrl1() {
		return similarUrl1;
	}
	public void setSimilarImg2(String similarImg2) {
		this.similarImg2 = similarImg2;
	}
	public String getSimilarImg2() {
		return similarImg2;
	}
	public void setSimilarUrl2(String SimilarUrl2) {
		this.similarUrl2 = SimilarUrl2;
	}
	public String getSimilarUrl2() {
		return similarUrl2;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getHits() {
		return hits;
	}
	
	public void newHit(int hits) {
		hits = hits++;
	}
	
}
