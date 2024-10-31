package com.board.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReplyVO {
    private int id;
    private int rno;
    private String content;
    private String writer;
    private Date regdate;
}
