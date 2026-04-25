import { Component } from '@angular/core';

@Component({
  selector: 'app-inicio',
  standalone: false,
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio {

  titulo: string = "Bienvenido al servicio de Reparticiones";

  url: string = `https://e7.pngegg.com/pngimages/758/126/png-clipart-car-pickup-truck-delivery-car-angle-freight-transport-thumbnail.png`

  constructor() {}

}

