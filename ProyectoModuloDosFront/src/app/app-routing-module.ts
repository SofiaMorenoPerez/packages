

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Inicio } from './inicio/inicio';
import { Usuario } from './usuario/usuario';
import { Paquete } from './paquete/paquete';
import {Manipulador} from './manipulador/manipulador';
import {Admin} from './admin/admin';
import {Conductor} from './conductor/conductor';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: Inicio },
  { path: 'usuario', component: Usuario },
  { path: 'admin', component: Admin},
  { path: 'manipulador', component: Manipulador},
  { path: 'conductor', component: Conductor},
  { path: 'paquete', component: Paquete }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
