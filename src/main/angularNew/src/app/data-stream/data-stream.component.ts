import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {model} from "../dto/model";
import {StreamService} from "../service/stream.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-data-stream',
  templateUrl: './data-stream.component.html',
  styleUrls: ['./data-stream.component.scss']
})
export class DataStreamComponent implements OnInit {

  public rows: any[];
  currency: any;
  subscription: Subscription;

  constructor(private http: HttpClient, private Stream: StreamService) {
    this.getCurrencyData();
  }

  ngOnInit() {
    this.getTableData(5, "EURUSD").subscribe(rs => {
      this.rows = rs["data"];
    });
  }


  getTableData(days: any, currency: any) {
    return this.http.get<model[]>('http://204.48.30.126:8080/api/scrape/getChartData/' + currency + '/' + days);
  }

  getCurrencyData() {
    this.subscription = this.Stream.getCurrency().subscribe(currency => {
      this.currency = currency;
      console.log("getting data for -" + this.currency);

      this.getTableData(5, this.currency).subscribe(re => {
        this.rows = re["data"];
      });

    });
  }

}
