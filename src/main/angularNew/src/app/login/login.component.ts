import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    let user = (<HTMLInputElement>document.getElementById('user')).value;
    let pass = (<HTMLInputElement>document.getElementById('pass')).value;

    if (user === "admin" && pass === "akva") {
      this.router.navigateByUrl('/main');
    } else {
      alert("invalid user name or password.")
    }
  }

}
