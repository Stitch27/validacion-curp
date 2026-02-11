package com.fincomun.procesaarchivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fincomun.procesaarchivo.dto.RegistraArchivoDto;
import com.fincomun.procesaarchivo.dto.RegistraArchivoSPResponse;
import com.fincomun.procesaarchivo.repository.GuardaRegistroRenapo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GuardaRegistroCurp {

    @Autowired
    private GuardaRegistroRenapo guardaRegistroRenapo;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void guardaRegistroCurp(RegistraArchivoDto dto) {
        log.info("=============== GuardaRegistroCurp, Metodo: guardaRegistroCurp ===============");
        
        RegistraArchivoSPResponse response = guardaRegistroRenapo.guardarRegistroRenapo(dto);

        if (response.getCodigoOut() == -1) {
            log.error("Error al guardar registro: {}", response.getMensajeOut());
            throw new RuntimeException(response.getMensajeOut());
        }

        log.info("=============== finaliza: guardaRegistroCurp ===============");
    }
}
