package com.board.service.member;

import com.board.dao.member.MemberDAO;
import com.board.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDAO dao;

    @Override
    public void register(MemberVO vo) throws Exception {
        dao.register(vo);
    }

    @Override
    public MemberVO login(MemberVO member) throws Exception {
        return dao.login(member);
    }
}
