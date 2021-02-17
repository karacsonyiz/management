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
import org.springframework.data.domain.Sort;
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
     * @param formData incoming parameters from the request.
     * @return A DataTable consisting UserEntities and pageinformation according to the current page.
     */
    @RequestMapping(value = "/getUsersForPage")
    public DataTable getUsersForPage(@RequestBody String formData) {
        Map<String, String> formDataMap = convertFormDataToMap(formData);
        Map<String, String> criteria = getCriteriaParams(formDataMap);
        int start = Integer.parseInt(formDataMap.getOrDefault("start", "0"));
        int draw = Integer.parseInt(formDataMap.get("draw"));
        String direction = formDataMap.getOrDefault("order[0][dir]", "asc");
        String orderBy = formDataMap.getOrDefault("columns[" + formDataMap.getOrDefault("order[0][column]", "0") + "][data]", "userid");
        orderBy = validateOrder(orderBy);
        Pageable pageable = makePageAbleFromData(formDataMap);
        Page<GeneratedUserEntity> response = userRepositoryService.getUsersForPageByCriteria2(criteria,
                criteria.getOrDefault("condition", "Or"),pageable,direction,orderBy);
        if (response == null) {
            Page<GeneratedUserEntity> responseData = userEntityRepository.findAll(pageable);
            return new DataTable(draw, responseData.getTotalElements(), responseData.getTotalElements(), new ArrayList<>(), responseData.getContent(), start);
        }
        return new DataTable(draw, response.getTotalElements(), response.getTotalElements(), new ArrayList<>(), response.getContent(), start);
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
    public String getUserTheme(HttpSession session) {
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        String name = sessionBean.getLogin().getUsername();
        return userRepositoryService.getUserTheme(name);
    }

    @RequestMapping(value = "/setUserTheme/{theme}", method = RequestMethod.GET)
    public void setUserTheme(HttpSession session, @PathVariable String theme) {
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        String name = sessionBean.getLogin().getUsername();
        userRepositoryService.setUserTheme(name, theme);
    }

    @RequestMapping(value = "/resetActionMessage", method = RequestMethod.GET)
    public void resetActionMessage(HttpSession session) {
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        sessionBean.setActionMessage("display:none;");
        session.setAttribute("sessionBean", sessionBean);
    }

    /**
     * This method creates a Map from parameter keys and values to pass the service's search function.
     *
     * @return A Hashmap that contains the key and value pairs of thr required criteria parameters.
     * @params Parameters for search criteria
     */
    private Map<String, String> getCriteriaParams(Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        result.put("userid", params.getOrDefault("columns[0][search][value]", ""));
        result.put("name", params.getOrDefault("columns[1][search][value]", ""));
        result.put("email", params.getOrDefault("columns[2][search][value]", ""));
        result.put("address", params.getOrDefault("columns[3][search][value]", ""));
        result.put("phone", params.getOrDefault("columns[4][search][value]", ""));
        result.put("role", params.getOrDefault("columns[5][search][value]", ""));
        result.put("orgs", params.getOrDefault("columns[6][search][value]", ""));
        return result;
    }

    /**
     * This method converts formData from String format to a hashMap.
     *
     * @param formData The incoming data in String format.
     * @return A Map that contains formData key-value pairs.
     */
    private Map<String, String> convertFormDataToMap(String formData) {
        return URLEncodedUtils.parse(formData, Charset.defaultCharset()).stream()
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
    }

    /**
     * This method makes a Pageable class from form data.
     *
     * @param formDataMap A map that contains the necessary data
     * @return A Pageable class that can be used for querying data.
     */
    private Pageable makePageAbleFromData(Map<String, String> formDataMap) {
        int start = Integer.parseInt(formDataMap.getOrDefault("start", "1"));
        int length = Integer.parseInt(formDataMap.getOrDefault("length", "10"));
        String direction = formDataMap.getOrDefault("order[0][dir]", "asc");
        String orderByColumnNum = formDataMap.getOrDefault("order[0][column]", "0");
        String orderBy = formDataMap.getOrDefault("columns[" + orderByColumnNum + "][data]", "userid");
        String validatedOrderBy = validateOrder(orderBy);
        Sort.Direction dir = direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(start / length, length, dir, validatedOrderBy);
    }

    /**
     * This method validates the possible malfunctioning data.
     *
     * @param orderBy A possible malfunctioning parameter.
     * @return A valid parameter.
     */
    private String validateOrder(String orderBy) {
        if (orderBy.equals("function") || orderBy.equals("8") || orderBy.equals("7")) {
            return "userid";
        }
        return orderBy;
    }
}
