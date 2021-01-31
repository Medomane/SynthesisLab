import { Component } from '@angular/core';
import { SidebarService } from './sidebar/sidebar.service';
import {KeyCloakService} from './sec/key-cloak.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'e-commerce';
  public subTitle = 'Home';
  public suppliersUrl:string = 'http://localhost:8081/suppliers/' ;
  public customersUrl:string = 'http://localhost:8082/customers/' ;
  public productsUrl:string = 'http://localhost:8083/products/' ;
  public billingUrl:string = 'http://localhost:8084/' ;

  constructor(public http:HttpClient,private sidebarService: SidebarService,public keyCloak:KeyCloakService) {

  }
  toggleSidebar() {
    this.sidebarService.setSidebarState(!this.sidebarService.getSidebarState());
  }
  getSideBarState() {
    return this.sidebarService.getSidebarState();
  }
  setCartsProducts(product:any){
    this.sidebarService.setCartsProducts(product);
  }
  getCartsProducts(){return this.sidebarService.cartsProducts;};
  public formatDate(date:any) {
    let d = new Date(date);
    let month = '' + (d.getMonth() + 1), day = '' + d.getDate(), year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
  }

  public getUserId(){
    let id = localStorage.getItem("userId");
    return !id?0:parseInt(id);
  }
}
