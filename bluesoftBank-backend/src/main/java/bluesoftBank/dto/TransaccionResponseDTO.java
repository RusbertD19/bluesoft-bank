package bluesoftBank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransaccionResponseDTO {
    private LocalDateTime fecha;
    private Double valor;
    private String tipo;
    private String ciudadTransaccion;

    public TransaccionResponseDTO(LocalDateTime fecha, Double valor, String tipo, String ciudadTransaccion) {
    }
}
