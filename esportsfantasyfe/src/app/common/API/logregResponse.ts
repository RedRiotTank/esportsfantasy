/**
 * Interface for the response of the login and register API.
 */
export interface logregResponse{
    result: string;
    status: string;
    message: string;
    token: string;
    appStatus: string;
}