package com.rab3tech.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.service.CustomerLocationService;
import com.rab3tech.customer.dao.repository.CustomerLocationRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.dao.entity.Location;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.LocationVO;
import com.rab3tech.vo.LoginVO;

//why these two?
@Service
@Transactional
public class CustomerLocationServiceImpl implements CustomerLocationService{

	@Autowired
	private CustomerLocationRepository customerLocationRepository;
	@Autowired
	public LoginService  loginService;
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public Optional<LocationVO> findById(int lid){
		Optional<Location> optional=customerLocationRepository.findById(lid);
		if(optional.isPresent()){
			LocationVO locationVO=new LocationVO();
			BeanUtils.copyProperties(optional.get(), locationVO);
			locationVO.setName(optional.get().getLocation());
			return Optional.of(locationVO);//Creating optional of LocationVO type
		}else{
			return Optional.empty();
		}
	}
	
	
	@Override
	public String save(LocationVO locationVO){
		Location location=new Location();
		BeanUtils.copyProperties(locationVO, location);
		location.setLocation(locationVO.getName());
		location.setDoe(new Timestamp(new Date().getTime()));
		location.setDom(new Timestamp(new Date().getTime()));
		location.setLogin(null);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Login login = new Login();
		Optional optional = loginRepository.findById(userName);
		login = (Login) optional.get();
		location.setLogin(login);
//		login.setName(login.getName());
		System.out.println(login.getLoginid());
		System.out.println(login.getName());
		
		customerLocationRepository.save(location);		

		return "success";
	}
	
	@Override
	public List<LocationVO> findLocation(){
		List<LocationVO> locationVOs = new ArrayList<>();
		List<Location> location = customerLocationRepository.findAll();
		for(Location entity:location) {
			LocationVO locationVO= new LocationVO();
			BeanUtils.copyProperties(entity, locationVO);
			LoginVO loginVO=new LoginVO();
			BeanUtils.copyProperties(entity.getLogin(), loginVO);
			locationVO.setLogin(loginVO);
			locationVO.setName(entity.getLocation());
			
			locationVOs.add(locationVO);
		}
		return locationVOs;
	}
	
	@Override
	public void update(LocationVO locationVO) {
		Location location = customerLocationRepository.findById(locationVO.getId()).get();
		//New code
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Login login = new Login();
		Optional optional = loginRepository.findById(userName);
		login = (Login) optional.get();
		location.setLogin(login);
	
		location.setLcode(locationVO.getLcode());
		location.setLocation(location.getLocation());
		location.setLocation(locationVO.getName());
		

		customerLocationRepository.save(location);
	}


	@Override
	public void delete(int id) {
//		int locationId = Integer.parseInt(locationVO.getId());
	customerLocationRepository.deleteById(id);
		
	}
	
}
