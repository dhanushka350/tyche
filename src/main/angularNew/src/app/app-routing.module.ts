import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {ChartsComponent} from "./charts/charts.component";
import {LongerTermChartComponent} from "./longer-term-chart/longer-term-chart.component";
import {DataPullComponent} from "./data-pull/data-pull.component";
import {DataStreamComponent} from "./data-stream/data-stream.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'charts',
    component: ChartsComponent
  },
  {
    path: 'longerTermChart',
    component: LongerTermChartComponent
  },
  {
    path: 'dataPull',
    component: DataPullComponent
  },
  {
    path: 'dataStream',
    component: DataStreamComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
