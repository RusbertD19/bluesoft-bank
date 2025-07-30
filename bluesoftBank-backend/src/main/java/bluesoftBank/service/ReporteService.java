package bluesoftBank.service;

import bluesoftBank.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private final TransaccionRepository transaccionRepository;

    public List<Object[]> clientesConMasTransacciones(int mes, int anio) {
        return transaccionRepository.findClientesConMasTransacciones(mes, anio);
    }

    public List<Object[]> clientesConRetirosFueraCiudad(int mes, int anio, Double montoMinimo) {
        return transaccionRepository.findClientesConRetirosFueraCiudad(mes, anio, montoMinimo);
    }

}
