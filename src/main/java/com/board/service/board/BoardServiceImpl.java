package com.board.service.board;

import com.board.dao.board.BoardDAO;
import com.board.vo.BoardVO;
import com.board.vo.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDAO dao;

    @Override
    public void write(BoardVO boardVO) throws Exception {
        dao.write(boardVO);
    }

    @Override
    public List<BoardVO> list(SearchCriteria scri) throws Exception {
        return dao.list(scri);
    }

    @Override
    public int listCount(SearchCriteria scri) throws Exception {
        return dao.listCount(scri);
    }

    @Override
    public BoardVO read(int bno) throws Exception {
        return dao.read(bno);
    }

    @Override
    public void update(BoardVO boardVO) throws Exception {
        dao.update(boardVO);
    }

    @Override
    public void delete(int bno) throws Exception {
        dao.delete(bno);
    }
}
