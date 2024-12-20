package com.board.controller.board;

import com.board.service.board.BoardService;
import com.board.service.board.ReplyService;
import com.board.util.FileUtils;
import com.board.vo.BoardVO;
import com.board.vo.PageMaker;
import com.board.vo.ReplyVO;
import com.board.vo.SearchCriteria;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board/*")
public class BoardController {

    @Autowired
    BoardService service;

    @Autowired
    ReplyService replyService;

    @Autowired
    FileUtils fileUtils;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    //게시판 상세 뷰
    @RequestMapping(value = "/writeView", method = RequestMethod.GET)
    public ModelAndView writeView() {
        logger.info("writeView");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/writeView");
        return modelAndView;
    }

    //게시판 글 작성
    @PostMapping(value = "/write")
    public ModelAndView write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("write");
        service.write(boardVO, mpRequest);
        modelAndView.setViewName("redirect:/board/list");

        return modelAndView;
    }

    // 게시판 목록 조회
    @GetMapping(value = "/list")
    public ModelAndView list(@ModelAttribute("scri") SearchCriteria scri) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        logger.info("list");

        modelAndView.addObject("list", service.list(scri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(service.listCount(scri));
        modelAndView.addObject("pageMaker", pageMaker);
        modelAndView.addObject("scri", scri);

        modelAndView.setViewName("board/list");
        return modelAndView;
    }

    //게시판 조회
    @GetMapping(value = "/readView")
    public ModelAndView readView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri) throws Exception {
        logger.info("read");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("read", service.read(boardVO.getBno()));
        modelAndView.addObject("scri", scri);

        List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
        modelAndView.addObject("file", fileList);

        modelAndView.setViewName("board/readView");
        return modelAndView;
    }

    //게시판 수정뷰
    @GetMapping(value = "/updateView")
    public ModelAndView updateView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri) throws Exception {
        logger.info("updateView");
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("update", service.read(boardVO.getBno()));
        modelAndView.addObject("scri", scri);

        List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
        modelAndView.addObject("file", fileList);

        modelAndView.setViewName("board/updateView");
        return modelAndView;
    }

    //게시판 수정
    @PostMapping(value = "/update")
    public ModelAndView update(BoardVO boardVO,
                               @ModelAttribute("scri") SearchCriteria scri,
                               RedirectAttributes rttr,
                               @RequestParam(value = "fileNoDel[]") String[] files,
                               @RequestParam(value = "fileNameDel[]") String[] fileNames,
                               MultipartHttpServletRequest mpRequest) throws Exception {
        logger.info("update");
        service.update(boardVO, files, fileNames, mpRequest);
        ModelAndView modelAndView = new ModelAndView();

        rttr.addAttribute("page", scri.getPage());
        rttr.addAttribute("perPageNum", scri.getPerPageNum());
        rttr.addAttribute("searchType", scri.getSearchType());
        rttr.addAttribute("keyword", scri.getKeyword());

        modelAndView.setViewName("redirect:/board/list");
        return modelAndView;
    }

    //게시판 삭제
    @GetMapping(value = "/delete")
    public ModelAndView delete(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
        logger.info("delete");

        ModelAndView modelAndView = new ModelAndView();
        service.delete(boardVO.getBno());

        rttr.addAttribute("page", scri.getPage());
        rttr.addAttribute("perPageNum", scri.getPerPageNum());
        rttr.addAttribute("searchType", scri.getSearchType());
        rttr.addAttribute("keyword", scri.getKeyword());

        modelAndView.setViewName("redirect:/board/list");
        return modelAndView;
    }

    // 댓글 목록 조회
    @GetMapping(value = "/replyList")
    public ResponseEntity<List<ReplyVO>> replyList(BoardVO boardVO) {
        logger.info("replyList");
        try {
            // 댓글 목록 가져오기
            List<ReplyVO> replyList = replyService.replyList(boardVO.getBno());

            // 응답이 비어 있지 않으면 OK(200), 비어 있으면 NO_CONTENT(204) 상태 코드 반환
            if (!replyList.isEmpty()) {
                return ResponseEntity.ok(replyList);  // OK(200) 응답
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(replyList);  // NO_CONTENT(204) 응답
            }
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving reply list: ", e);
            // 서버 오류가 발생하면 INTERNAL_SERVER_ERROR(500) 상태 코드 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //댓글 작성
    @PostMapping(value = "/replyWrite")
    public ResponseEntity<String> replyWrite(ReplyVO vo) throws Exception {
        logger.info("replyWrite");
        try {
            replyService.writeReply(vo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reply created successfully");
        } catch (Exception e) {
            logger.error("Error while writing reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reply.");
        }
    }

    //댓글 수정POST
    @PostMapping(value = "/replyUpdate")
    public ResponseEntity<String> replyUpdate(ReplyVO vo) throws Exception {
        logger.info("replyUpdate");
        try {
            replyService.updateReply(vo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reply created successfully");
        } catch (Exception e) {
            logger.error("Error while writing reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reply.");
        }
    }

    //댓글 삭제
    @DeleteMapping(value = "/replyDelete")
    public ResponseEntity<String> replyDelete(ReplyVO vo) throws Exception {
        logger.info("replyDelete");
        try {
            replyService.deleteReply(vo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reply created successfully");
        } catch (Exception e) {
            logger.error("Error while writing reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reply.");
        }
    }

    //첨부파일 다운로드
    @GetMapping(value = "/fileDown")
    public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
        logger.info("fileDown");
        Map<String, Object> resultMap = service.selectFileInfo(map);

        String storedFileName = FileUtils.filePath + resultMap.get("STORED_FILE_NAME");
        String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
        File file = new File(storedFileName);

        if (!file.exists()) {
            logger.error("Requested file does not exist: {}", storedFileName);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // MIME 타입 감지
        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // 기본값 설정
        }

        response.setContentType(mimeType); // 동적으로 설정된 MIME 타입
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" +
                URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20") + "\"");

        // 파일 데이터 전송
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("Error while downloading file: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

