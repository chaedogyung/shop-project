package com.board.service.board;

import com.board.dao.board.ReplyDAO;
import com.board.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyDAO dao;

    @Override
    public List<ReplyVO> replyList(int bno) throws Exception {
        return dao.replyList(bno);
    }

    @Override
    public void writeReply(ReplyVO vo) throws Exception {
        dao.writeReply(vo);
    }

    @Override
    public void updateReply(ReplyVO vo) throws Exception {
        dao.updateReply(vo);
    }

    @Override
    public void deleteReply(ReplyVO vo) throws Exception {
        dao.deleteReply(vo);
    }

    @Override
    public ReplyVO selectReply(int re_rno) throws Exception {
        return dao.selectReply(re_rno);
    }
}
