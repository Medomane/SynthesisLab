import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class BadgeModule {
  public text: string;
  public class: string;
  constructor(){
    this.text = '';
    this.class = '';
  }
}
