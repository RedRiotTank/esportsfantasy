import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { credentialsResponse } from '../../credentials/credentialsResponse';

/**
 * This service is responsible for managing the API requests of the application.
 * It provides the methods to send requests to the server.
 * @author Alberto Plaza Montes.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  api_url = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  /**
   * This method sends a request to the server.
   * @param type the type of the request.
   * @param data the data of the request.
   * @returns the response of the request.
   */
  public sendRequest(type: string, data: any): Observable<credentialsResponse> {
    const url = this.api_url + type;
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.post<credentialsResponse>(url, data, { headers });
    
  }
}
