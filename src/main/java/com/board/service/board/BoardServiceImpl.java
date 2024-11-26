package com.board.service.board;

import com.board.dao.board.BoardDAO;
import com.board.util.FileUtils;
import com.board.vo.BoardVO;
import com.board.vo.SearchCriteria;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDAO dao;

    @Resource(name = "fileUtils")
    private FileUtils fileUtils;

    @Override
    public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
        dao.write(boardVO);

        List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(boardVO, mpRequest);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            dao.insertFile(list.get(i));
        }
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
    public void update(BoardVO boardVO,
                       String[] files,
                       String[] fileNames,
                       MultipartHttpServletRequest mpRequest) throws Exception {
        dao.update(boardVO);

        List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileNames, mpRequest);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            tempMap = list.get(i);
            if (tempMap.get("IS_NEW").equals("Y")) {
                dao.insertFile(tempMap);
            } else {
                dao.updateFile(tempMap);
            }
        }
    }

    @Override
    public void delete(int bno) throws Exception {
        dao.delete(bno);
    }

    @Override
    public List<Map<String, Object>> selectFileList(int bno) throws Exception {
        return dao.selectFileList(bno);
    }

    @Override
    public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
        return dao.selectFileInfo(map);
    }

}
