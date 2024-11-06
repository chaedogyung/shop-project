package com.board.controller.member;

import com.board.service.member.MemberService;
import com.board.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/member/*")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService service;

    //회원가입 get
    @GetMapping(value = "/registerView")
    public ModelAndView getRegister() throws Exception {
        logger.info("get register");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/register");
        return mav;
    }

    //회원가입 post
    @PostMapping(value = "/register")
    public String postRegister(MemberVO vo) throws Exception {
        logger.info("post register");
        service.register(vo);
        return null;
    }

    //로그인
    @PostMapping(value = "/login")
    public ModelAndView login(MemberVO member, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
        logger.info("login");
        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();
        MemberVO login = service.login(member);

        if (login != null) {
            session.setAttribute("member", login);
        } else {
            session.setAttribute("member", null);
            rttr.addFlashAttribute("msg",false);
        }
        mav.setViewName("redirect:/");
        return mav;
    }

    //로그아웃
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        session.invalidate();
        mav.setViewName("redirect:/");
        return mav;
    }
}
