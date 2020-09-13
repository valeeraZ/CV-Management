import {URL} from '../_constants/config';
import {handleResponse} from '../_utils/ErrorResponseHandler';

export const userService = {
    register
}

function register(username, name, password, email){
    const request = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, name, password, email})
    };

    return fetch(`${URL}/api/user/register`, request)
        .then(handleResponse);
}
