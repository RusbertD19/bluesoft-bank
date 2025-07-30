import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module'; 
import { ConsultarSaldoComponent } from './modules/cuentas/consultar-saldo/consultar-saldo.component';
import { ReporteClientesComponent } from './modules/reportes/reporte-clientes/reporte-clientes.component';
import { ReporteTransaccionesComponent } from './modules/reportes/reporte-transacciones/reporte-transacciones.component';
import { RouterOutlet } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule } from '@angular/forms'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TransaccionesComponent } from './transacciones/transacciones.component';
import { MatTableModule } from '@angular/material/table';
import { ExtractoComponent } from './extracto/extracto.component';
import { MatSelectModule } from '@angular/material/select';
import { MatMenuModule } from '@angular/material/menu';

@NgModule({
  declarations: [
    AppComponent,
    ConsultarSaldoComponent,
    ReporteClientesComponent,
    ReporteTransaccionesComponent,
    TransaccionesComponent,
    ExtractoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterOutlet,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    AppRoutingModule,
    FormsModule,
    MatTableModule,
     MatSelectModule,
       MatMenuModule,
    MatButtonModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
