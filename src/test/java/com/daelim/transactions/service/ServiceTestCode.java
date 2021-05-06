package com.daelim.transactions.service;

import com.daelim.transactions.configuration.MvcConfiguration;
import com.daelim.transactions.dto.AttachDTO;
import com.daelim.transactions.dto.BoardDTO;
import com.daelim.transactions.dto.MemberDTO;
import com.daelim.transactions.mapper.DaoAttach;
import com.daelim.transactions.mapper.DaoBoard;
import com.daelim.transactions.utils.CryptoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@SpringBootTest
public class ServiceTestCode {

    @Autowired
    ServiceTest serviceTest;

    @Autowired
    MailService mailService;

    @Autowired
    MvcConfiguration mockMvc;

    @Autowired
    DaoBoard daoBoard;

    @Autowired
    DaoAttach daoAttach;
    @Autowired
    BoardService boardService;

//    @Test
//    void OptEmpInfoTest(){
//        Optional<EmployeeDTO> optEmpInfo = serviceTest.optToEmpInfo("id");
//
//        if(!optEmpInfo.isPresent()){
//            // id가 존재하지 않을 시
//            System.out.println("비었다..");
//        }else{
//            EmployeeDTO empInfo = optEmpInfo.get();
//            System.out.println(empInfo.getLoginID());
//
//        }
//    }

    @Test
    void insertMemberTest() throws NoSuchAlgorithmException {
        MemberDTO member = new MemberDTO();
        member.setLoginId("hello12");
        member.setLoginPw("world");
        member.setName("김태양");
        member.setEmail("helloWorld@naver.com");
        member.setNickName("태비서");
        CryptoUtil cryptoUtil = new CryptoUtil();
        String enPass = cryptoUtil.sha256(member.getLoginPw());
        member.setLoginPw(enPass);
        int optMember = serviceTest.memberInsert(member);
    }
    @Test
    public void mailTest(){
        mailService.idSearch("explore2012@naver.com", "dkandkdlel123");
    }

    @Test
    public void getFindId(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("김태양");
        memberDTO.setEmail("212112");

        Optional<MemberDTO> member = Optional.ofNullable(serviceTest.getFindId(memberDTO));
        if(member.isEmpty()){
            // id가 존재하지 않을 시
            System.out.println("비었다..");
        }else{
            MemberDTO empInfo = member.get();
            System.out.println(empInfo.getName());
        }

    }
    @Test
    public void getRandom(){
        serviceTest.putRandomPass();
    }

    @Test
    public void getProfile(){
        MemberDTO memberDTO =serviceTest.getAllInfo("rlaxodid123");
        System.out.println(memberDTO.getProfile());
        System.out.println(memberDTO.getName());
    }

    @Test
    public void changeProfile(){
        String fileDir ="/tmp";
        String fileName = "test.txt";
        String fileFullPath = String.format("%s/%s",fileDir,fileName);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("test");
        MultipartFile multipartFile = new MockMultipartFile(fileFullPath, fileName, null, "hello file".getBytes());
        boolean hi = serviceTest.changProfile(memberDTO,multipartFile);
        System.out.println(hi);
    }

    @Test
    public void getBoardList(){
        int boardTotalCount = daoBoard.selectBoardTotalCount();
        System.out.println(boardTotalCount);
        if (boardTotalCount > 0) {
            List<BoardDTO> boardList  = daoBoard.selectBoardList(5);

            if (CollectionUtils.isEmpty(boardList) == false) {
                for (BoardDTO board : boardList) {
                    System.out.println("=========================");
                    System.out.println(board.getTitle());
                    System.out.println(board.getContents());
                    System.out.println(board.getLoginId());
                    System.out.println("=========================");
                }
            }
        }
    }


    @Test
    public void getAttachList(){
        int attachTotalCount = daoAttach.selectAttachTotalCount();
        System.out.println(attachTotalCount);
        List<BoardDTO> boardList = Collections.emptyList();
        int boardIdx = 0;
        int boardTotalCount = daoBoard.selectBoardTotalCount();
        if (boardTotalCount > 0) {
            boardList = daoBoard.selectBoardList(5);
            for(int i=0; i<boardList.size(); i++){
                boardIdx=boardList.get(i).getBoardIdx();
                if (attachTotalCount > 0) {
                    List<AttachDTO> attachList  = daoAttach.selectAttachList();

                    if (CollectionUtils.isEmpty(attachList) == false) {
                        for (AttachDTO attach : attachList) {
                            System.out.println("=========================");
                            System.out.println(attach.getIdx());
                            System.out.println(attach.getBoardIdx());
                            System.out.println(attach.getSaveName());
                            System.out.println("=========================");
                        }
                    }
                }
            }


        }

    }
}
