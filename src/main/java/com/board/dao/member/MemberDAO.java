package com.board.dao.member;

import com.board.vo.MemberVO;

public interface MemberDAO {

    //회원가입
    public void register(MemberVO member) throws Exception;

    //로그인
    public MemberVO login(MemberVO member) throws Exception;

    //회원정보 수정
    public void memberUpdate(MemberVO member) throws Exception;

    //회원 탈퇴
    public void memberDelete(MemberVO member) throws Exception;

    //패스워드 체크
    int passChk(MemberVO vo) throws Exception;

    //아이디 중복체크
    public int idChk(MemberVO vo) throws Exception;
}
