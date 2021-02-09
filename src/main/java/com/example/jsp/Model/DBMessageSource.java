package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.LanguageEntity;
import com.example.jsp.GeneratedEntityRepository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component("messageSource")
public class DBMessageSource implements MessageSource {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        LanguageEntity message = languageRepository.findByKeyAndLocale(code, locale.getLanguage());
        if (message == null) {
            return defaultMessage;
        }
        return message.getContent();
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        LanguageEntity message = languageRepository.findByKeyAndLocale(code, locale.getLanguage());
        if (message == null) {
            return code;
        }
        return message.getContent();
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        try{
            return Objects.requireNonNull(resolvable.getCodes())[resolvable.getCodes().length-1];
        } catch (IndexOutOfBoundsException | NoSuchMessageException e){
            return resolvable.getDefaultMessage();
        }
    }
}
