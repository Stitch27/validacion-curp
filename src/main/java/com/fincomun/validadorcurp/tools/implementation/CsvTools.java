package com.fincomun.validadorcurp.tools.implementation;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.dto.service.out.CsvObjectList;
import com.fincomun.validadorcurp.tools.definition.ICsvTools;
import com.fincomun.validadorcurp.tools.definition.IFechasTool;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
@Slf4j
public class CsvTools implements ICsvTools {


    private final IFechasTool fechasTool;

    @Autowired
    public CsvTools(IFechasTool fechasTool) {
        this.fechasTool = fechasTool;
    }

    @Override
    public <T> SimpleResponse

    lisToCsv(List<T> objects, Class<T> itemClass, String path, boolean append) {
        SimpleResponse response = new SimpleResponse();
        try {
            FileWriter fileWriter = new FileWriter(path, append);

            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(itemClass);


            StatefulBeanToCsv  beanToCsv = new StatefulBeanToCsvBuilder<T>(fileWriter)
                                                    .withMappingStrategy(mappingStrategy)
                                                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                                                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                                                    .build();
            beanToCsv.write(objects);
            fileWriter.close();
            response.setCodigo(0);
            response.setMensaje("EXITO");
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            response.setCodigo(-4);
            response.setMensaje("El archivo no se pudo generar correctamente");
        }

        return response;
    }

    @Override
    public <T> CsvObjectList<T> csvToListObject(String path, Integer skipLines, Class<T> itemClass) {
        CsvObjectList<T> response = new CsvObjectList();
        try{
            File file = new File(path);

            if(file.exists() && file.isFile()){
                log.info("El archivo existe");
                Reader reader = new BufferedReader(new FileReader(file));
                if(reader != null){

                    CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                            .withSkipLines(skipLines)
                            .withThrowExceptions(false)
                            .withType(itemClass).build();

                    List<T> listObtained = csvToBean.parse();
                    if(listObtained != null && !listObtained.isEmpty()){
                        response.setObjectsLoaded(listObtained);
                        response.setRegistros(listObtained.size());
                        response.setCodigo(0);
                        response.setMensaje("Información cargada correctamente");
                    }else{
                        response.setCodigo(3);
                        response.setMensaje("No existen registros en el archivo indicado");
                    }

                    response.setFailed(csvToBean.getCapturedExceptions());
                }else{
                    response.setCodigo(6);
                    response.setMensaje("El archivo no tiene los permisos suficientes");
                }
            }else {
                response.setCodigo(4);
                response.setMensaje("El archivo no existe en el path indicado");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            response.setCodigo(-5);
            response.setMensaje("El archivo no se pudo cargar");
        }
        return response;
    }
}
