package com.example.jsp;

import com.example.jsp.Model.Session;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    public AuditorAwareImpl() {
    }

    @Override
    public Optional<String> getCurrentAuditor() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        String userName = sessionBean.getLogin().getUsername();
        return Optional.of(Objects.requireNonNullElse(userName, "Guest"));
    }
}
