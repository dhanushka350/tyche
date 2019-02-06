import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {model} from "../dto/model";
import {Subscription} from "rxjs";
import {BaseChartDirective} from "ng2-charts";
import {HttpClient} from "@angular/common/http";
import {StreamService} from "../service/stream.service";
import {SETTING} from "../SETTING";
import {LocalStorageService} from 'ngx-webstorage';

@Component({
  selector: 'app-chart-small',
  templateUrl: './chart-small.component.html',
  styleUrls: ['./chart-small.component.scss']
})
export class ChartSmallComponent implements OnInit {
  ar: Object;
  lk: model;
  longTermChart: string = 'EURUSD';
  currentSearch: string = 'EURUSD – 5 Days';
  currency: any = "EURUSD";
  subscription: Subscription;
  @ViewChild(BaseChartDirective)
  public chart: BaseChartDirective;

  height="942px";
  img;

  constructor(private http: HttpClient, private stream: StreamService,private change:ChangeDetectorRef,private localSt:LocalStorageService) {
    this.getCurrencyData();

  }

  getCurrencyData() {
    this.subscription = this.stream.getCurrency().subscribe(currency => {
      this.currency = currency;
      console.log("getting data for -" + this.currency);
      this.getChartData(5, this.currency);
      this.img=this.localSt.retrieve(this.currency+"_IMG");
    });
  }

  public dataLine1: Array<any>=[];
  public dataLine2: Array<any>=[];
  public lineChartLabels: Array<any>=[];

  ngOnInit() {
    // this.dataLine1 = [];
    // this.dataLine2 = [];
    // this.lineChartLabels = [];0
    this.getChartData(5, "EURUSD");
    this.height=""+(window.innerHeight*0.96)+"px";
    console.log("ssssssssssssss"+this.height);
    this.change.markForCheck();

  }


  // lineChart
  public lineChartData: Array<any> = [
    {
      fill: true,
      label: 'Ratio',
      pointHoverRadius: 5,
      pointHitRadius: 5,
      lineTension: 0,
      yAxisID: "y-axis-0",
      data:
      this.dataLine1
      ,
      // backgroundColor: "rgb(255,255,255)"
    }, {
      fill: true,
      pointHoverRadius: 5,
      pointHitRadius: 5,
      lineTension: 0,
      yAxisID: "y-axis-1",
      label: 'Short',
      scaleFontColor: 'red',
      data: this.dataLine2
      // backgroundColor: "rgb(255,255,0)"
    }
  ];


  public lineChartOptions: any = {
    scaleFontColor: 'red',
    maintainAspectRatio: false,
    bezierCurveTension: 0,
    scales: {
      xAxes: [{
        display: true,
        ticks: {
          maxTicksLimit: 20,
          fontSize: 10,
          fontColor: "#aaa"
        }
      }],
      yAxes: [{
        position: "left",
        "id": "y-axis-0",
        display: true,
        gridLines: {
          color: 'rgba(255, 255, 255, 0.4)' // makes grid lines from y axis red
        }

      }, {
        position: "right",
        "id": "y-axis-1",
        display: true
      }]
    }
  };
  public lineChartColors: Array<any> = [
    { // grey
      backgroundColor: 'rgba(255,0,0,0)',
      borderColor: 'rgba(255,0,0,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(255,255,0,0)',
      borderColor: 'rgba(255,255,0,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public lineChartLegend: boolean = true;
  public lineChartType: string = 'line';


  getChartData(days: any, currency: any) {

    this.longTermChart = currency;
    this.currentSearch = currency + " - " + days + "Days";

    this.http.get(SETTING.HTTP + '/api/scrape/getChartData/' + currency + '/' + days).subscribe(data => {
      this.lineChartLabels.length = 0;
      console.log(data);
      this.dataLine1=[];
      this.dataLine2=[];
      this.lineChartLabels=[];
      let i=1;
      for (let ob of data["data"]) {
        this.lineChartLabels.push(ob.date.substr(0,16));
        this.dataLine1.push({x: ob.date.substr(0,16), y: parseFloat(ob.bid)});
        this.dataLine2.push({x: ob.date.substr(0,16), y: parseFloat(ob.shortValue.replace("%", ""))});
        i+=1;
        console.log(ob.bid);
        console.log(parseFloat(ob.shortValue.replace("%", "")));

      }
      this.lineChartData[0].data=this.dataLine1;
      this.lineChartData[1].data=this.dataLine2;
      console.log(this.dataLine1);
      console.log(this.dataLine2);
      // this.lineChartData = [
      //   {
      //     fill: true,
      //     label: 'Heat',
      //     pointHoverRadius: 5,
      //     pointHitRadius: 5,
      //     lineTension: 0,
      //     yAxisID: "y-axis-0",
      //     data:this.dataLine1
      //     ,
      //     // backgroundColor: "rgb(255,255,255)"
      //   }, {
      //     fill: true,
      //     pointHoverRadius: 5,
      //     pointHitRadius: 5,
      //     lineTension: 0,
      //     yAxisID: "y-axis-1",
      //     label: 'Mass',
      //     scaleFontColor: 'red',
      //     data: this.dataLine2
      //     // backgroundColor: "rgb(255,255,0)"
      //   }
      // ];

      console.log(this.lineChartData);
    });
  }

  buttonClick(days: any) {
    let value = this.currency;
    this.getChartData(days, value);
  }

  changeImage() {
    let url = (<HTMLSelectElement>document.getElementById('url')).value;
    this.localSt.store(this.currency+"_IMG",this.img);
    (<HTMLSelectElement>document.getElementById('image')).setAttribute("src", url);
  }
}
