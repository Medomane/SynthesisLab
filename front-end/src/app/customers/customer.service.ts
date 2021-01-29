import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private path:String = 'http://localhost:8082/';
  constructor(private http: HttpClient) { }
  getCustomers(){
    return this.http.get(this.path+'customers');
  }

  saveCustomer(data: any) {
    return this.http.post(this.path + 'customers', data);
  }
  deleteCustomer(id:any) {
    console.log(id);
    return this.http.delete(this.path + 'customers/' + id);
  }
}
