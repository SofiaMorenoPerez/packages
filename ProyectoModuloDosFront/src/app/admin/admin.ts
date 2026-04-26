import { Component, inject, OnInit } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { UsuarioService } from '../services/usuario.service';
import { PaqueteService } from '../services/paquete.service';
import { ConductorService } from '../services/conductor.service';
import { ManipuladorService } from '../services/manipulador.service';
import { AdminModel } from '../models/admin.model';
import { UsuarioModel } from '../models/usuario.model';
import { PaqueteModel } from '../models/paquete.model';
import { ConductorModel } from '../models/conductor.model';
import { ManipuladorModel } from '../models/manipulador.model';

declare var bootstrap: any;

@Component({
  selector: 'app-admin',
  standalone: false,
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin implements OnInit {
  private adminService = inject(AdminService);
  private usuarioService = inject(UsuarioService);
  private paqueteService = inject(PaqueteService);
  private conductorService = inject(ConductorService);
  private manipuladorService = inject(ManipuladorService);

  adminsLista: AdminModel[] = [];
  usuariosLista: UsuarioModel[] = [];
  paquetesLista: PaqueteModel[] = [];
  conductoresLista: ConductorModel[] = [];
  manipuladoresLista: ManipuladorModel[] = [];

  vista: string = 'registrar';
  idBuscar: number = 0;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = new Date().toISOString().slice(0, 16);
  zonaAsignada: string = '';

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#3fcd76' : '#c40505';
    setTimeout(() => {
      const toastEl = document.getElementById('adminToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
  }

  cargarLista(): void {
    this.adminService.getAll().subscribe({
      next: (response) => {
        this.adminsLista = response ?? [];
      },
      error: () => { this.mostrarToast('Error al cargar administrativos', false); },
    });
  }

  cargarTodasListas(): void {
    this.usuarioService.getAll().subscribe({
      next: (response) => { this.usuariosLista = response ?? []; },
      error: () => { this.usuariosLista = []; },
    });
    this.paqueteService.getAll().subscribe({
      next: (response) => { this.paquetesLista = response ?? []; },
      error: () => { this.paquetesLista = []; },
    });
    this.conductorService.getAll().subscribe({
      next: (response) => { this.conductoresLista = response ?? []; },
      error: () => { this.conductoresLista = []; },
    });
    this.manipuladorService.getAll().subscribe({
      next: (response) => { this.manipuladoresLista = response ?? []; },
      error: () => { this.manipuladoresLista = []; },
    });
  }

  cambiarVista(v: string): void {
    this.vista = v;
    this.idBuscar = 0;
    this.limpiarFormulario();
    if (v === 'mostrar') {
      this.cargarLista();
      this.cargarTodasListas();
    }
  }

  buscarPorId(): void {
    const encontrado = this.adminsLista.find(a => a.id === Number(this.idBuscar));
    if (encontrado) {
      this.modoEdicion = true;
      this.idEditando = encontrado.id!;
      this.nombre = encontrado.nombre;
      this.edad = encontrado.edad;
      this.fechaInicio = encontrado.fechaInicio;
      this.zonaAsignada = encontrado.zonaAsignada;
    } else {
      this.mostrarToast(`No se encontró administrativo con ID ${this.idBuscar}`, false);
    }
  }

  guardar(): void {
    if (this.modoEdicion) {
      this.adminService.update(this.idEditando, this.nombre, this.edad, this.fechaInicio, this.zonaAsignada).subscribe({
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
      this.adminService.create(this.nombre, this.edad, this.fechaInicio, this.zonaAsignada).subscribe({
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

  editar(a: AdminModel): void {
    this.modoEdicion = true;
    this.idEditando = a.id!;
    this.nombre = a.nombre;
    this.edad = a.edad;
    this.fechaInicio = a.fechaInicio;
    this.zonaAsignada = a.zonaAsignada;
    this.vista = 'editar';
  }

  eliminar(id: number): void {
    this.adminService.delete(id).subscribe({
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
    this.edad = 0;
    this.fechaInicio = new Date().toISOString().slice(0, 16);
    this.zonaAsignada = '';
    this.modoEdicion = false;
    this.idEditando = 0;
  }

  cargarUsuarios(): void {
    this.usuarioService.getAll().subscribe({
      next: (response) => { this.usuariosLista = response ?? []; },
      error: () => { this.mostrarToast('Error al cargar usuarios', false); },
    });
  }

  cargarConductores(): void {
    this.conductorService.getAll().subscribe({
      next: (response) => { this.conductoresLista = response ?? []; },
      error: () => { this.mostrarToast('Error al cargar conductores', false); },
    });
  }

  cargarManipuladores(): void {
    this.manipuladorService.getAll().subscribe({
      next: (response) => { this.manipuladoresLista = response ?? []; },
      error: () => { this.mostrarToast('Error al cargar manipuladores', false); },
    });
  }

  cargarPaquetes(): void {
    this.paqueteService.getAll().subscribe({
      next: (response) => { this.paquetesLista = response ?? []; },
      error: () => { this.mostrarToast('Error al cargar paquetes', false); },
    });
  }
}
