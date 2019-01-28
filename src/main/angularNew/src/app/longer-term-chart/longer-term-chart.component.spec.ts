import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LongerTermChartComponent } from './longer-term-chart.component';

describe('LongerTermChartComponent', () => {
  let component: LongerTermChartComponent;
  let fixture: ComponentFixture<LongerTermChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LongerTermChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LongerTermChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
