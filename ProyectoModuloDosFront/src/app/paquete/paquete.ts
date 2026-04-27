import { Component, inject, OnInit } from '@angular/core';
import { PaqueteService } from '../services/paquete.service';
import { PaqueteModel } from '../models/paquete.model';
import { TipoPaquete } from '../models/tipo.paquete';
import { UsuarioService } from '../services/usuario.service';
import { ConductorService } from '../services/conductor.service';
import { ManipuladorService } from '../services/manipulador.service';
import { UsuarioModel } from '../models/usuario.model';
import { ConductorModel } from '../models/conductor.model';
import { ManipuladorModel } from '../models/manipulador.model';

declare var bootstrap: any;

@Component({
  selector: 'app-paquete',
  standalone: false,
  templateUrl: './paquete.html',
  styleUrl: './paquete.css',
})
export class Paquete implements OnInit {
  private paqueteService = inject(PaqueteService);
  private usuarioService = inject(UsuarioService);
  private conductorService = inject(ConductorService);
  private manipuladorService = inject(ManipuladorService);

  usuariosLista: UsuarioModel[] = [];
  conductoresLista: ConductorModel[] = [];
  manipuladoresLista: ManipuladorModel[] = [];
  paquetesLista: PaqueteModel[] = [];
  paquetesUsuario: PaqueteModel[] = [];

  idUsuario: number = 0;
  idConductor: number = 0;
  idManipulador: number = 0;
  ciudadDeOrigen: string = '';
  ciudadDeDestino: string = '';
  direccionDeOrigen: string = '';
  direccionDeDestino: string = '';
  fechaEnvio: string = new Date().toISOString().slice(0, 16);
  tipo: TipoPaquete = TipoPaquete.ALIMENTICIO;
  peso: number = 0;
  maxHoras: number = 0;
  tiempo: number = 0;

  tiposDePaquete = Object.values(TipoPaquete);
  vista: string = 'crear';
  idBuscar: number = 0;
  modoEdicion: boolean = false;
  idEditando: number = 0;
  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  cambiarVista(v: string): void {
    this.vista = v;
    this.idBuscar = 0;
    this.paquetesUsuario = [];
    this.limpiarFormulario();
  }

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#b5f1ca' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('paqueteToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
    this.cargarUsuarios();
    this.cargarConductores();
    this.cargarManipuladores();
  }
  cargarUsuarios(): void {
    this.usuarioService.getAll().subscribe({
      next: (response) => { this.usuariosLista = response; },
      error: () => { this.mostrarToast('Error al cargar usuarios', false); }
    });
  }

  cargarConductores(): void {
    this.conductorService.getAll().subscribe({
      next: (response) => { this.conductoresLista = response; },
      error: () => { this.mostrarToast('Error al cargar conductores', false); }
    });
  }

  cargarManipuladores(): void {
    this.manipuladorService.getAll().subscribe({
      next: (response) => { this.manipuladoresLista = response; },
      error: () => { this.mostrarToast('Error al cargar manipuladores', false); }
    });
  }

  onTipoChange(): void {
    if (this.tipo === TipoPaquete.ALIMENTICIO) {
      this.maxHoras = 4;
    } else if (this.tipo === TipoPaquete.NO_ALIMENTICIO) {
      this.maxHoras = 24;
    } else if (this.tipo === TipoPaquete.CARTA) {
      this.maxHoras = 72;
    }
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

  buscarPaquetesPorIdUsuario(): void {
    if (this.idBuscar <= 0) {
      this.mostrarToast('El ID debe ser mayor a 0', false);
      return;
    }
    this.paquetesUsuario = this.paquetesLista.filter(p => p.idUsuario === Number(this.idBuscar));
    if (this.paquetesUsuario.length === 0) {
      this.mostrarToast(`No se encontraron paquetes para el usuario con ID ${this.idBuscar}`, false);
    }
  }

  validarTabla(): boolean {
    const soloLetras = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    const letrasNumeros = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\s#-]+$/;

    if (this.idUsuario <= 0) {
      this.mostrarToast('El ID de usuario debe ser mayor a 0', false);
      return false;
    }
    if (this.idConductor <= 0) {
      this.mostrarToast('El ID de conductor debe ser mayor a 0', false);
      return false;
    }
    if (this.idManipulador <= 0) {
      this.mostrarToast('El ID de manipulador debe ser mayor a 0', false);
      return false;
    }
    if (!this.tipo) {
      this.mostrarToast('Debe seleccionar un tipo de paquete', false);
      return false;
    }
    if (!soloLetras.test(this.ciudadDeOrigen)) {
      this.mostrarToast('La ciudad de origen solo puede contener letras', false);
      return false;
    }
    if (!soloLetras.test(this.ciudadDeDestino)) {
      this.mostrarToast('La ciudad de destino solo puede contener letras', false);
      return false;
    }
    if (!letrasNumeros.test(this.direccionDeOrigen)) {
      this.mostrarToast('La dirección de origen contiene caracteres no válidos', false);
      return false;
    }
    if (!letrasNumeros.test(this.direccionDeDestino)) {
      this.mostrarToast('La dirección de destino contiene caracteres no válidos', false);
      return false;
    }
    if (this.peso <= 0) {
      this.mostrarToast('El peso debe ser mayor a 0', false);
      return false;
    }
    return true;
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
      if (!this.validarTabla()) return;
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
    this.ciudadDeDestino = p.ciudadDeDestino;
    this.direccionDeDestino = p.direccionDeDestino;
  }

  eliminar(id: number): void {
    this.paqueteService.delete(id).subscribe({
      next: (response) => {
        this.mostrarToast(response, true);
        this.cargarLista();
        this.paquetesUsuario = this.paquetesUsuario.filter(p => p.id !== id);
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
    this.fechaEnvio = new Date().toISOString().slice(0, 16);
    this.tipo = TipoPaquete.ALIMENTICIO;
    this.peso = 0;
    this.maxHoras = 0;
    this.tiempo = 0;
    this.modoEdicion = false;
    this.idEditando = 0;
    this.paquetesUsuario = [];
    this.idBuscar = 0;
  }
}
