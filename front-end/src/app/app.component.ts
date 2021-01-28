import { Component } from '@angular/core';
import { SidebarService } from './sidebar/sidebar.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'e-commerce';
  public subTitle = 'Home';
  constructor(private sidebarService: SidebarService) {

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
}
