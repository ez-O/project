package com.daelim.transactions.controller;

import com.daelim.transactions.dto.AttachDTO;
import com.daelim.transactions.dto.BoardDTO;
import com.daelim.transactions.dto.MemberDTO;
import com.daelim.transactions.service.BoardService;
import com.daelim.transactions.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    ServiceTest serviceTest;

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/main")
    public String goMain(Model model) {
        List<BoardDTO> boardList = boardService.getBoardList();
        List<AttachDTO> attachList = boardService.getAttachList();
        model.addAttribute("boardList", boardList);
        model.addAttribute("attachList", attachList);
        return "main";
    }


    @GetMapping(value = "/main/myPage.do")
    public String toMyPage(HttpServletRequest request, Model model) {
        MemberDTO member = commonSession(request);
        List<BoardDTO> boardList = boardService.getBoardList(member.getLoginId());
        List<AttachDTO> attachList = boardService.getAttachList(boardList);
        System.out.println(attachList.get(0));
        model.addAttribute("boardList", boardList);
        model.addAttribute("attachList", attachList);
        model.addAttribute("memNick",member.getNickName());
        model.addAttribute("memProfile",member.getProfile());
        return "/myPage/myPage";
    }

    @GetMapping(value="/main/myPage/profile.do")
    public String toProfile(HttpServletRequest request, Model model){
        MemberDTO member = commonSession(request);
        MemberDTO newMemberPw = new MemberDTO();
        model.addAttribute("memNick",member.getNickName());
        model.addAttribute("memProfile",member.getProfile());
        model.addAttribute("memName",member.getName());
        model.addAttribute("member",newMemberPw);
        System.out.println(member.getNickName());
        return "/myPage/profile";
    }


    @PostMapping(value="/profile/upload.do")
    public String uploadProfile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
        MemberDTO member = commonSession(request);
        System.out.println("member ="+member.getLoginId());
        boolean isChange = serviceTest.changProfile(member,file);

        return "redirect:/main/myPage.do";
    }

    @GetMapping(value="/main/product.do")
    public String product(Model model){
        BoardDTO board = new BoardDTO();
        model.addAttribute("board",board);
        return "/productUpload";
    }

    /**
     * 검색할 경우 동작
     * @param product ->검색 값
     * @return html 파일
     */
    @GetMapping(value = "/search")
    public String searchProduct(String product){
        System.out.println("검색 값 : " +product);
        return "/search";
    }

    @GetMapping(value="/main/buyList")
    public String showBuyList(){
        return "/buyList";
    }

    /**
     * 카테고리 클릭 시 동작
     * @param categoriesProduct -> 카테고리
     * @return html 파일
     */
    @GetMapping(value = "/categories")
    public String categoriesProduct(String categoriesProduct){
        System.out.println("카테고리 선택 값 : " +categoriesProduct);
        return "/search";
    }

    @PostMapping(value = "/mainPaging")
    @ResponseBody
    public Object mainBoardListControl(@RequestBody Map<String,Integer> param){
        Map<String, Object> map = new HashMap<>();
        List<BoardDTO> listMore = boardService.getBoardList(param.get("Idx"));
        List<AttachDTO> attachMore = boardService.getAttachList(listMore);
        map.put("listMore", listMore);
        map.put("attachMore", attachMore);
        return map;
    }

    /**
     * 파일 업로드 인데 다른거로 할 예정
     * */
//    @PostMapping(value="/profile/upload.do")
//    public String uploadProfile(MultipartFile upload, HttpServletRequest request ){
//
//        String saveDir = request.getSession().getServletContext().getRealPath("/resources/static/images/profile");
//
//        File dir = new File(saveDir);
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//        if(!upload.isEmpty()){
//            String orifileName = upload.getOriginalFilename();
//            String ext = orifileName.substring(orifileName.lastIndexOf("."));
//
//            // 이름 값 변경을 위한 설정
//             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
//             int rand = (int)(Math.random()*1000);
//             // 파일 이름 변경
//             String reName = sdf.format(System.currentTimeMillis()) + "_" + rand + ext;
//             // 파일 저장
//             try {
//                 upload.transferTo(new File(saveDir + "/" + reName));
//             }
//             catch (IllegalStateException | IOException e) {
//                 e.printStackTrace();
//             }
//        }
//        return "redirect:/main/myPage";
//    }


    public MemberDTO commonSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("memId");
        System.out.println("로그인 아이디 : "+id);
        return serviceTest.getAllInfo(id);
    }
}
