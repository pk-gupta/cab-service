package com.marlabs.cab.service.common.email.template;

import com.marlabs.cab.service.common.email.EmailTemplateVO;


public abstract class AbstractEmailTemplate {
	
public StringBuilder createEmailTemplate(EmailTemplateVO emailTemplateVO){
	StringBuilder template = new StringBuilder();
	template.append("<html>");
	template.append("<br>");
	template.append("<body style=\"width: 600px;background-color: #ffffff; color:#085882;\">");
	
	createEmailHeader(template, emailTemplateVO);
	createEmailBody(template, emailTemplateVO);
	createEmailFooter(template, emailTemplateVO);
	
	template.append("</body>");
	template.append("<br>");
	template.append("</html>");
	
	return template;
}

abstract void createEmailHeader(StringBuilder template, EmailTemplateVO emailTemplateVO);

abstract void createEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO);

public void createEmailFooter(StringBuilder template, EmailTemplateVO emailTemplateVO){
	template.append("<div style='clear:both'></div>");
	template.append("Thank You,");
	template.append("<br>");
	template.append("mFleet Team");
	template.append("<br>");
	template.append("Email: helpdesk-admin@marlabs.com");
	template.append("<br>");
	template.append("Helpdesk-No: 9886375599");
	template.append("<br>");
	template.append("<br>");
	template.append("<b>NOTE: </b> This is an auto generated email. Please do not reply to this email.");
	
	}

}

