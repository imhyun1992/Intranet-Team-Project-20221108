package com.tjoeun.controller.approval;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.dao.MyBatisDAO;
import com.tjoeun.vo.ApprovalVO;
import com.tjoeun.vo.EmpVO;


@Controller
@RequestMapping("/approval")
public class ApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalController.class);
	
	AbstractApplicationContext CTX = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	ApprovalVO vo = CTX.getBean("approvalvo", ApprovalVO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	// 메인
	@RequestMapping("/approvalMain")
	public ModelAndView approvalMain(HttpServletRequest request, ModelAndView model) {
		HttpSession session = request.getSession();
		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
		
		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
		int empno = empvo.getEmpno();
		// System.out.println(empno);
		
		int approvalCount_YET = mapper.approvalCount_YET(empno);
		int approvalCount_UNDER = mapper.approvalCount_UNDER(empno);
		int approvalCount_DONE = mapper.approvalCount_DONE(empno);
		
//		List<ApprovalVO> mainList = null; // 내 결재 목록
//		List<ApprovalVO> mainList1 = null; // 내가 작성한 결재
//		List<ApprovalVO> mainList2 = null; // 결재 수신목록
//		
//		mainList = mapper.getRecentList(empno);
//		mainList1 = mapper.getRecentList1(empno);
//		mainList2 = mapper.getRecentList2(empno);
		
		model.addObject("countYet", approvalCount_YET);
		model.addObject("countUnder", approvalCount_UNDER);
		model.addObject("countDone", approvalCount_DONE);
//		model.addObject("mainList", mainList);
//		model.addObject("mainList1", mainList1);
//		model.addObject("mainList2", mainList2);
		model.setViewName("approval/approvalMain");

		return model;
	}
	
	// 결재리스트
//	@RequestMapping("/approvalList")
//	public ModelAndView approvalList(ModelAndView model, HttpServletRequest request, 
//			@RequestParam(value = "notice_search", required = false) String searchText,
//			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, 
//			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
//		
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		List<ApprovalVO> mainList2 = null;
//		int totalCount = mapper.getListCount(searchText);
//		BoardList boardList = new BoardList(pageSize, totalCount, currentPage);
//		
//		if (searchText != null) {
//			model.addObject("notice_search", searchText);
//		}
//		
//		model.addObject("mainList", mainList2);
//		model.addObject("boardList", boardList);
//		
//		model.setViewName("approval/approvalList");
//		return model;
//	}
//	
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
//	// 품의서 글쓰기
//	@RequestMapping("/letterOfApproval")
//	public ModelAndView letterOfApprovalWrite(HttpServletRequest request, ModelAndView model, ApprovalVO vo,
//			@RequestParam("appLoaFileUpload") MultipartFile upfile) {
//		
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		int result2 = 0;
//		int result3 = 0;
//		vo.setAppWriterNo(empno);
//		
//		// 파일 업로드 코딩
//		
//		
//		
//		
//		//
//		
//		result = mapper.saveLetterOfApproval(vo);
//		vo.setLoaAppNo(vo.getAppNo());
//		
//		result2 = mapper.saveLetterOfApproval2(vo);
//		vo.setReceiveRefNo(vo.getAppNo());
//		
//		result3 = mapper.saveLetterOfApproval3(vo);
//		
//		if (result > 0 && result2 > 0 && result3 > 0) {
//			model.addObject("msg", "품의서가 정상 등록 되었습니다.");
//			model.addObject("location", "/approval/approvalList");
//		} else {
//			model.addObject("msg", "품의서 등록에 실패했습니다.");
//			model.addObject("location", "/");
//		}
//		model.setViewName("common/msg");
//		
//		return model;
//	}
//	
//	// 품의서 수신 페이지
//	@RequestMapping("/letterOfApprovalView")
//	public ModelAndView letterOfApprovalView(ModelAndView model, @RequestParam("appNo") int appNo) {
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		ApprovalVO vo = mapper.findListByNo(appNo);
//		
//		model.addObject("approval", vo);
//		model.setViewName("/approval/letterOfApprovalView");
//		
//		return model;
//	}
//	
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
//	
//	// 품의서 수정
//	@ResponseBody
//	@RequestMapping("/letterOfApprovalUpdate")
//	public int letterOfApprovalUpdate(ApprovalVO vo, HttpServletRequest request,
//			@RequestParam(value = "rejectReasonText") String rejectReasonText,
//			@RequestParam(value = "appNo") int appNo) {
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		HttpSession session = request.getSession();
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		vo.setAppNo(appNo);
//		vo.setAppReason(rejectReasonText);
//		
//		System.out.println("appNo : " + appNo);
//		System.out.println("rejectReasonText : " + rejectReasonText);
//		System.out.println("vo.getAppNo() : " + vo.getAppNo());
//		System.out.println("vo.getAppReason() : " + vo.getAppReason());
//		
//		if (rejectReasonText != null) {
//			if (vo.getAppNo() != 0) {
//				result = mapper.rejectUpdate(vo);
//				System.out.println(result);
//			}
//		} else {}
//		
//		return result;
//	}
//	
//	// 자동 완성 (JSON으로 구현, 안 되면 뺄지도 ...) 
	@ResponseBody
	@RequestMapping("/search/json")
	public Map<String, String> searchJson(@RequestParam(value = "userName") String userName) {
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//
//		List<EmpVO> search = mapper.getSearchMember(userName);
//		
//		JsonArray array = new JsonArray();
//		for (int i = 0; i < search.size(); i++) {
//			array.add(search.get(i).getName() + "_" + search.get(i).getDeptno() + "_" + search.get(i).getPosition());
//		}
//		System.out.println(array);
		Map<String, String> data = new HashMap<String, String>();
		data.put("name", "abc");
		data.put("age", "11");
		
		return data;
		
	}
//	
//	// 결재 승인 스크립트(letterOfApprovalView)
//	@ResponseBody
//	@RequestMapping("/loaApproved1")
//	public int loaApproved1(HttpServletRequest request ,@RequestParam(value = "appNo") int appNo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		result = mapper.approved1(appNo);
//		
//		return result;
//	}
//	@ResponseBody
//	@RequestMapping("/loaApproved2")
//	public int loaApproved2(HttpServletRequest request, @RequestParam(value = "appNo") int appNo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		result = mapper.approved2(appNo);
//		
//		return result;
//	}
//	@ResponseBody
//	@RequestMapping("/loaApproved3")
//	public int loaApproved3(HttpServletRequest request ,@RequestParam(value = "appNo") int appNo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		result = mapper.approved3(appNo);
//		
//		// 쪽지보내기 기능 추가 해 볼 예정
//		
//		
//		
//		
//		// 
//		
//		return result;
//	}
//	
//	// 휴가 신청서 작성 폼
//	@RequestMapping("/leaveApplication")
//	public String leaveApplication(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		return "/approval/leaveApplication";
//	}
//	
//	// 휴가 신청서 글쓰기
//	@RequestMapping("/updateLeave")
//	public ModelAndView insertLeave(ModelAndView model, HttpServletRequest request, ApprovalVO vo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		System.out.println("휴가 신청서 작성 컨트롤러: " + vo);
//		
//		int result = 0;
//		int result2 = 0;
//		int result3 = 0;
//		
//		vo.setAppWriterNo(empno);
//		
//		if (empno == vo.getAppWriterNo()) {
//			result = mapper.insertApproval(vo);
//			vo.setLeaveAppNo(vo.getAppNo());
//			vo.setReceiveRefNo(vo.getAppNo());
//			
//			result2 = mapper.insertLeave(vo);
//			result3 = mapper.insertReceive(vo);
//			
//			if (result > 0 && result2 > 0 && result3 > 0) {
//				model.addObject("msg", "휴가 신청서가 정상 등록 되었습니다.");
//				model.addObject("location", "/approval/approvalList");
//			} else {
//				model.addObject("msg", "휴가 신청서 등록 실패");
//				model.addObject("location", "/approval/leaveApplication");
//			}
//		}
//		model.setViewName("common/msg");
//		
//		return model;
//	}
//	
//	// 휴가 신청서 수신
//	@RequestMapping("/leaveApplicationView")
//	public ModelAndView leaveApplicationView(ModelAndView model, HttpServletRequest request, 
//			@RequestParam("appNo") int appNo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		ApprovalVO vo = mapper.findListByLeaveNo(appNo);
//		
//		model.addObject("approval", vo);
//	    model.setViewName("/approval/leaveApplicationView");
//
//	    return model;
//		
//	}
//	
//
//	// 지출결의서 작성
//	@RequestMapping("/expenseReport")
//	public ModelAndView expenseReportWrite (ApprovalVO vo, ModelAndView model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		EmpVO empvo = (EmpVO) session.getAttribute("EmpVO");
//		int empno = empvo.getEmpno();
//		
//		int result = 0;
//		int result2 = 0;
//		int result3 = 0;
//		
//		vo.setAppWriterNo(empno);
//		result = mapper.saveExpenseReport(vo);
//		
//		vo.setErAppNo(vo.getAppNo());
//		result2 = mapper.saveExpenseReport2(vo);
//		
//		vo.setErAppNo(vo.getAppNo());
//		result3 = mapper.saveExpenseReport3(vo);
//		
//		if (result > 0 && result2 > 0 && result3 > 0) {
//			model.addObject("msg", "지출결의서가 등록되었습니다.");
//			model.addObject("location", "/approval/approvalList");
//		} else {
//			model.addObject("msg", "지출결의서 등록 실패");
//			model.addObject("location", "/");
//		}
//		model.setViewName("common/msg");
//		
//		return model;
//	}
//	
//	// 지출결의서 수신
//	@RequestMapping("/expenseReportView")
//	public ModelAndView expenseReportView(ModelAndView model, HttpServletRequest request,
//			@RequestParam("appNo") int appNo) {
//		HttpSession session = request.getSession();
//		MyBatisDAO mapper = sqlsession.getMapper(MyBatisDAO.class);
//		
//		ApprovalVO vo = mapper.findExpenseReportListByNo(appNo);
//		System.out.println(vo);
//		
//		model.addObject("approval", vo);
//		model.setViewName("/approval/expenseReportView");
//		
//		return model;
//	}
//	
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
//	
	
}
