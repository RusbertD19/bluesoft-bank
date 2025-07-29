package bluesoftBank.repository;

import bluesoftBank.entity.Transaccion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    // Método para obtener transacciones por número de cuenta ordenadas por fecha descendente
    List<Transaccion> findByCuentaNumeroOrderByFechaDesc(String numeroCuenta);

    // Método para obtener las últimas transacciones con paginación
    List<Transaccion> findByCuentaNumeroOrderByFechaDesc(String numeroCuenta, Pageable pageable);

    default List<Transaccion> findTopByCuentaNumeroOrderByFechaDesc(String numeroCuenta, int cantidad) {
        return findByCuentaNumeroOrderByFechaDesc(numeroCuenta, PageRequest.of(0, cantidad));
    }

    // Método para obtener transacciones por cliente, mes y año
    @Query("SELECT t FROM Transaccion t WHERE t.cuenta.cliente.id = :clienteId AND MONTH(t.fecha) = :mes AND YEAR(t.fecha) = :anio ORDER BY t.fecha DESC")
    List<Transaccion> findTransaccionesByClienteAndMes(
            @Param("clienteId") Long clienteId,
            @Param("mes") int mes,
            @Param("anio") int anio);

    // Método para obtener clientes con más transacciones en un mes específico
    @Query("SELECT c.nombre, COUNT(t) as numTransacciones " +
            "FROM Transaccion t JOIN t.cuenta cu JOIN cu.cliente c " +
            "WHERE MONTH(t.fecha) = :mes AND YEAR(t.fecha) = :anio " +
            "GROUP BY c.id, c.nombre " +
            "ORDER BY numTransacciones DESC")
    List<Object[]> findClientesConMasTransacciones(
            @Param("mes") int mes,
            @Param("anio") int anio);

    // Método para obtener clientes con retiros fuera de su ciudad de origen
    @Query("SELECT c.nombre, SUM(t.valor) as totalRetirado, c.ciudadOrigen, t.ciudadTransaccion " +
            "FROM Transaccion t JOIN t.cuenta cu JOIN cu.cliente c " +
            "WHERE t.tipo = 'RETIRO' AND t.ciudadTransaccion <> c.ciudadOrigen " +
            "GROUP BY c.id, c.nombre, c.ciudadOrigen, t.ciudadTransaccion " +
            "HAVING SUM(t.valor) > :montoMinimo " +
            "ORDER BY totalRetirado DESC")
    List<Object[]> findClientesConRetirosFueraCiudad(
            @Param("montoMinimo") Double montoMinimo);
}
