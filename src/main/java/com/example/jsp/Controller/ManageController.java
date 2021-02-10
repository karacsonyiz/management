package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.Session;
import com.example.jsp.Model.UserForm;
import com.example.jsp.Service.FormValidator;
import com.example.jsp.Service.UserRepositoryService;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
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
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ManageController {

    private final UserRepositoryService userRepositoryService;
    private final UserEntityRepository userEntityRepository;
    private final FormValidator formValidator;

    public ManageController(UserRepositoryService userRepositoryService, UserEntityRepository userEntityRepository, FormValidator formValidator) {
        this.userRepositoryService = userRepositoryService;
        this.userEntityRepository = userEntityRepository;
        this.formValidator = formValidator;
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage(@ModelAttribute("user") GeneratedUserEntity user, Model model, HttpSession httpSession, HttpServletResponse response) throws IOException {

        Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
        if (sessionBean == null) {
            response.sendRedirect("login");
            return null;
        }
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
     * @param formData incoming parameters from DataTable.
     * @return The UserEntities according to the current page.
     */
    @RequestMapping(value = "/getUsersForPage")
    public DataTable getUsersForPage(@RequestBody String formData) {
        Map<String, Integer> formDataMap = userRepositoryService.getDataFromParams(formData);
        Integer start = formDataMap.get("start");
        Integer length = formDataMap.get("length");
        Integer draw = formDataMap.get("draw");
        Pageable pageable = PageRequest.of(start / length, length);
        Page<GeneratedUserEntity> responseData = userEntityRepository.findAll(pageable);
        return new DataTable(draw, responseData.getTotalElements(), responseData.getTotalElements(), new ArrayList<>(), responseData.getContent(), start);
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<GeneratedUserEntity> getAllUsers() {
        return userRepositoryService.getAllUsers();
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
            formValidator.validateForm(userForm, errors);
            formValidator.checkConstraints(userForm, errors);
            if (!errors.hasErrors()) {
                user = userRepositoryService.matchFormDataToUserEntity(userForm);
                userRepositoryService.addUser(user, errors);
            }
        } catch (Exception e) {
            userRepositoryService.handleErrors(errors, user, e);
        }
        createErrorMessages(modelAndView, errors, httpSession, response);
        return modelAndView;
    }

    private void createErrorMessages(ModelAndView modelAndView, Errors errors, HttpSession httpSession, HttpServletResponse response) throws IOException {
        if (errors.hasErrors()) {
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
            sessionBean.setActionResponse("savesuccess");
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

    @RequestMapping(value = "/getUserTheme", method = RequestMethod.GET)
    public String getUserTheme(HttpSession session){
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        String name = sessionBean.getLogin().getUsername();
        return userRepositoryService.getUserTheme(name);
    }

    @RequestMapping(value = "/setUserTheme/{theme}", method = RequestMethod.GET)
    public void setUserTheme(HttpSession session,@PathVariable String theme){
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        String name = sessionBean.getLogin().getUsername();
        userRepositoryService.setUserTheme(name,theme);
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
     * @param formData incoming parameters from DataTable.
     * @return A dataTable consisting UserEntities and pageinformation according to the current page and criteria.
     */
    @RequestMapping(value = "/getUsersForPageByCriteria")
    public DataTable searchOnFieldForPage(@RequestBody String formData) {
        Map<String, String> params = convertFormDataToMap(formData);
        Map<String, String> criteria = getCriteriaParams(params);
        int start = Integer.parseInt(params.getOrDefault("start", "0"));
        int length = Integer.parseInt(params.getOrDefault("length", "10"));
        int draw = Integer.parseInt(params.getOrDefault("draw", "1"));
        Set<GeneratedUserEntity> userSet = userRepositoryService
                .getUsersForPageByCriteria(start, length, criteria, params.getOrDefault("condition", "Or"));
        if (userSet == null) {
            Pageable pageable = PageRequest.of(start / length, length);
            Page<GeneratedUserEntity> responseData = userEntityRepository.findAll(pageable);
            return new DataTable(draw, responseData.getTotalElements(), responseData.getTotalElements(), new ArrayList<>(), responseData.getContent(), start);
        }
        long userCount = userRepositoryService.countUsers();
        return new DataTable(draw, userCount, userCount, new ArrayList<>(), new ArrayList<>(userSet), start);
    }

    /**
     * This method creates a Map from parameter keys and values to pass the service's search function.
     *
     * @return A Hashmap that contains the key and value pairs of thr required criteria parameters.
     * @params Parameters for search criteria
     */
    private Map<String, String> getCriteriaParams(Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        result.put("userid", params.get("userid"));
        result.put("name", params.get("name"));
        result.put("email", params.get("email"));
        result.put("role", params.get("role"));
        result.put("orgs", params.get("orgs"));
        result.put("phone", params.get("phone"));
        result.put("address", params.get("address"));
        return result;
    }


    private Map<String, String> convertFormDataToMap(String formData) {
        return URLEncodedUtils.parse(formData, Charset.defaultCharset()).stream()
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
    }


}
