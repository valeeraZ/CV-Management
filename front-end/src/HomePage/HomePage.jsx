import React from 'react';
import axios from '../axios-init';

export default class HomePage extends React.Component  {

    state = {};

    componentDidMount(){
        this.hello();
    }

    hello = () => {
        /*fetch('/api/user/hello')
            .then(response => response.text())
            .then(message => {
                this.setState({ message: message });
            });*/
        const _this = this;
        axios.get("/api/user/hello").then(function(res){
           _this.setState({message : res.data})
        })
    };
    render(){
        return (
            <div>
                <h2>message from server: {this.state.message}</h2>
            </div>       
        )
    }
    
}