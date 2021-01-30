import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  public path:String = 'http://localhost:8083/';
  public ordersPath:String = 'http://localhost:8084/';
  constructor(private http: HttpClient) { }
  getProducts(){
    return this.http.get(this.path+'get');
  }
  saveProduct(data: any,file:any) {
    data.supplier = {id:data.supplierId};
    let headers = new Headers();
    let formData:FormData = new FormData();
    if(file) formData.append('image', file, file.name);
    formData.append('product', JSON.stringify(data));
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    // @ts-ignore
    return this.http.post(this.path + 'saveProduct', formData);
  }
  deleteProduct(id:any) {
    return this.http.delete(this.path+'products/'+id);
  }
  buy(cartsProducts: Array<any>) {
    let orders: any[] = [];
    cartsProducts.forEach(function(e){
      orders.push({quantity:e.selectedQuantity,product:e});
    });
    return this.http.post(this.ordersPath+'buy/1',orders);
  }
}
