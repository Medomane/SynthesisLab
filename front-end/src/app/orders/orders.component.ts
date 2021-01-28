import { Component, OnInit } from '@angular/core';
import {AppComponent} from '../app.component';
import {OrderService} from './order.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  rowData:any[] = [];
  currentBill: any;
  constructor(public service:OrderService,private modalService: NgbModal,public app:AppComponent) {}

  ngOnInit(): void {
    this.service.getOrders().subscribe(v =>{
      // @ts-ignore
      this.rowData = v;
      this.app.subTitle = 'Orders';
    },(er)=>{
      console.error(er);
    });
  }

  showDetail(content: any,bill: any) {
    this.currentBill = bill;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
}
