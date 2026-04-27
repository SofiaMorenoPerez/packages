import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Inicio } from './inicio/inicio';
import { Paquete } from './paquete/paquete';
import { Usuario } from './usuario/usuario';
import { Manipulador } from './manipulador/manipulador';
import { Admin } from './admin/admin';
import { Conductor } from './conductor/conductor';
import { Empleado } from './empleado/empleado';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [App, Inicio, Paquete, Usuario, Manipulador, Admin, Conductor, Empleado],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
