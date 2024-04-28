import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable, catchError, map, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppapiService {

  constructor(
    private api: ApiService
  ) { }


  public joinLeague(leagueData :any): Observable<any> {
    return this.api.sendRequest("League/joinLeague", leagueData);
  }

  public getCompetitions(game: string): Observable<any> {
    return this.api.sendRequest("realLeague/getGameRLeagues", game);
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
        console.log("error");
        observer.next(null);
        observer.complete();
      });
    });
  }



  public getGamesFromAPI(): Observable<string[]> {
    return this.api.sendRequest('games/getGames', {}).pipe(
      map(response => response.games),
      catchError(error => {
        console.log('Error al obtener los juegos:', error);
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
        console.log("error");
        observer.next(null);
        observer.complete();
      });
    });
  }





  getPlayerLeagues(){

    this.api.sendRequest("leagues/getPlayerLeagues",{}).subscribe(response =>{
      console.log(response);
    }, error => {
      console.log("error");
      
    });

  }


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
        console.log("error");
        observer.next(null);
        observer.complete();
      });
    });
  }

}
