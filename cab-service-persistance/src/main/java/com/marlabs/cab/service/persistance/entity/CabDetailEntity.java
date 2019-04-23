package com.marlabs.cab.service.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CAB_DETAIL")
@Indexed(index = "assignCab")
@Boost(1.7f)
@AnalyzerDefs({
	@AnalyzerDef(name = "autocompleteEdgeAnalyzer",
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
			     @Parameter(name = "maxGramSize", value = "15") }) }),
	
	@AnalyzerDef(name = "autocompleteNGramAnalyzer",
	tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = NGramFilterFactory.class, params = {
				 @Parameter(name = "minGramSize", value = "1"),
				 @Parameter(name = "maxGramSize", value = "15") }),
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
				 @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
				 @Parameter(name = "replacement", value = " "),
				 @Parameter(name = "replace", value = "all") })
	}),
	
	@AnalyzerDef(name = "standardAnalyzer",
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

public class CabDetailEntity implements Serializable{

	private static final long serialVersionUID = -9159178938255292408L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAB_DETAIL_ID")
	private Long cabDetailId;
	
	
	@JsonBackReference(value="cabOwner_cabDetail")
	@ManyToOne(fetch = FetchType.EAGER,optional=false/*, cascade = CascadeType.DETACH*/)
	@JoinColumn(name = "FK_CAB_OWNER_ID", referencedColumnName = "CAB_OWNER_ID")
	private CabOwnerEntity cabOwner;
	
	/* @JsonIgnore
	@ManyToMany(mappedBy = "cabDetail", fetch = FetchType.EAGER)
	private Set<OfficeCityEntity> officeCity;*/
	
	@JsonIgnore
	//@JsonManagedReference(value="cabDetail_officeCity")
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "OFFICE_CITY_CAB_DETAIL", joinColumns = @JoinColumn(name = "CAB_DETAIL_ID", nullable = false, updatable = false/*,columnDefinition="BIGINT"*/),
				inverseJoinColumns =  @JoinColumn(name = "OFFICE_CITY_ID",nullable = false, updatable = false/*,columnDefinition="BIGINT"*/)
			)
	private Set<OfficeCityEntity> officeCity;
	
	@Column(name = "DRIVER_FIRST_NAME")
	private String driverFirstName;
	
	@Column(name = "DRIVER_MIDDLE_NAME")
	private String driverMiddleName;
	
	@Column(name = "DRIVER_LAST_NAME")
	private String driverLastName;
	
	@Column(name = "DRIVER_PHONE1")
	private String driverPhone1;
	
	@Column(name = "DRIVER_PHONE2")
	private String driverPhone2;
	
	@Column(name = "DRIVER_ADDRESS")
	private String driverAddress;
	
	@Column(name = "DRIVER_LICENSE")
	private String driverLicense;
	
	@Column(name = "REGISTRATION_NO")
	@Fields({
		  @Field(name = "registrationNo", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzer")),
		  @Field(name = "edgeNGramTitle", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzer")),
		  @Field(name = "nGramTitle", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzer"))
		})
	private String registrationNo;
	
	@Column(name = "NUMBER_OF_SEATS")
	private Long numberOfSeats;
	
	@Column(name = "INSURANCE_EXP_DATE")
	private Date insuranceDate;
	
	@Column(name = "ATTACHMENTS")
	private String attachments;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

/*	@Column(name = "UPDATE_DATE")
	private Timestamp updateDate;*/

	public Long getCabDetailId() {
		return cabDetailId;
	}

	public void setCabDetailId(Long cabDetailId) {
		this.cabDetailId = cabDetailId;
	}

	public Set<OfficeCityEntity> getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(Set<OfficeCityEntity> officeCity) {
		this.officeCity = officeCity;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverMiddleName() {
		return driverMiddleName;
	}

	public void setDriverMiddleName(String driverMiddleName) {
		this.driverMiddleName = driverMiddleName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverPhone1() {
		return driverPhone1;
	}

	public void setDriverPhone1(String driverPhone1) {
		this.driverPhone1 = driverPhone1;
	}

	public String getDriverPhone2() {
		return driverPhone2;
	}

	public void setDriverPhone2(String driverPhone2) {
		this.driverPhone2 = driverPhone2;
	}

	public String getDriverAddress() {
		return driverAddress;
	}

	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}
	
	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Long getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Date getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(Date insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

/*	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}*/

	public CabOwnerEntity getCabOwner() {
		return cabOwner;
	}

	public void setCabOwner(CabOwnerEntity cabOwner) {
		this.cabOwner = cabOwner;
	}
	
    public CabDetailEntity()
	 {
	    setOfficeCity(new HashSet<OfficeCityEntity>());
	 }

}
