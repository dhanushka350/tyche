import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {NavibarComponent} from './navibar/navibar.component';
import {ChartsComponent} from './charts/charts.component';
import {LongerTermChartComponent} from './longer-term-chart/longer-term-chart.component';
import {DataPullComponent} from './data-pull/data-pull.component';
import {DataStreamComponent} from './data-stream/data-stream.component';
import {HttpClientModule} from "@angular/common/http";
import {ChartsModule} from "ng2-charts";
import {StreamService} from "./service/stream.service";
import { MainComponent } from './main/main.component';
import { ChartSmallComponent } from './chart-small/chart-small.component';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavibarComponent,
    ChartsComponent,
    LongerTermChartComponent,
    DataPullComponent,
    DataStreamComponent,
    MainComponent,
    ChartSmallComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ChartsModule,
    FormsModule,
    NgxWebstorageModule.forRoot(),
  ],
  providers: [StreamService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
