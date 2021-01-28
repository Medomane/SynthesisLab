import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private path:String = 'http://localhost:8888/PRODUCT-SERVICE/';
  constructor(private http: HttpClient) { }
  getProducts(){
    return this.http.get(this.path+'get');
  }
}
