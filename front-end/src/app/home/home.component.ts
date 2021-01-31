import { Component, OnInit } from '@angular/core';
// @ts-ignore
import * as CanvasJS from '../../assets/canvasjs.js';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private app:AppComponent) { }

  ngOnInit(): void {
    let dataPoints:any[] = [];
    let tmp = this.app.formatDate;
    this.app.http.get(this.app.billingUrl+"bills/byDays/"+this.app.getUserId()).subscribe(value => {
      // @ts-ignore
      value.forEach(function(e){
        dataPoints.push({y:parseInt(e[1]),label:tmp(e[0])});
      });
      let chart = new CanvasJS.Chart("chartContainer1", {
        animationEnabled: true,
        exportEnabled: true,
        title: {
          text: "Products quantity per day"
        },
        axisY: {
          title: "Quantity"
        },
        data: [{
          type: "line",
          dataPoints: dataPoints,
          xValueType: "dateTime"
        }]
      });
      chart.render();

      this.app.subTitle = 'Home';
    },er => {
      console.error(er.toString());
    });
  }
}
