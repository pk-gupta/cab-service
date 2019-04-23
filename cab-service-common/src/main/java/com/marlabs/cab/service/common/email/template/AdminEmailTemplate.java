package com.marlabs.cab.service.common.email.template;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.email.template.detail.EmailDetailContent;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;
import com.marlabs.cab.service.common.factory.EmailContentDetailFactory;

public class AdminEmailTemplate extends AbstractEmailTemplate {

@Override
public void createEmailHeader(StringBuilder template, EmailTemplateVO emailTemplateVO) {
	template.append("Hi Team,");
	template.append("<br>");
	template.append("<br>");
	template.append(getStatement(template,emailTemplateVO));
	template.append("<br>");
	template.append("<br>");
}

@Override
void createEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO) {
	
	EmailDetailContent emailBody = EmailContentDetailFactory.getEmailBody(emailTemplateVO.getEmailTemplateType());
	emailBody.setEmailBody(template, emailTemplateVO);
	
	template.append("</table></td></tr>");
    template.append("</table>");
    template.append("</td></tr></table>");
    template.append("<br>");
}


private String getStatement(StringBuilder template,EmailTemplateVO emailTemplateVO){
	String statement;
	switch(emailTemplateVO.getApprovalStatus()){
		case Constants.REQUEST_STATUS_CANCELLED:
			
			if(emailTemplateVO.getEmailTemplateType().equals(EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS)){
				statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_CANCELLED+"</b> by Employee:"+emailTemplateVO.getEmployeeFirstName()+"," ;
			}
			else{
			   statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_CANCELLED+",</b>";
			}
			break;
		case Constants.REQUEST_STATUS_APPROVED:
			statement = "You have <b>"+Constants.REQUEST_STATUS_APPROVED+"</b> following mFleet Request," ;
			
			break;
		case Constants.REQUEST_STATUS_REJECTED:
			statement = "You have <b>"+Constants.REQUEST_STATUS_REJECTED+"</b> following mFleet Request,";
				
			break;
				
			default:
				throw new IllegalArgumentException(emailTemplateVO.getApprovalStatus());
		}
		
		return statement;
	}
}
