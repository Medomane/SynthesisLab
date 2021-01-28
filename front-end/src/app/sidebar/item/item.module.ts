import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BadgeModule} from '../badge/badge.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class ItemModule {
  public title:string;
  public type:string;
  public icon:string;
  public active:boolean;
  public submenus:ItemModule[];
  public badge: BadgeModule;
  public link: string;

  constructor(){
    this.title = "";
    this.link = "";
    this.type = "";
    this.icon = "";
    this.active = false;
    this.submenus = [];
    this.badge = new BadgeModule();
  }
}
