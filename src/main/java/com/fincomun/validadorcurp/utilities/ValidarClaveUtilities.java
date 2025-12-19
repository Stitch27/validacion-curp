package com.fincomun.validadorcurp.utilities;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.fincomun.validadorcurp.model.ClaveModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

@Slf4j
@Component
public class ValidarClaveUtilities {

    public Integer validar_proceso(JSONObject solicitud) {

        if (solicitud.has("proceso")) {

            if (!(solicitud.get("proceso") + "").trim().isEmpty()) {

                if (StringUtils.isNumeric((solicitud.get("proceso") + "").trim())) {

                    switch (Integer.parseInt((solicitud.get("proceso") + "").trim())) {

                        case 10:
                            return 10;

                        default:
                            return 4;

                    }

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer validar_portal(JSONObject solicitud) {

        if (solicitud.has("datos_portal")) {
            
            JSONObject portal = new JSONObject(solicitud.get("datos_portal").toString());

            if (portal.has("identificador")) {

                if (!(portal.get("identificador") + "").trim().isEmpty()) {

                    if (StringUtils.isNumeric((portal.get("identificador") + "").trim())) {

                        if (portal.has("nombre")) {

                            if (!(portal.get("nombre") + "").trim().isEmpty()) {

                                return 0;

                            } else {

                                return 6;

                            }

                        } else {

                            return 5;

                        }

                    } else {

                        return 4;

                    }

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }

    public Integer validar_cliente(JSONObject solicitud) {
        

        if (solicitud.has("datos_cliente")) {
            
            JSONObject cliente = new JSONObject(solicitud.get("datos_cliente").toString());

            if (cliente.has("clave_unica")) {

                if (!(cliente.get("clave_unica") + "").trim().isEmpty()) {

                    return 0;

                } else {

                    return 3;

                }

            } else {

                return 2;

            }

        } else {

            return 1;

        }

    }


    public Integer validar_peticiones(Integer permitidas, Integer realizadas) {

        realizadas = realizadas + 1;

        if (realizadas <= permitidas) {

            return 0;

        } else {

            return 1;

        }

    }

}
