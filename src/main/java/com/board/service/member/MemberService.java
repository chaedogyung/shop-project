package com.board.service.member;

import com.board.vo.MemberVO;

public interface MemberService {

    public void register(MemberVO member) throws Exception;

    public MemberVO login(MemberVO member) throws Exception;

}
