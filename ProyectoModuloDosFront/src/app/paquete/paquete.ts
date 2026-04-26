import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-paquete',
  standalone: false,
  templateUrl: './paquete.html',
  styleUrl: './paquete.css',
})
export class Paquete {

  constructor(private router: Router) {}

  irPaquete() {
    this.router.navigate(['/paquete']);
  }
}
