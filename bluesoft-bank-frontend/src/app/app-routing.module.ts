// src/app/app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarSaldoComponent } from './modules/cuentas/consultar-saldo/consultar-saldo.component';
import { ReporteClientesComponent } from './modules/reportes/reporte-clientes/reporte-clientes.component';
import { ReporteTransaccionesComponent } from './modules/reportes/reporte-transacciones/reporte-transacciones.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { TransaccionesComponent } from './transacciones/transacciones.component';
import { ExtractoComponent } from './extracto/extracto.component';

const routes: Routes = [
  { path: 'consultar-saldo', component: ConsultarSaldoComponent },
  { path: 'reporte', component: ReporteTransaccionesComponent },
  { path: 'transacciones', component: TransaccionesComponent },
  { path: 'inicio', component: DashboardComponent },
  { path: 'extracto', component: ExtractoComponent },
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  { path: '**', redirectTo: '/inicio' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule] // ← ¡IMPORTANTE!
})
export class AppRoutingModule {}
