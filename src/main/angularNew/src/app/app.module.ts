import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavibarComponent } from './navibar/navibar.component';
import { ChartsComponent } from './charts/charts.component';
import { LongerTermChartComponent } from './longer-term-chart/longer-term-chart.component';
import { DataPullComponent } from './data-pull/data-pull.component';
import { DataStreamComponent } from './data-stream/data-stream.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavibarComponent,
    ChartsComponent,
    LongerTermChartComponent,
    DataPullComponent,
    DataStreamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
