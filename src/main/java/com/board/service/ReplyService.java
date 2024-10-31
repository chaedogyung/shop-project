package com.board.service;

import com.board.vo.ReplyVO;

import java.util.List;

public interface ReplyService {
    public List<ReplyVO> readReply(int bno) throws Exception;
}
