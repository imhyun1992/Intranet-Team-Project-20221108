package com.tjoeun.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.service.getCalendar;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.DateData;
import com.tjoeun.vo.EmpList;
import com.tjoeun.vo.EmpVO;
import com.tjoeun.vo.MeetRoomList;
import com.tjoeun.vo.MeetRoomVO;
import com.tjoeun.vo.Param;

@Controller
@RequestMapping("/board")
public class board {

	private static final Logger logger = LoggerFactory.getLogger(board.class);

	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	BoardVO vo = CTX.getBean("boardvo", BoardVO.class);
	Param param = CTX.getBean("param", Param.class);

	private int PageSize = 10;
	private int currentPage = 1;

	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("/move_board_list")
	public String move_board_list(HttpServletRequest request, Model model, RedirectAttributes redirect) {
		
		HttpSession session = request.getSession();
		
		try {
			session.setAttribute("searchcategory", null);
			session.setAttribute("searchobj", null);
		} catch (Exception e) {
			
		}
		
		String category = request.getParameter("category");
		redirect.addAttribute("category", category); // redirect할 페이지로 값 전달
		
		return "redirect:board_list";
	}

	// 글 목록
	@RequestMapping("/board_list")
	public String board_list(HttpServletRequest request, Model model, @RequestParam("category") String category) {

		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		String searchcategory = request.getParameter("searchcategory");
		String searchobj = request.getParameter("searchobj");

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (Exception e) {

		}

		if (searchobj != null) { 
			session.setAttribute("searchcategory", searchcategory);
			searchobj = searchobj.trim().length() == 0 ? "" : searchobj;
			session.setAttribute("searchobj", searchobj);
		} else { // 페이지가 이동되어도 검색 내용 유지
			searchcategory = (String) session.getAttribute("searchcategory");
			searchobj = (String) session.getAttribute("searchobj");
		}

		BoardList boardlist = null;

		// 검색 내용 없을 경우에 전체 글 목록 얻어오기
		if (searchobj == null || searchobj.trim().length() == 0) { 

			int totalCount = mapper.selectCount(category);

			boardlist = new BoardList(PageSize, totalCount, currentPage);

			param.setStartNo(boardlist.getStartNo());
			param.setEndNo(boardlist.getEndNo());
			param.setCategory(category);

			boardlist.setList(mapper.selectList(param));
			
		} else { // 검색 내용 찾기

			param.setSearchobj(searchobj);
			param.setSearchcategory(searchcategory);
			param.setCategory(category);

			int totalCount = mapper.selecCountMulti(param);

			boardlist = new BoardList(PageSize, totalCount, currentPage);

			param.setStartNo(boardlist.getStartNo());
			param.setEndNo(boardlist.getEndNo());

			boardlist.setList(mapper.selectListMulti(param));
		}
		
		// QNA는 따로 처리
		if (category.equals("QNA")) {
			int totalCount = mapper.selectQNACount(category);

			boardlist = new BoardList(PageSize, totalCount, currentPage);

			param.setStartNo(boardlist.getStartNo());
			param.setEndNo(boardlist.getEndNo());
			param.setCategory(category);

			boardlist.setList(mapper.selectQNAList(param));
		}

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("BoardList", boardlist);

		if (category.equals("자유 게시판")) {
			return "board/freeboard_view";
		} else if (category.equals("공지사항")) {
			return "board/noticeboard_view";
		} else if (category.equals("자료실")) {
			return "board/databoard_view";
		} else if (category.equals("QNA")) {
			return "board/QNAboard_view";
		}

		return "./error";
	}

	// 글 입력 폼 이동
	@RequestMapping("/freeboard_insert")
	public String freeboard_insert(HttpServletRequest request, Model model) {
		return "board/freeboard_insert";
	}
	@RequestMapping("/databoard_insert")
	public String databoard_insert(HttpServletRequest request, Model model) {
		return "board/databoard_insert";
	}
	@RequestMapping("/noticeboard_insert")
	public String noticeboard_insert(HttpServletRequest request, Model model) {
		return "board/noticeboard_insert";
	}
	@RequestMapping("/QNAboard_insert")
	public String QNAboard_insert(HttpServletRequest request, Model model) {
		return "board/QNAboard_insert";
	}
	
