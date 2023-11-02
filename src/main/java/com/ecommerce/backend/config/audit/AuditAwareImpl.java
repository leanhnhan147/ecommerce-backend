package com.ecommerce.backend.config.audit;

import com.ecommerce.backend.storage.entity.User;
import com.ecommerce.backend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        User user = JwtUtils.getPrincipal();
        if (user != null) {
            return Optional.ofNullable(user.getUsername());
        }
        return Optional.of("anonymous user");
    }
}
