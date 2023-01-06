package com.tjoeun.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.tjoeun.service.getCalendar;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.DateData;
import com.tjoeun.vo.EmpVO;
import com.tjoeun.vo.TodoList;
import com.tjoeun.vo.TodoVO;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private Date date = new Date();
	private String setdate = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/")
	public String login(Locale locale, Model model) {

		return "redirect:login";

	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		return "./login";
	}

	@RequestMapping("/login_confirm")
	public String login_confirm(HttpServletRequest request, Model model, EmpVO vo, TodoVO todovo) {

		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		int confirmEmpno = mapper.confirmEmpno(vo.getEmpno());

		if (confirmEmpno != 0) {

			EmpVO confirm = mapper.selectlogin(vo.getEmpno());

			if (vo.getPassword().equals(confirm.getPassword())) {
				if (confirm.getPermission().equals("waiting")) {
					session.setAttribute("alertt", "승인되지 않은 아이디입니다.");
					return "redirect:login";
				} else {
					session.setAttribute("EmpVO", confirm);

					AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
					TodoList todolist = CTX.getBean("todolist", TodoList.class);

					todovo.setSetdate(setdate);

					todolist.setList(mapper.todolist(todovo));
					session.setAttribute("todoList", todolist);

					return "redirect:main";
				}
			} else {
				session.setAttribute("alertt", "틀린 비밀번호입니다.");
				return "redirect:login";
			}

		} else {
			session.setAttribute("alertt", "존재하지 않는 사원번호입니다.");
			return "redirect:login";
		}
	}

	@RequestMapping("/main")
	public String main(HttpServletRequest request, Model model, DateData dateData) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		// JAVA의 Calendar 추상클래스 활용
		// 추상 클래스이기 때문에 직접 new 하여 객체 생성 불가
		// => Calendar.getInstance() 이용하거나, Calendar 클래스를 상속받는 GregorianCalendar 클래스 이용
		Calendar cal = Calendar.getInstance();
		
		// 달력 초기화
		if (dateData.getDate() == null && dateData.getMonth() == null) {
			dateData = new DateData(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DATE), null, null);
		}
		
		if (Integer.parseInt(dateData.getMonth()) == 12) {
			dateData.setMonth("0");
			dateData.setYear(String.valueOf(Integer.parseInt(dateData.getYear()) + 1));
		}
		
		if (Integer.parseInt(dateData.getMonth()) == -1) {
			dateData.setMonth("11");
			dateData.setYear(String.valueOf(Integer.parseInt(dateData.getYear()) - 1));
		}

		List<DateData> dateList = getCalendar.month_info(dateData, sqlSession); // 이번달 필드 

		model.addAttribute("dateList", dateList);
		model.addAttribute("datedata", dateData); // 오늘 날짜
		
		// 공지사항
		BoardList boardlist = new BoardList();
		String category = "공지사항";
		boardlist.setList(mapper.selectNoticeList(category));	
		model.addAttribute("noticeList",boardlist);
		
		return "main";
	}

	@RequestMapping("/logout")
	public String lotout(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		session.setAttribute("EmpVO", null);
		return "redirect:login";
	}

}
