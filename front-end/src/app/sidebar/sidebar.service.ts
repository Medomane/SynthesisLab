import {Injectable} from '@angular/core';
import {ItemModule} from './item/item.module';
import {HttpClient} from '@angular/common/http';
import {KeyCloakService} from '../sec/key-cloak.service';

@Injectable({
  providedIn: 'root',
})

export class SidebarService {
  private path:String = 'http://localhost:8888/BILLING-SERVICE/';
  private toggled = false;
  public cartsProducts: Array<any> = [];
  private billsNumber:number = 0;
  private ordersNumber:number = 0;
  private menus : ItemModule[] = [];
  public data:any ;
  constructor(private http: HttpClient,public secService:KeyCloakService) {
    this.data = this.secService.kc.tokenParsed;
    this.http.get(this.path+"bills/byCustomer/"+this.getUserId()).subscribe(value => {
      // @ts-ignore
      this.billsNumber = parseInt(value.length+'');
      this.http.get(this.path+"orders/byCustomer/"+this.getUserId()).subscribe(v => {
        // @ts-ignore
        this.ordersNumber = parseInt(v.length+'');
        let item = new ItemModule()
        item.title = "general";
        item.type = "header";
        this.menus.push(item);

        item = new ItemModule();
        item.title = "Dashboard";
        item.icon = 'fa fa-tachometer-alt';
        item.link = "/home";
        this.menus.push(item);

        item = new ItemModule();
        item.title = "E-commerce";
        item.icon = 'fa fa-shopping-cart';
        item.type = "dropdown";
        item.badge.text = this.cartsProducts.length+'';
        item.badge.class = "badge-warning";

        let subItem = new ItemModule();
        subItem.title = "Products";
        subItem.link = '/products';
        item.submenus.push(subItem);

        subItem = new ItemModule();
        subItem.title = "Orders";
        subItem.link = '/bills';
        subItem.badge.text = this.ordersNumber+'';
        subItem.badge.class = "badge-success";
        item.submenus.push(subItem);
        this.menus.push(item);
        if(this.secService.isManager()){
          item = new ItemModule();
          item.title = "Settings";
          item.icon = 'fas fa-cogs';
          item.type = "dropdown";
          if(this.secService.isCustomerManager()){
            subItem = new ItemModule();
            subItem.title = "Customers";
            subItem.link = '/customers';
            item.submenus.push(subItem);
          }
          if(this.secService.isSupplierManager()){
            subItem = new ItemModule();
            subItem.title = "Suppliers";
            subItem.link = '/suppliers';
            item.submenus.push(subItem);
          }
          if(this.secService.isProductManager()){
            subItem = new ItemModule();
            subItem.title = "Products";
            subItem.link = '/productsCogs';
            item.submenus.push(subItem);
          }
          this.menus.push(item);
        }
      });
    });
  }

  toggle() {
    this.toggled = ! this.toggled;
  }

  getSidebarState() {
    return this.toggled;
  }

  setSidebarState(state: boolean) {
    this.toggled = state;
  }

  getMenuList() {
    return this.menus;
  }

  setCartsProducts(product:any) {
    let exists = this.cartsProducts.find( el => el.id === product.id);
    if(product.added && !exists) this.cartsProducts.push(product);
    if(!product.added && exists) this.cartsProducts.splice(this.cartsProducts.indexOf(exists),1);
    this.menus[2].badge.text = this.cartsProducts.length+'';
  }


  public getUserId(){
    let id = localStorage.getItem("userId");
    return !id?0:parseInt(id);
  }
}
