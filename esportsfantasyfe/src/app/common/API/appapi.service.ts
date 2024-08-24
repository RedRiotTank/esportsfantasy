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

  public getRLeagueTotalJours(leagueuuid: string): Observable<any> {
    return this.api
      .sendRequest('realLeague/getRLeagueTotalJours', leagueuuid)
      .pipe(
        map((response) => response.totalJours),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getRLeagueCurrentJour(leagueuuid: string): Observable<any> {
    return this.api
      .sendRequest('realLeague/getRLeagueCurrentJour', leagueuuid)
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  //Leagues (Users)

  public joinLeague(leagueData: any): Observable<any> {
    return this.api.sendRequest('League/joinLeague', leagueData);
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

  public getMarketPlayers(
    leagueUUID: string,
    userUUID: string
  ): Observable<any> {
    return this.api
      .sendRequest('League/getMarketPlayers', {
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
        map((response) => response),
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

  public getPlayerInfo(
    playerUuid: String,
    leagueUuid: String
  ): Observable<any> {
    return this.api
      .sendRequest('Player/getPlayerInfo', {
        playeruuid: playerUuid,
        leagueuuid: leagueUuid,
      })
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getTeamInfo(teamUuid: string): Observable<any> {
    return this.api.sendRequest('Team/getTeamInfo', teamUuid).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
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

  public sellPlayer(
    playerUUID: String,
    leagueUUID: String,
    userUUID: String,
    value: number
  ): Observable<any> {
    return this.api.sendRequest('Market/sell', {
      playeruuid: playerUUID,
      leagueuuid: leagueUUID,
      useruuid: userUUID,
      value: value,
    });
  }

  public cancelSell(
    playerUUID: String,
    leagueUUID: String,
    userUUID: String
  ): Observable<any> {
    return this.api.sendRequest('Market/cancelSell', {
      playeruuid: playerUUID,
      leagueuuid: leagueUUID,
      useruuid: userUUID,
    });
  }

  public getOffers(leagueUUID: string, userUUID: string): Observable<any> {
    return this.api
      .sendRequest('Market/getOffers', {
        leagueuuid: leagueUUID,
        useruuid: userUUID,
      })
      .pipe(
        map((response) => response.offers),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public acceptOffer(offer: any): Observable<any> {
    return this.api.sendRequest('Market/acceptOffer', offer);
  }

  public rejectOffer(offer: any): Observable<any> {
    return this.api.sendRequest('Market/rejectOffer', offer);
  }

  public clausePlayer(
    playerUUID: String,
    leagueUUID: String,
    userUUID: String,
    newUserUUID: String,
    jour: number,
    value: number
  ): Observable<any> {
    return this.api.sendRequest('Market/clausePlayer', {
      playeruuid: playerUUID,
      leagueuuid: leagueUUID,
      useruuid: userUUID,
      biduseruuid: newUserUUID,
      jour: jour,
      value: value,
    });
  }

  public increaseClause(
    playerUUID: String,
    leagueUUID: String,
    userUUID: String,
    increaseType: number
  ): Observable<any> {
    return this.api.sendRequest('Market/increaseClause', {
      playerUuid: playerUUID,
      leagueUuid: leagueUUID,
      userUuid: userUUID,
      increaseType: increaseType,
    });
  }

  public getRanking(leagueUUID: string, jour: number): Observable<any> {
    return this.api
      .sendRequest('League/getRanking', { leagueUuid: leagueUUID, jour: jour })
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getLeagueTransferPosts(leagueUUID: string): Observable<any> {
    return this.api
      .sendRequest('TransferPost/getLeagueTransferPosts', leagueUUID)
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getCloseEvent(rleagueuuid: string): Observable<any> {
    return this.api.sendRequest('events/getCloseEvent', rleagueuuid).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getUserInfo(userUUID: string): Observable<any> {
    return this.api.sendRequest('user/getUserInfo', userUUID).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public updateUserInfo(
    uuid: string,
    mail: string,
    username: string,
    icon: string,
    password: string
  ): Observable<any> {
    return this.api.sendRequest('user/updateUserInfo', {
      uuid: uuid,
      mail: mail,
      username: username,
      icon: icon,
      password: password,
    });
  }

  public cancelBid(
    playerUUID: string,
    leagueUUID: string,
    userUUID: string
  ): Observable<any> {
    return this.api
      .sendRequest('Market/cancelBidup', {
        playeruuid: playerUUID,
        leagueuuid: leagueUUID,
        useruuid: userUUID,
      })
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getAllPlayers(leagueuuid: string) {
    return this.api.sendRequest('League/getAllPlayers', leagueuuid).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public leaveLeague(leagueuuid: string, useruuid: string) {
    return this.api
      .sendRequest('League/leaveLeague', {
        leagueuuid: leagueuuid,
        useruuid: useruuid,
      })
      .pipe(
        map((response) => response),
        catchError((error) => {
          this.sendToErrorPage(error);
          return of([]);
        })
      );
  }

  public getRLeagueTeams(leagueuuid: string) {
    return this.api.sendRequest('realLeague/getRLeagueTeams', leagueuuid).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }

  public getInvCodes(useruuid: string) {
    return this.api.sendRequest('League/getUserLeaguesInvCodes', useruuid).pipe(
      map((response) => response),
      catchError((error) => {
        this.sendToErrorPage(error);
        return of([]);
      })
    );
  }
}
