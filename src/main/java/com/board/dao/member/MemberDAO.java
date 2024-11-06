package com.board.dao.member;

import com.board.vo.MemberVO;

public interface MemberDAO {

    public void register(MemberVO member) throws Exception;

    //로그인
    public MemberVO login(MemberVO member) throws Exception;
}
