package com.tjoeun.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.EmpVO;
import com.tjoeun.vo.MailBoxesList;
import com.tjoeun.vo.MailBoxesVO;
import com.tjoeun.vo.Param;

@Controller
public class mypage {

	private static final Logger logger = LoggerFactory.getLogger(mypage.class);
	
	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	Param param = CTX.getBean("param", Param.class);
	
	private int currentPage = 1;

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping("/myinfo_view")
	public String myinfo_view(HttpServletRequest request, Model model) {

		return "myinfo_view";
	}

	@RequestMapping("/mywrite_view")
	public String mywrite_view(HttpServletRequest request, Model model) {

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		HttpSession session = request.getSession();
		EmpVO vo = (EmpVO) session.getAttribute("EmpVO");

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {

		}

		int PageSize = 10;

		int totalCount = mapper.mywrite_selectCount(vo.getEmpno());

		BoardList boardlist = new BoardList(PageSize, totalCount, currentPage);

		HashMap<String, Integer> myhmap = new HashMap<String, Integer>();
		myhmap.put("startNo", boardlist.getStartNo());
		myhmap.put("endNo", boardlist.getEndNo());
		myhmap.put("empno", vo.getEmpno());

		boardlist.setList(mapper.mywrite_selectList(myhmap));

		model.addAttribute("BoardList", boardlist);

		return "mywrite_view";
	}
	
	// 내가 쓴 글 보기
	@RequestMapping("/mywrite_content_view")
	public String mywrite_content_view(HttpServletRequest request, Model model) {
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardVO boardvo = mapper.selectContentByIdx(idx);
		
		model.addAttribute("BoardVO", boardvo);
		model.addAttribute("enter", "\r\n");
		
		return "board/mywrite_content_view";
	}

	// 받은 메일
	@RequestMapping("/mailboxes_receive_view")
	public String mailboxes_receive_view(HttpServletRequest request, Model model) {

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		HttpSession session = request.getSession();
		EmpVO vo = (EmpVO) session.getAttribute("EmpVO");

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {

		}

		int pageSize = 8;
		int totalCount = mapper.receiveMailSelectCount(vo.getEmail());	

		MailBoxesList mailBoxesList = new MailBoxesList(pageSize, totalCount, currentPage);

		param.setStartNo(mailBoxesList.getStartNo());
		param.setEndNo(mailBoxesList.getEndNo());
		param.setEmail(vo.getEmail());

		mailBoxesList.setList(mapper.receiveMailSelectList(param));

		model.addAttribute("MailBoxesList", mailBoxesList);
		return "mailboxes_receive_view";
	}
	
	// 보낸 메일
	@RequestMapping("/mailboxes_send_view")
	public String mailboxes_send_view(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		HttpSession session = request.getSession();
		EmpVO vo = (EmpVO) session.getAttribute("EmpVO");
		
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
			
		}
		
		int pageSize = 8;
		int totalCount = mapper.sendMailSelectCount(vo.getEmail());	
		
		MailBoxesList mailBoxesList = new MailBoxesList(pageSize, totalCount, currentPage);
		
		param.setStartNo(mailBoxesList.getStartNo());
		param.setEndNo(mailBoxesList.getEndNo());
		param.setEmail(vo.getEmail());
		
		mailBoxesList.setList(mapper.sendMailSelectList(param));
		
		model.addAttribute("MailBoxesList", mailBoxesList);
		return "mailboxes_send_view";
	}
	
	// 휴지통
	@RequestMapping("/mailboxes_trash_view")
	public String mailboxes_trash_view(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		HttpSession session = request.getSession();
		EmpVO vo = (EmpVO) session.getAttribute("EmpVO");
		
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
			
		}
		
		int pageSize = 8;
		int totalCount = mapper.trashMailSelectCount(vo.getEmail());	
		
		MailBoxesList mailBoxesList = new MailBoxesList(pageSize, totalCount, currentPage);
		
		param.setStartNo(mailBoxesList.getStartNo());
		param.setEndNo(mailBoxesList.getEndNo());
		param.setEmail(vo.getEmail());
		
		mailBoxesList.setList(mapper.trashMailSelectList(param));
		
		model.addAttribute("MailBoxesList", mailBoxesList);
		return "mailboxes_trash_view";
	}
	
	// 메일 글 보기
	@RequestMapping("/mailboxes_content_list")
	public String mailboxes_content_list(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));

		MailBoxesVO mailBoxesVO = mapper.mailSelectByIdx(idx);

		model.addAttribute("divs", request.getParameter("divs"));
		request.setAttribute("currentPage", Integer.parseInt(request.getParameter("currentPage")));
		request.setAttribute("MailBoxesVO", mailBoxesVO);
		request.setAttribute("enter", "\r\n");
		
		return "mailboxes_content_view";
	}
	
	// 메일 서비스
	@RequestMapping("/mailboxes_service")
	public String mailboxes_service(HttpServletRequest request, Model model, MailBoxesVO mo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
				
		int mode = Integer.parseInt(request.getParameter("mode"));
		
		int currentPage = 1;		
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {}
		
		if(mode == 1){ // 메일 전송
			
			mapper.mailInsert(mo);
			return "redirect:mailboxes_receive_view";
			
		}else if(mode == 2){ // 메일 삭제
			
			mapper.mailTrash(mo);
			return "redirect:mailboxes_receive_view";
			
		}else if(mode == 3){ // 메일 복구
			
			mapper.mailRestore(mo);
			return "redirect:mailboxes_trash_view";
			
		}else if(mode == 4){ // 메일 찐삭제
			
			mapper.mailDelete(mo);
			return "redirect:mailboxes_trash_view";

		}
		
		return "error";
	}
	
	@RequestMapping("/mailboxes_insert")
	public String mailboxes_insert(HttpServletRequest request, Model model) {
		return "mailboxes_insert";
	}
	
	// 오늘 할 일
		@RequestMapping("/todo_view")
		public String todo_view(HttpServletRequest request, Model model) {
			
			MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
			
			HttpSession session = request.getSession();
			EmpVO vo = (EmpVO) session.getAttribute("EmpVO");
			
			return "todo_view";
		}

}
