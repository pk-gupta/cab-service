package com.marlabs.cab.service.common.email.template.detail;

import com.marlabs.cab.service.common.email.EmailTemplateVO;

public interface EmailDetailContent {
	
	public void setEmailBody(StringBuilder template, EmailTemplateVO emailTemplateVO);
}
