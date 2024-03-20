import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { logregResponse } from './logregResponse';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  api_url = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  sendRequest(type: string, data: any): Observable<logregResponse> {
    const url = this.api_url + 'user' + '/' + type;
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.post<logregResponse>(url, data, { headers });
    
  }
}
