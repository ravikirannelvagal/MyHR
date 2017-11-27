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
import com.MyHR.Exceptions.InvalidApplicationIdException;
import com.MyHR.Exceptions.InvalidApplicationUpdateStatusException;
import com.MyHR.Exceptions.InvalidEmailIdPatternException;
import com.MyHR.Exceptions.JobAlreadyAppliedException;
import com.MyHR.Exceptions.NoApplicationsCreatedForThisJobYet;
import com.MyHR.Exceptions.NoApplicationsCreatedYet;
import com.MyHR.Model.JobApplication;
import com.MyHR.Service.JobApplicationService;
import com.MyHR.Utils.MyHRConstants;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages={MyHRConstants.DAO_BASE_PKG})
@RequestMapping(path=MyHRConstants.APP_MANAGER_MAPPING)
public class JobApplicationController {

	@Autowired
	private JobApplicationService applService;
	
	@RequestMapping(path=MyHRConstants.NEW_APPLN_MAPPING)
	public ModelAndView createAnApplication(){
		ModelAndView m = new ModelAndView();
		m.addObject(MyHRConstants.APPLN_OBJECT, new JobApplication());
		m.setViewName(MyHRConstants.JOB_APPLN_CREATE);
		return m;
	}
	
	@GetMapping(path=MyHRConstants.APPLN_TO_JOB_MAPPING)
	public ModelAndView applyToAJob(@PathVariable String jobId){
		long jobIdLong = Long.parseLong(jobId);
		ModelAndView m = new ModelAndView();
		JobApplication appln = new JobApplication();
		appln.setJobOfferId(jobIdLong);
		m.addObject(MyHRConstants.APPLN_OBJECT, appln);
		m.setViewName(MyHRConstants.JOB_APPLN_CREATE);
		return m;
	}
	
	@PostMapping(path=MyHRConstants.CREATE_APPLN_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ModelAndView createApplication(@Valid JobApplication application,BindingResult result,Model m){
		ModelAndView response = new ModelAndView();
		long applicationId;
		try {
			applicationId = applService.create(application);
		} catch (InvaildJobIdException | JobAlreadyAppliedException | InvalidEmailIdPatternException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT,e.getMessage());
			return response;
		}
		
		response.setViewName(MyHRConstants.JOB_APPLN_CREATE_RES);
		response.addObject(MyHRConstants.APPLN_ID_OBJECT, applicationId);
		response.setStatus(HttpStatus.CREATED);
		return response;
	}
	
	@GetMapping(path=MyHRConstants.ALL_APPLNS_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView viewAllApplications(){
		ModelAndView response = new ModelAndView();
		List<JobApplication> allApplications;
		try {
			allApplications = applService.getAllApplications();
			response.addObject(MyHRConstants.ALL_APPLNS_OBJECT, allApplications);
		} catch (NoApplicationsCreatedYet e) {
			response.addObject(MyHRConstants.ALL_APPLNS_OBJECT, e.getMessage());
			response.setStatus(HttpStatus.NO_CONTENT);
		}
		response.setViewName(MyHRConstants.JOB_APPLNS_LIST);
		
		return response;
	}
	
	@GetMapping(path=MyHRConstants.APPLN_BY_ID_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getApplicationById(@PathVariable String id){
		ModelAndView response = new ModelAndView();
		long applId= Long.parseLong(id);
		JobApplication application;
		try {
			application = applService.getApplicationById(applId);
		} catch (InvalidApplicationIdException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT, e.getMessage());
			return response;
		}
		response.setViewName(MyHRConstants.JOB_APPLN_DETAILS);
		response.addObject(MyHRConstants.APPLN_OBJECT, application);
		return response;
	}
	
	@GetMapping(path=MyHRConstants.APPLNS_BY_JOB_ID_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView viewApplicationsByJobId(@PathVariable String jobOfferId){
		ModelAndView response = new ModelAndView();
		long jobId = Long.parseLong(jobOfferId);
		List<JobApplication> allApplications;
		try {
			allApplications = applService.getAllApplicationsByJobId(jobId);
			response.addObject(MyHRConstants.ALL_APPLNS_OBJECT, allApplications);
		} catch (NoApplicationsCreatedForThisJobYet e) {
			response.addObject(MyHRConstants.ALL_APPLNS_OBJECT, e.getMessage());
			response.setStatus(HttpStatus.NO_CONTENT);
		}
		
		response.setViewName(MyHRConstants.JOB_APPLNS_LIST);
		return response;
	}
	
	@GetMapping(path=MyHRConstants.UPD_APPLNS_MAPPING,produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView updateApplication(@PathVariable String id, @PathVariable String updCode){
		ModelAndView response = new ModelAndView();
		
		long jobId= Long.parseLong(id);
		JobApplication application;
		try {
			application = applService.getApplicationById(jobId);
		} catch (InvalidApplicationIdException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT, e.getMessage());
			return response;
		}
		try {
			applService.update(application,updCode);
		} catch (InvalidApplicationUpdateStatusException e) {
			response.setViewName(MyHRConstants.ERROR);
			response.addObject(MyHRConstants.ERROR_OBJECT, e.getMessage());
			return response;
		}
		response.setViewName(MyHRConstants.JOB_APPLN_DETAILS);
		response.addObject(MyHRConstants.APPLN_OBJECT, application);
		
		return response;
	}
	 
}
