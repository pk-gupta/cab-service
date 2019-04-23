/*package com.marlabs.cab.service.common.email;

import java.text.ParseException;

import com.marlabs.cab.service.common.constant.Constants;
import com.marlabs.cab.service.common.util.CabServiceUtil;

public class EmailTemplate {

	public static String employeeTemplate() {
		String employeeTemplate = "";
	
		return employeeTemplate;
	}

	public static String managerTemplate() {
		String managerTemplate = "";

		return managerTemplate;
	}

	public static String adminTemplate() {
		String adminTemplate = "";

		return adminTemplate;
	}
	
	public static String managerApprovalTemplate(EmailTemplateVO emailTemplateVO) throws ParseException {
		
		if(Constants.REQUEST_TYPE_SINGLE.equals(emailTemplateVO.getRequestType())){
		
		
		 if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
		        
			 String managerNewSingleRequestApprovalTemplate ="<html>"
						+"<head> </head>"
						+"<body>"
						+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
						+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>"  
						+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
						+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
						+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
						+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
						+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
						+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
	                  	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
						+"Thank You,"+"<br>"
						+"<b>"+"Cab Service Team."+"<b>"
			         +"</html>";
	        return managerNewSingleRequestApprovalTemplate;
	
	}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){

		String managerNewSingleRequestApprovalTemplate ="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
				+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
              	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	         +"</html>";
         return managerNewSingleRequestApprovalTemplate;
	}else{

		String managerNewSingleRequestApprovalTemplate ="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
				+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>" 
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
              	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	         +"</html>";
         return managerNewSingleRequestApprovalTemplate;


	}
		 
		}else{
			
			if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
		        
				 String managerNewSingleRequestApprovalTemplate ="<html>"
							+"<head> </head>"
							+"<body>"
							+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
							+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>"  
							+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
							+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
							+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
							+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
							+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
		                  	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
							+"Thank You,"+"<br>"
							+"<b>"+"Cab Service Team."+"<b>"
				         +"</html>";
		        return managerNewSingleRequestApprovalTemplate;
		
		}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){

			String managerNewSingleRequestApprovalTemplate ="<html>"
					+"<head> </head>"
					+"<body>"
					+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
					+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>"
					+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
					+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
					+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
					+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
					+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
					+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
					+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
	              	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
					+"Thank You,"+"<br>"
					+"<b>"+"Cab Service Team."+"<b>"
		         +"</html>";
	         return managerNewSingleRequestApprovalTemplate;
		}else{

			String managerNewSingleRequestApprovalTemplate ="<html>"
					+"<head> </head>"
					+"<body>"
					+"Hi "+ emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+","+"<br><br>"
					+" You have "+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+" following request of "+" <b>"+emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+":</b><br><br>" 
					+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
					+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
					+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
					+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
					+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
					+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
					+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
					+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
	              	+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
					+"Thank You,"+"<br>"
					+"<b>"+"Cab Service Team."+"<b>"
		         +"</html>";
	         return managerNewSingleRequestApprovalTemplate;


		}
				
	         }  
		        }
	
	
	

	
	public static String employeeApprovalTemplate(EmailTemplateVO emailTemplateVO) throws ParseException {
		
		
		if(Constants.REQUEST_TYPE_SINGLE.equals(emailTemplateVO.getRequestType())){
		
		     if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
		       
		    	   String managerNewSingleRequestApprovalTemplate ="<html>"
															+"<head> </head>"
															+"<body>"
															+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
															+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
															+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b> <br><br><br>"
															+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
															+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
															+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
															+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
															+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
															+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
															+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
															+"Thank You,"+"<br>"
															+"<b>"+"Cab Service Team."+"<b>"
													    +"</html>";
		return managerNewSingleRequestApprovalTemplate;
		
	}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
		
		
		String managerNewSingleRequestApprovalTemplate ="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
				+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
				+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>" +"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b> <br><br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
						+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
				+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
		    +"</html>";
return managerNewSingleRequestApprovalTemplate;
		
		
	}else{
		
		String managerNewSingleRequestApprovalTemplate ="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
				+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
				+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>" +"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b><br><br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
						+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
				+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
		    +"</html>";
return managerNewSingleRequestApprovalTemplate;
		
		
	}
		 
		
		}else{
		
		
		
			 if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
					String managerNewSingleRequestApprovalTemplate ="<html>"
																		+"<head> </head>"
																		+"<body>"
																		+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
																		+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
																		+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>"+"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b> <br><br><br>"
																		+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
																		+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
																		+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
																		+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
																		+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
                                                                        +"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
																		+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
																		+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
																		+"Thank You,"+"<br>"
																		+"<b>"+"Cab Service Team."+"<b>"
																    +"</html>";
					return managerNewSingleRequestApprovalTemplate;
				}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
					
					
					String managerNewSingleRequestApprovalTemplate ="<html>"
							+"<head> </head>"
							+"<body>"
							+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
							+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
							+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>" +"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b> <br><br><br>"
							+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
							+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
							+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
							+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
									+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
							+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
							+"Thank You,"+"<br>"
							+"<b>"+"Cab Service Team."+"<b>"
					    +"</html>";
			return managerNewSingleRequestApprovalTemplate;
					
					
				}else{
					
					String managerNewSingleRequestApprovalTemplate ="<html>"
							+"<head> </head>"
							+"<body>"
							+"Hi "+ emailTemplateVO.getEmployeeFirstName()+" "+emailTemplateVO.getEmployeeLastName()+","+"<br>"
							+"<p>"+"Your Following  <b>Request ID:</b>" +emailTemplateVO.getRequestId()
							+" has been <b>"+"<b style=color:red;>"+emailTemplateVO.getApprovalStatus()+"</b>" +"</b> by <b>"+emailTemplateVO.getManagerFirstName()+" "+emailTemplateVO.getManagerLastName()+":</b><br><br><br>"
							+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestId()+"<br>"
							+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
							+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
							+"<b>"+"From Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getFromDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"To Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(CabServiceUtil.parseDate(emailTemplateVO.getToDate(),"yyyy-MM-dd"))+"<br>"
							+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
							+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
							+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getReasonDescription()+"<br>"
							+"<b>"+"Manager Remark:"+"</b>&ensp;"+emailTemplateVO.getApprovalStatus()+"<br><br><br>"
							+"Thank You,"+"<br>"
							+"<b>"+"Cab Service Team."+"<b>"
					    +"</html>";
			return managerNewSingleRequestApprovalTemplate;
					
					
				}	
		
		
	}
		 
	
	   }

	
	public static String employeeNewSingleRequestTemplate(EmailTemplateVO emailTemplateVO) {
		     
		if(Constants.SERVICE_TYPE_DROP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
		String employeeNewSingleRequestTemplate = "<html>"
														+"<head> </head>"
														+"<body>"
														+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
														+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
														+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
														+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
														+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
														+"<b>"+"Travel Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
														+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
														+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br>" 
														+"Thank You,"+"<br>"
														+"<b>"+"Cab Service Team."+"<b>"
												   +"</html>";
		return employeeNewSingleRequestTemplate;
		}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
			
			String employeeNewSingleRequestTemplate = "<html>"
					+"<head> </head>"
					+"<body>"
					+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
					+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
					+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
					+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
					+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
					+"<b>"+"Travel Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
					+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
					+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br> "
					+"Thank You,"+"<br>"
					+"<b>"+"Cab Service Team."+"<b>"
			   +"</html>";
        return employeeNewSingleRequestTemplate;
						
		}else{
			
			String employeeNewSingleRequestTemplate = "<html>"
					+"<head> </head>"
					+"<body>"
					+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
					+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
					+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
					+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
					+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
					+"<b>"+"Travel Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
					+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
					+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
					+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br>"
					+"Thank You,"+"<br>"
					+"<b>"+"Cab Service Team."+"<b>"
			   +"</html>";
              return employeeNewSingleRequestTemplate;	
			
			
			
		}
	}

	
	
	
	
	public static String managerNewSingleRequestTemplate(EmailTemplateVO emailTemplateVO) {
		                if(Constants.SERVICE_TYPE_DROP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
		        
		String managerNewSingleRequestTemplate="<html>"
													+"<head> </head>"
													+"<body>"
													+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
													+"<p>"+"Please "+"<b style=color:red;>"+ "Approve/Reject "+"</b>"+ "cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
													+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
													+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
													+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
													+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
													+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
													+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
													+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
													+"Thank You,"+"<br>"
													+"<b>"+"Cab Service Team."+"<b>"
										        +"</html>";
		return managerNewSingleRequestTemplate;
	}else if(Constants.SERVICE_TYPE_PICKUP.equalsIgnoreCase(emailTemplateVO.getRequestServiceType())){
		
		String managerNewSingleRequestTemplate="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
				+"<p>"+"Please "+"<b style=color:red;>"+ "Approve/Reject "+"</b>"+ "cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
				+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	        +"</html>";
return managerNewSingleRequestTemplate;
		
	}else{
		
		String managerNewSingleRequestTemplate="<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
				+"<p>"+"Please "+"<b style=color:red;>"+ "Approve/Reject "+"</b>"+ "cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"Travel Date:"+"</b>&ensp;" +CabServiceUtil.formatDate(emailTemplateVO.getServiceDate())+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
				+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	        +"</html>";
return managerNewSingleRequestTemplate;
		
		
	}
		                }
	
	

	public static String employeeNewScheduleRequestTemplate( EmailTemplateVO emailTemplateVO) {
		
		  if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
		String employeeNewSingleRequestTemplate = "<html>"
														+"<head> </head>"
														+"<body>"
														+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
														+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
														+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
														+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
														+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
														+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
														+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
														+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
														+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br>"
														+"Thank You,"+"<br>"
														+"<b>"+"Cab Service Team."+"<b>"
											        +"</html>";
		return employeeNewSingleRequestTemplate;
		
		  }else if(Constants.SERVICE_TYPE_PICKUP.equals(emailTemplateVO.getRequestServiceType())){
			  
			  String employeeNewSingleRequestTemplate = "<html>"
						+"<head> </head>"
						+"<body>"
						+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
						+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
						+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
						+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
						+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
						+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
						+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
						+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
						+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br>"
						+"Thank You,"+"<br>"
						+"<b>"+"Cab Service Team."+"<b>"
			        +"</html>";
return employeeNewSingleRequestTemplate;
			  
		  }else{
			  
			  String employeeNewSingleRequestTemplate = "<html>"
						+"<head> </head>"
						+"<body>"
						+"Hi "+ emailTemplateVO.getEmployeeName()+","+"<br>"
						+"<p>"+"Your New Cab Service Request details are as below:"+"<br><br>"
						+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
						+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
						+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
						+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
						+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
						+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
						+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
						+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br><br>"
						+"Thank You,"+"<br>"
						+"<b>"+"Cab Service Team."+"<b>"
			        +"</html>";
return employeeNewSingleRequestTemplate;
			  
			  
		  }
	}
	
	


	public static String managerNewScheduleRequestTemplate( EmailTemplateVO emailTemplateVO) {
		 
	
    if(Constants.SERVICE_TYPE_DROP.equals(emailTemplateVO.getRequestServiceType())){
        
    	 String managerNewScheduleRequestTemplate = "<html>"
					+"<head> </head>"
					+"<body>"
					+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
					+"<p>"+"Please"+"<b style=color:red;>&ensp;"+"Approve/Reject "+"</b>"+ " new cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
					+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
					+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
					+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
					+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
					+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
					+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
					+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
					+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"
					+"Thank You,"+"<br>"
					+"<b>"+"Cab Service Team."+"<b>"
		         +"</html>";
return managerNewScheduleRequestTemplate;
}else if(Constants.SERVICE_TYPE_PICKUP.equals(emailTemplateVO.getRequestServiceType())){

	 String managerNewScheduleRequestTemplate = "<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
				+"<p>"+"Please"+"<b style=color:red;>&ensp;"+"Approve/Reject "+"</b>"+ " new cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
				+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"				
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
				+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"		
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	         +"</html>";
return managerNewScheduleRequestTemplate;
}else{

	 String managerNewScheduleRequestTemplate = "<html>"
				+"<head> </head>"
				+"<body>"
				+"Hi "+ emailTemplateVO.getManagerName()+","+"<br>"
				+"<p>"+"Please"+"<b style=color:red;>&ensp;"+"Approve/Reject "+"</b>"+ " new cab request from "+"<b>"+emailTemplateVO.getEmployeeName()+"</b>"+" as below:"+"<br><br>"
				+"<b>"+"Request No:"+"</b>&ensp;"+emailTemplateVO.getRequestHeaderId()+"<br>"
				+"<b>"+"Service Type:"+"</b>&ensp;" +emailTemplateVO.getRequestType()+"<br>"
				+"<b>"+"Travel Type:"+"</b>&ensp;" +emailTemplateVO.getRequestServiceType()+"<br>"
				+"<b>"+"From Date:"+"</b>&ensp;"+ CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedFromDate())+"<br>"
				+"<b>"+"To Date:"+"</b>&ensp;"+CabServiceUtil.formatDate(emailTemplateVO.getEmplopyeeSelectedToDate())+"<br>"
				+"<b>"+"Login Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogin()+"<br>"
				+"<b>"+"Logout Time:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedLogout()+"<br>"
				+"<b>"+"Reason:"+"</b>&ensp;" +emailTemplateVO.getEmployeeSelectedReason()+"<br>"
				+"<b>"+"Remark:"+"</b>&ensp;" +emailTemplateVO.getEmployeeRemark()+"<br><br>"	
				+"Thank You,"+"<br>"
				+"<b>"+"Cab Service Team."+"<b>"
	         +"</html>";
     return managerNewScheduleRequestTemplate;


}
	
	

}  }
*/