import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ManipuladorModel } from '../models/manipulador.model';

@Injectable({
  providedIn: 'root'
})
export class ManipuladorService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8080';

  getAll() {
    return this.cliente.get<ManipuladorModel[]>(
      this.urlbase + '/manipulador/mostrartodo',
      { observe: 'response' }
    );
  }

  create(nombre: string, edad: number, fechaInicio: string, tipoDePaquete: string) {
    return this.cliente.post(
      this.urlbase + `/manipulador/crearmanipulador?nombre=${nombre}&edad=${edad}&fechaInicio=${fechaInicio}&tipoDePaquete=${tipoDePaquete}`,
      null,
      { responseType: 'text' }
    );
  }

  update(id: number, nombre: string, edad: number, fechaInicio: string, tipoDePaquete: string) {
    return this.cliente.put(
      this.urlbase + `/manipulador/actualizar?id=${id}&nombre=${nombre}&edad=${edad}&fechaInicio=${fechaInicio}&tipoDePaquete=${tipoDePaquete}`,
      null,
      { responseType: 'text' }
    );
  }

  delete(id: number) {
    return this.cliente.delete(
      this.urlbase + `/manipulador/eliminar?id=${id}`,
      { responseType: 'text' }
    );
  }
}
