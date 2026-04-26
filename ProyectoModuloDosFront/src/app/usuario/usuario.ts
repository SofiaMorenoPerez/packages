import { Component, inject, OnInit } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { UsuarioModel } from '../models/usuario.model';
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

  usuariosLista: UsuarioModel[] = [];

  nombre: string = '';
  tipo: TipoUsuario | '' = '';
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
    this.toastColor = exito ? '#9fdfb8' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('usuarioToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
  }

  cargarLista(): void {
    this.usuarioService.getAll().subscribe({
      next: (response) => {
        this.usuariosLista = response;
      },
      error: () => {
        this.mostrarToast('Error al cargar la lista', false);
      },
    });
  }

  guardar(): void {
    if (this.modoEdicion) {
      this.usuarioService
        .update(this.idEditando, this.nombre, this.tipo as TipoUsuario, this.ciudad, this.direccion, this.telefono)
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
        .create(this.nombre, this.tipo as TipoUsuario, this.ciudad, this.direccion, this.telefono)
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

  editar(u: UsuarioModel): void {
    this.modoEdicion = true;
    this.idEditando = u.id;
    this.nombre = u.nombre;
    this.tipo = u.tipo as TipoUsuario;
    this.ciudad = u.ciudad;
    this.direccion = u.direccion;
    this.telefono = u.telefono;
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
    this.tipo = '';
    this.ciudad = '';
    this.direccion = '';
    this.telefono = 0;
    this.modoEdicion = false;
    this.idEditando = 0;
  }
}
