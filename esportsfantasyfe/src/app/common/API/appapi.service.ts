import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable, catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { ErrorModel } from '../error/esf-error/ErrorModel';

@Injectable({
  providedIn: 'root'
})
export class AppapiService {

  private error :ErrorModel;

  constructor(
    private api: ApiService,
    private router: Router
  ) { }


  // --- KO MANAGEMENT ---

  public getError(): ErrorModel {
    return this.error;
  }




  public sendToErrorPage(error :any) {
    if(error != null) 
      this.error = new ErrorModel(error.status, error.error.appStatus, error.error.description);
    else
      this.error = new ErrorModel(404, "404", "Unknown error");
    
      this.router.navigate(['/error']);
  }

  // --- API CALLS ---

  // --- GAMES ---

  public getGamesFromAPI(): Observable<string[]> {
    return this.api.sendRequest('games/getGames', {}).pipe(
      map(response => response.games),
      catchError(error => {
        
        this.sendToErrorPage(error);
        return of([]);
        
      })
    );
  }

  public getGameIcon(game :String): Observable<string> {
    return new Observable<string>(observer => {
      const reader = new FileReader();

      this.api.sendBlobRequest("games/getGameIcon", game).subscribe(response => {
        reader.readAsDataURL(response);
        reader.onload = () => {
          observer.next(reader.result.toString());
          observer.complete();
        }
      }, error => {
        observer.next(null);
        observer.complete();
        this.sendToErrorPage(null);
      });
    });
  }

  // --- LEAGUES (Players) ---

  public getGameRLeagues(game: string): Observable<any> {
    return this.api.sendRequest("realLeague/getGameRLeagues", game).pipe(
      map(response => response.leagues),
      catchError(error => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getLeagueIcon(uuid :String): Observable<string> {
    return new Observable<string>(observer => {
      const reader = new FileReader();

      this.api.sendBlobRequest("realLeague/getRLeagueIcon", uuid).subscribe(response => {
        reader.readAsDataURL(response);
        reader.onload = () => {
          observer.next(reader.result.toString());
          observer.complete();
        }
      }, error => {
        observer.next(null);
        observer.complete();
        this.sendToErrorPage(null);
      });
    });
  }

  //Leagues (Users)

  public joinLeague(leagueData :any) {

    this.api.sendRequest("League/joinLeague", leagueData).subscribe(response => {
      
      this.router.navigate(['/home']);
      
    }, error => {
      this.sendToErrorPage(error);
    });
  }

  // --- USER ---

  public getUserPfp(userMail :String): Observable<string> {
    return new Observable<string>(observer => {
      const reader = new FileReader();

      this.api.sendBlobRequest("user/getPfp", {mail: userMail}).subscribe(response => {
        reader.readAsDataURL(response);
        reader.onload = () => {
          observer.next(reader.result.toString());
          observer.complete();
        }
      }, error => {
        
        observer.next(null);
        observer.complete();
        this.sendToErrorPage(null);
      });
    });
  }  

}
