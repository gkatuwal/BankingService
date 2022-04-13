package com.rab3tech.admin.ui.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rab3tech.admin.service.CustomerLocationService;
import com.rab3tech.vo.LocationVO;
import com.rab3tech.vo.LoginVO;



@Controller
@RequestMapping("/admin")
public class LocationController {
	 
	@Autowired
	private CustomerLocationService locationService;
	
	@GetMapping("/locations")
	public String showLocation(Model model) {
		List<LocationVO> locationVO = locationService.findLocation();
		model.addAttribute("locationVO", locationVO);
		return "admin/locations";		
	}
	
	@PostMapping("/locations")
	public String updateLocation (@ModelAttribute LocationVO locationVO, Model model)throws IOException{
		locationService.update(locationVO);
		return "redirect:/admin/locations";
		
	}
	
	@PostMapping("/location")
	public String saveLocation(@ModelAttribute LocationVO locationVO, Model model) throws IOException{
		
		locationService.save(locationVO);
		return "redirect:/admin/locations";
	}
	
	@PostMapping("/deletelocation")
	public String deleteLocation(@ModelAttribute LocationVO locationVO, Model model) throws IOException{
		int id = locationVO.getId();
		locationService.delete(id);
	//	System.out.println("these are ready to Delete" + locationVO);
		return "redirect:/admin/locations";
	}
}
