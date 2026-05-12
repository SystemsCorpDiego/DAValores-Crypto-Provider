package com.davalores.crypto.provider.infra.ripio.adapter.in.dto.caas.api;

import lombok.Data;

@Data
public class BillingInfoDto {
    private Boolean condición_extranjero;
    private String cuit;
    private String apellidos;
    private String nombres;
    private String tipo_documento;
    private String num_documento;
    private String nacionalidad;
    private String fecha_nac; // "01/01/1980"
    private String fecha_alta; // "01/01/2023"
    private String fecha_baja;
    private Boolean es_pep;
}
