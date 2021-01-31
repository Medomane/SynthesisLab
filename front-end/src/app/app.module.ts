import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';

import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import { HomeComponent } from './home/home.component';
import {BadgeModule} from './sidebar/badge/badge.module';
import {ItemModule} from './sidebar/item/item.module';
import {HttpClientModule} from '@angular/common/http';
import {ProductsComponent} from './products/products.component';
import {NgbAlertModule} from '@ng-bootstrap/ng-bootstrap';
import { OrdersComponent } from './orders/orders.component';
import { CustomersComponent } from './customers/customers.component';
import {ReactiveFormsModule} from '@angular/forms';
import { SuppliersComponent } from './suppliers/suppliers.component';
import { ProductsCogComponent } from './products-cog/products-cog.component';
import {KeyCloakService} from './sec/key-cloak.service';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

export function kcFactory(kcService:KeyCloakService) {
  return () => kcService.init();
}

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    HomeComponent,
    ProductsComponent,
    OrdersComponent,
    CustomersComponent,
    SuppliersComponent,
    ProductsCogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    BsDropdownModule.forRoot(),
    PerfectScrollbarModule,
    ItemModule,
    BadgeModule,
    HttpClientModule,
    NgbAlertModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },
    {
      provide: APP_INITIALIZER,
      deps:[KeyCloakService],
      useFactory: kcFactory,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
