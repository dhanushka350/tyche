import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StreamService {

  private currencyValue: BehaviorSubject<String> = new BehaviorSubject("EURUSD");

  constructor() {
  }

  public setCurrency(value: string) {
    console.log(value + " - service");
    this.currencyValue.next(value);
  }

  public getCurrency(): Observable<any> {
    return this.currencyValue.asObservable();
  }
}
