package com.board.dao.member;

import com.board.vo.MemberVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO{
    @Autowired
    SqlSession sql;

    @Override
    public void register(MemberVO vo) throws Exception {
        sql.insert("memberMapper.register", vo);
    }

    @Override
    public MemberVO login(MemberVO member) throws Exception {
        return sql.selectOne("memberMapper.login", member);
    }

    @Override
    public void memberUpdate(MemberVO member) throws Exception {
        sql.update("memberMapper.memberUpdate", member);
    }

    @Override
    public void memberDelete(MemberVO member) throws Exception {
        sql.delete("memberMapper.memberDelete", member);
    }

    @Override
    public int passChk(MemberVO vo) throws Exception {
        int result = sql.selectOne("memberMapper.passChk", vo);
        return result;
    }

    @Override
    public int idChk(MemberVO vo) throws Exception {
        int result = sql.selectOne("memberMapper.idChk", vo);
        return result;
    }
}
