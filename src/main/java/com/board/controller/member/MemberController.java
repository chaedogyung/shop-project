package com.board.controller.member;

import com.board.service.member.MemberService;
import com.board.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

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
    public ModelAndView postRegister(MemberVO vo) throws Exception {
        ModelAndView mav = new ModelAndView();
        logger.info("post register");

        //비밀번호 암호화
        String inputPass = vo.getUserpass();
        String pwd = pwdEncoder.encode(inputPass);
        vo.setUserpass(pwd);

        service.register(vo);
        mav.setViewName("redirect:/");
        return mav;
    }

    //로그인
    @PostMapping(value = "/login")
    public ModelAndView login(MemberVO member, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
        logger.info("login");
        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();
        MemberVO login = service.login(member);

        if (login != null) {
            boolean pwdMatch = pwdEncoder.matches(member.getUserpass(), login.getUserpass());
            if (pwdMatch) {
                session.setAttribute("member", login);
            } else if (!pwdMatch) {
                session.setAttribute("member", null);
                rttr.addFlashAttribute("msg", false);
            }
        } else {
            session.setAttribute("member", null);
            rttr.addFlashAttribute("msg", false);
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

    //개인정보 수정화면
    @GetMapping(value = "/memberUpdateView")
    public ModelAndView registerUpdateView() throws Exception {
        ModelAndView mav = new ModelAndView("member/memberUpdateView");
        return mav;
    }

    //개인정보 수정
    @PostMapping(value = "/memberUpdate")
    public ModelAndView postMemberUpdate(MemberVO vo, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/");
        service.memberUpdate(vo);
        session.invalidate();
        return mav;
    }

    //회원 탈퇴 View
    @GetMapping(value = "/memberDeleteView")
    public ModelAndView memberDeleteView() throws Exception {
        ModelAndView mav = new ModelAndView("member/memberDeleteView");
        return mav;
    }

    //회원탈퇴 post
    @PostMapping(value = "/memberDelete")
    public ModelAndView memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/");
//        ModelAndView mav2 = new ModelAndView("redirect:/member/memberDeleteView");
//        MemberVO member = (MemberVO) session.getAttribute("member");
//        String sessionPass = member.getUserpass();
//        String voPass = vo.getUserpass();
//
//        if (!sessionPass.equals(voPass)) {
//            rttr.addFlashAttribute("msg", false);
//            return mav2;
//        }
        service.memberDelete(vo);
        session.invalidate();
        return mav;
    }

    //비밀번호 체크
    @PostMapping(value = "/passChk")
    public boolean passChk(MemberVO vo) throws Exception {
        MemberVO login = service.login(vo);
        boolean pwdChk = pwdEncoder.matches(vo.getUserpass(), login.getUserpass());
//        int result = service.passChk(vo);
        return pwdChk;
    }

    //아이디 중복 체크
    @GetMapping(value = "/idChk")
    public int idChk(MemberVO vo) throws Exception {
        int result = service.idChk(vo);
        return result;
    }
}
