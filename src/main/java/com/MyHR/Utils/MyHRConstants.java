package com.MyHR.Utils;

public interface MyHRConstants {

	public static final String APPLIED="APPLIED";
	public static final String INVITED="INVITED";
	public static final String REJECTED="REJECTED";
	public static final String HIRED="HIRED";
	
	public static final String ERROR="Error";
	public static final String HOMEPAGE="Home";
	public static final String JOB_APPLN_CREATE="JobApplicationCreate";
	public static final String JOB_APPLN_CREATE_RES="JobApplicationCreateRes";
	public static final String JOB_APPLN_DETAILS="JobApplicationDetails";
	public static final String JOB_APPLNS_HOME="JobApplicationsHome";
	public static final String JOB_APPLNS_LIST="JobApplicationsList";
	public static final String JOB_OFFER_CREATE="JobOfferCreate";
	public static final String JOB_OFFER_CREATE_RES="JobOfferCreateRes";
	public static final String JOB_OFFER_DETAILS="JobOfferDetails";
	public static final String JOB_OFFERS_HOME="JobOffersHome";
	public static final String JOB_OFFERS_LIST="JobOffersList";
	
	public static final String UPD_STATUS_TO_INVITED="I";
	public static final String UPD_STATUS_TO_REJECTED="R";
	public static final String UPD_STATUS_TO_HIRED="H";
	
	public static final String ERROR_OBJECT="error";
	public static final String APPLN_OBJECT="appln";
	public static final String ALL_APPLNS_OBJECT="allapplns";
	public static final String APPLN_ID_OBJECT="applnId";
	public static final String ALL_JOBS_OBJECT="allJobs";
	public static final String JOB_OFFER_OBJECT="jobOffer";
	public static final String JOB_OFFER_ID_OBJECT="jobOfferId";
	
	public static final String JOB_MANAGER_MAPPING="/jobManager";
	public static final String NEW_JOB_MAPPING="/newJob";
	public static final String CREATE_JOB_MAPPING="/createJobOffer";
	public static final String ALL_JOBS_MAPPING="/jobs";
	public static final String JOB_BY_ID_MAPPING="/jobs/{id}";
	public static final String APP_MANAGER_MAPPING="/appManager";
	public static final String NEW_APPLN_MAPPING="/newAppln";
	public static final String APPLN_TO_JOB_MAPPING="/applyToJob/{jobId}";
	public static final String CREATE_APPLN_MAPPING="/createApplication";
	public static final String ALL_APPLNS_MAPPING="/applns";
	public static final String APPLN_BY_ID_MAPPING="/applns/{id}";
	public static final String APPLNS_BY_JOB_ID_MAPPING="/viewApplnsForJob/{jobOfferId}";
	public static final String UPD_APPLNS_MAPPING="/applns/{id}/{updCode}";
	public static final String APPLN_HOME_MAPPING="/applnHome";
	public static final String JOBS_HOME_MAPPING="/jobHome";
	public static final String[] HOME_PATH_MAPPING={"/","/home","/Home"};
	
	public static final String DAO_BASE_PKG="com.MyHR.DAO";
	public static final String TIMESTAMP_APPEND=" 00:00:00";
}
