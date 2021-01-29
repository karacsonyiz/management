package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.LanguageEntity;
import com.example.jsp.GeneratedEntityRepository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component("messageSource")
public class DBMessageSource extends AbstractMessageSource {

    @Autowired
    private LanguageRepository languageRepository;

    private static final String DEFAULT_LOCALE_CODE = "hu";

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {
        LanguageEntity message = languageRepository.findByKeyAndLocale(key, locale.getLanguage());
        if (message == null) {
            return null;
        }
        return new MessageFormat(message.getContent(), locale);
    }
}
