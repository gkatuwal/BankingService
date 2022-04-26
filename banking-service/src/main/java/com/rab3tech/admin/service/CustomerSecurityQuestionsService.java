package com.rab3tech.admin.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.rab3tech.vo.SecurityQuestionsVO;

/**
 * 
 * @author javahunk
 *
 */
public interface CustomerSecurityQuestionsService {

	List<SecurityQuestionsVO> findSecurityQuestions();
	void updateStatus(String status, int qid);
	void addSecurityQuestion(String question, String loginid);
	void delete(int id);
	void updateSecurityQuestion(SecurityQuestionsVO securityQuestionsVO, String loginid);

}
