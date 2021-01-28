import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ProductService} from './product.service';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Array<any> = [];
  currentSupplier:any;
  public url:String = 'http://localhost:8888/PRODUCT-SERVICE/';

  constructor(private service:ProductService,private modalService: NgbModal,public app:AppComponent) { }

  ngOnInit(): void {
    let arr:Array<any> = this.app.getCartsProducts();
    this.service.getProducts().subscribe(v =>{
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
}
