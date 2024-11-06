package com.board.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {
    private String userid;
    private String userpass;
    private String username;
    private Date regdate;
}
