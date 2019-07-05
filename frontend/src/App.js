import React, {Component} from 'react';
import './App.css';
import MaybeLeft from './component/MaybeLeft';
import MaybeRight from "./component/MaybeRight";
import {BrowserRouter} from "react-router-dom";
import { Nav, Navbar, NavItem ,NavDropdown, Form, FormControl, Button} from 'react-bootstrap';

class App extends Component {


    updateLeft = () => {
        console.log()
    }

    render() {
        return (
            <div className="container" style={{textAlign: 'center'}}>

                <BrowserRouter>

                    <h1>Restaurant Application</h1>
                    <div className="baseComp" id="leftComp" style={{margin: '5'}}>
                        <MaybeLeft/>
                    </div>
                    <div className="baseComp" id="rightComp" style={{margin: '5'}}>
                        <MaybeRight/>
                    </div>

                </BrowserRouter>
            </div>

        );
    }
}

export default App;