package com.twistlet.falcon.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.falcon.controller.bean.Service;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconService;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.ServicesTypeService;

@Controller
public class ListAvailableServicesController {
	
	private final ServicesTypeService servicesTypeService;

	private final PatronService patronService;
	
	@Autowired
	public ListAvailableServicesController(
			ServicesTypeService servicesTypeService, PatronService patronService) {
		this.servicesTypeService = servicesTypeService;
		this.patronService = patronService;
	}

	
	@RequestMapping("/list-services-patron/{admin}/{date}")
	@ResponseBody
	public List<FalconService> listPatronServices(@PathVariable String admin, @PathVariable(value="date") String date){
		FalconUser falconUser = new FalconUser();
		List<FalconService> falconServices = new ArrayList<>();
		falconUser.setUsername(admin);
		List<FalconPatron> patronsAdmin = patronService.listAllPatronsAdmin(falconUser);
		for(FalconPatron thisUser : patronsAdmin){
			Set<FalconService> locations  = thisUser.getFalconUserByAdmin().getFalconServices();
			for(FalconService service : locations){
				falconServices.add(service);
			}
		}
		return falconServices;
	}
	
	
	@RequestMapping("/list-services/{admin}/{date}")
	@ResponseBody
	public List<FalconService> listServices(@PathVariable String admin, @PathVariable(value="date") String date){
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		List<FalconService> services = servicesTypeService.listAvailableServiceByAdmin(falconUser);
		return services;
	}
	
	@RequestMapping("/list-service/{admin}/{date}")
	@ResponseBody
	public List<Service> listService(@PathVariable String admin, @RequestParam("term") String name){
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		FalconService falconService = new FalconService();
		falconService.setFalconUser(falconUser);
		falconService.setName(name);
		List<FalconService> services = servicesTypeService.listAvailableServiceByAdminLike(falconService);
		List<Service> availableServices = new ArrayList<>();
		Service serv = null;
		for(FalconService theService : services){
			serv = new Service();
			serv.setId(theService.getId());
			serv.setName(theService.getName());
			availableServices.add(serv);
		}
		return availableServices;
	}
	

}
