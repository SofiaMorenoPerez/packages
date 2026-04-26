import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ManipuladorModel } from '../models/manipulador.model';

@Injectable({
  providedIn: 'root',
})
export class ManipuladorService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8081';

  getAll() {
    return this.cliente.get<ManipuladorModel[]>(this.urlbase + '/manipulador/mostrartodo');
  }

  create(nombre: string, edad: number, fechaInicio: string, tipoDePaquete: string) {
    const params =
      `nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&tipoPaquete=${encodeURIComponent(tipoDePaquete)}`;
    return this.cliente.post(this.urlbase + '/manipulador/crear?' + params, null, { responseType: 'text' });
  }

  update(id: number, nombre: string, edad: number, fechaInicio: string, tipoDePaquete: string) {
    const params =
      `id=${id}` +
      `&nombre=${encodeURIComponent(nombre)}` +
      `&edad=${edad}` +
      `&fechaInicio=${encodeURIComponent(fechaInicio)}` +
      `&tipoPaquete=${encodeURIComponent(tipoDePaquete)}`;
    return this.cliente.put(this.urlbase + '/manipulador/actualizarmanipulador?' + params, null, { responseType: 'text' });
  }

  delete(id: number) {
    return this.cliente.delete(
      this.urlbase + '/manipulador/eliminarmanipulador?id=' + id,
      { responseType: 'text' }
    );
  }
}
