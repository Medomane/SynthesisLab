import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private path:String = 'http://localhost:8081/';
  constructor(private http: HttpClient) { }
  getSuppliers(){
    return this.http.get(this.path+'suppliers');
  }

  saveSupplier(data: any) {
    return this.http.post(this.path + 'suppliers', data);
  }
  deleteSupplier(id:any) {
    console.log(id);
    return this.http.delete(this.path + 'suppliers/' + id);
  }
}
