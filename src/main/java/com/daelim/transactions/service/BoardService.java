package com.daelim.transactions.service;

import com.daelim.transactions.dto.AttachDTO;
import com.daelim.transactions.dto.BoardDTO;
import com.daelim.transactions.dto.BuyBoardDTO;
import com.daelim.transactions.dto.SaleLikeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    public boolean registerBoard(BoardDTO board);
    public boolean registerBoard(BoardDTO board, MultipartFile[] files);
    public List<BoardDTO> getBoardList();
    /**
     *  모든 게시판 찾기
     * */
    public List<AttachDTO> getAttachList( );
    public List<AttachDTO> getAttachList(long idx);
    /**
     * 아이디로 게시판 찾기
     * */
    public List<BoardDTO> getBoardList(String loginId);

    /**
     *  보더idx로 이미지 찾아오기
     * */
    public List<AttachDTO> getAttachList(List<BoardDTO> boardList);
    public List<BoardDTO> getBoardList(int count);

    /**
     * getSearchBoardList -> 검색값에 따라 게시글 검색
     * getCategoryBoardList -> 카테고리 값에 따라 게시글 검색
     */
    public List<BoardDTO> getSearchBoardList(BoardDTO params);
    public List<BoardDTO> getCategoryBoardList(BoardDTO params);
    public int getSearchCount(BoardDTO params);


    /**
     *  상세페이지 가져오기
     * */
    public BoardDTO getBoardDetail(long idx);

    /**
     * 마이페이지 위시리스트(찜 한 팔아요 게시글)
     */
    public List<BoardDTO> getLikeBoardList(String loginId);
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    /**
     * 마이페이지 게시글 삭제
     */
    public void removeBoardList(List<Integer> params);
<<<<<<< Updated upstream

    public int getCommentCount();
=======


    public int getCommentCount();

>>>>>>> Stashed changes
}
