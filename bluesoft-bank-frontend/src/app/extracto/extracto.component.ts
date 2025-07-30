import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ExtractoService } from '../extracto.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-extracto',
  templateUrl: './extracto.component.html',
  styleUrls: ['./extracto.component.scss']
})
export class ExtractoComponent {
  numeroCuenta: string = '';
  mes: number = new Date().getMonth() + 1;
  anio: number = new Date().getFullYear();

  meses = [
    { valor: 1, nombre: 'Enero' },
    { valor: 2, nombre: 'Febrero' },
    { valor: 3, nombre: 'Marzo' },
    { valor: 4, nombre: 'Abril' },
    { valor: 5, nombre: 'Mayo' },
    { valor: 6, nombre: 'Junio' },
    { valor: 7, nombre: 'Julio' },
    { valor: 8, nombre: 'Agosto' },
    { valor: 9, nombre: 'Septiembre' },
    { valor: 10, nombre: 'Octubre' },
    { valor: 11, nombre: 'Noviembre' },
    { valor: 12, nombre: 'Diciembre' }
  ];

  constructor(
    private extractoService: ExtractoService,
    private snackBar: MatSnackBar
  ) {}

  generarExtracto() {
    const payload = {
      numeroCuenta: this.numeroCuenta,
      mes: this.mes,
      anio: this.anio
    };

    this.extractoService.generarExtracto(payload.numeroCuenta, payload.mes, payload.anio).subscribe({
      next: (blob: Blob) => {
        const file = new Blob([blob], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(file);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'extracto_mensual.pdf';
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 500) {
          this.snackBar.open('Cuenta no encontrada. Verifica el número e intenta de nuevo.', 'Cerrar', {
            duration: 4000
          });
        } else {
          console.error('Error al generar el extracto', error);
          this.snackBar.open('Ocurrió un error al generar el extracto.', 'Cerrar', {
            duration: 4000
          });
        }
      }
    });
  }
}
