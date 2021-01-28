import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  private path:String = 'http://localhost:8888/BILLING-SERVICE/';
  constructor(private http: HttpClient) { }

  getInfoPerDay(){
    return this.http.get(this.path+"billsByDays/1");
  }
}
