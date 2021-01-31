import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) { }
  getProducts(path:string){
    return this.http.get(path);
  }
  saveProduct(data: any,file:any,path:string,headers:HttpHeaders) {
    data.supplier = {id:data.supplierId};
    let formData:FormData = new FormData();
    if(file) formData.append('image', file, file.name);
    formData.append('product', JSON.stringify(data));
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    // @ts-ignore
    return this.http.post(path + 'save', formData,{headers:headers});
  }
  deleteProduct(path:string) {
    return this.http.delete(path);
  }
  buy(cartsProducts: Array<any>,path:string,headers:HttpHeaders) {
    let orders: any[] = [];
    cartsProducts.forEach(function(e){
      orders.push({quantity:e.selectedQuantity,product:e});
    });
    return this.http.post(path,orders,{headers:headers});
  }
}
