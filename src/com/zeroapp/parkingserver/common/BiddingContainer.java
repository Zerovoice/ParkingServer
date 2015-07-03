package com.zeroapp.parkingserver.common;

public class BiddingContainer extends Bidding{
			private String areaName;
			private double earnings;
			private String companyName;
			private double cost;
			
			public String getAreaName(){
				return areaName;
			}
			public void setAreaName(String aName){
				this.areaName = aName;
			}
			public void setEarnings(double e){
				this.earnings = e;
			}
			public double getEarnings(){
				return earnings;
			}
			public String getComName(){
				return companyName;
			}
			public void setComName(String cn){
				this.companyName = cn;
			}
			public void setCost(double e){
				this.cost = e;
			}
			public double getCost(){
				return cost;
			}
}
