package com.marlabs.cab.service.persistance.vo;

import java.io.Serializable;
import java.util.List;

public class AutoCompleteSearchTripVO  implements Serializable{

	private static final long serialVersionUID = -140796987016910833L;
	
	List<Long> tripHeaderIdList;

	public List<Long> getTripHeaderIdList() {
		return tripHeaderIdList;
	}

	public void setTripHeaderIdList(List<Long> tripHeaderIdList) {
		this.tripHeaderIdList = tripHeaderIdList;
	}
}
