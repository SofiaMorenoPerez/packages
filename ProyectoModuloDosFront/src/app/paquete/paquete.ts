import { Component, inject, OnInit } from '@angular/core';
import { PaqueteService } from '../services/paquete.service';
import { PaqueteModel } from '../models/paquete.model';
import { TipoPaquete } from '../models/tipo.paquete';

declare var bootstrap: any;

@Component({
  selector: 'app-paquete',
  standalone: false,
  templateUrl: './paquete.html',
  styleUrl: './paquete.css',
})
export class Paquete implements OnInit {
  private paqueteService = inject(PaqueteService);

  paquetesLista: PaqueteModel[] = [];

  idUsuario: number = 0;
  idConductor: number = 0;
  idManipulador: number = 0;
  ciudadDeOrigen: string = '';
  ciudadDeDestino: string = '';
  direccionDeOrigen: string = '';
  direccionDeDestino: string = '';
  fechaEnvio: string = '';
  tipo: TipoPaquete = TipoPaquete.ALIMENTICIO;
  peso: number = 0;
  maxHoras: number = 0;
  tiempo: number = 0;

  tiposDePaquete = Object.values(TipoPaquete);

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#9fdfb8' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('paqueteToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
  }

  cargarLista(): void {
    this.paqueteService.getAll().subscribe({
      next: (response) => {
        this.paquetesLista = response;
      },
      error: () => {
        this.mostrarToast('Error al cargar la lista', false);
      },
    });
  }

  guardar(): void {
    if (this.modoEdicion) {
      this.paqueteService
        .update(this.idEditando, this.ciudadDeDestino, this.direccionDeDestino)
        .subscribe({
          next: (response) => {
            this.mostrarToast(response, true);
            this.limpiarFormulario();
            this.cargarLista();
          },
          error: (error) => {
            const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
            this.mostrarToast(msg, false);
          },
        });
    } else {
      this.paqueteService
        .create(this.idUsuario, this.idConductor, this.idManipulador,
          this.ciudadDeOrigen, this.ciudadDeDestino,
          this.direccionDeOrigen, this.direccionDeDestino,
          this.tipo, this.peso)
        .subscribe({
          next: (response) => {
            this.mostrarToast(response, true);
            this.limpiarFormulario();
            this.cargarLista();
          },
          error: (error) => {
            const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
            this.mostrarToast(msg, false);
          },
        });
    }
  }

  editar(p: PaqueteModel): void {
    this.modoEdicion = true;
    this.idEditando = p.id;
    this.idUsuario = p.idUsuario;
    this.idConductor = p.idConductor;
    this.idManipulador = p.idManipulador;
    this.ciudadDeOrigen = p.ciudadDeOrigen;
    this.ciudadDeDestino = p.ciudadDeDestino;
    this.direccionDeOrigen = p.direccionDeOrigen;
    this.direccionDeDestino = p.direccionDeDestino;
    this.fechaEnvio = p.fechaEnvio;
    this.tipo = p.tipo as TipoPaquete;
    this.peso = p.peso;
    this.maxHoras = p.maxHoras;
    this.tiempo = p.tiempo;
  }

  eliminar(id: number): void {
    this.paqueteService.delete(id).subscribe({
      next: (response) => {
        this.mostrarToast(response, true);
        this.cargarLista();
      },
      error: (error) => {
        const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
        this.mostrarToast(msg, false);
      },
    });
  }

  limpiarFormulario(): void {
    this.idUsuario = 0;
    this.idConductor = 0;
    this.idManipulador = 0;
    this.ciudadDeOrigen = '';
    this.ciudadDeDestino = '';
    this.direccionDeOrigen = '';
    this.direccionDeDestino = '';
    this.fechaEnvio = '';
    this.tipo = TipoPaquete.ALIMENTICIO;
    this.peso = 0;
    this.maxHoras = 0;
    this.tiempo = 0;
    this.modoEdicion = false;
    this.idEditando = 0;
  }
}
