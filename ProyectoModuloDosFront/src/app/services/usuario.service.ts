import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsuarioModel } from '../models/usuario.model';
import {TipoUsuario} from '../models/tipo.usuario';


@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private cliente = inject(HttpClient);
  private readonly urlbase: string = 'http://localhost:8081';

  getAll() {
    return this.cliente.get<UsuarioModel[]>(this.urlbase + '/usuario/mostrartodo');
  }



  create(nombre: string, tipo: TipoUsuario, tarifa: number, ciudad: string, direccion: string, telefono: number) {
    const params =
      `nombre=${encodeURIComponent(nombre)}` +
      `&tipo=${tipo}` +
      `&tarifa=${tarifa}` +
      `&ciudad=${encodeURIComponent(ciudad)}` +
      `&direccion=${encodeURIComponent(direccion)}` +
      `&telefono=${telefono}`;
    return this.cliente.post(this.urlbase + '/usuario/crear?' + params, null, { responseType: 'text' });
  }

  update(id: number,nombre: string, tipo: TipoUsuario, tarifa: number, ciudad: string, direccion: string, telefono: number) {
    const params =
      `id=${id}` +
      `&nombre=${encodeURIComponent(nombre)}` +
      `&tipo=${tipo}` +
      `&tarifa=${tarifa}` +
      `&ciudad=${encodeURIComponent(ciudad)}` +
      `&direccion=${encodeURIComponent(direccion)}` +
      `&telefono=${telefono}`;
    return this.cliente.put(this.urlbase + '/usuario/actualizar?' + params, null, { responseType: 'text' });
  }

  delete(id: number) {
    return this.cliente.delete(this.urlbase + '/usuario/eliminar?id=' + id, { responseType: 'text' });
  }
}
