package com.example.bank.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }

}
