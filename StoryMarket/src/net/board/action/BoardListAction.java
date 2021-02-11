package net.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.reviewDAO;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M : BoardListAction_execute() 호출!");
		
		//BoardDAO 객체 생성
		reviewDAO rdao = new reviewDAO();
		
		//게시판의 글 갯수 계산 (게시글의 유무 체크) - getBoardCount()메서드
		int count = rdao.getBoardCount();
		System.out.println("M : 총 "+ count + "개");

		//페이징 처리--------------------------------------------------------------
		 
		//현 페이지의 위치 저장변수 
		 String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		//한 페이지에 보여줄 글의 개수
		int pageSize = 3;
		
		//시작행 계산
		int currentPage = Integer.parseInt(pageNum);
		int startPage = (currentPage - 1)*pageSize +1;
		
		
		//끝행 계산
		int endPage = currentPage *pageSize;
		//---------------------------------------------------------------------
		
		//DB에서 모든 글의 정보를 가져오기()메서드
		ArrayList boardList = rdao.getBoardList(startPage, pageSize);
		
		
		//페이징처리---------------------------------------------------------------
		// 하단부 페이지 이동링크
		
		//전체 페이지수 계산
		int pageCount = count / pageSize + (count % pageSize == 0? 0 :1);
		
		// 한 화면에 보여질 페이지 블럭의 수
		int pageBlock = 2;
		
		//블럭 시작페이지
		int startBlock = ((currentPage -1)/pageBlock) * pageBlock +1;
		
		//블럭 끝페이지
		int endBlock = startBlock + pageBlock -1;
		if(endBlock > pageCount) {
			endBlock = pageCount;
		}
		
		
		//----------------------------------------------------------------------
		
		
		
		//request 영역에 필요한 정보를 저장. 
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("count", count);//총 글의 수
		request.setAttribute("pageCount", pageCount); // 총 페이지 수
		request.setAttribute("pageBlock", pageBlock); // 페이지 블럭의 수
		request.setAttribute("startBlock", startBlock);//블럭 시작페이지
		request.setAttribute("endBlock", endBlock);//블럭 끝페이지
				
		
		//페이지 이동(forward)
		
		ActionForward forword = new ActionForward();
		forword.setPath("./board/boardList.jsp");
		forword.setRedirect(false);
		return forword;
	}

}
