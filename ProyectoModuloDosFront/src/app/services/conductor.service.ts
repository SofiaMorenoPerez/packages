import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConductorModel } from '../models/conductor.model';

@Injectable({
  providedIn: 'root',
})
export class ConductorService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8080';

  getAll() {
    return this.cliente.get<ConductorModel[]>(this.urlbase + '/conductor/mostrartodo', { observe: 'response' });
  }

  create(nombre: string, edad: number, fechaInicio: string, tipoVehiculo: string) {
    const params =
      `nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&tipoVehiculo=${encodeURIComponent(tipoVehiculo)}`;
    return this.cliente.post(this.urlbase + '/conductor/crearconductor?' + params, null, { responseType: 'text' });
  }

  update(id: number, nombre: string, edad: number, fechaInicio: string, tipoVehiculo: string) {
    const params =
      `id=${id}` +
      `&nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&tipoVehiculo=${encodeURIComponent(tipoVehiculo)}`;
    return this.cliente.put(this.urlbase + '/conductor/actualizar?' + params, null, { responseType: 'text' });
  }

  delete(id: number) {
    return this.cliente.delete(this.urlbase + '/conductor/eliminar?id=' + id, { responseType: 'text' });
  }
}
