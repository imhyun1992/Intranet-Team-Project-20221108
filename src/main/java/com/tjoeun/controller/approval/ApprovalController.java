package com.tjoeun.controller.approval;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.ApprovalVO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.EmpVO;
import com.tjoeun.vo.PagingInfo;
import com.tjoeun.vo.Param;

@Controller
@RequestMapping("/approval")
public class ApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);
	
	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	ApprovalVO vo = CTX.getBean("approvalvo", ApprovalVO.class);

	@Autowired
	private SqlSession sqlsession;
	
	@RequestMapping("/approvalMain")
	public ModelAndView approvalMain(HttpServletRequest request, ModelAndView model, ApprovalVO vo, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		BoardVO boardVO = new BoardVO();
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		int approvalCount_YET = mapper.approvalCount_YET(empno);
		int approvalCount_UNDER = mapper.approvalCount_UNDER(empno);
		int approvalCount_DONE = mapper.approvalCount_DONE(empno);
		
		List<ApprovalVO> mainList = null; // 내가 결재할 목록
		List<ApprovalVO> mainList1 = null; // 내가 작성한 결재
		List<ApprovalVO> mainList2 = null; // 결재 수신목록

		int totalCount = mapper.listCount(mapper);
		PagingInfo paging = new PagingInfo(pageSize, totalCount, currentPage);
		
		Param param = new Param();
			param.setUserName(vo.getUserName());
			param.setUserNo(empno);
			param.setStartNo(paging.getStartNo());
			param.setEndNo(paging.getEndNo());
		
		mainList = mapper.selectRecentList(param);
		mainList1 = mapper.selectRecentList1(param);
		mainList2 = mapper.selectRecentList2(param);
		
		model.addObject("countYet", approvalCount_YET);
		model.addObject("countUnder", approvalCount_UNDER);
		model.addObject("countDone", approvalCount_DONE);
		model.addObject("mainList", mainList);
		model.addObject("mainList1", mainList1);
		model.addObject("mainList2", mainList2);
		model.setViewName("approval/approvalMain");
		
		return model;
	}
	
	// 결재리스트
	@RequestMapping("/approvalList")
	public ModelAndView approvalList(ModelAndView model, HttpServletRequest request, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		List<ApprovalVO> mainList = null;
		
		int totalCount = mapper.listCount(mapper);
		// logger.error("listCount: {}", totalCount);
		PagingInfo paging = new PagingInfo(pageSize, totalCount, currentPage);
		// logger.error("approvalList: {}", paging);
		
		// 페이지 작업
		Param param = new Param();
		param.setStartNo(paging.getStartNo());
		param.setEndNo(paging.getEndNo());
		
		mainList = paging.setList(mapper.selectApprovalList(param));
        request.setAttribute("mainList", mainList);
        request.setAttribute("currentPage", currentPage);
        
        model.addObject("paging", paging);
		model.addObject("mainList", mainList);
		// logger.error("mainList{}: ", mainList);
		
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
	public String letterOfApproval_insert(HttpServletRequest request, Model model, ApprovalVO vo){
			 // @RequestParam("appLoaFileUpload") MultipartFile upfile) 
		
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

		String loaTitle = request.getParameter("loaTitle");
		vo.setLoaTitle(loaTitle);
		
		String loaContent = request.getParameter("loaContent");
		vo.setLoaContent(loaContent);
		
		logger.error("loaContent: {}", loaContent);
		
		
		// 파일 업로드 코딩
		//
		
		vo.setLoaAppNo(vo.getAppNo());
		result = mapper.insertLetterOfApproval(vo); // APPROVAL
		
		request.setAttribute("result2", vo);
		result2 = mapper.insertLetterOfApproval2(vo); // APP_LOA
		
		vo.setReRefAppNo(vo.getAppNo());
		result3 = mapper.insertLetterOfApproval3(vo); // APP_RECEIVE_REF
		
		if (result > 0 && result2 > 0 && result3 > 0) {
			model.addAttribute("msg", "품의서가 정상 등록 되었습니다.");
			model.addAttribute("location", "/approval/approvalList");
			System.out.println("정상");
		} else {
			model.addAttribute("msg", "품의서 등록에 실패했습니다.");
			model.addAttribute("location", "/");
		}
		
		model.addAttribute("includes/msg");
		model.addAttribute("approval/letterOfApproval");
		
		return "redirect:approvalList";
	}
	
	// 품의서 수신 페이지
	@RequestMapping("/letterOfApprovalView")
	public ModelAndView letterOfApprovalView(ModelAndView model, ApprovalVO vo) {
		// @RequestParam(value = "appNo", required = false) Integer appNo
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		int appNo = vo.getAppNo();
		vo = mapper.selectApprovalListDetail(appNo);
		logger.info("appNo: {}", appNo);
		logger.info("vo: {}", vo);
		
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
		HttpSession session = request.getSession();
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		
		return "/approval/leaveApplication";
	}
	
	// 휴가 신청서 (insert)
	@RequestMapping(value = "/leaveApplication_insert", method = {RequestMethod.POST})
	public ModelAndView leaveApplication_insert(ModelAndView model, HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		String name = empvo.getName();
		int deptno = empvo.getDeptno();
		logger.info("휴가 신청서 작성 컨트롤러: {}", empno);
		
		int result = 0;
		int result2 = 0;
		int result3 = 0;
		
		vo.setAppWriteNo(empno);
		vo.setUserName(name);
		vo.setDeptName(deptno);
		
		logger.info("vo: {}", vo);
		
		if (empno == vo.getAppWriteNo()) {
			result = mapper.insertApproval(vo);
			vo.setLeaveAppNo(vo.getAppNo());
			vo.setReceiveRefNo(vo.getAppNo());
			
			result2 = mapper.insertAppLeave(vo);
			result3 = mapper.insertReceiveRef(vo);
			
			if (result > 0 && result2 > 0 && result3 > 0) {
//				model.addObject("msg", "휴가 신청서가 정상 등록 되었습니다.");
//				model.addObject("location", "/approval/approvalList");
				System.out.println("성공");
			} else {
//				model.addObject("msg", "휴가 신청서 등록 실패");
//				model.addObject("location", "/approval/leaveApplication");
				System.out.println("실패");
			}
		}
		
		model.setViewName("common/msg");

		return model;
	}

	// 휴가 신청서 수신
	@RequestMapping("/leaveApplicationView")
	public ModelAndView leaveApplicationView(ModelAndView model, HttpServletRequest request, ApprovalVO vo) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		int appNo = vo.getAppNo();

		vo = mapper.viewAppLeaveList(appNo);
		System.out.println(vo);
		
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
		
		vo.setAppWriteNo(empno);
		result = mapper.insertExpenseReport(vo);
		
		vo.setErAppNo(vo.getAppNo());
		result2 = mapper.insertExpenseReport2(vo);
		
		vo.setErAppNo(vo.getAppNo());
		result3 = mapper.insertExpenseReport3(vo);
		
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
		System.out.println(vo);
		
		model.addObject("approval", vo);
		model.setViewName("/approval/expenseReportView");
		
		return model;
	}
	
