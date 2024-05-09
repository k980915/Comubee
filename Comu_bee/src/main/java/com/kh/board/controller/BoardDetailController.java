package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.contents.model.vo.Contents;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardService bs = new BoardService();
		
		int result=new BoardService().increaseCount(bno);
		if(result>0) {
			ArrayList<Category> cList = bs.selectCategoryList();
			Board b = bs.selectBoard(bno);
//			System.out.println(b);
			// 첨부파일 정보도 조회하기
			ArrayList<Attachment> atList = bs.selectAttachment(bno);
//			System.out.println(at);
			ArrayList<Contents> bestContList=bs.bestContList();
			System.out.println(bestContList);
			ArrayList<Board> newPopList=bs.newPopList(b);
			System.out.println(newPopList);
			ArrayList<Board> bestPopList=bs.bestPopList(b);
			System.out.println(bestPopList);
			request.setAttribute("b",b);
			request.setAttribute("atList",atList);
			request.setAttribute("cList", cList);
			request.setAttribute("newPopList", newPopList);
			request.setAttribute("bestPopList", bestPopList);
			request.setAttribute("bestContList", bestContList);
			request.getRequestDispatcher("views/board/boardDetail.jsp").forward(request, response);

		}else {
			request.getSession().setAttribute("alertMsg", "조회실패");
			response.sendRedirect(request.getHeader("referer"));// 이전 주소 반환
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
