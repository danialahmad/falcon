package com.twistlet.falcon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminSendNotificationsController {

	@RequestMapping("/admin/send-notifications")
	public ModelAndView view() {
		return new ModelAndView("/admin/send-notifications");
	}
}
