package com.petrushin.task3.service.parsers;

import java.util.List;

public interface Parser <T> {

    List<T> parse(String path);

}
