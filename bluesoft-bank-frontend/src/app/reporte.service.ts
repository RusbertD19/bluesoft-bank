import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  private apiUrl = 'http://localhost:8080/api/reportes';

  constructor(private http: HttpClient) { }

  obtenerClientesConMasTransacciones(mes: number, anio: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/transacciones-mes?mes=${mes}&anio=${anio}`);
  }

  obtenerClientesConRetirosFueraCiudad(mes: number, anio: number, monto:number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/retiros-fuera-ciudad?mes=${mes}&anio=${anio}&monto=${monto}`);
  }
   generarExtracto(cuentaId: number, year: number, month: number) {
    const url = `${this.apiUrl}?cuentaId=${cuentaId}&year=${year}&month=${month}`;
    return this.http.get(url, { responseType: 'blob' });
  }
}