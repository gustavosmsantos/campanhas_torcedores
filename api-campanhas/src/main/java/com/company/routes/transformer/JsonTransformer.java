package com.company.routes.transformer;

import spark.ResponseTransformer;

import static com.company.utils.ApplicationUtils.mapper;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) throws Exception {
        return mapper.writeValueAsString(model);
    }

}
