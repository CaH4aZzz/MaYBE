import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import CompWithOrderAndTables from "./CompWithOrderAndTables";
import Statistics from "./Statistics";
import ComponentList from "./ComponentList";
import NaviBar from "./NaviBar";

class SuperComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="superComponent">
                <div style={{border: "black 1px solid"}}>
                    <Route path="/"
                           render={props => <NaviBar {...props} />}/>
                </div>
                <div style={{border: "black 1px solid"}}>
                    <Switch>
                        <Route path="/" exact component={CompWithOrderAndTables}/>
                        <Route path="/orders/:id" exact component={CompWithOrderAndTables}/>}/>
                        <Route path="/components" exact component={ComponentList}/>
                        <Route path="/statistics" exact component={Statistics}/>
                    </Switch>
                </div>
            </div>
        )
    }
}

export default SuperComponent