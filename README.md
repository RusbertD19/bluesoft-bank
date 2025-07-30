Bluesoft Bank es un banco tradicional que se encarga de guardar el dinero de sus ahorradores. Ofrece dos tipos de cuenta:

Ahorros para personas naturales
Corrientes para empresas
Para cada cuenta se pueden realizar:

Consignaciones
Retiros
Servicios Disponibles
Consultar el saldo de la cuenta
Consultar los movimientos más recientes
Generar extractos mensuales
Reglas de Negocio
Una cuenta no puede tener un saldo negativo.
El saldo debe ser consistente frente a operaciones concurrentes.
Reportes en Tiempo Real
Listado de clientes con más transacciones en un mes específico
Clientes con retiros fuera de su ciudad por más de $1.000.000

Configuracion

-BD en Posgresql

SQL para probar

-- Tabla de Clientes
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('NATURAL', 'JURIDICA')),
    ciudad_origen VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Cuentas
CREATE TABLE cuenta (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.00,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('AHORROS', 'CORRIENTE')),
    cliente_id INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabla de Transacciones
CREATE TABLE transaccion (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valor DECIMAL(15, 2) NOT NULL,
    tipo VARCHAR(15) NOT NULL CHECK (tipo IN ('CONSIGNACION', 'RETIRO')),
    ciudad_transaccion VARCHAR(50) NOT NULL,
    cuenta_id INTEGER NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_transaccion_cuenta_id ON transaccion(cuenta_id);
CREATE INDEX idx_transaccion_fecha ON transaccion(fecha);
CREATE INDEX idx_cuenta_cliente_id ON cuenta(cliente_id);


-- Insertar clientes
INSERT INTO cliente (nombre, tipo, ciudad_origen) VALUES
('Juan Pérez', 'NATURAL', 'Bogotá'),
('Empresa XYZ S.A.S.', 'JURIDICA', 'Medellín'),
('María Gómez', 'NATURAL', 'Cali'),
('Carlos Rodríguez', 'NATURAL', 'Barranquilla'),
('Compañía ABC Ltda.', 'JURIDICA', 'Bucaramanga');

-- Insertar cuentas
INSERT INTO cuenta (numero, saldo, tipo, cliente_id) VALUES
('1001-123456', 1500000.00, 'AHORROS', 1),
('2001-654321', 5000000.00, 'CORRIENTE', 2),
('1002-987654', 800000.00, 'AHORROS', 3),
('1003-456789', 2500000.00, 'AHORROS', 4),
('2002-321654', 10000000.00, 'CORRIENTE', 5);

-- Insertar transacciones (últimos 2 meses)
INSERT INTO transaccion (fecha, valor, tipo, ciudad_transaccion, cuenta_id) VALUES
-- Transacciones para cuenta 1 (Juan Pérez)
(NOW() - INTERVAL '1 day', 500000.00, 'CONSIGNACION', 'Bogotá', 1),
(NOW() - INTERVAL '2 days', 200000.00, 'RETIRO', 'Bogotá', 1),
(NOW() - INTERVAL '15 days', 1000000.00, 'CONSIGNACION', 'Bogotá', 1),
(NOW() - INTERVAL '30 days', 300000.00, 'RETIRO', 'Medellín', 1),
(NOW() - INTERVAL '45 days', 700000.00, 'CONSIGNACION', 'Bogotá', 1),

-- Transacciones para cuenta 2 (Empresa XYZ)
(NOW() - INTERVAL '3 days', 2000000.00, 'CONSIGNACION', 'Medellín', 2),
(NOW() - INTERVAL '5 days', 1500000.00, 'RETIRO', 'Bogotá', 2),
(NOW() - INTERVAL '10 days', 3000000.00, 'CONSIGNACION', 'Medellín', 2),
(NOW() - INTERVAL '25 days', 1000000.00, 'RETIRO', 'Cali', 2),
(NOW() - INTERVAL '40 days', 4000000.00, 'CONSIGNACION', 'Medellín', 2),

-- Transacciones para cuenta 3 (María Gómez)
(NOW() - INTERVAL '2 days', 300000.00, 'CONSIGNACION', 'Cali', 3),
(NOW() - INTERVAL '7 days', 150000.00, 'RETIRO', 'Bogotá', 3),
(NOW() - INTERVAL '20 days', 500000.00, 'CONSIGNACION', 'Cali', 3),
(NOW() - INTERVAL '35 days', 200000.00, 'RETIRO', 'Medellín', 3),

-- Transacciones para cuenta 4 (Carlos Rodríguez)
(NOW() - INTERVAL '1 day', 1000000.00, 'CONSIGNACION', 'Barranquilla', 4),
(NOW() - INTERVAL '4 days', 500000.00, 'RETIRO', 'Cartagena', 4),
(NOW() - INTERVAL '18 days', 2000000.00, 'CONSIGNACION', 'Barranquilla', 4),
(NOW() - INTERVAL '32 days', 300000.00, 'RETIRO', 'Santa Marta', 4),

-- Transacciones para cuenta 5 (Compañía ABC)
(NOW() - INTERVAL '5 days', 3000000.00, 'CONSIGNACION', 'Bucaramanga', 5),
(NOW() - INTERVAL '12 days', 2000000.00, 'RETIRO', 'Bogotá', 5),
(NOW() - INTERVAL '22 days', 5000000.00, 'CONSIGNACION', 'Bucaramanga', 5),
(NOW() - INTERVAL '38 days', 1000000.00, 'RETIRO', 'Medellín', 5);
