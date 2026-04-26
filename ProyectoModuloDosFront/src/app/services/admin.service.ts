import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmpleadoAdminModel } from '../models/admin.model';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoAdminService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8080';

  getAll() {
    return this.cliente.get<EmpleadoAdminModel[]>(
      this.urlbase + '/administrativo/mostrartodo',
      { observe: 'response' }
    );
  }

  create(nombre: string, edad: number, fechaInicio: string, zonaAsignada: string) {
    return this.cliente.post(
      this.urlbase + `/administrativo/crearadministrativo?nombre=${nombre}&edad=${edad}&fechaInicio=${fechaInicio}&zonaAsignada=${zonaAsignada}`,
      null,
      { responseType: 'text' }
    );
  }

  update(id: number, nombre: string, edad: number, fechaInicio: string, zonaAsignada: string) {
    return this.cliente.put(
      this.urlbase + `/administrativo/actualizar?id=${id}&nombre=${nombre}&edad=${edad}&fechaInicio=${fechaInicio}&zonaAsignada=${zonaAsignada}`,
      null,
      { responseType: 'text' }
    );
  }

  delete(id: number) {
    return this.cliente.delete(
      this.urlbase + `/administrativo/eliminar?id=${id}`,
      { responseType: 'text' }
    );
  }
}
