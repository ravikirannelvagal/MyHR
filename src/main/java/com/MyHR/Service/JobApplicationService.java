package com.MyHR.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MyHR.Exceptions.NoApplicationsCreatedForThisJobYet;
import com.MyHR.DAO.JobApplicationDAO;
import com.MyHR.Exceptions.InvaildJobIdException;
import com.MyHR.Exceptions.InvalidApplicationIdException;
import com.MyHR.Exceptions.InvalidApplicationUpdateStatusException;
import com.MyHR.Exceptions.InvalidEmailIdPatternException;
import com.MyHR.Exceptions.JobAlreadyAppliedException;
import com.MyHR.Exceptions.NoApplicationsCreatedYet;
import com.MyHR.Model.JobApplication;
import com.MyHR.Model.JobOffer;
import com.MyHR.Utils.MyHRConstants;

@Transactional
@Service
public class JobApplicationService {

	@Autowired
	private JobApplicationDAO applicationDao;
	@Autowired
	private JobOfferService jobOfferService;
	//@Autowired
	
	public synchronized long create(JobApplication application) throws InvaildJobIdException, JobAlreadyAppliedException, InvalidEmailIdPatternException{
		ValidateEmail emailValidator = new ValidateEmail();
		if(!emailValidator.validate(application.getCandidateEmail())){
			throw new InvalidEmailIdPatternException();
		}
		application.setApplnStatus(MyHRConstants.APPLIED);
		long jobOfferId = application.getJobOfferId();
		JobOffer jobOffer = jobOfferService.getJobOfferById(jobOfferId);
		if(jobOffer == null){
			throw new InvaildJobIdException();
		}
		application.setOffer(jobOffer);
		JobApplication alreadyApplied= this.isJobAlreadyApplied(application);
		if(alreadyApplied!=null){
			throw new JobAlreadyAppliedException();
		}
		long applicationId=applicationDao.create(application);
		jobOffer.setNoOfApplications(jobOffer.getNoOfApplications()+1);
		jobOfferService.updateJobOffer(jobOffer);
		return applicationId;
	}
	
	public List<JobApplication> getAllApplications() throws NoApplicationsCreatedYet{
		List<JobApplication> allApplications = applicationDao.getAllApplications();
		if(allApplications.size()==0){
			throw new NoApplicationsCreatedYet();
		}
		return allApplications;
	}
	
	public JobApplication getApplicationById(long jobId) throws InvalidApplicationIdException{
		JobApplication application = applicationDao.getApplicationById(jobId);
		if(application == null){
			throw new InvalidApplicationIdException();
		}
		return application;
	}
	
	public List<JobApplication> getAllApplicationsByJobId(long jobOfferId) throws NoApplicationsCreatedForThisJobYet{
		List<JobApplication> allApplications = applicationDao.getAllApplicationsByJobId(jobOfferId);
		if(allApplications.size()==0){
			throw new NoApplicationsCreatedForThisJobYet();
		}
		return allApplications;
	}
	
	
	
	public synchronized void update(JobApplication application,String updateCode) throws InvalidApplicationUpdateStatusException{
		String currStatus=application.getApplnStatus();
		
		if(MyHRConstants.APPLIED.equals(application.getApplnStatus())){
			if(MyHRConstants.UPD_STATUS_TO_INVITED.equalsIgnoreCase(updateCode)){
				application.setApplnStatus(MyHRConstants.INVITED);
			}else if(MyHRConstants.UPD_STATUS_TO_REJECTED.equalsIgnoreCase(updateCode)){
				application.setApplnStatus(MyHRConstants.REJECTED);
			}else{
				throw new InvalidApplicationUpdateStatusException();
			}
		}else if(MyHRConstants.INVITED.equals(application.getApplnStatus())){
			if(MyHRConstants.UPD_STATUS_TO_REJECTED.equalsIgnoreCase(updateCode)){
				application.setApplnStatus(MyHRConstants.REJECTED);
			}else if(MyHRConstants.UPD_STATUS_TO_HIRED.equalsIgnoreCase(updateCode)){
				application.setApplnStatus(MyHRConstants.HIRED);
			}else{
				throw new InvalidApplicationUpdateStatusException();
			}
		}
		else{
			throw new InvalidApplicationUpdateStatusException();
		}
		
		applicationDao.update(application);
		triggerNotification(currStatus,application);
	}
	
	public void delete(long jobApplicationId) throws InvalidApplicationIdException{
		this.getApplicationById(jobApplicationId);
		applicationDao.delete(jobApplicationId);
	}
	
	public void deleteAllApplications(){
		applicationDao.deleteAll();
	}
	
	public JobApplication isJobAlreadyApplied(JobApplication application){
		return applicationDao.isJobAlreadyApplied(application);
	}
	
	private void triggerNotification(String previousStatus, JobApplication application){
		StringBuffer notificationText =new StringBuffer();
		notificationText.append("Dear Applicant,\n");
		notificationText.append("With reference to your job application, with ID: ");
		notificationText.append(application.getApplicationId());
		notificationText.append(", for the job of ");
		notificationText.append(application.getOffer().getJobTitle());
		notificationText.append(", with ID: ");
		notificationText.append(application.getOffer().getJobOfferId());
		notificationText.append(" has been processed.\n");
		notificationText.append("Updated Status: "+application.getApplnStatus());
		notificationText.append("\n");
		notificationText.append("Previous Status: "+previousStatus);
		notificationText.append("\n");
		notificationText.append("This mail is intended to be delivered to "+application.getCandidateEmail());
		notificationText.append("\n");
		notificationText.append("With Best Regards,\n");
		notificationText.append("Team MyHr");
		System.out.println(notificationText.toString());
		MailNotificationService mns = new MailNotificationService();
		try {
			mns.sendEmail(notificationText.toString(),application.getCandidateEmail());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ValidateEmail{
		private Pattern pattern;
		private Matcher matcher;
		
		private static final String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		public ValidateEmail(){
			pattern = Pattern.compile(EMAIL_PATTERN);
		}
		
		public boolean validate(final String hex) {
			matcher = pattern.matcher(hex);
			return matcher.matches();

		}
		
	}
	
	private class MailNotificationService {

		public void sendEmail(String msg,String recipientEMail) throws EmailException{
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("userName", "pwd"));
			email.setSSLOnConnect(true);
			email.setFrom("Admin@myhr.com");
			email.setSubject("Notification: Application Status Updated");
			email.setMsg(msg);
			email.addTo(recipientEMail);
			//email.send();
		}
	}
}
