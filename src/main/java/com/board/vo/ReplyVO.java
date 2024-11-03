package com.board.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReplyVO {
    private int bno;
    private int re_rno;
    private String re_content;
    private String re_writer;
    private Date re_regdate;
}
