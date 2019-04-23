package com.marlabs.cab.service.common.email.template.detail;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.util.CabServiceUtil;

public class EmployeeCabDetailContent implements EmailDetailContent {

	@Override
	public void setEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		
		template.append("<table style=\"width:100%;\"><tr><td>");
		template.append("<table style=\"width:500; border:1px solid;\" cellpadding=\"5\" cellspacing=\"1\" >");
		template.append("<tr>");
		template.append("<td  style=\"background-color: lightgrey;color: black;\">Your mFleet Request details as below:</td>");
		template.append("<td  style=\"background-color: lightgrey;text-align: right;color: black;\">https://mfleet.marlabs.com</td>");
		template.append("</tr>");
		
		template.append("<tr><td colspan=\"2\"><table style=\"width:100%; color:#085882 \"  cellpadding=\"5\" cellspacing=\"1\" >");
		
		template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Trip Number</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+emailTemplateVO.getTripNumber());
        template.append("</td>");
        template.append("</tr>");
      
        template.append("<tr>");
		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Service Type</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+emailTemplateVO.getServiceType());
        template.append("</td>");
        template.append("</tr>");
		
		
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Trip Date</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ CabServiceUtil.formatDate(emailTemplateVO.getServiceDate()));
        template.append("</td>");
        template.append("</tr>");
        
		
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Landmark</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getEmployeeLandmark());
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Trip Time</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ getTripTime(emailTemplateVO));
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Expected Time</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getExpectedTime());
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Cab Number</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getVehicleNumber());
        template.append("</td>");
        template.append("</tr>");
        
        template.append("<tr>");
  		template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Driver Name</th>");
        template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getDriverFirstName() + " " + emailTemplateVO.getDriverLastName());
        template.append("</td>");
        template.append("</tr>");
        
	}
	
	private String getTripTime(EmailTemplateVO emailTemplateVO){
		if(emailTemplateVO.getServiceType().equalsIgnoreCase(Constants.SERVICE_TYPE_DROP)){
			return emailTemplateVO.getLogoutTime();
		}else{
		return emailTemplateVO.getLoginTime();
		}
	}
	
}
