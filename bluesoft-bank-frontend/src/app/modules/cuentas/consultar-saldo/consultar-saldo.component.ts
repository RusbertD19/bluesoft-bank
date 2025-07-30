import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CuentaService } from '../../../cuenta-service';

@Component({
  selector: 'app-consultar-saldo',
  templateUrl: './consultar-saldo.component.html',
  styleUrls: ['./consultar-saldo.component.scss']
})
export class ConsultarSaldoComponent {
  consultaForm: FormGroup;
  saldo: number | null = null;
  error: string | null = null;
  cargando: boolean = false;
  cuentaInfo: { numero: string; tipo: string } | null = null;
  transacciones: any[] = [];

  constructor(
    private fb: FormBuilder,
    private cuentaService: CuentaService
  ) {
    this.consultaForm = this.fb.group({
      numeroCuenta: ['', Validators.required]
    });
  }

  consultarSaldo() {
    if (this.consultaForm.valid) {
      this.cargando = true;
      this.error = null;
      this.saldo = null;
      this.cuentaInfo = null;
      this.transacciones = [];

      const numeroCuenta = this.consultaForm.value.numeroCuenta!;

      this.cuentaService.consultarSaldo(numeroCuenta).subscribe({
        next: (saldo) => {
          this.saldo = saldo;
          this.cuentaInfo = {
            numero: numeroCuenta,
            tipo: numeroCuenta.startsWith('1') ? 'Ahorros' : 'Corriente'
          };

          // Obtener últimas transacciones
          this.cuentaService.obtenerUltimasTransacciones(numeroCuenta, 5).subscribe({
            next: (datos) => {
              this.transacciones = datos;
              this.cargando = false;
            },
            error: () => {
              this.error = 'Saldo consultado, pero no se pudieron obtener transacciones recientes.';
              this.cargando = false;
            }
          });
        },
        error: (err) => {
          this.error = err.error?.message || 'Error al consultar el saldo. Verifique el número de cuenta.';
          this.cargando = false;
        }
      });
    }
  }
}
