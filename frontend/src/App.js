import React, {Component} from 'react';
import './App.css';
import {BrowserRouter} from "react-router-dom";
import SuperComponent from "./component/SuperComponent";

class App extends Component {

    render() {
        return (
            <div className="container" style={{textAlign: 'center'}}>
                <BrowserRouter>
                    <div className="superComp" style={{margin: '5'}}>
                        <SuperComponent/>
                    </div>
                </BrowserRouter>
            </div>

        );
    }
}

export default App;