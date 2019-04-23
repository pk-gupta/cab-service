package com.marlabs.cab.service.common.email.template;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.email.EmailTemplateVO;
import com.marlabs.cab.service.common.email.template.detail.EmailDetailContent;
import com.marlabs.cab.service.common.factory.EmailContentDetailFactory;
import com.marlabs.cab.service.common.util.CabServiceUtil;

public class ManagerEmailTemplate extends AbstractEmailTemplate {

	@Override
	void createEmailHeader(StringBuilder template, EmailTemplateVO emailTemplateVO) {
		template.append("Hi " + "<b>"+emailTemplateVO.getManagerName()+",</b>");
		template.append("<br>");
		template.append("<br>");
		template.append(getStatement(emailTemplateVO));
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

	private String getStatement(EmailTemplateVO emailTemplateVO){
		String statement;
		switch(emailTemplateVO.getApprovalStatus()){
			case Constants.REQUEST_STATUS_APPROVAL_PENDING:
				
				statement = "Please <b>Approve/Reject</b> following mFleet Request from " + emailTemplateVO.getEmployeeName();
				break;
				
			case Constants.REQUEST_STATUS_CANCELLED:
				
				if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
				statement = "Following mFleet Request of " + emailTemplateVO.getEmployeeName()+" has been <b>"+Constants.REQUEST_STATUS_CANCELLED+"</b> by Admin Team, ";
				}else{
				statement = "Following mFleet Request has been <b>"+Constants.REQUEST_STATUS_CANCELLED+"</b> By "+emailTemplateVO.getEmployeeName()+",";
				}
				
				break;
			case Constants.REQUEST_STATUS_APPROVED:
				if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
					statement = "Following mFleet Request of " + emailTemplateVO.getEmployeeName()+" has been <b>"+Constants.REQUEST_STATUS_APPROVED+"</b> by Admin Team,";
				}
				else {
				statement = "You have <b>"+Constants.REQUEST_STATUS_APPROVED+"</b> following mFleet Request of " + emailTemplateVO.getEmployeeName()+",";
				}
				
				break;
			case Constants.REQUEST_STATUS_REJECTED:
				if(!CabServiceUtil.isNULL(emailTemplateVO.getApprovalBy())){
					statement = "Following mFleet Request of " + emailTemplateVO.getEmployeeName()+" has been <b>"+Constants.REQUEST_STATUS_REJECTED+"</b> by Admin Team,";
				}
				else{
				statement = "You have <b>"+Constants.REQUEST_STATUS_REJECTED+"</b> following mFleet Request of " + emailTemplateVO.getEmployeeName()+",";
				}
				break;
			default:
				throw new IllegalArgumentException(emailTemplateVO.getApprovalStatus());
		}
		
		return statement;
	}

}
