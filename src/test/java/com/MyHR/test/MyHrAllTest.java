package com.MyHR.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.MyHR.Exceptions.InvaildJobIdException;
import com.MyHR.Exceptions.InvalidApplicationIdException;
import com.MyHR.Exceptions.InvalidApplicationUpdateStatusException;
import com.MyHR.Exceptions.InvalidEmailIdPatternException;
import com.MyHR.Exceptions.JobAlreadyAppliedException;
import com.MyHR.Exceptions.JobTitleAlreadyExistsException;
import com.MyHR.Exceptions.NoApplicationsCreatedForThisJobYet;
import com.MyHR.Exceptions.NoApplicationsCreatedYet;
import com.MyHR.Exceptions.NoJobOffersCreatedYet;
import com.MyHR.Model.JobApplication;
import com.MyHR.Model.JobOffer;
import com.MyHR.Service.JobApplicationService;
import com.MyHR.Service.JobOfferService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyHrAllTest {

	@Autowired
	private JobOfferService jobOfferService;
	@Autowired
	private JobApplicationService jobApplicationService;
	//public List<Long> jobOffersAdded = new ArrayList<Long>();
	public List<Long> jobApplicationsAdded = new ArrayList<>();
	public static long jobOfferId;
	public static long jobOfferId1;
	public static long jobOfferId2;
	public static long jobOfferId3;
	public static long jobOfferId4;
	public static long jobApplicationId;
	
	@Test
	public void a_addAJobOffer(){
		JobOffer offer = new JobOffer();
		offer.setJobTitle("Systems Engineerr");
		offer.setStartDateString("2018-04-28");
		//long jobOfferId=0l;
		try {
			jobOfferId = jobOfferService.create(offer);
		} catch (JobTitleAlreadyExistsException e) {
			e.getMessage();
		}
		//jobOffersAdded.add(jobOfferId);
		assertNotEquals("Job offer added!",jobOfferId,0l);
		
	}
	
	@Test
	public void b_fetchJobOfferById(){
		JobOffer fetch=null;
		try {
			fetch = jobOfferService.getJobOfferById(jobOfferId);
		} catch (InvaildJobIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull("Read job offer by ID success!",fetch);
		
	}
	
	@Test(expected=InvaildJobIdException.class)
	public void c_fetchInvalidJobOfferById() throws InvaildJobIdException{
		long getJobOfferById=-5l;
		JobOffer fetch=null;
		fetch = jobOfferService.getJobOfferById(getJobOfferById);
	}
	
	@Test
	public void d_addFewMoreJobOffers(){
		JobOffer offer = new JobOffer();
		offer.setJobTitle("Senior Systems Engineer");
		offer.setStartDateString("2018-05-28");
		JobOffer offer1 = new JobOffer();
		offer1.setJobTitle("Technical Analyst");
		offer1.setStartDateString("2018-06-28");
		JobOffer offer2 = new JobOffer();
		offer2.setJobTitle("Team Leader");
		offer2.setStartDateString("2018-07-28");
		JobOffer offer3 = new JobOffer();
		offer3.setJobTitle("Project Manager");
		offer3.setStartDateString("2018-08-28");
		try {
			jobOfferId1=jobOfferService.create(offer);
			jobOfferId2=jobOfferService.create(offer1);
			jobOfferId3=jobOfferService.create(offer2);
			jobOfferId4=jobOfferService.create(offer3);
		} catch (JobTitleAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		/*List<JobOffer> allJobOffers=null;
		try {
			allJobOffers = jobOfferService.getAllJobOffers();
		} catch (NoJobOffersCreatedYet e) {
			e.printStackTrace();
		}
		assertEquals("All jobs fetched!", allJobOffers.size(),5);*/
	}
	
	@Test
	public void e_fetchAllJobOffers(){
		List<JobOffer> allJobOffers=null;
		try {
			allJobOffers = jobOfferService.getAllJobOffers();
		} catch (NoJobOffersCreatedYet e) {
			e.printStackTrace();
		}
		assertEquals("All jobs fetched!", allJobOffers.size(),5);
	}
	
	@Test(expected=JobTitleAlreadyExistsException.class)
	public void f_addExistingJobTitle() throws JobTitleAlreadyExistsException{
		JobOffer offer = new JobOffer();
		offer.setJobTitle("Senior Systems Engineer");
		offer.setStartDateString("2018-05-28");
		jobOfferService.create(offer);
		
	}
	
	@Test
	public void g_applyToAJobOffer(){
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJobOfferId(jobOfferId);
		jobApplication.setCandidateEmail("abcd@123.com");
		jobApplication.setResumeText("xyz zyx yxz");
		try {
			jobApplicationId = jobApplicationService.create(jobApplication);
		} catch (InvaildJobIdException | JobAlreadyAppliedException | InvalidEmailIdPatternException e) {
			e.printStackTrace();
		}
		assertNotEquals("Job application added!", jobApplicationId,0l);
		
	}
	
	@Test
	public void h_fetchApplicationById(){
		JobApplication fetch=null;
		try {
			fetch = jobApplicationService.getApplicationById(jobApplicationId);
		} catch (InvalidApplicationIdException e) {
			e.printStackTrace();
		}
		assertNotNull("Read job application by ID success!",fetch);
	}
	
	@Test(expected=InvalidApplicationIdException.class)
	public void i_fetchInvalidApplicationById() throws InvalidApplicationIdException{
		long fetchByJobApplicationid=-5l;
		JobApplication fetch=null;
		fetch = jobApplicationService.getApplicationById(fetchByJobApplicationid);
	}
	
	@Test
	public void j_applyToManyJobs(){
			
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJobOfferId(jobOfferId);
		jobApplication.setCandidateEmail("abc@123.com");
		jobApplication.setResumeText("xyz zyx yxz");
			
		JobApplication jobApplication1 = new JobApplication();
		jobApplication1.setJobOfferId(jobOfferId1);
		jobApplication1.setCandidateEmail("abc@123.com");
		jobApplication1.setResumeText("xyz zyx yxz");
		
		JobApplication jobApplication2 = new JobApplication();
		jobApplication2.setJobOfferId(jobOfferId);
		jobApplication2.setCandidateEmail("def@456.com");
		jobApplication2.setResumeText("xyz zyx yxz");
		
		JobApplication jobApplication3 = new JobApplication();
		jobApplication3.setJobOfferId(jobOfferId2);
		jobApplication3.setCandidateEmail("efg@789.com");
		jobApplication3.setResumeText("xyz zyx yxz");
		
		JobApplication jobApplication4 = new JobApplication();
		jobApplication4.setJobOfferId(jobOfferId2);
		jobApplication4.setCandidateEmail("efg@789.com");
		jobApplication4.setResumeText("xyz zyx yxz");
		try {
			jobApplicationService.create(jobApplication);
			jobApplicationService.create(jobApplication1);
			jobApplicationService.create(jobApplication2);
			jobApplicationService.create(jobApplication3);
			jobApplicationService.create(jobApplication4);
		} catch (InvaildJobIdException | JobAlreadyAppliedException | InvalidEmailIdPatternException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void k_fetchAllJobApplications(){
		List<JobApplication> allJobApplications=null;
		try {
			allJobApplications = jobApplicationService.getAllApplications();
		} catch (NoApplicationsCreatedYet e) {
			e.printStackTrace();
		}
		assertEquals("All applications fetched!", allJobApplications.size(),2);
	}
	
	@Test(expected=JobAlreadyAppliedException.class)
	public void l_applyToAlreadyAppliedJob() throws InvaildJobIdException, JobAlreadyAppliedException, InvalidEmailIdPatternException{
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJobOfferId(jobOfferId);
		jobApplication.setCandidateEmail("abc@123.com");
		jobApplication.setResumeText("xyz zyx yxz");
		jobApplicationService.create(jobApplication);
	}
	
	@Test
	public void m_updateApplicationStatus(){
		JobApplication fetch=null;
		try {
			fetch = jobApplicationService.getApplicationById(jobApplicationId);
			jobApplicationService.update(fetch, "I");
		} catch (InvalidApplicationIdException | InvalidApplicationUpdateStatusException e) {
			e.printStackTrace();
		}
		assertNotNull("Update application status success!",fetch);
	}
	
	@Test(expected=InvalidApplicationUpdateStatusException.class)
	public void n_updateApplicationStatusInvalid() throws InvalidApplicationUpdateStatusException, InvalidApplicationIdException{
		JobApplication fetch=null;
		fetch = jobApplicationService.getApplicationById(jobApplicationId);
		jobApplicationService.update(fetch, "M");
	}
	
	@Test
	public void o_getNoOfApplicationsForJob() throws NoApplicationsCreatedForThisJobYet{
		List<JobApplication> jobApplsList = new ArrayList<>();
		jobApplsList = jobApplicationService.getAllApplicationsByJobId(jobOfferId);
		assertEquals("Applications per job fetch successful", jobApplsList.size(),2);
	}
	
	@Test
	public void p_deleteAllJobApplications(){
		jobApplicationService.deleteAllApplications();
	}
	
	@Test
	public void q_deleteAllJobOffers(){
		jobOfferService.deleteAllOffers();
	}
}
