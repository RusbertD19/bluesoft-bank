package bluesoftBank.dto;

import lombok.Data;

@Data
public class ExtractoRequestDTO {
    private String numeroCuenta;
    private int mes;
    private int anio;
}
