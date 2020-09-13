import {BehaviorSubject} from 'rxjs';
import {URL} from '../_constants/config';
import {handleResponse} from '../_utils/ErrorResponseHandler';

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

export const authenticationService = {
    login,
    logout,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue(){ return currentUserSubject.value}
};

function login(usernameOrEmail, password, rememberMe){
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({usernameOrEmail, password, rememberMe})
    }

    return fetch(`${URL}/api/auth/login`, requestOptions)
        .then(handleResponse)
        .then(user => {
            //store user details in local strorage
            localStorage.setItem('currentUser',JSON.stringify(user));
            currentUserSubject.next(user);
            return user;
        })
}

function logout(){
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    currentUserSubject.next(null);
}