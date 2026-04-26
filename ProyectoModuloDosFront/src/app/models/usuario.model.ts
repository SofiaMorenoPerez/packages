import {TipoUsuario} from './tipo.usuario';

interface UsuarioModel {
  id: number;
  nombre: string;
  tipo: TipoUsuario;
  tarifa: number;
  ciudad: string;
  direccion: string;
  telefono: number;
}
