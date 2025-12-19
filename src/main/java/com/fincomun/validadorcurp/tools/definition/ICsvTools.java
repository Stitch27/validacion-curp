package com.fincomun.validadorcurp.tools.definition;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.dto.service.out.CsvObjectList;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ICsvTools {

    <T> SimpleResponse lisToCsv (@NotEmpty List<T> objects, @NotNull Class<T> itemClass, @NotBlank String path , boolean append);

    <T> CsvObjectList<T> csvToListObject (@NotBlank String path, @Min(0) Integer skipLines, @NotNull Class<T> itemClass);


}
