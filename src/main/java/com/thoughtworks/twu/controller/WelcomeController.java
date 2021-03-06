package com.thoughtworks.twu.controller;

import com.thoughtworks.twu.domain.Book;
import com.thoughtworks.twu.domain.User;
import com.thoughtworks.twu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes("user")
public class WelcomeController {

    @Autowired
    private UserService userService;

    @Autowired
    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(@ModelAttribute("user") User user) {
        ModelAndView welcome = new ModelAndView("welcome");

        List<Book> booksFromWantToReadList = userService.getBooksFromWantToReadList(user.getCasname());

        if (!booksFromWantToReadList.isEmpty()){
            welcome.addObject("books", booksFromWantToReadList);
        }
        else{
             welcome.addObject("bookNotFound","Sorry! You haven't added any book to your reading list");
        }

        return welcome;
    }
}
