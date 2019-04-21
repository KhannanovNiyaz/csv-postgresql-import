package com.javasampleapproach.batchcsvpostgresql.controller;

import com.javasampleapproach.batchcsvpostgresql.dao.CustomerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@Autowired
	CustomerDao customerDao;

	@GetMapping("/")
	public String startPage(){
		return "start";
	}

	@GetMapping("/returnMenu")
	public String showMenu(){
		return "menu";
	}

	@RequestMapping(value = "menu", method = RequestMethod.GET)
	public String handle() throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return "menu";
	}

	@RequestMapping(value = "useformlasthours", method = RequestMethod.GET)
	public String loadFormUseLastHourse(Model model){
		model.addAttribute("useForms", customerDao.loadFormUseLastHourse());
		return "useformlasthours";
	}

	@RequestMapping(value = "userstatus", method = RequestMethod.GET)
	public String userStatus(Model model){
		model.addAttribute("userStatus", customerDao.userStatus());
		return "userstatus";
	}

	@RequestMapping(value = "topfiveuseforms", method = RequestMethod.GET)
	public String topFiveUseForm(Model model){
		model.addAttribute("topFiveUseForms", customerDao.topFiveUseForms());
		return "topfiveuseforms";
	}

}
