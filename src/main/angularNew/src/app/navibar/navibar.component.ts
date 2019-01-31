import {Component, OnInit} from '@angular/core';
import {StreamService} from "../service/stream.service";

@Component({
  selector: 'app-navibar',
  templateUrl: './navibar.component.html',
  styleUrls: ['./navibar.component.scss']
})
export class NavibarComponent implements OnInit {

  constructor(private stream: StreamService) {
  }

  ngOnInit() {
  }

  setCurrency(value: any) {
    this.stream.setCurrency(value);
  }
}
