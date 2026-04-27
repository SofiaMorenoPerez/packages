import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdminModel } from '../models/admin.model';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8081';

  getAll() {
    return this.cliente.get<AdminModel[]>(this.urlbase + '/administrativo/mostrartodo');
  }

  create(nombre: string, edad: number, fechaInicio: string, zonaAsignada: string) {
    const params =
      `nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&zonaAsignada=${encodeURIComponent(zonaAsignada)}`;
    return this.cliente.post(this.urlbase + '/administrativo/crearadministrativo?' + params, null, { responseType: 'text' });
  }

  update(id: number, nombre: string, edad: number, fechaInicio: string, zonaAsignada: string) {
    const params =
      `id=${id}` +
      `&nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&zonaAsignada=${encodeURIComponent(zonaAsignada)}`;
    return this.cliente.put(this.urlbase + '/administrativo/actualizar?' + params, null, { responseType: 'text' });
  }

  delete(id: number) {
    return this.cliente.delete(this.urlbase + '/administrativo/eliminar?id=' + id, { responseType: 'text' });
  }
}
