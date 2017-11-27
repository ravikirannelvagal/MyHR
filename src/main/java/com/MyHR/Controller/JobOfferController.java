package com.MyHR.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.MyHR.Exceptions.InvaildJobIdException;
import com.MyHR.Exceptions.JobTitleAlreadyExistsException;
import com.MyHR.Exceptions.NoJobOffersCreatedYet;
import com.MyHR.Model.JobOffer;
import com.MyHR.Service.JobOfferService;
import com.MyHR.Utils.MyHRConstants;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages={MyHRConstants.DAO_BASE_PKG})
@RequestMapping(path=MyHRConstants.JOB_MANAGER_MAPPING)
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;
	
	@RequestMapping(path=MyHRConstants.NEW_JOB_MAPPING)
	public ModelAndView createAJob(){
		ModelAndView m = new ModelAndView();
		m.addObject(MyHRConstants.JOB_OFFER_OBJECT, new JobOffer());
		m.setViewName(MyHRConstants.JOB_OFFER_CREATE);
		return m;
	}
	 
	@PostMapping(path=MyHRConstants.CREATE_JOB_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ModelAndView createJobOffer(@Valid JobOffer jobOffer,BindingResult result,Model m){
		ModelAndView response = new ModelAndView();
		Long jobOfferId=0l;
		try {
			jobOfferId = jobOfferService.create(jobOffer);
		} catch (JobTitleAlreadyExistsException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT, e.getMessage());
			return response;
		}
		response.setViewName(MyHRConstants.JOB_OFFER_CREATE_RES);
		response.addObject(MyHRConstants.JOB_OFFER_ID_OBJECT, jobOfferId);
		response.setStatus(HttpStatus.CREATED);
		return response;
	}
	
	@GetMapping(path=MyHRConstants.ALL_JOBS_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView viewAllJobs(){
		ModelAndView response = new ModelAndView();
		List<JobOffer> allJobs;
		try {
			allJobs = jobOfferService.getAllJobOffers();
			response.addObject(MyHRConstants.ALL_JOBS_OBJECT, allJobs);
		} catch (NoJobOffersCreatedYet e) {
			response.addObject(MyHRConstants.ALL_JOBS_OBJECT, e.getMessage());
			response.setStatus(HttpStatus.NO_CONTENT);
		}
		response.setViewName(MyHRConstants.JOB_OFFERS_LIST);
		return response;
	}
	
	@GetMapping(path=MyHRConstants.JOB_BY_ID_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getJobOfferById(@PathVariable String id){
		ModelAndView response = new ModelAndView();
		long jobId= Long.parseLong(id);
		JobOffer job;
		try {
			job = jobOfferService.getJobOfferById(jobId);
		} catch (InvaildJobIdException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT, e.getMessage());
			return response;
		}
		response.setViewName(MyHRConstants.JOB_OFFER_DETAILS);
		response.addObject(MyHRConstants.JOB_OFFER_OBJECT, job);
		return response;
	}
}
