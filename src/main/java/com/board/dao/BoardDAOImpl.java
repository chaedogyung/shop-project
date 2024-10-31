package com.board.dao;

import com.board.vo.BoardVO;
import com.board.vo.SearchCriteria;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOImpl implements BoardDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void write(BoardVO boardVO) throws Exception {
        sqlSession.insert("boardMapper.insert", boardVO);
    }

    @Override
    public int listCount(SearchCriteria scri) throws Exception {
        return sqlSession.selectOne("boardMapper.listCount",scri);
    }

    @Override
    public List<BoardVO> list(SearchCriteria scri) throws Exception {

        return sqlSession.selectList("boardMapper.listPage",scri);
    }

    @Override
    public BoardVO read(int bno) throws Exception {
        return sqlSession.selectOne("boardMapper.read", bno);
    }

    @Override
    public void update(BoardVO boardVO) throws Exception {
        sqlSession.update("boardMapper.update", boardVO);
    }

    @Override
    public void delete(int bno) throws Exception {
        sqlSession.delete(("boardMapper.delete"), bno);
    }
}
