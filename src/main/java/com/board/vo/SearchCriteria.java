package com.board.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria extends Criteria {

    private String searchType = "";
    private String keyword = "";

    @Override
    public String toString() {
        return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
    }

}