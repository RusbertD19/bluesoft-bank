package bluesoftBank.service;

import bluesoftBank.entity.Cuenta;
import bluesoftBank.entity.Transaccion;
import bluesoftBank.repository.CuentaRepository;
import bluesoftBank.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    public Transaccion consignar(String numeroCuenta, Double valor, String ciudad) {
        Cuenta cuenta = cuentaRepository.findWithLockingByNumero(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setSaldo(cuenta.getSaldo() + valor);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setValor(valor);
        transaccion.setTipo("CONSIGNACION");
        transaccion.setCiudadTransaccion(ciudad);
        transaccion.setCuenta(cuenta);

        return transaccionRepository.save(transaccion);
    }

    public Transaccion retirar(String numeroCuenta, Double valor, String ciudad) {
        Cuenta cuenta = cuentaRepository.findWithLockingByNumero(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (cuenta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo() - valor);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setValor(valor);
        transaccion.setTipo("RETIRO");
        transaccion.setCiudadTransaccion(ciudad);
        transaccion.setCuenta(cuenta);

        return transaccionRepository.save(transaccion);
    }

    public Double consultarSaldo(String numeroCuenta) {
        return cuentaRepository.findByNumero(numeroCuenta)
                .map(Cuenta::getSaldo)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    public List<Transaccion> obtenerUltimasTransacciones(String numeroCuenta, int cantidad) {
        return transaccionRepository.findTopByCuentaNumeroOrderByFechaDesc(numeroCuenta, cantidad);
    }
}
