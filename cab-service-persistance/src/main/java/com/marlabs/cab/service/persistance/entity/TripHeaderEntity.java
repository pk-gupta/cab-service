package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.LongBridge;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TRIP_HEADER")
@Indexed(index="assignCabTrip")
@AnalyzerDefs({
	@AnalyzerDef(name = "autocompleteEdgeAnalyzerTrip",
	tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
			     @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
			     @Parameter(name = "replacement", value = " "),
			     @Parameter(name = "replace", value = "all") }),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = StopFilterFactory.class),
	    		 @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
			     @Parameter(name = "minGramSize", value = "1"),
			     @Parameter(name = "maxGramSize", value = "20") }) }),
	
	@AnalyzerDef(name = "autocompleteNGramAnalyzerTrip",
	tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = NGramFilterFactory.class, params = {
				 @Parameter(name = "minGramSize", value = "1"),
				 @Parameter(name = "maxGramSize", value = "20") }),
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
				 @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
				 @Parameter(name = "replacement", value = " "),
				 @Parameter(name = "replace", value = "all") })
	}),
	
	@AnalyzerDef(name = "standardAnalyzerTrip",
	tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
			     @Parameter(name = "pattern", value = "([^a-zA-Z0-9\\.])"),
			     @Parameter(name = "replacement", value = " "),
			     @Parameter(name = "replace", value = "all") })
	}) 
})

public class TripHeaderEntity implements Serializable{
	
	private static final long serialVersionUID = -2534328843950051079L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId(name="id")
	@Column(name = "TRIP_HEADER_ID", unique= true)
    @Fields({
		  @Field(name = "tripHeaderId", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzerTrip")),
		  @Field(name = "edgeNGramTitleTrip", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerTrip")),
		  @Field(name = "nGramTitleTrip", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzerTrip"))
		})
    @FieldBridge(impl = LongBridge.class)
	private Long tripHeaderId;
    
    @JsonManagedReference(value="tripHeader_tripDetail")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "tripHeader", fetch = FetchType.EAGER)
	@Column(insertable=false, updatable=false)
	private List<TripDetailEntity> tripDetaillList;
    
    @JsonBackReference(value="cabDetail_tripHeader")
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "FK_CAB_DETAIL_ID", referencedColumnName = "CAB_DETAIL_ID")
	private CabDetailEntity cabDetail;
    
	@Column(name = "SERVICE_TYPE")
	private String serviceType;
	
	@Column(name = "TRIP_DATE")
	private Date tripDate;
	
	@Column(name = "TRIP_TIME")
	private String tripTime;
	
	@Column(name = "OTHER_VEHICLE_REG_NO")
	private String otherVehicleRegNo;
	
	@Column(name = "OTHER_DRIVER_NAME")
	private String otherDriverName;
	
	@Column(name = "OTHER_DRIVER_PHONE")
	private String otherDriverPhone;
	
	@Column(name = "TRIP_STATUS")
	private String tripStatus;
	
	@Column(name = "TRIP_START_TIME")
	private String tripStartTime;
	
	@Column(name = "TRIP_END_TIME")
	private String tripEndTime;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATE_DATE",insertable=false)
	private Timestamp updateDate;

	public Long getTripHeaderId() {
		return tripHeaderId;
	}

	public void setTripHeaderId(Long tripHeaderId) {
		this.tripHeaderId = tripHeaderId;
	}

	public CabDetailEntity getCabDetail() {
		return cabDetail;
	}

	public void setCabDetail(CabDetailEntity cabDetail) {
		this.cabDetail = cabDetail;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public List<TripDetailEntity> getTripDetaillList() {
		return tripDetaillList;
	}

	public void setTripDetaillList(List<TripDetailEntity> tripDetaillList) {
		this.tripDetaillList = tripDetaillList;
	}

	public String getOtherVehicleRegNo() {
		return otherVehicleRegNo;
	}

	public void setOtherVehicleRegNo(String otherVehicleRegNo) {
		this.otherVehicleRegNo = otherVehicleRegNo;
	}

	public String getOtherDriverName() {
		return otherDriverName;
	}

	public void setOtherDriverName(String otherDriverName) {
		this.otherDriverName = otherDriverName;
	}

	public String getOtherDriverPhone() {
		return otherDriverPhone;
	}

	public void setOtherDriverPhone(String otherDriverPhone) {
		this.otherDriverPhone = otherDriverPhone;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(String tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public String getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
