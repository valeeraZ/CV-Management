import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Grow from '@material-ui/core/Grow';
import Copyright from '../components/Copyright';
import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';
import { useTranslation } from 'react-i18next';

const useStyles = makeStyles((theme) => ({
    root: {
        //backgroundImage: 'url(https://source.unsplash.com/random)',
        //backgroundRepeat: 'no-repeat',
        display: 'flex',
        flexDirection: 'column',
        minHeight: '100vh',
    },
    main: {
        marginTop: theme.spacing(8),
        marginBottom: theme.spacing(2),
    },
    footer: {
        padding: theme.spacing(3, 2),
        marginTop: 'auto',
    }
}))

export default function IntroPage() {
    const classes = useStyles();
    // eslint-disable-next-line
    const { t } = useTranslation();
    return (
        <div className={classes.root}>
            <CssBaseline />
            <Container component="main" className={classes.main} maxWidth="sm">
                <Grow in>
                    <Typography variant="h2">Chat</Typography>
                </Grow>
                <Grow in>
                    <Typography paragraph>A simple broadcast chat room using Spring Boot</Typography>
                </Grow>
                <Grow in style={{ transformOrigin: '0 0 0' }} {...({ timeout: 1000 })}>
                    <div>
                        <Grid container spacing={2}>
                            <Grid item>
                                <Link href="login" variant="h6" color="textPrimary">
                                    {t('sign-in')}
                                </Link>
                            </Grid>
                            <Grid item>
                                <Link href="register" variant="h6" color="textPrimary">
                                    {t('sign-up')}
                                </Link>
                            </Grid>
                        </Grid>
                    </div>
                </Grow>
            </Container>
            <footer className={classes.footer}>
                <Container maxWidth="sm">
                    <Copyright />
                </Container>
            </footer>
        </div>
    );
}
