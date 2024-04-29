import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../../API/appapi.service';
import { ErrorModel } from './ErrorModel';

/**
 * This component is responsible for managing the error page of the application.
 * It shows a message to the user when an error occurs.
 * @author Alberto Plaza Montes.
 */
@Component({
  selector: 'app-esf-error',
  templateUrl: './esf-error.component.html',
  styleUrl: './esf-error.component.scss'
})
export class EsfErrorComponent implements OnInit {

  error :ErrorModel = new ErrorModel(404, "404", "Unknown error")

  constructor(
    private appApi: AppapiService
  ) { }
  
  ngOnInit(): void {
    var e  = this.appApi.getError();

    if(e != null) this.error = e;
    
  }
}
