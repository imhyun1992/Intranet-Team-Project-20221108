package com.tjoeun.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.EmpVO;

@RestController
@RequestMapping("/manager")
public class rest {

	private static final Logger logger = LoggerFactory.getLogger(rest.class);

	@Autowired
	private SqlSession sqlSession;
	
	@ResponseBody
	@RequestMapping("/account_approvalOK")
	public void account_approvalOK(HttpServletRequest request, Model model, @RequestBody EmpVO vo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		mapper.account_approval(vo);
		
	}
	
	
}
