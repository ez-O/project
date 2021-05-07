package com.daelim.transactions.controller;

import com.daelim.transactions.dto.AttachDTO;
import com.daelim.transactions.dto.BoardDTO;
import com.daelim.transactions.dto.BuyBoardDTO;
import com.daelim.transactions.dto.MemberDTO;
import com.daelim.transactions.service.BoardService;
import com.daelim.transactions.service.BuyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BuyBoardController {

    @Autowired
    BuyBoardService buyBoardService;

    @GetMapping(value="/main/buyList")
    public String showBuyList( Model model){
        List<BuyBoardDTO> boardList = buyBoardService.getAttachList();

        model.addAttribute("boardList", boardList);
        return "/buyList";
    }
}