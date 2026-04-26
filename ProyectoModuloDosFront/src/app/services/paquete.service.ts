import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PaqueteModel } from '../models/paquete.model';
import { TipoPaquete } from '../models/tipo.paquete';

@Injectable({
  providedIn: 'root'
})
export class PaqueteService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8081';

  getAll() {
    return this.cliente.get<PaqueteModel[]>(this.urlbase + '/paquete/mostrartodo');
  }

  create(idUsuario: number, idConductor: number, idManipulador: number,
         ciudadDeOrigen: string, ciudadDeDestino: string,
         direccionDeOrigen: string, direccionDeDestino: string,
         tipo: TipoPaquete, peso: number) {
    const params =
      `idUsuario=${idUsuario}` +
      `&idConductor=${idConductor}` +
      `&idManipulador=${idManipulador}` +
      `&ciudadDeOrigen=${encodeURIComponent(ciudadDeOrigen)}` +
      `&ciudadDeDestino=${encodeURIComponent(ciudadDeDestino)}` +
      `&direccionDeOrigen=${encodeURIComponent(direccionDeOrigen)}` +
      `&direccionDeDestino=${encodeURIComponent(direccionDeDestino)}` +
      `&tipo=${tipo}` +
      `&peso=${peso}`;
    return this.cliente.post(this.urlbase + '/paquete/crear?' + params, null, { responseType: 'text' });
  }

  update(id: number, ciudadDeDestino: string, direccionDeDestino: string) {
    const params =
      `id=${id}` +
      `&ciudadDeDestino=${encodeURIComponent(ciudadDeDestino)}` +
      `&direccionDeDestino=${encodeURIComponent(direccionDeDestino)}`;
    return this.cliente.put(this.urlbase + '/paquete/actualizar?' + params, null, { responseType: 'text' });
  }

  delete(id: number) {
    return this.cliente.delete(this.urlbase + '/paquete/eliminarpaquete?id=' + id, { responseType: 'text' });
  }
}
