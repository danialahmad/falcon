package com.twistlet.falcon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.falcon.controller.bean.Location;
import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.LocationService;
import com.twistlet.falcon.model.service.PatronService;

@Controller
public class ListLocationController {

	private final LocationService locationService;
	
	private final PatronService patronService;
	
	@Autowired
	public ListLocationController(LocationService locationService,
			PatronService patronService) {
		this.locationService = locationService;
		this.patronService = patronService;
	}


	@RequestMapping("/list-location-patron/{admin}/{date}")
	@ResponseBody
	public List<FalconLocation> listPatronsLocation(@PathVariable String admin, @PathVariable(value="date") String date){
		FalconUser falconUser = new FalconUser();
		List<FalconLocation> falconLocations = new ArrayList<>();
		falconUser.setUsername(admin);
		List<FalconPatron> patronsAdmin = patronService.listAllPatronsAdmin(falconUser);
		for(FalconPatron thisUser : patronsAdmin){
			Set<FalconLocation> locations  = thisUser.getFalconUserByAdmin().getFalconLocations();
			for(FalconLocation location : locations){
				falconLocations.add(location);
			}
		}
		return falconLocations;
	}
	
	
	@RequestMapping("/list-location/{admin}/{date}")
	@ResponseBody
	public List<FalconLocation> listLocation(@PathVariable String admin, @PathVariable(value="date") String date){
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		List<FalconLocation> locations = locationService.listAdminLocations(falconUser);
		return locations;
	}
	
	@RequestMapping("/list-location/{admin}/{date}/{startTime}/{endTime}")
	@ResponseBody
	public Set<FalconLocation> listAvailableLocation(@PathVariable String admin,
			@PathVariable(value="date") String date,
			@PathVariable("startTime") String start,
			@PathVariable("endTime") String end) {
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		Set<FalconLocation> locations = new HashSet<>();
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			locations = locationService.listAvailableLocations(falconUser, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return locations;
	}
	
	@RequestMapping("/list-location/{admin}/{date}/{startTime}/{endTime}/{appointmentId}")
	@ResponseBody
	public Set<FalconLocation> listAvailableLocationReschedule(@PathVariable String admin,
			@PathVariable(value="date") String date,
			@PathVariable("startTime") String start,
			@PathVariable("endTime") String end,
			@PathVariable("appointmentId") Integer appointmentId) {
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		Set<FalconLocation> locations = new HashSet<>();
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			locations = locationService.listAvailableLocations(falconUser, startDate, endDate, appointmentId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return locations;
	}
	
	@RequestMapping("/list-vanues/{admin}/{date}")
	@ResponseBody
	public List<Location> listAdminsLocations(@PathVariable("admin") String username, @RequestParam("term") String name){
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		FalconLocation location = new FalconLocation();
		location.setFalconUser(admin);
		location.setName(name);
		List<FalconLocation> locations = locationService.listAdminLocationLike(location);
		List<Location> availLocations = new ArrayList<>();
		Location loc = null;
		for(FalconLocation falconLocation : locations){
			loc = new Location();
			loc.setId(falconLocation.getId());
			loc.setName(falconLocation.getName());
			availLocations.add(loc);
		}
		return availLocations;
	}
	
}
