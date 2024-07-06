import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable, catchError, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { ErrorModel } from '../error/esf-error/ErrorModel';

@Injectable({
  providedIn: 'root',
})
export class AppapiService {
  private error: ErrorModel;

  constructor(private api: ApiService, private router: Router) {}

  // --- KO MANAGEMENT ---

  public getError(): ErrorModel {
    return this.error;
  }

  public sendToErrorPage(error: any) {
    if (error != null)
      this.error = new ErrorModel(
        error.status,
        error.error.appStatus,
        error.error.description
      );
    else this.error = new ErrorModel(404, '404', 'Unknown error');

    this.router.navigate(['/error']);
  }

  // --- API CALLS ---

  //Match schedule

  public getMatchSchedule(leagueuuid: string): Observable<any> {
    console.log('leagueuuid: ' + leagueuuid);
    return this.api.sendRequest('events/getEvents', leagueuuid).pipe(
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  // --- GAMES ---

  public getGamesFromAPI(): Observable<string[]> {
    return this.api.sendRequest('games/getGames', {}).pipe(
      map((response) => response.games),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getGameIcon(game: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('games/getGameIcon', game).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  // --- LEAGUES (Players) ---

  public getGameRLeagues(game: string): Observable<any> {
    return this.api.sendRequest('realLeague/getGameRLeagues', game).pipe(
      map((response) => response.leagues),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getRLeagueIcon(uuid: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('realLeague/getRLeagueIcon', uuid).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  //Leagues (Users)

  public joinLeague(leagueData: any) {
    this.api.sendRequest('League/joinLeague', leagueData).subscribe(
      (response) => {
        this.router.navigate(['/home']);
      },
      (error) => {
        this.sendToErrorPage(error);
      }
    );
  }

  public getUserLeagues(mail: string): Observable<any> {
    return this.api.sendRequest('League/getUserLeagues', mail).pipe(
      map((response) => response.leagues),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getLeagueIcon(uuid: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('League/getLeagueIcon', uuid).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  public getMarketPlayers(leagueUUID: string): Observable<any> {
    return this.api.sendRequest('League/getMarketPlayers', leagueUUID).pipe(
      map((response) => response.players),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  // --- USER ---

  public getUserPfp(userMail: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('user/getPfp', { mail: userMail }).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  // --- TEAMS ---

  public getPlayerTeamIcon(
    playeruuid: String,
    leagueuuid: String
  ): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api
        .sendBlobRequest('Team/getPlayerTeamIcon', {
          playeruuid: playeruuid,
          leagueuuid: leagueuuid,
        })
        .subscribe(
          (response) => {
            reader.readAsDataURL(response);
            reader.onload = () => {
              observer.next(reader.result.toString());
              observer.complete();
            };
          },
          (error) => {
            observer.next(null);
            observer.complete();
            this.sendToErrorPage(null);
          }
        );
    });
  }

  public getTeamIcon(uuid: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('Team/getTeamIcon', uuid).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  public getUserTeamInfo(
    userUUID: string,
    leagueUUID: string
  ): Observable<any> {
    return this.api
      .sendRequest('UserXLeagueXPlayer/TeamInfo', {
        useruuid: userUUID,
        leagueuuid: leagueUUID,
      })
      .pipe(
        map((response) => response.players),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public setAlignment(
    userUUID: string,
    leagueUUID: string,
    playerUUID: string,
    alignment: number
  ): Observable<any> {
    //console.log("alignment: " + alignment);
    return this.api
      .sendRequest('UserXLeagueXPlayer/SetAligned', {
        useruuid: userUUID,
        leagueuuid: leagueUUID,
        playeruuid: playerUUID,
        aligned: alignment,
      })
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  // --- PLAYERS ---

  public getPlayerIcon(uuid: String): Observable<string> {
    return new Observable<string>((observer) => {
      const reader = new FileReader();

      this.api.sendBlobRequest('Player/getPlayerIcon', uuid).subscribe(
        (response) => {
          reader.readAsDataURL(response);
          reader.onload = () => {
            observer.next(reader.result.toString());
            observer.complete();
          };
        },
        (error) => {
          observer.next(null);
          observer.complete();
          this.sendToErrorPage(null);
        }
      );
    });
  }

  // --- MARKET ---
  public bidPlayer(
    playerUUID: String,
    leagueUUID: String,
    userUUID: String,
    value: number
  ): Observable<any> {
    return this.api
      .sendRequest('Market/bidup', {
        playeruuid: playerUUID,
        leagueuuid: leagueUUID,
        useruuid: userUUID,
        value: value,
      })
      .pipe(
        map((response) => response.players),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }
}
