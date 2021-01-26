package com.example.jsp;

import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {



    public AuditorAwareImpl() {
    }

    @Override
    public Optional<String> getCurrentAuditor() {
            return Optional.of("Guest");
    }
}
