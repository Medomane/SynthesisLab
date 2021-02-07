import { Component, OnInit } from '@angular/core';
// @ts-ignore
import * as CanvasJS from '../../assets/canvasjs.js';
import {AppComponent} from '../app.component';
import {ConnectWebSocket} from '@ngxs/websocket-plugin';
import {Select, Store} from '@ngxs/store';
import {KafkaState} from '../state/kafka.state';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  @Select(KafkaState.messages)
    // @ts-ignore
  kafkaMessages$: Observable<string[]>


  constructor(private app:AppComponent,private store:Store) { }
  private static refreshStreamChart(streamDataPoints:any[]){
    let options = {
      animationEnabled: true,
      exportEnabled: true,
      title: {
        text: "Kafka stream orders by customer id"
      },
      axisY: {
        title: "Quantity"
      },
      data: [{
        type: "line",
        dataPoints: streamDataPoints
      }]
    };
    let streamChart = new CanvasJS.Chart("chartContainer2", options);
    streamChart.render();
  }
  ngOnInit(): void {
    let dataPoints:any[] = [];
    let streamDataPoints:any[] = [];
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

      this.store.dispatch(new ConnectWebSocket());

      this.kafkaMessages$.subscribe((value1 => {
        let tmp = value1[value1.length-1];
        if(tmp){
          let val:string[]=value1[value1.length-1].split('|');
          let index = -1;
          let list = streamDataPoints;
          for(let i=0;i<list.length;i++){
            if(parseInt(list[i].label) === parseInt(val[0])) index = i;
          }
          if(index > -1) streamDataPoints[index].y = parseInt(val[1]);
          else streamDataPoints.push({y:parseInt(val[1]),label:parseInt(val[0]), x:parseInt(val[0])});
          streamDataPoints.sort((a:any,b:any) => (a.x > b.x) ? 1 : ((b.x > a.x) ? -1 : 0));
          HomeComponent.refreshStreamChart(streamDataPoints);
        }
      }));
    },er => {
      console.error(er.toString());
    });
  }
}
