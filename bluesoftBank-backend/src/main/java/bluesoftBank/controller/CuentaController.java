package bluesoftBank.controller;

import bluesoftBank.entity.Transaccion;
import bluesoftBank.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {
    private final CuentaService cuentaService;

    @GetMapping("/{numero}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable String numero) {
        return ResponseEntity.ok(cuentaService.consultarSaldo(numero));
    }

    @GetMapping("/{numero}/transacciones/recientes")
    public ResponseEntity<List<Transaccion>> obtenerTransaccionesRecientes(
            @PathVariable String numero,
            @RequestParam(defaultValue = "10") int cantidad) {
        return ResponseEntity.ok(cuentaService.obtenerUltimasTransacciones(numero, cantidad));
    }

    @PostMapping("/{numero}/consignar")
    public ResponseEntity<Transaccion> consignar(
            @PathVariable String numero,
            @RequestParam Double valor,
            @RequestParam String ciudad) {
        return ResponseEntity.ok(cuentaService.consignar(numero, valor, ciudad));
    }

    @PostMapping("/{numero}/retirar")
    public ResponseEntity<Transaccion> retirar(
            @PathVariable String numero,
            @RequestParam Double valor,
            @RequestParam String ciudad) {
        return ResponseEntity.ok(cuentaService.retirar(numero, valor, ciudad));
    }





}
