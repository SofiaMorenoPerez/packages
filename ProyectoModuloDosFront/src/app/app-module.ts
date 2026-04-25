import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Inicio } from './inicio/inicio';
import { Models } from './models/models';
import { Services } from './services/services';
import { Paquete } from './paquete/paquete';
import { Usuario } from './usuario/usuario';
import { Empleado } from './empleado/empleado';

@NgModule({
  declarations: [App, Inicio, Models, Services, Paquete, Usuario, Empleado],
  imports: [BrowserModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
