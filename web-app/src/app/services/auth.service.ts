import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { User } from '../model/model.user';
import 'rxjs/add/operator/map';
import { AppComponent } from '../app.component';
@Injectable()
export class AuthService {
    constructor(public http: Http) { }

    public logIn(user: User) {

        const headers = new Headers();
        headers.append('Accept', 'application/json');
        // creating base64 encoded String from user name and password
        const base64Credential: string = btoa(user.username + ':' + user.password);
        headers.append('Authorization', 'Basic ' + base64Credential);

        const options = new RequestOptions();
        options.headers = headers;

        return this.http.get(AppComponent.API_URL + '/account/login', options)
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                const userResponse = response.json().principal; // the returned user object is a principal object
                if (userResponse) {
                    // store user details  in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(userResponse));
                }
            });
    }

    logOut() {
        // remove user from local storage to log user out
        return this.http.post(AppComponent.API_URL + 'logout', {})
            .map((response: Response) => {
                localStorage.removeItem('currentUser');
            });

    }
}
