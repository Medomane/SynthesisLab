import { Injectable } from '@angular/core';
import {KeycloakInstance} from 'keycloak-js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
declare var Keycloak:any;
@Injectable({
  providedIn: 'root'
})
export class KeyCloakService {
  // @ts-ignore
  public kc:KeycloakInstance;

  constructor(private http:HttpClient) {
  }

  public getHeader() {return new HttpHeaders({"Authorization":"Bearer "+this.kc.token});}

  public async init(){
    console.log("KeyCloakService initialisation");
    this.kc = new Keycloak({
      url: "http://localhost:8080/auth",
      realm: "MySynthesisLab-realm",
      clientId: "angular-app"
    });
    await this.kc.init({
      onLoad: "login-required",
      // @ts-ignore
      promiseType: 'native'
    });
    if(this.kc.authenticated) {
      // @ts-ignore
      this.http.get("http://localhost:8082/customers/byEmail/"+this.kc.tokenParsed.email).subscribe((v)=>{
        // @ts-ignore
        localStorage.setItem("userId",v.id);
      },(er)=>{
        console.log(er);
      });
    }
  }

  public isManager(){
    return this.isSupplierManager() || this.isProductManager() || this.isCustomerManager();
  }
  public isProductManager(){
    let roles = this.kc.tokenParsed?.realm_access?.roles;
    return roles?.includes("PRODUCT_MANAGER");
  }
  public isCustomerManager(){
    let roles = this.kc.tokenParsed?.realm_access?.roles;
    return roles?.includes("CUSTOMER_MANAGER");
  }
  public isSupplierManager(){
    let roles = this.kc.tokenParsed?.realm_access?.roles;
    return roles?.includes("SUPPLIER_MANAGER");
  }
}
