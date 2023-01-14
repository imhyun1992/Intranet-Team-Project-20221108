package com.tjoeun.controller.approval;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.ApprovalVO;
import com.tjoeun.vo.EmpVO;
import com.tjoeun.vo.PagingInfo;
import com.tjoeun.vo.Param;

@Controller
@RequestMapping("/approval")
public class ApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);
	
	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	
	@Autowired
	private SqlSession sqlsession;
	
	@RequestMapping("/approvalMain")
	public ModelAndView approvalMain(HttpServletRequest request, ModelAndView model, ApprovalVO vo, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		int approvalCount_YET = mapper.approvalCount_YET(empno);
		int approvalCount_UNDER = mapper.approvalCount_UNDER(empno);
		int approvalCount_DONE = mapper.approvalCount_DONE(empno);
		
		List<ApprovalVO> mainList = null; // 내 결재 목록
		// List<ApprovalVO> mainList1 = null; // 내가 작성한 결재
		// List<ApprovalVO> mainList2 = null; // 결재 수신목록

		int totalCount = mapper.listCount(mapper);
		PagingInfo paging = new PagingInfo(pageSize, totalCount, currentPage);
		
		Param param = new Param();
			param.setUserName(vo.getUserName());
			param.setUserNo(empno);
			param.setStartNo(paging.getStartNo());
			param.setEndNo(paging.getEndNo());
		
		mainList = mapper.selectRecentList(param);
		// mainList1 = mapper.selectRecentList1(param);
		// mainList2 = mapper.selectRecentList2(param);
		
		model.addObject("countYet", approvalCount_YET);
		model.addObject("countUnder", approvalCount_UNDER);
		model.addObject("countDone", approvalCount_DONE);
		model.addObject("mainList", mainList);
		// model.addObject("mainList1", mainList1);
		// model.addObject("mainList2", mainList2);
		model.setViewName("approval/approvalMain");
		
		return model;
	}
	
	// 결재리스트
	@RequestMapping("/approvalList")
	public ModelAndView approvalList(ModelAndView model, HttpServletRequest request, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);

		String searchcategory = request.getParameter("searchcategory");
		String searchobj = request.getParameter("searchobj");
		
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (Exception e) {

		}
		
		PagingInfo paging = null;
		
		if (searchobj != null) { 
			
			session.setAttribute("searchcategory", searchcategory);
			searchobj = searchobj.trim().length() == 0 ? "" : searchobj;
			session.setAttribute("searchobj", searchobj);
			
		} else { // 페이지가 이동되어도 검색 내용 유지
			searchcategory = (String) session.getAttribute("searchcategory");
			searchobj = (String) session.getAttribute("searchobj");
		}
		
		if (searchobj == null || searchobj.trim().length() == 0) {
			
			int totalCount = mapper.listCount(mapper);
			paging = new PagingInfo(pageSize, totalCount, currentPage);
			
			Param param = new Param();
			param.setStartNo(paging.getStartNo());
			param.setEndNo(paging.getEndNo());
			
			paging.setList(mapper.selectApprovalList(param));
			
		} else {
			
			Param param = new Param();
			param.setSearchobj(searchobj);
			param.setSearchcategory(searchcategory);
			
			int totalCount = mapper.AselecCountMulti(param);
			
			paging = new PagingInfo(pageSize, totalCount, currentPage);
			param.setStartNo(paging.getStartNo());
			param.setEndNo(paging.getEndNo());
			
			paging.setList(mapper.AselectListMulti(param));
			
//			System.out.println(searchobj);
//			System.out.println(searchcategory);
			
		}
		
