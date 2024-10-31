package com.board.controller;

import com.board.service.BoardService;
import com.board.service.ReplyService;
import com.board.vo.BoardVO;
import com.board.vo.PageMaker;
import com.board.vo.ReplyVO;
import com.board.vo.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/board/*")
public class BoardController {

    @Autowired
    BoardService service;

    @Autowired
    ReplyService replyService;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    //게시판 상세 뷰
    @RequestMapping(value = "/writeView", method = RequestMethod.GET)
    public ModelAndView writeView() {
        logger.info("writeView");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/board/writeView");
        return modelAndView;
    }

    //게시판 글 작성
    @PostMapping(value = "/write")
    public ModelAndView write(BoardVO boardVO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("write");
        service.write(boardVO);
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    // 게시판 목록 조회
    @GetMapping(value = "/list")
    public ModelAndView list(@ModelAttribute("scri") SearchCriteria scri) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        logger.info("list");

        modelAndView.addObject("list", service.list(scri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(service.listCount(scri));
        modelAndView.addObject("pageMaker", pageMaker);
        modelAndView.addObject("scri", scri);

        modelAndView.setViewName("board/list");
        return modelAndView;
    }

    //게시판 조회
    @GetMapping(value = "/readView")
    public ModelAndView readView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri) throws Exception {
        logger.info("read");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("read", service.read(boardVO.getBno()));
        modelAndView.addObject("scri", scri);

        List<ReplyVO> replyList = replyService.readReply((boardVO.getBno()));
        modelAndView.addObject("replyList", replyList);

        modelAndView.setViewName("board/readView");
        return modelAndView;
    }

    //게시판 수정뷰
    @GetMapping(value = "/updateView")
    public ModelAndView updateView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri) throws Exception {
        logger.info("updateView");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("update", service.read(boardVO.getBno()));
        modelAndView.addObject("scri", scri);
        modelAndView.setViewName("board/updateView");
        return modelAndView;
    }

    //게시판 수정
    @PostMapping(value = "/update")
    public ModelAndView update(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
        logger.info("update");
        service.update(boardVO);
        ModelAndView modelAndView = new ModelAndView();
        rttr.addAttribute("page", scri.getPage());
        rttr.addAttribute("perPageNum", scri.getPerPageNum());
        rttr.addAttribute("searchType", scri.getSearchType());
        rttr.addAttribute("keyword", scri.getKeyword());

        modelAndView.setViewName("redirect:/board/list");
        return modelAndView;
    }

    //게시판 삭제
    @PostMapping(value = "/delete")
    public ModelAndView delete(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
        logger.info("delete");

        ModelAndView modelAndView = new ModelAndView();
        service.delete(boardVO.getBno());

        rttr.addAttribute("page", scri.getPage());
        rttr.addAttribute("perPageNum", scri.getPerPageNum());
        rttr.addAttribute("searchType", scri.getSearchType());
        rttr.addAttribute("keyword", scri.getKeyword());

        modelAndView.setViewName("redirect:/board/list");
        return modelAndView;
    }

}

