import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private path:String = 'http://localhost:8888/BILLING-SERVICE/';
  public pathProds:String = 'http://localhost:8888/PRODUCT-SERVICE/';
  constructor(private http: HttpClient) { }
  getOrders(){
    return this.http.get(this.path+'billsByCustomer/1');
  }
}
