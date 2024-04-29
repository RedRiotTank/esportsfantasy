export class ErrorModel{

    private httpStatus: number = 404;
    private appStatus: string = "404";
    private errorDescription: string = "Unknown error";

    constructor(httpStatus: number, errorCode: string, errorDescription: string){
        this.httpStatus = httpStatus;
        this.appStatus = errorCode;
        this.errorDescription = errorDescription;
    }

    public getHttpStatus(): number {
        return this.httpStatus;
    }

    public getErrorCode(): string {
        return this.appStatus;
    }

    public getErrorDescription(): string {
        return this.errorDescription;
    }

}