//		for(ApprovalVO item : paging.getList()) {
//			System.out.println("vo");
//			System.out.println(item);
//		}
		
        request.setAttribute("currentPage", currentPage);
        model.addObject("paging", paging);
		model.setViewName("approval/approvalList");
		
		return model;
	}
	
	// 품의서 작성 페이지
	@RequestMapping("/letterOfApproval")
	public String letterOfApproval() {
		return "approval/letterOfApproval";
	}
	
	// 품의서 글쓰기 (insert)
	@RequestMapping("/letterOfApproval_insert")
	public ModelAndView letterOfApproval_insert(HttpServletRequest request, 
			@RequestPart(value="appLoaFileUpload", required=false) MultipartFile appLoaFileUpload,
			ModelAndView model, ApprovalVO vo){
		
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		String name = empvo.getName();
		int deptno = empvo.getDeptno();
		
		int result = 0;
		int result2 = 0;
		int result3 = 0;
		
		vo.setAppWriteNo(empno);
		vo.setUserName(name);
		vo.setDeptName(deptno);
		// 파일 업로드 코딩
		//
		result = mapper.insertLetterOfApproval(vo); // APPROVAL
		vo.setLoaAppNo(vo.getAppNo());
		// request.setAttribute("result2", vo);
		result2 = mapper.insertLetterOfApproval2(vo); // APP_LOA
		vo.setReRefAppNo(vo.getAppNo());
		// System.out.println(vo.getUserName());
		result3 = mapper.insertLetterOfApproval3(vo); // APP_RECEIVE_REF
		
		if (result > 0 && result2 > 0 && result3 > 0) {
			model.addObject("msg", "품의서가 정상 등록 되었습니다.");
			model.addObject("location", "/approval/approvalList");
			System.out.println("정상");
		} else {
			model.addObject("msg", "품의서 등록에 실패했습니다.");
			model.addObject("location", "/");
		}
		
		model.addObject("includes/msg");
		model.setViewName("approval/approvalList");
		
		return model;
	}
	
	// 품의서 수신 페이지
	@RequestMapping("/letterOfApprovalView")
	public ModelAndView letterOfApprovalView(HttpServletRequest request, ModelAndView model,
			ApprovalVO vo, EmpVO empvo) {
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		int appNo = Integer.parseInt(request.getParameter("appNo"));
		
		vo = mapper.selectApprovalListDetail(appNo);
		
		model.addObject("approval", vo);
		model.setViewName("/approval/letterOfApprovalView");
		
		return model;
	}
	
	// 결재 승인 스크립트(letterOfApprovalView)
	@ResponseBody
	@RequestMapping("/loaApproved1")
	public int loaApproved1(HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		int appNo = vo.getAppNo();
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		int result = 0;
		result = mapper.approved1(appNo);
		
		return result;
	}
	@ResponseBody
	@RequestMapping("/loaApproved2")
	public int loaApproved2(HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		int appNo = vo.getAppNo();
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		int result = 0;
		result = mapper.approved2(appNo);
		
		return result;
	}
	@ResponseBody
	@RequestMapping("/loaApproved3")
	public int loaApproved3(HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		int appNo = vo.getAppNo();
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		int result = 0;
		result = mapper.approved3(appNo);
		
		// 쪽지보내기 기능 추가 해 볼 예정
		// 
		
		return result;
	}
	
	// 휴가 신청서 작성 폼
	@RequestMapping("/leaveApplication")
	public String leaveApplication(HttpServletRequest request) {
		return "/approval/leaveApplication";
	}
	
	// 휴가 신청서 (insert)
	@RequestMapping(value = "/leaveApplication_insert", method = RequestMethod.POST)
	public ModelAndView leaveApplication_insert(HttpServletRequest request, @ModelAttribute ApprovalVO vo, ModelAndView model) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		String name = empvo.getName();
		int deptno = empvo.getDeptno();
		
		int result = 0;
		int result2 = 0;
		int result3 = 0;
		
		vo.setAppWriteNo(empno);
		vo.setUserName(name);
		vo.setDeptName(deptno);
		
		if (empno == vo.getAppWriteNo()) {
			result = mapper.insertApproval(vo);
			vo.setLeaveAppNo(vo.getAppNo());
			request.setAttribute("result", vo);
			
			result2 = mapper.insertAppLeave(vo);
			vo.setReRefAppNo(vo.getAppNo());
			
			result3 = mapper.insertReceiveRef(vo);
			
			if (result > 0 && result2 > 0 && result3 > 0) {
				model.addObject("msg", "휴가 신청서가 정상 등록 되었습니다.");
				model.addObject("location", "/approval/leaveApplication");
				System.out.println("성공");
			} else {
				model.addObject("msg", "휴가 신청서 등록 실패");
				model.addObject("location", "/approval/leaveApplication");
				System.out.println("실패");
			}
		}
		model.addObject("includes/msg");
		model.setViewName("approval/leaveApplication");

		return model;
	}

	// 휴가 신청서 수신
	@RequestMapping("/leaveApplicationView")
	public ModelAndView leaveApplicationView(ModelAndView model, HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		int appNo = vo.getAppNo();

		vo = mapper.viewAppLeaveList(appNo);
		// System.out.println(vo);
		
		model.addObject("approval", vo);
	    model.setViewName("/approval/leaveApplicationView");

	    return model;
	}
	
	// 지출결의서 작성 페이지
	@RequestMapping("/expenseReport")
	public String expenseReport() {
		return "approval/expenseReport";
	}
	
	// 지출결의서 글쓰기(insert)
	@RequestMapping("/expenseReport_insert")
	public ModelAndView expenseReport_insert (ApprovalVO vo, ModelAndView model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		String name = empvo.getName();
		int deptno = empvo.getDeptno();
		
		int result = 0;
		int result2 = 0;
		int result3 = 0;
		
		vo.setAppWriteNo(empno);
		vo.setUserName(name);
		vo.setDeptName(deptno);
		
		result = mapper.insertExpenseReport(vo); // APPROVAL
		vo.setAppWriteNo(vo.getAppNo());
		request.setAttribute("approval", vo);
		
		result2 = mapper.insertExpenseReport2(vo); // APP_ER
		vo.setErAppNo(vo.getAppNo());
		
		result3 = mapper.insertExpenseReport3(vo); // APP_RECEIVE_REF
		
		if (result > 0 && result2 > 0 && result3 > 0) {
			model.addObject("msg", "지출결의서가 등록되었습니다.");
			model.addObject("location", "/approval/approvalList");
		} else {
			model.addObject("msg", "지출결의서 등록 실패");
			model.addObject("location", "/");
		}
		model.setViewName("includes/msg");
		model.setViewName("/approval/expenseReport");
		
		return model;
	}
	
	// 지출결의서 수신
	@RequestMapping("/expenseReportView")
	public ModelAndView expenseReportView(ModelAndView model, HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		int appNo = vo.getAppNo();
		vo = mapper.selectExpenseReportListDetail(appNo);
		
		model.addObject("approval", vo);
		model.setViewName("/approval/expenseReportView");
		
		return model;
	}
	
