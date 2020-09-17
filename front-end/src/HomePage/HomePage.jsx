import React from 'react';
import { authenticationService } from '../_services/authentication.service';
import { useTranslation } from 'react-i18next';

export default function HomePage(props) {

    const currentUser = authenticationService.currentUserValue
    // eslint-disable-next-line
    const { t, i18n } = useTranslation();


    function logout() {
        authenticationService.logout();
        props.history.push("/login");
    }

    return (

        <div>
            <h1>Hi, {currentUser.name}, @{currentUser.username}</h1>
            <button onClick={logout}>{t('log-out')}</button>
        </div>

    )

}