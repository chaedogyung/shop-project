package com.board.dao;

import com.board.vo.ReplyVO;

import java.util.List;

public interface ReplyDAO {
    //댓글조회
    public List<ReplyVO> readReply(int bno) throws Exception;
}