//	// 수신참조자 모달 검색
	@ResponseBody
	@RequestMapping("/searchMemberInModal")
	public List<EmpVO> searchMemberInModal(EmpVO empVO, HttpServletRequest request,
			@RequestParam(value = "searchData") String searchData, EmpVO empvo) {
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		HttpSession session = request.getSession();

		empVO = (EmpVO) session.getAttribute("EmpVO");
		int empno = empVO.getEmpno();
		
		List<EmpVO> empList = null;
		empList = mapper.selectSearchedMemberForApproval(searchData, empvo);

		return empList;
	}
	
//	// 수신참조자 모달 내 멤버 리스트 불러오기 (leaveApplication)
	@ResponseBody
	@RequestMapping(value = "/leaveApplication", method = { RequestMethod.GET })
	public ModelAndView leaveApplication (HttpServletRequest request, EmpVO empVO, ModelAndView model) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		List<EmpVO> empList = null;
		
		empList = mapper.selectMemberAllForApproval(empvo);
		
		model.addObject("empList", empList);
		model.setViewName("approval/leaveApplication");
		
		return model;
	}
	
//	// 수신참조자 모달 내 멤버 리스트 불러오기 (expenseReport)
	@ResponseBody
	@RequestMapping(value = "/expenseReport", method = { RequestMethod.GET })
	public ModelAndView expenseReport(HttpServletRequest request, ModelAndView model, EmpVO empVO) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		List<EmpVO> empList = null;
		
		empList = mapper.selectMemberAllForApproval(empvo);
		
		model.addObject("empList", empList);
		model.setViewName("approval/expenseReport");
		
		return model;
	}
	
//	// 수신참조자 모달 내 멤버 리스트 리스트 불러오기 (letterOfApproval)
	@ResponseBody
	@RequestMapping(value = "/letterOfApproval", method = { RequestMethod.GET })
	public ModelAndView letterOfApproval(HttpServletRequest request, ModelAndView model, EmpVO empvo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
		String today = format.format(date);
		model.addObject("serverTime", today);
		
		List<EmpVO> empList = null;
		empList = mapper.selectMemberAllForApproval(empvo);
		// System.out.println(empList);
		
		model.addObject("empList", empList);
		model.setViewName("approval/letterOfApproval");
		
		return model;
	}
//	
//	
//	/*
//	 * saveAppFile 파일첨부 관련 코딩
//	 * 
//	 * 
//	 * 
//	 * 
//	 * appFileDown
//	 * 
//	 * 
//	 * 
//	 * 
//	 */
	
//	// 자동 완성 (JSON으로 구현, 안 되면 뺄지도 ...) 
//	@ResponseBody
//	@RequestMapping("/search/json")
//	 public Map<String, String> searchJson(@RequestParam(value = "userName") String userName) {
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//
//		List<EmpVO> search = mapper.getSearchMember(userName);
//		
//		JsonArray array = new JsonArray();
//		for (int i = 0; i < search.size(); i++) {
//			array.add(search.get(i).getName() + "_" + search.get(i).getDeptno() + "_" + search.get(i).getPosition());
//		}
//		System.out.println(array);
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("name", "abc");
//		data.put("age", "11");
//		
//		return data;
//		
// 	}
//
	}