	// 글 입력
	@RequestMapping("/board_insert")
	public String board_insert(HttpServletRequest request, Model model, BoardVO vo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);		
		mapper.boardinsert(vo);
		model.addAttribute("category", vo.getCategory());
		return "redirect:board_list";
	}
	
	// 자료실 글 입력
	@RequestMapping("/data_upload")
	public String data_upload(MultipartHttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);		
		
		logger.info(request.getParameter("name"));
		
		return "redirect:board_list";
	}
	
	// Q&A 답변 입력
	@RequestMapping("/answer_insert")
	public String answer_insert(HttpServletRequest request, Model model, BoardVO vo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);		
		mapper.answerinsert(vo);
		model.addAttribute("category", vo.getCategory());
		return "redirect:board_list";
	}
	
	// 글 보기
	@RequestMapping("/content_list")
	public String content_list(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);		
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (Exception e) {

		}	
		String category = request.getParameter("category");
		
		mapper.increment_hit(idx);
		
		BoardVO vo = mapper.selectContentByIdx(idx);
		
		int PageSize = 7;
		int totalCount = mapper.selectCommentCount(idx);

		BoardList commentlist = new BoardList(PageSize, totalCount, currentPage);

		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", commentlist.getStartNo());
		hmap.put("endNo", commentlist.getEndNo());
		hmap.put("idx", idx);

		commentlist.setList(mapper.selectCommentList(hmap));
		
		model.addAttribute("commentList", commentlist);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("BoardVO", vo);
		model.addAttribute("enter", "\r\n");
		
		if (category.equals("자유 게시판")) {
			return "board/free_content_view";
		} else if (category.equals("공지사항")) {
			return "board/notice_content_view";
		} else if (category.equals("자료실")) {
			return "board/data_content_view";
		}  else if (category.equals("QNA")) {
			return "board/QNA_content_view";
		}
		
		return "index/error";
	}
		
	// 글 삭제
	@RequestMapping("/board_delete")
	public String contentdelete(HttpServletRequest request, Model model) {
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));	
		String category = request.getParameter("category");
		
		BoardVO vo = mapper.selectContentByIdx(idx);
		
		mapper.contentdelete(vo);
		
		model.addAttribute("currnetPage", currentPage);
		model.addAttribute("category", category);
		
		return "redirect:board_list";
	}
	
	// 글 수정 폼 이동
	@RequestMapping("/board_update")
	public String board_update(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardVO boardvo = mapper.selectContentByIdx(idx);	
		
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("BoardVO", boardvo);
		model.addAttribute("enter", "\r\n");
		
		if (boardvo.getCategory().equals("자유 게시판")) {
			return "board/freeboard_update";
		} else if  (boardvo.getCategory().equals("공지사항")) {
			return "board/noticeboard_update";
		} else if  (boardvo.getCategory().equals("자료실")) {
			return "board/databoard_update";
		} 
		
		return "index/error";
	}
	
	// 글 수정
	@RequestMapping("/board_updateOK")
	public String board_updateOK(HttpServletRequest request, Model model, BoardVO vo) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		mapper.boardupdate(vo);
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("category", request.getParameter("category"));
		
		return "redirect:content_list";
	}
	
	// 댓글 서비스
	@RequestMapping("/comment_service")
	public String comment_service(HttpServletRequest request, Model model, BoardVO vo) {
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		model.addAttribute("idx", vo.getGup());
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("category", request.getParameter("category"));
		
		if (Integer.parseInt(request.getParameter("mode")) == 1) {
			mapper.commentinsert(vo); //입력
		} else if (Integer.parseInt(request.getParameter("mode")) == 2) {
			mapper.boardupdate(vo); // 수정
		} else if (Integer.parseInt(request.getParameter("mode")) == 3) {
			mapper.commentdelete(vo); // 삭제
		}		
		
		return "redirect:content_list";
	}
	
	// 조직도 메인
	@RequestMapping("/groupchart_main")
	public String groupchart_main(HttpServletRequest request, Model model) {
		return "board/groupchart_main";
	}

	// 특정 부서 데이터 보기
	@RequestMapping("/groupchart_view")
	public String groupchart_view(HttpServletRequest request, Model model) {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);

		int deptno = Integer.parseInt(request.getParameter("deptno"));

		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
		}
		
		int totalCount =  mapper.countByDept(deptno);
		
	    EmpList empList = new EmpList(PageSize, totalCount, currentPage);
	    
	    HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", empList.getStartNo());
		hmap.put("endNo", empList.getEndNo());
		hmap.put("deptno", deptno);
	        
    	empList.setList((ArrayList<EmpVO>) mapper.selectByDept(hmap));

		model.addAttribute("EmpList", empList);
		model.addAttribute("deptno", deptno);
		
		return "board/groupchart_view";
	}
	
	// 회의실 메인 페이지
	@RequestMapping("/meetroom_main")
	public String meetroom_main(HttpServletRequest request, Model model, DateData dateData) {

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
		
		return "board/meetroom_main";
	}
	
	@RequestMapping("/meetroom_select")
	public String meetroom_select(HttpServletRequest request, Model model, DateData dateData, MeetRoomVO mvo) throws ParseException {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		String writedate = dateData.getYear() + "-" + dateData.getMonth() + "-" + dateData.getDate();
		mvo.setSetdate(sdf.parse(writedate));
		
		AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list103 = CTX.getBean("meetroomlist",MeetRoomList.class);
		list103.setList(mapper.list103(mvo));
		model.addAttribute("list103",list103);
		
		CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list222 = CTX.getBean("meetroomlist",MeetRoomList.class);
		list222.setList(mapper.list222(mvo));
		model.addAttribute("list222",list222);
		
		CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list503= CTX.getBean("meetroomlist",MeetRoomList.class);
		list503.setList(mapper.list503(mvo));
		model.addAttribute("list503",list503);
		
		CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list710 = CTX.getBean("meetroomlist",MeetRoomList.class);
		list710.setList(mapper.list710(mvo));
		model.addAttribute("list710",list710);
		
		CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list901 = CTX.getBean("meetroomlist",MeetRoomList.class);
		list901.setList(mapper.list901(mvo));
		model.addAttribute("list901",list901);
		
		model.addAttribute("meetdata", dateData);
		return "board/meetroom_select";
	}
	
	@RequestMapping("/meetroom_reserve")
	public String meetroom_reserve(HttpServletRequest request, Model model, DateData dateData, MeetRoomVO mvo) throws ParseException {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		String writedate = dateData.getYear() + "-" + dateData.getMonth() + "-" + dateData.getDate();
		mvo.setSetdate(sdf.parse(writedate));
		
		System.out.println(mvo);
		
		AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MeetRoomList list = CTX.getBean("meetroomlist",MeetRoomList.class);
		
		if(mvo.getRoomnum() == 103) {
			list.setList(mapper.list103(mvo));
		} else if(mvo.getRoomnum() == 222) {
			list.setList(mapper.list222(mvo));
		} else if(mvo.getRoomnum() == 503) {
			list.setList(mapper.list503(mvo));
		} else if(mvo.getRoomnum() == 710) {
			list.setList(mapper.list710(mvo));
		} else if(mvo.getRoomnum() == 901) {
			list.setList(mapper.list901(mvo));
		}
		
		model.addAttribute("roomlist", list);
		model.addAttribute("mvo", mvo);
		model.addAttribute("meetdata", dateData);
		return "board/meetroom_reserve";
	}
	
	@RequestMapping("/meetroom_confirm")
	public String meetroom_confirm(HttpServletRequest request, Model model, DateData dateData, MeetRoomVO mvo) throws ParseException {
		
		MyBatisDAO mapper = sqlSession.getMapper(MyBatisDAO.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		String writedate = dateData.getYear() + "-" + dateData.getMonth() + "-" + dateData.getDate();
		mvo.setSetdate(sdf.parse(writedate));
		
		mapper.meetroominsert(mvo);
		
		model.addAttribute("year",dateData.getYear());
		model.addAttribute("month",dateData.getMonth());
		model.addAttribute("date",dateData.getDate());

		model.addAttribute("meetdate",mvo.getSetdate());
		
		return "redirect:meetroom_select";
	}
}
