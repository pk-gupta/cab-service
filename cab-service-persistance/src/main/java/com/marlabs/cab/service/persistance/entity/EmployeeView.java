package com.marlabs.cab.service.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Immutable;
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

@Entity
@Immutable
@Table(name = "employee_details_view")
@Indexed(index = "employeeDetails")
@Boost(1.7f)
@AnalyzerDefs({
	@AnalyzerDef(name = "autocompleteEdgeAnalyzerEmployee",
	tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
			     @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
			     @Parameter(name = "replacement", value = " "),
			     @Parameter(name = "replace", value = "all") }),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = StopFilterFactory.class),
	    		 @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
			     @Parameter(name = "minGramSize", value = "3"),
			     @Parameter(name = "maxGramSize", value = "3") }) }),
	
	@AnalyzerDef(name = "autocompleteNGramAnalyzerEmployee",
	tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
	
	filters = {
				 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
				 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
				 @TokenFilterDef(factory = NGramFilterFactory.class, params = {
				 @Parameter(name = "minGramSize", value = "3"),
				 @Parameter(name = "maxGramSize", value = "3") }),
				 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
				 @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
				 @Parameter(name = "replacement", value = " "),
				 @Parameter(name = "replace", value = "all") })
	}),
	
	@AnalyzerDef(name = "standardAnalyzerEmployee",
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

public class EmployeeView {

	@Id
	@Column(name = "EmployeeId")
	private String employeeId;

	@Column(name = "GlobalEmpId")
	@Fields({
		  @Field(name = "globalEmpId", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzerEmployee")),
		  @Field(name = "edgeNGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerEmployee")),
		  @Field(name = "nGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzerEmployee"))
		})
	private String globalEmpId;

	@Column(name = "FirstName")
	@Fields({
		  @Field(name = "employeeFirstName", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzerEmployee")),
		  @Field(name = "edgeNGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerEmployee")),
		  @Field(name = "nGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzerEmployee"))
		})
	private String employeeFirstName;

	@Column(name = "MiddleName")
	@Fields({
		  @Field(name = "employeeMiddleName", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzerEmployee")),
		  @Field(name = "edgeNGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerEmployee")),
		  @Field(name = "nGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzerEmployee"))
		})
	private String employeeMiddleName;

	@Column(name = "LastName")
	@Fields({
		  @Field(name = "employeeLastName", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzerEmployee")),
		  @Field(name = "edgeNGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerEmployee")),
		  @Field(name = "nGramTitleEmployee", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzerEmployee"))
		})
	private String employeeLastName;

	@Column(name = "Status")
	private String employeeStatus;

	@Column(name = "Gender")
	private String employeeGender;

	@Column(name = "Designation")
	private String employeeDesignation;

	@Column(name = "OffEmailID")
	private String offEmailID;

	@Column(name = "Address1")
	private String employeeAddress1;

	@Column(name = "Address2")
	private String employeeAddress2;

	@Column(name = "Address3")
	private String employeeAddress3;

	@Column(name = "OfficeLocation")
	private String employeeOffcLocation;

	@Column(name = "WorkLocation")
	private String employeeWorkLocation;

	@Column(name = "Mobile")
	private String employeeMobile;

	@Column(name = "WorkPhone")
	private String workPhone;

	@Column(name = "Country")
	private String country;

	@Column(name = "ManagerID")
	private String managerID;

	@Column(name = "MgrFName")
	private String managerFirstName;

	@Column(name = "MgrMName")
	private String managerMiddleName;

	@Column(name = "MgrLName")
	private String managerLastName;

	@Column(name = "MgrEmailId")
	private String managerEmail;
	
	@Column(name = "IsManager")
	private String isManager;
	
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getGlobalEmpId() {
		return globalEmpId;
	}

	public void setGlobalEmpId(String globalEmpId) {
		this.globalEmpId = globalEmpId;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public String getEmployeeDesignation() {
		return employeeDesignation;
	}

	public void setEmployeeDesignation(String employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	public String getOffEmailID() {
		return offEmailID;
	}

	public void setOffEmailID(String offEmailID) {
		this.offEmailID = offEmailID;
	}

	public String getEmployeeAddress1() {
		return employeeAddress1;
	}

	public void setEmployeeAddress1(String employeeAddress1) {
		this.employeeAddress1 = employeeAddress1;
	}

	public String getEmployeeAddress2() {
		return employeeAddress2;
	}

	public void setEmployeeAddress2(String employeeAddress2) {
		this.employeeAddress2 = employeeAddress2;
	}

	public String getEmployeeAddress3() {
		return employeeAddress3;
	}

	public void setEmployeeAddress3(String employeeAddress3) {
		this.employeeAddress3 = employeeAddress3;
	}

	public String getEmployeeOffcLocation() {
		return employeeOffcLocation;
	}

	public void setEmployeeOffcLocation(String employeeOffcLocation) {
		this.employeeOffcLocation = employeeOffcLocation;
	}

	public String getEmployeeWorkLocation() {
		return employeeWorkLocation;
	}

	public void setEmployeeWorkLocation(String employeeWorkLocation) {
		this.employeeWorkLocation = employeeWorkLocation;
	}

	public String getEmployeeMobile() {
		return employeeMobile;
	}

	public void setEmployeeMobile(String employeeMobile) {
		this.employeeMobile = employeeMobile;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}

	public String getManagerMiddleName() {
		return managerMiddleName;
	}

	public void setManagerMiddleName(String managerMiddleName) {
		this.managerMiddleName = managerMiddleName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	
}
