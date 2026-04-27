import {TipoPaquete} from './tipo.paquete';

export interface PaqueteModel {
  id: number;
  idUsuario: number;
  idConductor: number;
  idManipulador: number;
  ciudadDeOrigen: string;
  ciudadDeDestino: string;
  direccionDeOrigen: string;
  direccionDeDestino: string;
  fechaEnvio: string;
  tipo: TipoPaquete;
  peso: number;
  maxHoras: number;
  tiempo: number;
}
