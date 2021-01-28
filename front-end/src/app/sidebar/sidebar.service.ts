import {Injectable} from '@angular/core';
import {ItemModule} from './item/item.module';
import {HttpClient} from '@angular/common/http';

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
  constructor(private http: HttpClient) {
    this.http.get(this.path+"countBillsByCustomer/1").subscribe(value => {
      this.billsNumber = parseInt(value+'');
      this.http.get(this.path+"countOrdersByCustomer/1").subscribe(v => {
        this.ordersNumber = parseInt(v+'');
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
}
