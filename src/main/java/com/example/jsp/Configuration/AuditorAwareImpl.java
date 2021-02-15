package com.example.jsp.Configuration;

import com.example.jsp.Model.Session;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        if(sessionBean == null){
            return Optional.of("Guest");
        }
        if (sessionBean.getLogin() != null) {
            return Optional.of(sessionBean.getLogin().getUsername());
        }
        return Optional.of("Guest");
    }
}
