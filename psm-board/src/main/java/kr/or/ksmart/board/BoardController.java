package kr.or.ksmart.board;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ksmart.service.Board;
import kr.or.ksmart.service.BoardDao;

@Controller
public class BoardController {
	
	@Autowired
	private BoardDao boardDao;
	
	// 게시글 리스트 요청
	@RequestMapping(value="/psmList", method = RequestMethod.GET)
    public String boardList(Model model
                            , @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {
        int boardCount = boardDao.getBoardCount();
        int pagePerRow = 10;
        int lastPage = (int)(Math.ceil(boardCount / pagePerRow));
        ArrayList<Board> list = boardDao.getBoardList(currentPage, pagePerRow);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("list", list);
        return "/boardList";
    }
	
	// 입력(액션) 요청
	@RequestMapping(value = "/psmAdd", method = RequestMethod.POST)
	public String psmAdd(Board board) {
		System.out.println("BoardController -> psmAdd");
		System.out.println(board);
		boardDao.insertBoard(board);
		return "redirect:/psmList";
	}
	
	// 입력페이지 요청
	@RequestMapping(value = "/psmAdd", method = RequestMethod.GET)
	public String psmAdd() {
		System.out.println("BoardController-> psmAdd");
		return "psmAdd";
	}
}
