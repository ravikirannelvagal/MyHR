package com.MyHR.Service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MyHR.DAO.JobOfferDAO;
import com.MyHR.Exceptions.JobTitleAlreadyExistsException;
import com.MyHR.Exceptions.InvaildJobIdException;
import com.MyHR.Exceptions.NoJobOffersCreatedYet;
import com.MyHR.Model.JobOffer;
import com.MyHR.Utils.MyHRConstants;

@Transactional
@Service
public class JobOfferService {

	@Autowired
	private JobOfferDAO jobOfferDao;
	
	public synchronized long create(JobOffer jobOffer) throws JobTitleAlreadyExistsException{
		String newJobTitle = jobOffer.getJobTitle().trim();
		if(this.isTitleExists(newJobTitle)){
			throw new JobTitleAlreadyExistsException();
		}
		StringBuffer tempStartDate = new StringBuffer(jobOffer.getStartDateString());
		tempStartDate.append(MyHRConstants.TIMESTAMP_APPEND);
		Timestamp sqlStartTs = Timestamp.valueOf(tempStartDate.toString());
		
		jobOffer.setStartDate(sqlStartTs);
		return jobOfferDao.create(jobOffer);
	}
	
	public List<JobOffer> getAllJobOffers() throws NoJobOffersCreatedYet{
		List<JobOffer> allJobs = jobOfferDao.getAllJobOffers();
		if(allJobs.size()==0){
			throw new NoJobOffersCreatedYet();
		}
		
		return allJobs;
	}
	
	public JobOffer getJobOfferById(long jobId) throws InvaildJobIdException{
		JobOffer job = jobOfferDao.getJobOfferById(jobId);
		if(job == null){
			throw new InvaildJobIdException();
		}
		return job;
	}
	
	public synchronized void updateJobOffer(JobOffer jobOffer){
		jobOfferDao.update(jobOffer);
	}
	
	public void delete(long jobId) throws InvaildJobIdException{
		this.getJobOfferById(jobId);
		jobOfferDao.delete(jobId);
	}
	
	public void deleteAllOffers(){
		jobOfferDao.deleteAll();
	}
	
	public boolean isTitleExists(String jobTitle){
		boolean ret =jobOfferDao.getJobOfferByTitle(jobTitle); 
		return ret;
	}
}
