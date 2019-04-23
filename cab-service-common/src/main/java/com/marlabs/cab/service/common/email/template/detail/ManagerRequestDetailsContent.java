package com.marlabs.cab.service.common.email.template.detail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.util.CabServiceUtil;

public class ManagerRequestDetailsContent implements EmailDetailContent {

	@Override
	public void setEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		setBody(template, emailTemplateVO);
	}
	
	private void setBody(StringBuilder template, EmailTemplateVO emailTemplateVO){
		switch(emailTemplateVO.getRequestServiceType()){
			case Constants.SERVICE_TYPE_PICKUP:
				setPickupContent(template, emailTemplateVO);
				break;
			case Constants.SERVICE_TYPE_DROP:
				setDropContent(template, emailTemplateVO);
				break;
			case Constants.SERVICE_TYPE_PICKUP_DROP:
				setPickupDropContent(template, emailTemplateVO);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private void setPickupContent(StringBuilder template, EmailTemplateVO emailTemplateVO){
		setPrimaryDetails(template, emailTemplateVO);
		
		if(emailTemplateVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)){
			setTravelScheduleDate(template, emailTemplateVO);
		}else{
		setTravelDate(template, emailTemplateVO);
		}
		setLoginTime(template, emailTemplateVO);
		if(emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)){
			setCancelledRemark(template, emailTemplateVO);
			setCancelTravelSchedule(template, emailTemplateVO);
		}else{
			setOtherDetails(template, emailTemplateVO);
			setEmployeeRemark(template, emailTemplateVO);
		}
	}

	private void setDropContent(StringBuilder template, EmailTemplateVO emailTemplateVO){
		setPrimaryDetails(template, emailTemplateVO);
		
		if(emailTemplateVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)){
			setTravelScheduleDate(template, emailTemplateVO);
		}else{
		setTravelDate(template, emailTemplateVO);
		}
		setLogoutTime(template, emailTemplateVO);
		if(emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)){
			setCancelledRemark(template, emailTemplateVO);
			setCancelTravelSchedule(template, emailTemplateVO);
		}else{
			setOtherDetails(template, emailTemplateVO);
			setEmployeeRemark(template, emailTemplateVO);
		}
	 }
	private void setPickupDropContent(StringBuilder template, EmailTemplateVO emailTemplateVO){
		setPrimaryDetails(template, emailTemplateVO);
		
		
		if(emailTemplateVO.getRequestType().equalsIgnoreCase(Constants.REQUEST_TYPE_SCHEDULE)){
			setTravelScheduleDate(template, emailTemplateVO);
		}else{
		setTravelDate(template, emailTemplateVO);
		}
		
		setLoginTime(template, emailTemplateVO);
		setLogoutTime(template, emailTemplateVO);
		
		if(emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_CANCELLED)){
			setCancelledRemark(template, emailTemplateVO);
			setCancelTravelSchedule(template, emailTemplateVO);
		}else{
			setOtherDetails(template, emailTemplateVO);
			setEmployeeRemark(template, emailTemplateVO);
		}
	}

	private void setPrimaryDetails(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		
		template.append("<table style=\"width:100%;\"><tr><td>");
		template.append("<table style=\"width:500; border:1px solid;\" cellpadding=\"5\" cellspacing=\"1\" >");
		template.append("<tr>");
		template.append("<td style=\"background-color: lightgrey;color: black;\">Request Details are as below:</td>");
		template.append("<td style=\"background-color: lightgrey;text-align: right;color: black;\">https://mfleet.marlabs.com</td>");
		template.append("</tr>");	

		template.append("<tr><td colspan=\"2\"><table style=\"width:100%; color:#085882 \"  cellpadding=\"5\" cellspacing=\"1\" >");
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Request Id</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getRequestHeaderId());
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Request Type</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getRequestType());
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Travel Type</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+emailTemplateVO.getRequestServiceType());
        template.append("</td>");
        template.append("</tr>");
        
	}
	
	private void setTravelDate(StringBuilder template, EmailTemplateVO emailTemplateVO) {

		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Travel Date</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ CabServiceUtil.formatDate(emailTemplateVO.getServiceDate()));
	    template.append("</td>");
	    template.append("</tr>");
	
	}
	
	private void setLoginTime(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Office Login Time</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+  emailTemplateVO.getEmployeeSelectedLogin());
	    template.append("</td>");
	    template.append("</tr>");
	    
	}
	
	private void setLogoutTime(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Office Logout Time</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+  emailTemplateVO.getEmployeeSelectedLogout());
	    template.append("</td>");
	    template.append("</tr>");
	    
	}
	
	private void setOtherDetails(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Reason</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getEmployeeSelectedReason());
	    template.append("</td>");
	    template.append("</tr>");
	 	
	    template.append("<tr>");
	 	template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Project</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getProjectName());
	    template.append("</td>");
	    template.append("</tr>");
	       
	 }
	
	private void setTravelScheduleDate(StringBuilder template, EmailTemplateVO emailTemplateVO){
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">From Date</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ CabServiceUtil.formatDate(emailTemplateVO.getFromDate()));
        template.append("</td>");
        template.append("</tr>");
		
        template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">To Date</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ CabServiceUtil.formatDate(emailTemplateVO.getToDate()));
        template.append("</td>");
        template.append("</tr>");
		
	}
	
	 private void setCancelTravelSchedule(StringBuilder template, EmailTemplateVO emailTemplateVO){
			
	    	Map<List<String>,List<Date>> requestCancelledDays =  new HashMap<>();
	    	List<String> serviceTypeList = new ArrayList<>();
	    	List<Date> serviceDateList = new ArrayList<>();
	    	
	    	template.append("<tr>");
	    	template.append("<th style=\"vertical-align: top; text-align: left;\">Cancelled Requests </th>");
			template.append("<td>");
			template.append("<table style=\"border-collapse: collapse; color:#085882; width:100%\"  cellpadding=\"5\" cellspacing=\"1\" >");
			template.append("<tr style=\"background-color: #f9f5f5;\">");
			template.append("<th style=\"padding: 5px; text-align: left;\">Trip Type</th>");
			template.append("<th style=\"padding: 5px; text-align: left;\">Trip Date</th>");
			template.append("</tr>");
			
			requestCancelledDays = emailTemplateVO.getCancelScheduleDays();
			
			requestCancelledDays.entrySet().forEach(serviceType->{
		    	 serviceTypeList.addAll(serviceType.getKey());
		    	 serviceDateList.addAll(serviceType.getValue());
			  });
			
			for(int i = 0; i<serviceTypeList.size(); i++) {
				
				template.append("<tr>");
				template.append("<td style=\"padding: 5px; text-align: left;\">"+serviceTypeList.get(i)+"</td>");
				template.append("<td style=\"padding: 5px; text-align: left;\">"+CabServiceUtil.formatDate(serviceDateList.get(i))+"</td>");
				template.append("</tr>");
			}
			template.append("</table>");
			template.append("</td>");
			template.append("</tr>");

	   }
	
    private void setEmployeeRemark(StringBuilder template, EmailTemplateVO emailTemplateVO){
			
	    template.append("<tr>");
	    template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Employee Remark</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">" + emailTemplateVO.getEmployeeRemark());
	    template.append("</td>");
	    template.append("</tr>");
	}
    
    private void setCancelledRemark(StringBuilder template, EmailTemplateVO emailTemplateVO){
		
	    template.append("<tr>");
	    template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Remark</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">" + emailTemplateVO.getEmployeeRemark()+"</td>");
	    template.append("</tr>");
	}
}
