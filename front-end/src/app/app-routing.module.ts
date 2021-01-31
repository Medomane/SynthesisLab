import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ProductsComponent} from './products/products.component';
import {OrdersComponent} from './orders/orders.component';
import {CustomersComponent} from './customers/customers.component';
import {SuppliersComponent} from './suppliers/suppliers.component';
import {ProductsCogComponent} from './products-cog/products-cog.component';

const routes: Routes = [
  {path: 'productsCogs', component: ProductsCogComponent},
  {path: 'suppliers', component: SuppliersComponent},
  {path: 'customers', component: CustomersComponent},
  {path: 'bills', component: OrdersComponent},
  {path: 'products', component: ProductsComponent},
  {path: '**', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
