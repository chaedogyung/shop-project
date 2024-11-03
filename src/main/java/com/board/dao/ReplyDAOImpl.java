package com.board.dao;

import com.board.vo.ReplyVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    @Autowired
    private SqlSession sql;

    @Override
    public List<ReplyVO> replyList(int bno) throws Exception {
        return sql.selectList("replyMapper.listReply", bno);
    }

    @Override
    public void writeReply(ReplyVO vo) throws Exception {
        sql.insert("replyMapper.writeReply", vo);
    }

    @Override
    public void updateReply(ReplyVO vo) throws Exception {
        sql.update("replyMapper.updateReply", vo);
    }

    @Override
    public void deleteReply(ReplyVO vo) throws Exception {
        sql.delete("replyMapper.deleteReply", vo);
    }

    @Override
    public ReplyVO selectReply(int re_rno) throws Exception {
        return sql.selectOne("replyMapper.selectReply", re_rno);
    }

}
