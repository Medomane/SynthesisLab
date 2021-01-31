import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ProductService} from './product.service';
import {AppComponent} from '../app.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Array<any> = [];
  currentSupplier:any;

  constructor(private service:ProductService,public modalService: NgbModal,public app:AppComponent) { }

  ngOnInit(): void {
    let arr:Array<any> = this.app.getCartsProducts();
    this.service.getProducts(this.app.productsUrl).subscribe(v =>{
      // @ts-ignore
      this.products = v;
      this.products.map((e)=>{
        e.selectedQuantity = 1;
        e.added = arr.find( el => el.id === e.id) !== undefined;
      });
      this.app.subTitle = 'Products';
    },(er)=>{
      console.error(er);
    });
  }

  plus(product:any) {
    if (product.selectedQuantity < product.quantityAvailable) product.selectedQuantity++;
  }
  minus(product:any) {
    if (product.selectedQuantity > 1) product.selectedQuantity--;
  }

  open(content: any,supplier: any) {
    this.currentSupplier = supplier;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
  addToCart(product:any){
    product.added = !product.added;
    this.app.setCartsProducts(product);
  }

  buy() {
    this.service.buy(this.app.getCartsProducts(),this.app.billingUrl+"orders/buy/"+this.app.getUserId(),this.app.keyCloak.getHeader()).subscribe(() =>{
      Swal.fire(
        'Success!',
        'Successfully bought!',
        'success'
      ).then(()=>{
        location.reload();
      });
    },(er)=>{
      console.error(er);
    })
  }
  getSym(){
    let sum:number = 0;
    this.app.getCartsProducts().forEach(function(e){
      sum += parseFloat(e.selectedQuantity)*parseFloat(e.price);
    });
    return sum/10;
  }
}
