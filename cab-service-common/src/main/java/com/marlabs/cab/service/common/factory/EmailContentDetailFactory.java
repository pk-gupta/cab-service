package com.marlabs.cab.service.common.factory;

import com.marlabs.cab.service.common.email.template.detail.AdminRequestDetailsContent;
import com.marlabs.cab.service.common.email.template.detail.EmailDetailContent;
import com.marlabs.cab.service.common.email.template.detail.EmployeeCabDetailContent;
import com.marlabs.cab.service.common.email.template.detail.EmployeeRequestDetailContent;
import com.marlabs.cab.service.common.email.template.detail.ManagerRequestDetailsContent;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;

public class EmailContentDetailFactory {
	
	public static EmailDetailContent getEmailBody(EmailTemplateTypeEnum contentType){
		EmailDetailContent emailBodyContent;
		
		switch(contentType){
			case EMPLOYEE_REQUEST_DETAILS:
				emailBodyContent = new EmployeeRequestDetailContent();
				break;
			case MANAGER_REQUEST_DETAILS:
				emailBodyContent = new ManagerRequestDetailsContent();
				break;
			case ADMIN_REQUEST_DETAILS:
				emailBodyContent = new AdminRequestDetailsContent();
				break;
				
			case EMPLOYEE_CAB_DETAILS:
				emailBodyContent = new EmployeeCabDetailContent();
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		return emailBodyContent;
	}

}
