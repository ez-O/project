package com.daelim.transactions.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.daelim.transactions.dto.*;
import com.daelim.transactions.dto.AttachDTO;
import com.daelim.transactions.dto.BoardDTO;
import com.daelim.transactions.dto.BuyBoardDTO;
import com.daelim.transactions.dto.BuyLikeDTO;
import com.daelim.transactions.mapper.DaoAttach;
import com.daelim.transactions.mapper.DaoBoard;
import com.daelim.transactions.mapper.DaoBuyBoard;
import com.daelim.transactions.mapper.DaoIttach;
import com.daelim.transactions.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor
public class BuyBoardServiceImpl implements BuyBoardService {

    private final DaoBuyBoard daoBuyBoard;

    private final FileUtils fileUtils;

    private final DaoIttach daoIttach;

    @Override
    public boolean registerBoard(BuyBoardDTO board) {
        return daoBuyBoard.insertBoard(board) == 1;  //DB에 들어갔다면 True
    }

    @Override
    public boolean registerBoard(BuyBoardDTO board, MultipartFile[] files) {
        int queryResult = 1;

        if (registerBoard(board) == false) {
            return false;
        }
        List<IttachDTO> fileList = fileUtils.uploadBoardFile(files, board.getBoardIdx());
        if (CollectionUtils.isEmpty(fileList) == false){
            queryResult = daoIttach.insertIttach(fileList);
            if (queryResult < 1 ) {
                queryResult = 0;
            }
        }

        return (queryResult > 0);
    }

    @Override
    public List<BuyBoardDTO> getBoardList() {
        List<BuyBoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = daoBuyBoard.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            boardList = daoBuyBoard.selectBoardList(0);
        }

        return boardList;
    }

    @Override
    public List<BuyBoardDTO> getBoardList(int count) {
        List<BuyBoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = daoBuyBoard.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            boardList = daoBuyBoard.selectBoardList(count);
        }

        return boardList;
    }

    @Override
    public List<BuyBoardDTO> getBoardList(String loginId) {
        List<BuyBoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = daoBuyBoard.selectBoardMemberCount(loginId);

        if (boardTotalCount > 0) {
            boardList = daoBuyBoard.selectBoardListById(loginId);
        }

        return boardList;
    }

    @Override
    public List<IttachDTO> getAttachList(List<BuyBoardDTO> boardList) {
        List<IttachDTO> ittachList = Collections.emptyList();

        int boardTotalCount = daoBuyBoard.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            ittachList = daoIttach.selectIttachList();
        }

        return ittachList;
    }

    @Override
    public List<BuyBoardDTO> getSearchBoardList(BuyBoardDTO params) {
        return null;
    }


    @Override
    public List<IttachDTO> getIttachList() {
        List<IttachDTO> ittachList = Collections.emptyList();

        int boardTotalCount = daoIttach.selectIttachTotalCount();

        if (boardTotalCount > 0) {
            ittachList = daoIttach.selectIttachList();
        }

        return ittachList;
    }
    /**
     *  모든 게시판 찾기
     * */
    public List<IttachDTO> getIttachList(List<BuyBoardDTO> boardList) {
        List<IttachDTO> ittachList = Collections.emptyList();

        int boardTotalCount = daoIttach.selectIttachIdxCount(boardList);

        if (boardTotalCount > 0) {
            ittachList = daoIttach.selectIttachListByBoardIdx(boardList);
            System.out.println(ittachList.get(0));
        }

        return ittachList;
    }

    @Override
    public List<BuyBoardDTO> getCategoryBoardList(BuyBoardDTO params) {
        List<BuyBoardDTO> boardList = Collections.emptyList();
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        map.put("searchType", 1);
        int boardTotalCount = daoBuyBoard.selectBoardTotalCount2(map);
        System.out.println("카테고리에 해당하는 수 : " + boardTotalCount);
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardTotalCount);

        params.setPaginationInfo(paginationInfo);
        if (boardTotalCount > 0) {
            boardList = daoBuyBoard.selectBoardListCategory(params);
        }

        return boardList;
    }

    @Override
    public BuyBoardDTO getBoardDetail(long idx) {

        return daoBuyBoard.selectBoardDetail(idx);
    }

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    @Override
    public List<BuyBoardDTO> getAttachList() {
        return null;
    }

<<<<<<< Updated upstream
    ;


    @Override
    public void addBoardViews(long idx) {
        daoBuyBoard.updateBoardViews(idx);
    }

    @Override
    public void addBuyLikes(BuyLikeDTO param) {
        daoBuyBoard.insertBuyLike(param);
    }

    @Override
    public void removeBuyLikes(long idx) {
        daoBuyBoard.deleteBuyLike(idx);
    }

    @Override
    public boolean getBuyLikes(long idx) {
        return (daoBuyBoard.selectBuyLike(idx) != null);
    }

=======
>>>>>>> Stashed changes

}

