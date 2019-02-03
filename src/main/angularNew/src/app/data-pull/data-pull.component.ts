import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SETTING} from "../SETTING";

@Component({
  selector: 'app-data-pull',
  templateUrl: './data-pull.component.html',
  styleUrls: ['./data-pull.component.scss']
})
export class DataPullComponent implements OnInit {

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  exportCSV() {
    let from = (<HTMLInputElement>document.getElementById('from')).value;
    let to = (<HTMLInputElement>document.getElementById('to')).value;
    window.open(SETTING.HTTP+'/api/scrape/exportCSV/' + from + '/' + to);
  }

}

