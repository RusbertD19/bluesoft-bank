import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {
  private apiUrl = 'http://localhost:8080/api/cuentas';

  constructor(private http: HttpClient) { }

  consultarSaldo(numeroCuenta: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${numeroCuenta}/saldo`);
  }

  obtenerUltimasTransacciones(numeroCuenta: string, cantidad: number = 10): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${numeroCuenta}/transacciones/recientes?cantidad=${cantidad}`);
  }

  consignar(numeroCuenta: string, valor: number, ciudad: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${numeroCuenta}/consignar`, null, {
      params: {
        valor: valor.toString(),
        ciudad
      }
    });
  }

  retirar(numeroCuenta: string, valor: number, ciudad: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${numeroCuenta}/retirar`, null, {
      params: {
        valor: valor.toString(),
        ciudad
      }
    });
  }

}