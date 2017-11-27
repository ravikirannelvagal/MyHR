package com.MyHR.Controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.MyHR.Utils.MyHRConstants;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages={MyHRConstants.DAO_BASE_PKG})
public class NavigationController {

	@RequestMapping(path={"/","/home","/Home"})
	public ModelAndView init(){
		ModelAndView m = new ModelAndView();
		m.setViewName(MyHRConstants.HOMEPAGE);
		return m;
	}
	
	@RequestMapping(path=MyHRConstants.APP_MANAGER_MAPPING)
	public ModelAndView initAppManager(){
		ModelAndView m = new ModelAndView();
		m.setViewName(MyHRConstants.JOB_APPLNS_HOME);
		return m;
	}
	
	@RequestMapping(path=MyHRConstants.APPLN_HOME_MAPPING)
	public ModelAndView applicationsHome(){
		ModelAndView m = new ModelAndView();
		m.setViewName(MyHRConstants.JOB_APPLNS_HOME);
		return m;
	}
	
	@RequestMapping(path=MyHRConstants.JOB_MANAGER_MAPPING)
	public ModelAndView initJobManager(){
		ModelAndView m = new ModelAndView();
		m.setViewName(MyHRConstants.JOB_OFFERS_HOME);
		return m;
	}
	
	@RequestMapping(path=MyHRConstants.JOBS_HOME_MAPPING)
	public ModelAndView jobsHome(){
		ModelAndView m = new ModelAndView();
		m.setViewName(MyHRConstants.JOB_OFFERS_HOME);
		return m;
	}
}
