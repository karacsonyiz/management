package com.example.jsp.Controller;

import com.example.jsp.Model.Login;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    private final UserRepositoryService userRepositoryService;

    public LoginController(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping(value = {"/"})
    public ModelAndView redirectLoginOrHello(HttpSession httpSession, HttpServletResponse response) throws IOException {
        Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
        if (sessionBean == null) {
            response.sendRedirect("login");
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }

    @GetMapping(value = {"/login"})
    public ModelAndView showLogin(HttpSession httpSession, HttpServletResponse response) throws IOException {
        Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
        if (sessionBean != null) {
            if (sessionBean.getLogin() != null) {
                response.sendRedirect("hello");
                return null;
            }
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }

    /**
     * This method checks if session exists, if yes, invalidates it.
     * Then checks it again, and if it is successfully invalidated, throws an IllegalStateException.
     * @return Returns an OK status if the invalidation is successful,and ERROR status if not.
     */
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public ResponseEntity<String> logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        if (session.getAttributeNames() != null) {
            session.removeAttribute("sessionBean");
            session.invalidate();
        }
        try {
            session.getAttributeNames();
        } catch (IllegalStateException  e){
            response.sendRedirect("login");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ModelAndView hello(@ModelAttribute("login") Login login, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = null;
        if (!userRepositoryService.validateUserForLogin(login, result)) {
            modelAndView = new ModelAndView("login");
        } else {
            Session sessionBean = new Session(login);
            request.getSession().setAttribute("sessionBean", sessionBean);
            response.sendRedirect("hello");
        }
        return modelAndView;
    }
}
