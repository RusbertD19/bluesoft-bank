import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ExtractoService {
 private apiUrl = 'http://localhost:8080/api/extracto/pdf';

  constructor(private http: HttpClient) {}

  generarExtracto(numeroCuenta: string, mes: number, anio: number) {
    const body = { numeroCuenta, mes, anio };
    return this.http.post(this.apiUrl, body, { responseType: 'blob' });
  }
}