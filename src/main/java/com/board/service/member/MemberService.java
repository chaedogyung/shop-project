package com.board.service.member;

import com.board.vo.MemberVO;

public interface MemberService {

    public void register(MemberVO member) throws Exception;

    public MemberVO login(MemberVO member) throws Exception;
    //회원정보 수정
    public void memberUpdate(MemberVO member) throws Exception;

}
