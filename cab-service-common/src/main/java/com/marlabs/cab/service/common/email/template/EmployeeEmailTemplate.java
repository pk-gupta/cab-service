package com.marlabs.cab.service.common.email.template;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.email.template.detail.EmailDetailContent;
import com.marlabs.cab.service.common.enums.EmailTemplateTypeEnum;
import com.marlabs.cab.service.common.factory.EmailContentDetailFactory;
import com.marlabs.cab.service.common.util.CabServiceUtil;

public class EmployeeEmailTemplate extends AbstractEmailTemplate {

	@Override
	void createEmailHeader(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		template.append("Hi " + "<b>"+emailTemplateVO.getEmployeeName()+",</b>");
		template.append("<br>");
		template.append("<br>");
		template.append(getStatement(emailTemplateVO.getEmailTemplateType(), emailTemplateVO));
		template.append("<br>");
		template.append("<br>");
}

@Override
void createEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO) {
	
	EmailDetailContent emailBody = EmailContentDetailFactory.getEmailBody(emailTemplateVO.getEmailTemplateType());
	emailBody.setEmailBody(template, emailTemplateVO);
	
	if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalStatus())){
		setManagerRemark(template, emailTemplateVO);
	}
	template.append("</table></td></tr>");
	template.append("</table>");
	template.append("</td></tr></table>");
	if(emailTemplateVO.getEmailTemplateType() == EmailTemplateTypeEnum.EMPLOYEE_REQUEST_DETAILS){
		
		template.append("<br>");
		template.append("<table><tr><td>");
		template.append("<b>NOTE: </b> Admin can also Approve your request on behalf of your Approval Manager.");
		template.append("</td></tr></table>");
		template.append("<br>");
		template.append("<br>");
	}
	if(emailTemplateVO.getEmailTemplateType() == EmailTemplateTypeEnum.EMPLOYEE_CAB_DETAILS){
		template.append("<br>");
		template.append("<br>");
	}
	
}

private void setManagerRemark(StringBuilder template, EmailTemplateVO emailTemplateVO) {
	if(emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_APPROVED) || emailTemplateVO.getApprovalStatus().equalsIgnoreCase(Constants.REQUEST_STATUS_REJECTED) ){
		
		template.append("<tr>");
	  	template.append("<th width=\"40%\" style=\"padding: 5px 5px 5px 5px; text-align: left;\">Remark</th>");
	    template.append("<td style=\"padding: 5px 5px 5px 5px;\">"+ emailTemplateVO.getManagerRemark());
	    template.append("</td>");
	    template.append("</tr>");
		
	}
}

private String getStatement(EmailTemplateTypeEnum emailType, EmailTemplateVO emailTemplateVO){
	String statement = "";
	switch(emailType){
		case EMPLOYEE_REQUEST_DETAILS:
			statement = getRequestDetailHeader(emailTemplateVO);
			break;
		case EMPLOYEE_CAB_DETAILS:
			statement = getCabDetailHeader(emailTemplateVO);
			break;
		default:
			throw new IllegalArgumentException();
	}
	
	return statement;
}

private String getRequestDetailHeader(EmailTemplateVO emailTemplateVO){
	String statement = "";
	switch(emailTemplateVO.getApprovalStatus()){
		case Constants.REQUEST_STATUS_APPROVAL_PENDING:
			statement=" Your New mFleet Request details are as below:";
			break;
		case Constants.REQUEST_STATUS_CANCELLED:
			
			if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
			  statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_CANCELLED+"</b> by Admin Team,";
			}else{
			  statement = "You have <b>"+Constants.REQUEST_STATUS_CANCELLED+"</b> following mFleet Request,";
			}
			break;
		case Constants.REQUEST_STATUS_APPROVED:
			
			if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
				statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_APPROVED+"</b> " + "by Admin Team," ;
			}
			else{
			    statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_APPROVED+"</b> by " +emailTemplateVO.getManagerName()+",";
			}
			break;
		case Constants.REQUEST_STATUS_REJECTED:
			
			if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
				statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_REJECTED+"</b> "+ "by Admin Team," ;
			}
			else{
			statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_REJECTED+"</b> by " +emailTemplateVO.getManagerName()+",";
			}
			break;
		default:
			throw new IllegalArgumentException(emailTemplateVO.getApprovalStatus());
	}
	
	return statement;
}

private String getCabDetailHeader(EmailTemplateVO emailTemplateVO){
	String statement = "Your mFleet Trip Details are as below:";
		
		return statement;
	}

}
