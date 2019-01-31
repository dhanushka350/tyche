import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {model} from "../dto/model";
import {BaseChartDirective} from "ng2-charts";
import {Subscription} from "rxjs";
import {StreamService} from "../service/stream.service";


@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.scss']
})
export class ChartsComponent implements OnInit {
  ar: Object;
  lk: model;
  longTermChart: string = 'EURUSD';
  currentSearch: string = 'EURUSD – 5 Days';
  currency: any = "EURUSD";
  subscription: Subscription;
  @ViewChild(BaseChartDirective)
  public chart: BaseChartDirective;

  constructor(private http: HttpClient, private stream: StreamService) {
    this.getCurrencyData();
  }

  getCurrencyData() {
    this.subscription = this.stream.getCurrency().subscribe(currency => {
      this.currency = currency;
      console.log("getting data for -" + this.currency);

    });
  }

  public dataLine1: Array<any>;
  public dataLine2: Array<any>;
  public lineChartLabels: Array<any>;

  ngOnInit() {
    this.dataLine1 = [];
    this.dataLine2 = [];
    this.lineChartLabels = [];
    this.getChartData(5, "EURUSD");
  }


  // lineChart
  public lineChartData: Array<any> = [
    {
      fill: true,
      label: 'Heat',
      pointHoverRadius: 5,
      pointHitRadius: 5,
      lineTension: 0,
      yAxisID: "y-axis-0",
      data: [],
      // backgroundColor: "rgb(255,255,255)"
    }, {
      fill: true,
      pointHoverRadius: 5,
      pointHitRadius: 5,
      lineTension: 0,
      yAxisID: "y-axis-1",
      label: 'Mass',
      data: [],
      // backgroundColor: "rgb(255,255,0)"
    }
  ];


  public lineChartOptions: any = {
    maintainAspectRatio: false,
    responsive: true,
    bezierCurveTension: 0,
    scales: {
      xAxes: [{
        display: true,
        ticks: {
          maxTicksLimit: 3,
          fontSize: 10
        }
      }],
      yAxes: [{
        position: "left",
        "id": "y-axis-0",
        display: true
      }, {
        position: "right",
        "id": "y-axis-1",
        display: true
      }]
    }
  };
  public lineChartColors: Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
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

    this.http.get('api/scrape/getChartData/' + currency + '/' + days).subscribe(data => {
      this.lineChartLabels.length = 0;
      console.log(data);
      for (let ob of data["data"]) {
        this.lineChartLabels.push(ob.date.split(".")[0]);
        this.dataLine1.push(ob.bid);
        this.dataLine2.push(ob.shortValue.replace("%", ""));
        console.log(ob.date.split(".")[0] + "-" + ob.bid + "-" + ob.ask);
        console.log(ob.date.split(".")[0] + "-" + ob.shortValue);
      }
      this.lineChartData = [
        {
          fill: false,
          label: 'Ratio',
          pointHoverRadius: 5,
          pointHitRadius: 5,
          lineTension: 0,
          yAxisID: "y-axis-0",
          data: this.dataLine1,

        }, {
          fill: false,
          pointHoverRadius: 5,
          pointHitRadius: 5,
          lineTension: 0,
          yAxisID: "y-axis-1",
          label: 'Short',
          data: this.dataLine2,

        }
      ];

      console.log(this.lineChartData);
    });
  }

  buttonClick(days: any) {
    let value = this.currency;
    this.getChartData(days, value);
  }

  changeImage() {
    let url = (<HTMLSelectElement>document.getElementById('url')).value;
    (<HTMLSelectElement>document.getElementById('image')).setAttribute("src", url);
  }
}
