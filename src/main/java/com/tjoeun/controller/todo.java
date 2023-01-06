package com.tjoeun.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.TodoList;
import com.tjoeun.vo.TodoVO;

@Controller
public class todo {

	private static final Logger logger = LoggerFactory.getLogger(todo.class);

	@Autowired
	private SqlSession sqlSession;
	
	private Date date = new Date();
	private String setdate = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	
	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	TodoList todolist = CTX.getBean("todolist",TodoList.class);

	@ResponseBody
	@RequestMapping("/todo_insert")
	public String todo_insert(HttpServletRequest request, Model model, TodoVO vo) throws ParseException {

		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		String writedate = request.getParameter("dateinfo");
		vo.setWritedate(sdf.parse(writedate));
		
		int todocount = mapper.todocount(vo); //날짜별 todo 총 개수	
		vo.setIdx(todocount + 1);
		
		mapper.todoinsert(vo);

		vo.setSetdate(setdate);
		todolist.setList(mapper.todolist(vo));	
		
		HttpSession session = request.getSession();
		session.setAttribute("todoList", null);
		session.setAttribute("todoList", todolist);
		
		String idx = vo.getIdx() + "";
		return idx;
	}
	
	@ResponseBody
	@RequestMapping("/todo_updatestatus")
	public void todo_updatestatus(HttpServletRequest request, Model model, TodoVO vo) {
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		vo.setSetdate(setdate);
		
		mapper.todoupdateS(vo);	
		
		todolist.setList(mapper.todolist(vo));	
		
		HttpSession session = request.getSession();
		session.setAttribute("todoList", null);
		session.setAttribute("todoList", todolist);
	}	

}
