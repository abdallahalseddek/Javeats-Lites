package com.javaeat.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> T mapEntity(S source, Class<T> target) {
        return modelMapper.map(source, target);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> target) {
        return source
                .stream()
                .map(sourceItem -> modelMapper.map(sourceItem, target))
                .collect(Collectors.toList());
    }
}
