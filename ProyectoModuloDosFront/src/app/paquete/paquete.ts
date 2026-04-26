import { Component } from '@angular/core';

@Component({
  selector: 'app-paquete',
  standalone: false,
  templateUrl: './paquete.html',
  styleUrl: './paquete.css',
})
export class Paquete {
  envio = {
    idUsuario: 0,
    idConductor: 0,
    idManipulador: 0,
    ciudadDeOrigen: '',
    ciudadDeDestino: '',
    direccionDeOrigen: '',
    direccionDeDestino: '',
    fechaEnvio: '',
    tipo: '',
    peso: 0,
    maxHoras: 0,
    tiempo: 0,
  };

  paquete() {
    console.log(this.envio);
    // aquí después conectamos con el backend
  }
}

