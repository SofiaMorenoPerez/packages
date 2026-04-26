import { Component, inject, OnInit } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { PaqueteService } from '../services/paquete.service';
import { UsuarioModel } from '../models/usuario.model';
import { PaqueteModel } from '../models/paquete.model';
import { TipoUsuario } from '../models/tipo.usuario';

declare var bootstrap: any;

@Component({
  selector: 'app-usuario',
  standalone: false,
  templateUrl: './usuario.html',
  styleUrl: './usuario.css',
})
export class Usuario implements OnInit {
  private usuarioService = inject(UsuarioService);
  private paqueteService = inject(PaqueteService);

  usuariosLista: UsuarioModel[] = [];
  paquetesLista: PaqueteModel[] = [];
  paquetesUsuario: PaqueteModel[] = [];

  vista: string = 'registrar';
  idBuscar: number = 0;
  usuarioEncontrado: UsuarioModel | null = null;

  nombre: string = '';
  tipo: TipoUsuario = TipoUsuario.NORMAL;
  ciudad: string = '';
  direccion: string = '';
  telefono: number = 0;

  tiposUsuario = Object.values(TipoUsuario);

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#b5f1ca' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('usuarioToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
    this.cargarPaquetes();
  }

  cargarLista(): void {
    this.usuarioService.getAll().subscribe({
      next: (response) => { this.usuariosLista = response; },
      error: () => { this.mostrarToast('Error al cargar usuarios', false); },
    });
  }

  cargarPaquetes(): void {
    this.paqueteService.getAll().subscribe({
      next: (response) => { this.paquetesLista = response; },
      error: () => { this.paquetesLista = []; },
    });
  }

  buscarPorId(): void {
    const encontrado = this.usuariosLista.find(u => u.id === Number(this.idBuscar));
    if (encontrado) {
      this.modoEdicion = true;
      this.idEditando = encontrado.id;
      this.nombre = encontrado.nombre;
      this.tipo = encontrado.tipo as TipoUsuario;
      this.ciudad = encontrado.ciudad;
      this.direccion = encontrado.direccion;
      this.telefono = encontrado.telefono;
      this.usuarioEncontrado = encontrado;
    } else {
      this.mostrarToast(`No se encontró usuario con ID ${this.idBuscar}`, false);
    }
  }

  buscarPaquetesPorId(): void {
    const encontrado = this.usuariosLista.find(u => u.id === Number(this.idBuscar));
    if (encontrado) {
      this.usuarioEncontrado = encontrado;
      this.paquetesUsuario = this.paquetesLista.filter(p => p.idUsuario === Number(this.idBuscar));
    } else {
      this.usuarioEncontrado = null;
      this.paquetesUsuario = [];
      this.mostrarToast(`No se encontró usuario con ID ${this.idBuscar}`, false);
    }
  }

  cambiarVista(v: string): void {
    this.vista = v;
    this.idBuscar = 0;
    this.usuarioEncontrado = null;
    this.paquetesUsuario = [];
    this.limpiarFormulario();
  }

  guardar(): void {
    if (this.modoEdicion) {
      this.usuarioService
        .update(this.idEditando, this.nombre, this.tipo, this.ciudad, this.direccion, this.telefono)
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
      this.usuarioService
        .create(this.nombre, this.tipo, this.ciudad, this.direccion, this.telefono)
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



  buscarPorIdYEliminar(): void {
    const encontrado = this.usuariosLista.find(u => u.id === Number(this.idBuscar));
    if (encontrado) {
      this.eliminar(encontrado.id);
    } else {
      this.mostrarToast(`No se encontró usuario con ID ${this.idBuscar}`, false);
    }
  }





  eliminar(id: number): void {
    this.usuarioService.delete(id).subscribe({
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
    this.nombre = '';
    this.tipo = TipoUsuario.NORMAL;
    this.ciudad = '';
    this.direccion = '';
    this.telefono = 0;
    this.modoEdicion = false;
    this.idEditando = 0;
    this.usuarioEncontrado = null;
  }
}
