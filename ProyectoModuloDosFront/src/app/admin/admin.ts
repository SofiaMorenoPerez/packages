import { Component, inject, OnInit } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { AdminModel } from '../models/admin.model';

declare var bootstrap: any;

@Component({
  selector: 'app-admin',
  standalone: false,
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin implements OnInit {
  private adminService = inject(AdminService);

  adminsLista: AdminModel[] = [];

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  zonaAsignada: string = '';

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
        this.adminsLista = response;
      },
      error: () => {
        this.mostrarToast('Error al cargar la lista', false);
      },
    });
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
    this.fechaInicio = '';
    this.zonaAsignada = '';
    this.modoEdicion = false;
    this.idEditando = 0;
  }
}
