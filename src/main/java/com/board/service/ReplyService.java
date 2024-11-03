package com.board.service;

import com.board.vo.ReplyVO;

import java.util.List;

public interface ReplyService {

    //댓글 조회
    public List<ReplyVO> replyList(int bno) throws Exception;

    //댓글 작성
    public void writeReply(ReplyVO vo) throws Exception;

    //댓글 수정
    public void updateReply(ReplyVO vo) throws Exception;

    //댓글 삭제
    public void deleteReply(ReplyVO vo) throws Exception;

    //댓글 선택 조회
    public ReplyVO selectReply(int re_rno) throws Exception;

}
