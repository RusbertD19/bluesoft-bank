import { Component } from '@angular/core';
import { CuentaService } from '../cuenta-service';

@Component({
  selector: 'app-transacciones',
  templateUrl: './transacciones.component.html',
  styleUrls: ['./transacciones.component.scss']
})
export class TransaccionesComponent {
  numeroCuenta: string = '';
  tipoTransaccion: string = 'CONSIGNACION';
  valor: number = 0;
  ciudad: string = '';
  mensaje: string = '';
  error: string = '';

  constructor(private cuentaService: CuentaService) {}

realizarTransaccion() {
  this.mensaje = '';
  this.error = '';

  if (this.valor <= 0) {
    this.error = 'El valor debe ser mayor que cero.';
    return;
  }

  if (!this.numeroCuenta || !this.ciudad) {
    this.error = 'Debe ingresar número de cuenta y ciudad.';
    return;
  }

  const manejarError = (err: any) => {
    if (err?.error?.message) {
      this.error = err.error.message;
    } else {
      this.error = 'Ocurrió un error inesperado.';
    }
    console.error('Error:', err);
  };

  if (this.tipoTransaccion === 'CONSIGNACION') {
   this.cuentaService.consignar(this.numeroCuenta, this.valor, this.ciudad)
  .subscribe({
    next: (respuesta) => {
      console.log('cosignación exitosa:', respuesta);
      alert('cosignación exitosa');
        location.reload();
    },
    error: (err) => {
      console.error('Error al retirar:', err);
      alert('Error al retirar: ' + (err.error?.message || 'Error inesperado'));
    }
    });
  } else if (this.tipoTransaccion === 'RETIRO') {
    this.cuentaService.retirar(this.numeroCuenta, this.valor, this.ciudad)
  .subscribe({
    next: (respuesta) => {
      console.log('Retiro exitoso:', respuesta);
      alert('Retiro exitoso');
        location.reload();
    },
    error: (err) => {
      console.error('Error al retirar:', err);
      alert('Error al retirar: ' + (err.error?.message || 'Error inesperado'));
    }
    });
  }
}



  limpiarFormulario() {
    this.numeroCuenta = '';
    this.tipoTransaccion = 'CONSIGNACION';
    this.valor = 0;
    this.ciudad = '';
  }
}
