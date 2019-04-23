package com.marlabs.cab.service.common.constant;

public final class Constants {
	
	private Constants(){}
	
	public static final String REQUEST_TYPE_SINGLE = "Single";
	public static final String REQUEST_TYPE_SCHEDULE = "Schedule";
	public static final String REQUEST_ACTION_SAVE = "Save";
	public static final String REQUEST_ACTION_SUBMIT = "Submit";
	
	public static final String REQUEST_STATUS_APPROVAL_PENDING = "Pending";
	public static final String REQUEST_STATUS_DRAFT = "Draft";
	public static final String REQUEST_STATUS_APPROVED = "Approved";
	public static final String REQUEST_STATUS_CANCELLED = "Cancelled";
	public static final String REQUEST_STATUS_REJECTED = "Rejected";
	public static final String REQUEST_APPROVAL_BY_ADMIN = "Admin";
	
	public static final String SINGLE_REQUEST_CANCELLED_SUCCESS = "Selected Single Request Cancelled successfully";
	public static final String SINGLE_REQUEST_CANCELLED_FAILED ="Failed to Cancel Selected Single Request. Please try again later. ";
	
	public static final String SCHEDULE_REQUEST_CANCELLED_SUCCESS="Selected Schedule Request Cancelled successfully";
	public static final String SCHEDULE_REQUEST_CANCELLED_FAILED ="Failed to Cancel Selected Schedule Request. Please try again later. ";
	
	public static final String EMAIL_STATUS_SUCCESS = "Success";
	public static final String EMAIL_STATUS_FAILED = "Failed";
	public static final String EMAIL_NEW_REQUEST = "mFleet - New Request";
	public static final String EMAIL_CANCEL_REQUEST = "mFleet - Request Cancelled";
	public static final String EMAIL_REQUEST_APPROVAL_BY_ADMIN = "mFleet - Admin Approval";
	public static final String EMAIL_REQUEST_APPROVAL_BY_MANAGER = "mFleet - Manager Approval";
	
	public static final String SERVICE_TYPE_DROP = "Drop";
	public static final String SERVICE_TYPE_PICKUP = "Pick-Up";
	public static final String SERVICE_TYPE_PICKUP_DROP = "Pick-Up & Drop";
	
	public static final String REQUEST_STATUS_SUCCESS = "Success";
	public static final String REQUEST_STATUS_FAILED = "Failed";
	public static final String REQUEST_STATUS_PARTIAL = "Partial Success";
	
	public static final String SERVER_ERROR = "Server Error";
	
	public static final String QUERY_PARAM_ALL = "All";
	
	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILED = "Failed";
	
	public static final String STATUS_TRUE = "true";
	public static final String STATUS_FALSE = "false";
	
	public static final String CAB_OWNER_STATUS = "true";
	public static final String CAB_OWNER_ATTACHMENTS = "NA";
	public static final String CAB_DETAIL_STATUS = "true";
	public static final String CAB_DETAIL_ATTACHMENTS = "NA";
	
	public static final String TRIP_STATUS_NEW = "New";
	public static final String TRIP_STATUS_COMPLETED = "Completed";
	public static final String TRIP_STATUS_NOT_COMPLETED = "Not Completed";
	
	public static final String USER_UNAUTHORIZED = "Unauthorized";
	
	public static final String ADMIN_EMAIL_ID = "girish.hiremath@marlabs.com";
	
}
