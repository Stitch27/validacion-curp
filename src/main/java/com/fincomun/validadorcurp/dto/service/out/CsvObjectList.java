package com.fincomun.validadorcurp.dto.service.out;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.opencsv.exceptions.CsvException;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
public class CsvObjectList<E> extends SimpleResponse {

    private List<E> objectsLoaded = new ArrayList<>();
    private Integer registros = 0 ;

    private List<CsvException> failed = new ArrayList<>();
}
