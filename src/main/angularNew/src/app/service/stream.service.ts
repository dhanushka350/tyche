import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StreamService {

  private currencyValue: Subject<String> = new Subject();

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
