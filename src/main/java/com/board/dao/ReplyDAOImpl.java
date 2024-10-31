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
    public List<ReplyVO> readReply(int bno) throws Exception {
        return sql.selectList("replyMapper.readReply", bno);
    }
}