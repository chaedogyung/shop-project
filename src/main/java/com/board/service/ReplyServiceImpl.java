package com.board.service;

import com.board.dao.ReplyDAO;
import com.board.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private ReplyDAO dao;

    @Override
    public List<ReplyVO> readReply(int bno) throws Exception {
        return dao.readReply(bno);
    }
}
