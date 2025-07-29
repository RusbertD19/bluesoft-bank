package bluesoftBank.controller;

import bluesoftBank.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final ReporteService reporteService;

    @GetMapping("/transacciones-mes")
    public ResponseEntity<List<Object[]>> clientesConMasTransacciones(
            @RequestParam int mes,
            @RequestParam int anio) {
        return ResponseEntity.ok(reporteService.clientesConMasTransacciones(mes, anio));
    }

    @GetMapping("/retiros-fuera-ciudad")
    public ResponseEntity<List<Object[]>> clientesConRetirosFueraCiudad(
            @RequestParam(defaultValue = "1000000") Double montoMinimo) {
        return ResponseEntity.ok(reporteService.clientesConRetirosFueraCiudad(montoMinimo));
    }
}
