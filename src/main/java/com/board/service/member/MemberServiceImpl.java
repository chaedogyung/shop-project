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

    @Override
    public void memberUpdate(MemberVO member) throws Exception {
        dao.memberUpdate(member);
    }

    @Override
    public void memberDelete(MemberVO member) throws Exception {
        dao.memberDelete(member);
    }

    @Override
    public int passChk(MemberVO vo) throws Exception {
      int result = dao.passChk(vo);
      return result;
    }

    @Override
    public int idChk(MemberVO vo) throws Exception {
        int result = dao.idChk(vo);
        return result;
    }
}
