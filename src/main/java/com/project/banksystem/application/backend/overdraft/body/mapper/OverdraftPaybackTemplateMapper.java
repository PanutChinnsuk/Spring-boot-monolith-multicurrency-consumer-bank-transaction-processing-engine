package com.project.banksystem.application.backend.overdraft.body.mapper;

import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import org.springframework.stereotype.Component;

@Component
public class OverdraftPaybackTemplateMapper {


    public OverdraftPaybackTemplateMapper() {

    }



    public OverdraftPaybackTemplate publicPaybackTemplate() {
        return OverdraftPaybackTemplate.builder().build();
    }
}
