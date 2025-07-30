import { Component } from '@angular/core';
import { ReporteService } from '../../../reporte.service';

@Component({
  selector: 'app-reporte-transacciones',
  templateUrl: './reporte-transacciones.component.html',
  styleUrls: ['./reporte-transacciones.component.scss']
})
export class ReporteTransaccionesComponent {
  mes: number = 7;
  anio: number = 2025;
  transaccionesMes: any[] = [];
  retirosFueraCiudad: any[] = [];
  mesRetiros: number = 7;
  anioRetiros: number = 2025;
monto: number = 1000000;


  constructor(private reporteService: ReporteService) {}

  generarReporteTransacciones() {
    this.reporteService.obtenerClientesConMasTransacciones(this.mes, this.anio).subscribe(data => {
      this.transaccionesMes = data;
    });
  }

  generarReporteRetiros() {
  this.reporteService.obtenerClientesConRetirosFueraCiudad(this.mesRetiros, this.anioRetiros, this.monto)
    .subscribe(data => {
      this.retirosFueraCiudad = data;
    });
}

}
