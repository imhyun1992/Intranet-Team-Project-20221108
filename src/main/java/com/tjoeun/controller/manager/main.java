package com.tjoeun.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.EmpList;
import com.tjoeun.vo.EmpVO;

@Controller
@RequestMapping("/manager")
public class main {
	
	private static final Logger logger = LoggerFactory.getLogger(rest.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping("/Manager_Meun")
	public String Manager_Meun(HttpServletRequest request, Model model) {

		return "manager/Manager_Meun";
	}
	
	@RequestMapping("/account_approval")
	public String account_approval(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int currentPage = 1;
		int PageSize = 10;

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
		}
		
		int totalCount = mapper.countwaiting();
		
		EmpList waitingList = new EmpList(PageSize, totalCount, currentPage);
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", waitingList.getStartNo());
		hmap.put("endNo", waitingList.getEndNo());
		
		waitingList.setList((ArrayList<EmpVO>) mapper.selectwaiting(hmap));
		
		model.addAttribute("waitingList", waitingList);
		model.addAttribute("currentPage", currentPage);
		
		return "manager/account_approval";
	}
	
	
}
