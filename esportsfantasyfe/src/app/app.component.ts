import { Component } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { CredentialsService } from './credentials/credentials.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'esportsfantasyfe';

  constructor(private router: Router, private credentialsService: CredentialsService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.credentialsService.updateLoginCredentials();
      }
    });
  }

}
