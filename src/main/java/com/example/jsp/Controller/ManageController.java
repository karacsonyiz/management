package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.Session;
import com.example.jsp.Model.UserForm;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
public class ManageController {

    private final UserRepositoryService userRepositoryService;
    private final UserEntityRepository userEntityRepository;

    public ManageController(UserRepositoryService userRepositoryService,UserEntityRepository userEntityRepository) {
        this.userRepositoryService = userRepositoryService;
        this.userEntityRepository = userEntityRepository;
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage(@ModelAttribute("user") GeneratedUserEntity user, Model model, HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView("manage");
        Object name = httpSession.getAttribute("name");
        model.addAttribute("name", name);
        modelAndView.addObject("userTableStyle", "display:none;");
        return modelAndView;
    }

    /**
     * This method is the backend implementation of the jQuery DataTable paging functionality.
     * Searches for the users to display on the current page.
     *
     * @param draw Can be true(1) or false(0), draws the DataTable if true. (default true)
     * @param start The starting number of actual page.
     * @param length The number of entries to display on one page.
     * @return The UserEntities according to the current page.
     */
    @RequestMapping(value = "/getUsersForPage")
    public DataTable getUsersForPage(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length) {
        Pageable pageable = PageRequest.of(start / length, length);
        Page<GeneratedUserEntity> responseData = userEntityRepository.findAll(pageable);
        return new DataTable(draw,responseData.getTotalElements(),responseData.getTotalElements(),new ArrayList<>(),responseData.getContent(),start);
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable String id) {
        userRepositoryService.deleteUser(Integer.parseInt(id));
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public GeneratedUserEntity getUser(@PathVariable String id) {
        return userRepositoryService.findUserById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/countusers", method = RequestMethod.GET)
    public long countusers() {
        return userRepositoryService.countUsers();
    }

    @RequestMapping(value = "/addOrgs/{id}", method = RequestMethod.POST)
    public void addOrgs(@RequestBody Map<String, List<String>> orgs, @PathVariable String id) {
        List<String> orgValues = orgs.get("org");
        if (orgValues.size() != 0) {
            userRepositoryService.addOrgs(orgValues, Integer.parseInt(id));
        }
    }

    @Transactional
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("user") UserForm userForm, HttpServletResponse response, Errors errors, HttpSession httpSession) throws IOException {
        ModelAndView modelAndView = new ModelAndView("manage");
        GeneratedUserEntity user = new GeneratedUserEntity();
        try {
            user = userRepositoryService.matchFormDataToUserEntity(userForm);
            userRepositoryService.addUser(user, errors);
        } catch (Exception e) {
            userRepositoryService.handleErrors(errors, user, e);
        }
        createErrorMessages(modelAndView, errors, httpSession, response);
        return modelAndView;
    }

    private void createErrorMessages(ModelAndView modelAndView, Errors errors, HttpSession httpSession, HttpServletResponse response) throws IOException {
        if (errors.hasErrors()) {
            for(int i = 0;i<errors.getAllErrors().size();i++){
                errors.getAllErrors().get(i).getCodes()[i] = errors.getAllErrors().get(i).getDefaultMessage();
            }
            ModelMap modelMap = new ModelMap()
                    .addAttribute("userTableStyle", "display:block;")
                    .addAttribute("errorMsg", "Error: " + errors.getAllErrors().get(0).getDefaultMessage());
            modelAndView.addAllObjects(modelMap);
            Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
            sessionBean.setActionMessage("display:none;");
            httpSession.setAttribute("sessionBean", sessionBean);
        } else {
            modelAndView.addObject("userTableStyle", "display:none;");
            Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
            sessionBean.setActionMessage("display:block;color:green;");
            httpSession.setAttribute("sessionBean", sessionBean);
            response.sendRedirect("/manage");
        }
    }

    @RequestMapping(value = "/deleteOrgForUser/{id}", method = RequestMethod.POST)
    public void deleteOrgForUser(@RequestBody Map<String, List<String>> orgs, @PathVariable String id) {
        List<String> orgNames = orgs.get("orgs");
        userRepositoryService.deleteOrgs(orgNames, Integer.parseInt(id));
    }

    @RequestMapping(value = "/resetActionMessage", method = RequestMethod.GET)
    public void resetActionMessage(HttpSession session) {
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        sessionBean.setActionMessage("display:none;");
        session.setAttribute("sessionBean", sessionBean);
    }
    /**
     * This method returns a list of Users by given field,value and current page.
     *
     * @param draw Can be true(1) or false(0), draws the DataTable if true. (default true)
     * @param start The starting number of actual page.
     * @param length The number of entries to display on one page.
     * @return A dataTable consisting UserEntities and pageinformation according to the current page and criteria.
     */
    @RequestMapping(value = "/getUsersForPageByCriteria")
    public DataTable searchOnFieldForPage(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length, @RequestParam("userid") String userid,@RequestParam("name") String name,
                                          @RequestParam("email") String email,@RequestParam("role") String role, @RequestParam("orgs") String orgs, @RequestParam("phone") String phone,@RequestParam("address") String address,
                                          @RequestParam("condition") String condition) {
        Map<String,String> params = getRequestParams(userid,name,email,role,orgs,phone,address);
        Set<GeneratedUserEntity> userSet = userRepositoryService.getUsersForPageByCriteria(start,length,params,condition);
        if(userSet == null){
            Pageable pageable = PageRequest.of(start / length, length);
            Page<GeneratedUserEntity> responseData = userEntityRepository.findAll(pageable);
            return new DataTable(draw,responseData.getTotalElements(),responseData.getTotalElements(),new ArrayList<>(),responseData.getContent(),start);
        }
        long userCount = userRepositoryService.countUsers();
        return new DataTable(draw,userCount,userCount,new ArrayList<>(),new ArrayList<>(userSet),start);
    }

    /**
     * This method creates a Map from parameter keys and values to pass the service's search function.
     * @params Parameters for search criteria
     * @return A Hashmap that contains the key and value pairs of request parameters.
     */
    private Map<String,String> getRequestParams(String userid,String name,String email,String role,String orgs,String phone,String address){
        Map<String,String> result = new HashMap<>();
        result.put("userid",userid);
        result.put("name",name);
        result.put("email",email);
        result.put("role",role);
        result.put("orgs",orgs);
        result.put("phone",phone);
        result.put("address",address);
        return result;
    }
}