//	// 수신참조자 모달 검색
//	@ResponseBody
//	@RequestMapping("/searchMemberInModal")
//	public List<EmpVO> searchMemberInModal(EmpVO empVO, HttpServletRequest request,
//			@RequestParam(value = "searchData") String searchData) {
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		HttpSession session = request.getSession();
//
//		empVO = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empVO.getEmpno();
//		
//		List<EmpVO> memberList = null;
//		memberList = mapper.selectSearchedMemberForApproval(searchData, empno);
//
//		return memberList;
//	}
	
//	// 수신참조자 모달 내 멤버 리스트 불러오기 (leaveApplication)
//	@RequestMapping("/leaveApplication")
//	public ModelAndView leaveApplication (HttpServletRequest request, EmpVO empVO, ModelAndView model) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		List<EmpVO> memberList = null;
//		
//		memberList = mapper.selectMemberAllForApproval(empno);
//		
//		model.addObject("memberList", memberList);
//		model.setViewName("approval/leaveApplication");
//		
//		return model;
//	}
//	
//	// 수신참조자 모달 내 멤버 리스트 불러오기 (expenseReport)
//	@RequestMapping("/expenseReport")
//	public ModelAndView expenseReport(HttpServletRequest request, ModelAndView model, EmpVO empVO) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		List<EmpVO> memberList = null;
//		
//		memberList = mapper.selectMemberAllForApproval(empno);
//		
//		model.addObject("memberList", memberList);
//		model.setViewName("approval/expenseReport");
//		
//		return model;
//	}
	
//	// 수신참조자 모달 내 멤버 리스트 호출
//	@RequestMapping("/letterOfApproval")
//	public ModelAndView letterOfApproval(HttpServletRequest request, ModelAndView model, EmpVO empvo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
//		String today = format.format(date);
//		model.addObject("serverTime", today);
//		
//		List<EmpVO> empList = null;
//		empList = mapper.selectMemberAllForApproval(empno);
//		
//		model.addObject("empList", empList);
//		model.setViewName("approval/letterOfApproval");
//		
//		return model;
//	}
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
		
//		return data;
		
// 	}
//	

}
