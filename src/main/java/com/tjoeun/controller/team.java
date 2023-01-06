package com.tjoeun.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.tjoeun.vo.ReportList;
import com.tjoeun.vo.ReportVO;

@Controller
@RequestMapping("/team")
public class team {

	private static final Logger logger = LoggerFactory.getLogger(team.class);

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping("/address_book_list")
	public String address_book_list(HttpServletRequest request, Model model) {

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		HttpSession session = request.getSession();
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");

		int currentPage = 1;
		int PageSize = 10;

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
		}

		int totalCount = mapper.countByDept(empvo.getDeptno());

		EmpList addressBookList = new EmpList(PageSize, totalCount, currentPage);

		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", addressBookList.getStartNo());
		hmap.put("endNo", addressBookList.getEndNo());
		hmap.put("deptno", empvo.getDeptno());

		addressBookList.setList((ArrayList<EmpVO>) mapper.selectByDept(hmap));

		// 저장 후 표시할 페이지로 넘겨주기
		model.addAttribute("AddressBookList", addressBookList);
		model.addAttribute("currentPage", currentPage);

		return "team/address_book_view";
	}
	
	@RequestMapping("/report_list")
	public String report_list(HttpServletRequest request, Model model) {

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int currentPage = 1;
		
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {}
		
		int pageSize = 10;
		int totalCount = mapper.selectReportCount(mapper);
		
		ReportList reportList = new ReportList(pageSize, totalCount, currentPage);
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        hmap.put("startNo", reportList.getStartNo());
        hmap.put("endNo", reportList.getEndNo());
        
        reportList.setList(mapper.seletReportList(hmap));

		request.setAttribute("ReportList", reportList);
		request.setAttribute("currentPage", currentPage);
		return "team/report_view";
	}
	
	@RequestMapping("/report_content_list")
	public String report_content_list(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		ReportVO reportVO = mapper.selectReportByIdx(idx);
				
		model.addAttribute("ReportVO", reportVO);
		model.addAttribute("currentPage", currentPage);
		
		return "team/report_content_view";
	}
	
	@RequestMapping("/report_service")
	public String report_service(HttpServletRequest request, Model model, ReportVO vo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int mode = Integer.parseInt(request.getParameter("mode"));
		
		try {
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			model.addAttribute("currentPage", currentPage);
		} catch (Exception e) { }
		
		
		if (mode == 2) {
			
			int idx = Integer.parseInt(request.getParameter("idx"));
			vo = mapper.selectReportByIdx(idx);
			request.setAttribute("ReportVO", vo);
			return "team/report_update";
			
		} else if (mode == 3) {	
			
			mapper.deleteReport(vo);
			return "redirect:report_list";
			
		} else if (mode == 4) {
			
			mapper.updateReport(vo);
			int idx = Integer.parseInt(request.getParameter("idx"));
			
			model.addAttribute("idx", idx);
			return "redirect:report_content_list";
		} 
		
		return "./error";
	}
	
	@RequestMapping("/report_insert")
	public String report_insert(HttpServletRequest request, Model model) {
	
		return "team/report_insert";
	}
}
